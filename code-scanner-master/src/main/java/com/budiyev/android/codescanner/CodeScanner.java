/*
 * MIT License
 *
 * Copyright (c) 2017 Yuriy Budiyev [yuriy.budiyev@yandex.ru]
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.budiyev.android.codescanner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.hardware.Camera;
import android.os.Handler;
import android.os.Process;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.SurfaceHolder;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.android.camera.CameraConfigurationUtils;

/**
 * Code scanner.
 * Supports portrait and landscape screen orientations, back and front facing cameras,
 * auto focus and flash light control, viewfinder customization.
 *
 * @see CodeScannerView
 * @see BarcodeFormat
 */
public final class CodeScanner {

    /**
     * All supported barcode formats
     */
    public static final List<BarcodeFormat> ALL_FORMATS =
            Collections.unmodifiableList(Arrays.asList(BarcodeFormat.values()));

    /**
     * One dimensional barcode formats
     */
    public static final List<BarcodeFormat> ONE_DIMENSIONAL_FORMATS = Collections.unmodifiableList(
            Arrays.asList(BarcodeFormat.CODABAR, BarcodeFormat.CODE_39, BarcodeFormat.CODE_93, BarcodeFormat.CODE_128,
                    BarcodeFormat.EAN_8, BarcodeFormat.EAN_13, BarcodeFormat.ITF, BarcodeFormat.RSS_14,
                    BarcodeFormat.RSS_EXPANDED, BarcodeFormat.UPC_A, BarcodeFormat.UPC_E,
                    BarcodeFormat.UPC_EAN_EXTENSION));

    /**
     * Two dimensional barcode formats
     */
    public static final List<BarcodeFormat> TWO_DIMENSIONAL_FORMATS = Collections.unmodifiableList(
            Arrays.asList(BarcodeFormat.AZTEC, BarcodeFormat.DATA_MATRIX, BarcodeFormat.MAXICODE, BarcodeFormat.PDF_417,
                    BarcodeFormat.QR_CODE));

    /**
     * First back-facing camera
     */
    public static final int CAMERA_BACK = -1;

    /**
     * First front-facing camera
     */
    public static final int CAMERA_FRONT = -2;

    private static final List<BarcodeFormat> DEFAULT_FORMATS = ALL_FORMATS;
    private static final ScanMode DEFAULT_SCAN_MODE = ScanMode.SINGLE;
    private static final AutoFocusMode DEFAULT_AUTO_FOCUS_MODE = AutoFocusMode.SAFE;
    private static final boolean DEFAULT_AUTO_FOCUS_ENABLED = true;
    private static final boolean DEFAULT_FLASH_ENABLED = false;
    private static final long DEFAULT_SAFE_AUTO_FOCUS_INTERVAL = 2000L;
    private static final int SAFE_AUTO_FOCUS_ATTEMPTS_THRESHOLD = 2;
    private final Object mInitializeLock = new Object();
    private final Context mContext;
    private final Handler mMainThreadHandler;
    private final CodeScannerView mScannerView;
    private final SurfaceHolder mSurfaceHolder;
    private final SurfaceHolder.Callback mSurfaceCallback;
    private final Camera.PreviewCallback mPreviewCallback;
    private final Camera.AutoFocusCallback mSafeAutoFocusCallback;
    private final Runnable mSafeAutoFocusTask;
    private final Runnable mStopPreviewTask;
    private final DecoderStateListener mDecoderStateListener;
    private volatile List<BarcodeFormat> mFormats = DEFAULT_FORMATS;
    private volatile ScanMode mScanMode = DEFAULT_SCAN_MODE;
    private volatile AutoFocusMode mAutoFocusMode = DEFAULT_AUTO_FOCUS_MODE;
    private volatile DecodeCallback mDecodeCallback;
    private volatile ErrorCallback mErrorCallback;
    private volatile DecoderWrapper mDecoderWrapper;
    private volatile boolean mInitialization;
    private volatile boolean mInitialized;
    private volatile boolean mStoppingPreview;
    private volatile boolean mAutoFocusEnabled = DEFAULT_AUTO_FOCUS_ENABLED;
    private volatile boolean mFlashEnabled = DEFAULT_FLASH_ENABLED;
    private volatile long mSafeAutoFocusInterval = DEFAULT_SAFE_AUTO_FOCUS_INTERVAL;
    private volatile int mCameraId = CAMERA_BACK;
    private boolean mPreviewActive;
    private boolean mSafeAutoFocusing;
    private boolean mSafeAutoFocusTaskScheduled;
    private boolean mInitializationRequested;
    private int mSafeAutoFocusAttemptsCount;
    private int mViewWidth;
    private int mViewHeight;

