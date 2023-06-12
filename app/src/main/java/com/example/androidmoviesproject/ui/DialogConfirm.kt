package com.example.androidmoviesproject.ui

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.androidmoviesproject.R

class DialogConfirm(
    val title: String,
    val message: String,
    val onPositive: () -> Unit,
) : DialogFragment() {
    companion object {
        const val TAG_DIALOG_CONFIRM: String = "tag_confirm_dialog"
    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext()).setTitle(title).setMessage(message)
            .setPositiveButton(R.string.confirm_lang) { dialog: DialogInterface, _: Int ->
                onPositive()
                dialog.dismiss()
            }.setNegativeButton(R.string.cancel_lang) { dialog: DialogInterface, _: Int ->
                dialog.dismiss()
            }.create()
    }
}