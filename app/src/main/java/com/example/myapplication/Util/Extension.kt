package com.example.myapplication.Util
import android.app.AlertDialog
import android.app.Dialog
import androidx.fragment.app.Fragment
import com.example.myapplication.MainActivity
import com.example.myapplication.R

fun Fragment.getLoading(): Dialog {
    val builder = AlertDialog.Builder((activity as MainActivity))
    builder.setView(R.layout.progress)
    builder.setCancelable(false)
    var dialog = builder.create()
    dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
    return dialog
}