    /**
     * CodeScanner, associated with the first back-facing camera on the device
     *
     * @param context Context
     * @param view    A view to display the preview
     * @see CodeScannerView
     */
    @MainThread
    public CodeScanner(@NonNull final Context context, @NonNull final CodeScannerView view) {
        mContext = context;
        mScannerView = view;
        mSurfaceHolder = view.getPreviewView().getHolder();
        mMainThreadHandler = new Handler();
        mSurfaceCallback = new SurfaceCallback();
        mPreviewCallback = new PreviewCallback();
        mSafeAutoFocusCallback = new SafeAutoFocusCallback();
        mSafeAutoFocusTask = new SafeAutoFocusTask();
        mStopPreviewTask = new StopPreviewTask();
        mDecoderStateListener = new DecoderStateListener();
        mScannerView.setCodeScanner(this);
        mScannerView.setLayoutListener(new ScannerLayoutListener());
    }

    /**
     * CodeScanner, associated with particular hardware camera
     *
     * @param context  Context
     * @param view     A view to display the preview
     * @param cameraId Camera id (between {@code 0} and
     *                 {@link Camera#getNumberOfCameras()} - {@code 1})
     *                 or {@link #CAMERA_BACK} or {@link #CAMERA_FRONT}
     * @see CodeScannerView
     */
    @MainThread
    public CodeScanner(@NonNull final Context context, @NonNull final CodeScannerView view, final int cameraId) {
        this(context, view);
        mCameraId = cameraId;
    }

    /**
     * Get current camera id, or {@link #CAMERA_BACK} or {@link #CAMERA_FRONT}
     *
     * @see #setCamera
     */
    public int getCamera() {
        return mCameraId;
    }

    /**
     * Camera to use
     *
     * @param cameraId Camera id (between {@code 0} and
     *                 {@link Camera#getNumberOfCameras()} - {@code 1})
     *                 or {@link #CAMERA_BACK} or {@link #CAMERA_FRONT}
     */
    @MainThread
    public void setCamera(final int cameraId) {
        synchronized (mInitializeLock) {
            if (mCameraId != cameraId) {
                mCameraId = cameraId;
                if (mInitialized) {
                    final boolean previewActive = mPreviewActive;
                    releaseResources();
                    if (previewActive) {
                        initialize();
                    }
                }
            }
        }
    }

    /**
     * Get current list of formats to decode
     *
     * @see #setFormats(List)
     */
    @NonNull
    public List<BarcodeFormat> getFormats() {
        return mFormats;
    }

    /**
     * Formats, decoder to react to ({@link #ALL_FORMATS} by default)
     *
     * @param formats Formats
     * @see BarcodeFormat
     * @see #ALL_FORMATS
     * @see #ONE_DIMENSIONAL_FORMATS
     * @see #TWO_DIMENSIONAL_FORMATS
     */
    @MainThread
    public void setFormats(@NonNull final List<BarcodeFormat> formats) {
        synchronized (mInitializeLock) {
            mFormats = Objects.requireNonNull(formats);
            if (mInitialized) {
                final DecoderWrapper decoderWrapper = mDecoderWrapper;
                if (decoderWrapper != null) {
                    decoderWrapper.getDecoder().setFormats(formats);
                }
            }
        }
    }

