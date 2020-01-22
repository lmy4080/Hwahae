package com.lmy.hwahae.ui.views

import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ScrollView
import androidx.appcompat.app.AlertDialog
import androidx.dynamicanimation.animation.SpringForce
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.lmy.hwahae.R
import com.lmy.hwahae.ui.status.DetailViewStatus
import com.lmy.hwahae.ui.utils.AnimationHelper


class DetailViewDialog: BottomSheetDialogFragment() {

    private var svProduct: ScrollView? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val dialog = AlertDialog.Builder(requireContext()).setView(R.layout.dialog_layout).create()

        /* Execute the animation of dialog */
        registerDialogAnimation(dialog)
        return dialog
    }

    override fun onStart() {
        super.onStart()

        /* Resize the dialog dynamically */
        resizeDialog()

        /* Restore the positionY of ScrollView */
        restoreScrollViewState(dialog!!)
    }


    override fun onDismiss(dialog: DialogInterface) {

        /* Save the current positionY of ScrollView */
        DetailViewStatus.currentPositionY = svProduct?.scrollY

        super.onDismiss(dialog)
    }

    /**
     * Create and execute the animation of dialog
     */
    private fun registerDialogAnimation(dialog: AlertDialog) {

        AnimationHelper.getSpringAnimation(
            dialog.window!!.decorView,
            (resources.displayMetrics.heightPixels * 2).toFloat(),
            AnimationHelper.getSpringForce(((resources.displayMetrics.heightPixels / 15).toFloat()), 0.725f, SpringForce.STIFFNESS_VERY_LOW)
        ).addEndListener { _, _, _, _ ->

            val btnPurchase = dialog.window?.findViewById<Button>(R.id.btn_purchase)
            btnPurchase?.visibility = View.VISIBLE

            AnimationHelper.getSpringAnimation(
                btnPurchase!!,
                2000f,
                AnimationHelper.getSpringForce(0f,0.80f, SpringForce.STIFFNESS_LOW)
            ).start()

        }.start()
    }

    /**
     * Resize the dialog dynamically
     */
    private fun resizeDialog() {

        /* Resize the width and height of dialog */
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        /* Resize the width and height of Cancel Button */
        val btnCancel = dialog!!.findViewById(R.id.btn_cancel) as Button
        btnCancel?.layoutParams?.width = (resources.displayMetrics.heightPixels / 17.5).toInt()
        btnCancel?.layoutParams?.height = (resources.displayMetrics.heightPixels / 17.5).toInt()
        btnCancel?.requestLayout()

        /* Resize the width and height of Thumbnail ImageView */
        val ivProductImage = dialog!!.findViewById(R.id.iv_product_image) as ImageView
        ivProductImage?.layoutParams?.width = (resources.displayMetrics.heightPixels / 2.25).toInt()
        ivProductImage?.layoutParams?.height = (resources.displayMetrics.heightPixels / 2.25).toInt()
        ivProductImage?.requestLayout()
    }

    /**
     * Restore the positionY of ScrollView
     */
    private fun restoreScrollViewState(dialog: Dialog) {
        svProduct = dialog!!.findViewById(R.id.sv_product) as ScrollView
        svProduct?.post{
            svProduct?.scrollTo(0, DetailViewStatus.currentPositionY!!)
        }
    }
}