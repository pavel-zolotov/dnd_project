package com.coolguys.dndproject

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.google.zxing.BarcodeFormat
import kotlinx.android.synthetic.main.activity_qr_scan.*
import android.content.pm.PackageManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import com.budiyev.android.codescanner.*
import com.coolguys.dndproject.data.Manager
import com.coolguys.dndproject.model.Character
import com.google.gson.Gson
import org.json.JSONException


class QRScanActivity: AppCompatActivity() {
    private lateinit var codeScanner: CodeScanner
    private var mPermissionGranted: Boolean = false
    private val RC_PERMISSION = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr_scan)

        codeScanner = CodeScanner(this, scanner_view)
        codeScanner.formats = listOf(BarcodeFormat.QR_CODE) // list of type BarcodeFormat
        codeScanner.scanMode = ScanMode.SINGLE
        codeScanner.autoFocusMode = AutoFocusMode.SAFE

        // Callbacks
        codeScanner.decodeCallback = DecodeCallback {
            runOnUiThread {
                try{
                    val character = Gson().fromJson(it.text, Character::class.java)
                    Manager().insertCharacter(this, character)
                    val intent = Intent()
                    setResult(RESULT_OK, intent)
                    finish()
                }catch (e: com.google.gson.JsonSyntaxException){
                    codeScanner.startPreview()
                    Toast.makeText(this, R.string.wrong_data_format, Toast.LENGTH_LONG).show()
                }
            }
        }
        codeScanner.setErrorCallback({ error -> runOnUiThread { Toast.makeText(this, R.string.pls_permission, Toast.LENGTH_LONG).show() } })
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                mPermissionGranted = false
                requestPermissions(arrayOf(Manifest.permission.CAMERA), RC_PERMISSION)
            } else {
                mPermissionGranted = true
            }
        } else {
            mPermissionGranted = true
        }
        codeScanner.startPreview()
    }

    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == RC_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mPermissionGranted = true
                codeScanner.startPreview()
            } else {
                mPermissionGranted = false
            }
        }
    }

}