    /**
     * Get current decode callback
     *
     * @see #setDecodeCallback
     */
    @Nullable
    public DecodeCallback getDecodeCallback() {
        return mDecodeCallback;
    }

    /**
     * Callback of decoding process
     *
     * @param decodeCallback Callback
     * @see DecodeCallback
     */
    public void setDecodeCallback(@Nullable final DecodeCallback decodeCallback) {
        synchronized (mInitializeLock) {
            mDecodeCallback = decodeCallback;
            if (mInitialized) {
                final DecoderWrapper decoderWrapper = mDecoderWrapper;
                if (decoderWrapper != null) {
                    decoderWrapper.getDecoder().setCallback(decodeCallback);
                }
            }
        }
    }

    /**
     * Get current error callback
     *
     * @see #setErrorCallback
     */
    @Nullable
    public ErrorCallback getErrorCallback() {
        return mErrorCallback;
    }

    /**
     * Camera initialization error callback.
     * If not set, an exception will be thrown when error will occur.
     *
     * @param errorCallback Callback
     * @see ErrorCallback#SUPPRESS
     * @see ErrorCallback
     */
    public void setErrorCallback(@Nullable final ErrorCallback errorCallback) {
        mErrorCallback = errorCallback;
    }

    /**
     * Get current scan mode
     *
     * @see #setScanMode
     */
    @NonNull
    public ScanMode getScanMode() {
        return mScanMode;
    }

    /**
     * Scan mode, {@link ScanMode#SINGLE} by default
     *
     * @see ScanMode
     */
    public void setScanMode(@NonNull final ScanMode scanMode) {
        mScanMode = Objects.requireNonNull(scanMode);
    }

    /**
     * Whether if auto focus is currently enabled
     *
     * @see #setAutoFocusEnabled
     */
    public boolean isAutoFocusEnabled() {
        return mAutoFocusEnabled;
    }

    /**
     * Whether to enable or disable auto focus if it's supported, {@code true} by default
     */
    @MainThread
    public void setAutoFocusEnabled(final boolean autoFocusEnabled) {
        synchronized (mInitializeLock) {
            final boolean changed = mAutoFocusEnabled != autoFocusEnabled;
            mAutoFocusEnabled = autoFocusEnabled;
            mScannerView.setAutoFocusEnabled(autoFocusEnabled);
            final DecoderWrapper decoderWrapper = mDecoderWrapper;
            if (mInitialized && mPreviewActive && changed && decoderWrapper != null &&
                    decoderWrapper.isAutoFocusSupported()) {
                setAutoFocusEnabledInternal(autoFocusEnabled);
            }
        }
    }

    /**
     * Get current auto focus mode
     *
     * @see #setAutoFocusMode
     */
    @NonNull
    public AutoFocusMode getAutoFocusMode() {
        return mAutoFocusMode;
    }

    /**
     * Auto focus mode, {@link AutoFocusMode#SAFE} by default
     *
     * @see AutoFocusMode
     */
    @MainThread
    public void setAutoFocusMode(@NonNull final AutoFocusMode autoFocusMode) {
        synchronized (mInitializeLock) {
            mAutoFocusMode = Objects.requireNonNull(autoFocusMode);
            if (mInitialized && mAutoFocusEnabled) {
                setAutoFocusEnabledInternal(true);
            }
        }
    }

    /**
     * Auto focus interval in milliseconds for {@link AutoFocusMode#SAFE} mode, 2000 by default
     *
     * @see #setAutoFocusMode
     */
    public void setAutoFocusInterval(final long autoFocusInterval) {
        mSafeAutoFocusInterval = autoFocusInterval;
    }

    /**
     * Whether if flash light is currently enabled
     */
    public boolean isFlashEnabled() {
        return mFlashEnabled;
    }

