package com.coolguys.dndproject

import android.support.design.widget.BottomSheetDialogFragment
import android.os.Bundle
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import com.coolguys.dndproject.model.Character
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_qr_view.*
import net.glxn.qrgen.android.QRCode

class QRViewFragment: BottomSheetDialogFragment() {
    lateinit var character: Character

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_qr_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val myBitmap = QRCode.from(Gson().toJson(character)).withSize(700, 700).bitmap()
        qr.setImageBitmap(myBitmap)

        close.setOnClickListener { this.dismiss() }
    }
}