    /**
     * Whether to enable or disable flash light if it's supported, {@code false} by default
     */
    @MainThread
    public void setFlashEnabled(final boolean flashEnabled) {
        synchronized (mInitializeLock) {
            final boolean changed = mFlashEnabled != flashEnabled;
            mFlashEnabled = flashEnabled;
            mScannerView.setFlashEnabled(flashEnabled);
            final DecoderWrapper decoderWrapper = mDecoderWrapper;
            if (mInitialized && mPreviewActive && changed && decoderWrapper != null &&
                    decoderWrapper.isFlashSupported()) {
                setFlashEnabledInternal(flashEnabled);
            }
        }
    }

    /**
     * Whether if preview is active
     */
    public boolean isPreviewActive() {
        return mPreviewActive;
    }

    /**
     * Start camera preview
     * <br>
     * Requires {@link Manifest.permission#CAMERA} permission
     */
    @MainThread
    public void startPreview() {
        synchronized (mInitializeLock) {
            if (!mInitialized && !mInitialization) {
                initialize();
                return;
            }
        }
        if (!mPreviewActive) {
            mSurfaceHolder.addCallback(mSurfaceCallback);
            startPreviewInternal(false);
        }
    }

    /**
     * Stop camera preview
     */
    @MainThread
    public void stopPreview() {
        if (mInitialized && mPreviewActive) {
            mSurfaceHolder.removeCallback(mSurfaceCallback);
            stopPreviewInternal(false);
        }
    }

    /**
     * Release resources, and stop preview if needed; call this method in {@link Activity#onPause()}
     */
    @MainThread
    public void releaseResources() {
        if (mInitialized) {
            if (mPreviewActive) {
                stopPreview();
            }
            releaseResourcesInternal();
        }
    }

    private void initialize() {
        initialize(mScannerView.getWidth(), mScannerView.getHeight());
    }

    private void initialize(final int width, final int height) {
        mViewWidth = width;
        mViewHeight = height;
        if (width > 0 && height > 0) {
            mInitialization = true;
            mInitializationRequested = false;
            new InitializationThread(width, height).start();
        } else {
            mInitializationRequested = true;
        }
    }

    private void startPreviewInternal(final boolean internal) {
        try {
            final DecoderWrapper decoderWrapper = mDecoderWrapper;
            if (decoderWrapper != null) {
                final Camera camera = decoderWrapper.getCamera();
                camera.setPreviewCallback(mPreviewCallback);
                camera.setPreviewDisplay(mSurfaceHolder);
                if (!internal && decoderWrapper.isFlashSupported() && mFlashEnabled) {
                    setFlashEnabledInternal(true);
                }
                camera.startPreview();
                mStoppingPreview = false;
                mPreviewActive = true;
                mSafeAutoFocusing = false;
                mSafeAutoFocusAttemptsCount = 0;
                if (mAutoFocusMode == AutoFocusMode.SAFE) {
                    scheduleSafeAutoFocusTask();
                }
            }
        } catch (final Exception ignored) {
        }
    }

    private void startPreviewInternalSafe() {
        if (mInitialized && !mPreviewActive) {
            startPreviewInternal(true);
        }
    }

    private void stopPreviewInternal(final boolean internal) {
        try {
            final DecoderWrapper decoderWrapper = mDecoderWrapper;
            if (decoderWrapper != null) {
                final Camera camera = decoderWrapper.getCamera();
                if (!internal && decoderWrapper.isFlashSupported() && mFlashEnabled) {
                    final Camera.Parameters parameters = camera.getParameters();
                    if (parameters != null && Utils.setFlashMode(parameters, Camera.Parameters.FLASH_MODE_OFF)) {
                        camera.setParameters(parameters);
                    }
                }
                camera.setPreviewCallback(null);
                camera.stopPreview();
            }
        } catch (final Exception ignored) {
        }
        mStoppingPreview = false;
        mPreviewActive = false;
        mSafeAutoFocusing = false;
        mSafeAutoFocusAttemptsCount = 0;
    }

    private void stopPreviewInternalSafe() {
        if (mInitialized && mPreviewActive) {
            stopPreviewInternal(true);
        }
    }

    private void releaseResourcesInternal() {
        mInitialized = false;
        mInitialization = false;
        mStoppingPreview = false;
        mPreviewActive = false;
        mSafeAutoFocusing = false;
        final DecoderWrapper decoderWrapper = mDecoderWrapper;
        if (decoderWrapper != null) {
            mDecoderWrapper = null;
            decoderWrapper.release();
        }
    }

    private void setFlashEnabledInternal(final boolean flashEnabled) {
        try {
            final DecoderWrapper decoderWrapper = mDecoderWrapper;
            if (decoderWrapper != null) {
                final Camera camera = decoderWrapper.getCamera();
                final Camera.Parameters parameters = camera.getParameters();
                if (parameters == null) {
                    return;
                }
                final boolean changed;
                if (flashEnabled) {
                    changed = Utils.setFlashMode(parameters, Camera.Parameters.FLASH_MODE_TORCH);
                } else {
                    changed = Utils.setFlashMode(parameters, Camera.Parameters.FLASH_MODE_OFF);
                }
                if (changed) {
                    CameraConfigurationUtils.setBestExposure(parameters, flashEnabled);
                    camera.setParameters(parameters);
                }
            }
        } catch (final Exception ignored) {
        }
    }

    private void setAutoFocusEnabledInternal(final boolean autoFocusEnabled) {
        try {
            final DecoderWrapper decoderWrapper = mDecoderWrapper;
            if (decoderWrapper != null) {
                final Camera camera = decoderWrapper.getCamera();
                final Camera.Parameters parameters = camera.getParameters();
                if (parameters == null) {
                    return;
                }
                final boolean changed;
                final AutoFocusMode autoFocusMode = mAutoFocusMode;
                if (autoFocusEnabled) {
                    changed = Utils.setAutoFocusMode(parameters, autoFocusMode);
                } else {
                    camera.cancelAutoFocus();
                    changed = Utils.disableAutoFocus(parameters);
                }
                if (changed) {
                    camera.setParameters(parameters);
                }
                if (autoFocusEnabled) {
                    mSafeAutoFocusAttemptsCount = 0;
                    mSafeAutoFocusing = false;
                    if (autoFocusMode == AutoFocusMode.SAFE) {
                        scheduleSafeAutoFocusTask();
                    }
                }
            }
        } catch (final Exception ignored) {
        }
    }

    private void safeAutoFocusCamera() {
        if (!mInitialized || !mPreviewActive) {
            return;
        }
        final DecoderWrapper decoderWrapper = mDecoderWrapper;
        if (decoderWrapper == null || !decoderWrapper.isAutoFocusSupported() || !mAutoFocusEnabled) {
            return;
        }
        if (mSafeAutoFocusing && mSafeAutoFocusAttemptsCount < SAFE_AUTO_FOCUS_ATTEMPTS_THRESHOLD) {
            mSafeAutoFocusAttemptsCount++;
        } else {
            try {
                final Camera camera = decoderWrapper.getCamera();
                camera.cancelAutoFocus();
                camera.autoFocus(mSafeAutoFocusCallback);
                mSafeAutoFocusAttemptsCount = 0;
                mSafeAutoFocusing = true;
            } catch (final Exception e) {
                mSafeAutoFocusing = false;
            }
        }
        scheduleSafeAutoFocusTask();
    }

    private void scheduleSafeAutoFocusTask() {
        if (mSafeAutoFocusTaskScheduled) {
            return;
        }
        mSafeAutoFocusTaskScheduled = true;
        mMainThreadHandler.postDelayed(mSafeAutoFocusTask, mSafeAutoFocusInterval);
    }

    boolean isAutoFocusSupportedOrUnknown() {
        final DecoderWrapper wrapper = mDecoderWrapper;
        return wrapper == null || wrapper.isAutoFocusSupported();
    }

    boolean isFlashSupportedOrUnknown() {
        final DecoderWrapper wrapper = mDecoderWrapper;
        return wrapper == null || wrapper.isFlashSupported();
    }

    private final class ScannerLayoutListener implements CodeScannerView.LayoutListener {
        @Override
        public void onLayout(final int width, final int height) {
            synchronized (mInitializeLock) {
                if (width != mViewWidth || height != mViewHeight) {
                    final boolean previewActive = mPreviewActive;
                    if (mInitialized) {
                        releaseResources();
                    }
                    if (previewActive || mInitializationRequested) {
                        initialize(width, height);
                    }
                }
            }
        }
    }

    private final class PreviewCallback implements Camera.PreviewCallback {
        @Override
        public void onPreviewFrame(final byte[] data, final Camera camera) {
            if (!mInitialized || mStoppingPreview || mScanMode == ScanMode.PREVIEW || data == null) {
                return;
            }
            final DecoderWrapper decoderWrapper = mDecoderWrapper;
            if (decoderWrapper == null) {
                return;
            }
            final Decoder decoder = decoderWrapper.getDecoder();
            if (decoder.getState() != Decoder.State.IDLE) {
                return;
            }
            final Rect frameRect = mScannerView.getFrameRect();
            if (frameRect == null || frameRect.getWidth() < 1 || frameRect.getHeight() < 1) {
                return;
            }
            decoder.decode(new DecodeTask(data, decoderWrapper.getImageSize(), decoderWrapper.getPreviewSize(),
                    decoderWrapper.getViewSize(), frameRect, decoderWrapper.getDisplayOrientation(),
                    decoderWrapper.shouldReverseHorizontal()));
        }
    }

    private final class SurfaceCallback implements SurfaceHolder.Callback {
        @Override
        public void surfaceCreated(final SurfaceHolder holder) {
            startPreviewInternalSafe();
        }

        @Override
        public void surfaceChanged(final SurfaceHolder holder, final int format, final int width, final int height) {
            if (holder.getSurface() == null) {
                mPreviewActive = false;
                return;
            }
            stopPreviewInternalSafe();
            startPreviewInternalSafe();
        }

        @Override
        public void surfaceDestroyed(final SurfaceHolder holder) {
            stopPreviewInternalSafe();
        }
    }

    private final class DecoderStateListener implements Decoder.StateListener {
        @Override
        public boolean onStateChanged(@NonNull final Decoder.State state) {
            if (state == Decoder.State.DECODED) {
                final ScanMode scanMode = mScanMode;
                if (scanMode == ScanMode.PREVIEW) {
                    return false;
                } else if (scanMode == ScanMode.SINGLE) {
                    mStoppingPreview = true;
                    mMainThreadHandler.post(mStopPreviewTask);
                }
            }
            return true;
        }
    }

    private final class InitializationThread extends Thread {
        private final int mWidth;
        private final int mHeight;

        public InitializationThread(final int width, final int height) {
            super("cs-init");
            mWidth = width;
            mHeight = height;
        }

        @Override
        public void run() {
            Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
            try {
                initialize();
            } catch (final Exception e) {
                releaseResourcesInternal();
                final ErrorCallback errorCallback = mErrorCallback;
                if (errorCallback != null) {
                    errorCallback.onError(e);
                } else {
                    throw e;
                }
            }
        }

        private void initialize() {
            Camera camera = null;
            final Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
            final int cameraId = mCameraId;
            if (cameraId == CAMERA_BACK || cameraId == CAMERA_FRONT) {
                final int numberOfCameras = Camera.getNumberOfCameras();
                final int facing = cameraId == CAMERA_BACK ? Camera.CameraInfo.CAMERA_FACING_BACK :
                        Camera.CameraInfo.CAMERA_FACING_FRONT;
                for (int i = 0; i < numberOfCameras; i++) {
                    Camera.getCameraInfo(i, cameraInfo);
                    if (cameraInfo.facing == facing) {
                        camera = Camera.open(i);
                        mCameraId = i;
                        break;
                    }
                }
            } else {
                camera = Camera.open(cameraId);
                Camera.getCameraInfo(cameraId, cameraInfo);
            }
            if (camera == null) {
                throw new CodeScannerException("Unable to access camera");
            }
            final Camera.Parameters parameters = camera.getParameters();
            if (parameters == null) {
                throw new CodeScannerException("Unable to configure camera");
            }
            final int orientation = Utils.getDisplayOrientation(mContext, cameraInfo);
            final boolean portrait = Utils.isPortrait(orientation);
            final Point imageSize =
                    Utils.findSuitableImageSize(parameters, portrait ? mHeight : mWidth, portrait ? mWidth : mHeight);
            final int imageWidth = imageSize.getX();
            final int imageHeight = imageSize.getY();
            parameters.setPreviewSize(imageWidth, imageHeight);
            final Point previewSize =
                    Utils.getPreviewSize(portrait ? imageHeight : imageWidth, portrait ? imageWidth : imageHeight,
                            mWidth, mHeight);
            final List<String> focusModes = parameters.getSupportedFocusModes();
            final boolean autoFocusSupported = focusModes != null &&
                    (focusModes.contains(Camera.Parameters.FOCUS_MODE_AUTO) ||
                            focusModes.contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE));
            if (!autoFocusSupported) {
                mAutoFocusEnabled = false;
            }
            if (autoFocusSupported && mAutoFocusEnabled) {
                Utils.setAutoFocusMode(parameters, mAutoFocusMode);
            }
            final List<String> flashModes = parameters.getSupportedFlashModes();
            final boolean flashSupported =
                    flashModes != null && flashModes.contains(Camera.Parameters.FLASH_MODE_TORCH);
            if (!flashSupported) {
                mFlashEnabled = false;
            }
            Utils.optimizeParameters(parameters);
            //CameraConfigurationUtils.setBestExposure(parameters, mFlashEnabled);
            camera.setParameters(parameters);
            camera.setDisplayOrientation(orientation);
            synchronized (mInitializeLock) {
                final Decoder decoder = new Decoder(mDecoderStateListener, mFormats, mDecodeCallback);
                mDecoderWrapper = new DecoderWrapper(camera, cameraInfo, decoder, imageSize, previewSize,
                        new Point(mWidth, mHeight), orientation, autoFocusSupported, flashSupported);
                decoder.start();
                mInitialization = false;
                mInitialized = true;
            }
            mMainThreadHandler.post(new FinishInitializationTask(previewSize));
        }
    }

    private final class SafeAutoFocusCallback implements Camera.AutoFocusCallback {
        @Override
        public void onAutoFocus(final boolean success, final Camera camera) {
            mSafeAutoFocusing = false;
        }
    }

    private final class SafeAutoFocusTask implements Runnable {
        @Override
        public void run() {
            mSafeAutoFocusTaskScheduled = false;
            if (mAutoFocusMode == AutoFocusMode.SAFE) {
                safeAutoFocusCamera();
            }
        }
    }

    private final class StopPreviewTask implements Runnable {
        @Override
        public void run() {
            stopPreview();
        }
    }

    private final class FinishInitializationTask implements Runnable {
        private final Point mPreviewSize;

        private FinishInitializationTask(@NonNull final Point previewSize) {
            mPreviewSize = previewSize;
        }

        @Override
        public void run() {
            if (!mInitialized) {
                return;
            }
            mScannerView.setPreviewSize(mPreviewSize);
            mScannerView.setAutoFocusEnabled(isAutoFocusEnabled());
            mScannerView.setFlashEnabled(isFlashEnabled());
            startPreview();
        }
    }
}
