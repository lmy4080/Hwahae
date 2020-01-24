package com.lmy.hwahae.ui.views

import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.net.toUri
import androidx.dynamicanimation.animation.SpringForce
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.lmy.hwahae.R
import com.lmy.hwahae.ui.status.DetailViewStatus
import com.lmy.hwahae.ui.utils.AnimationHelper
import com.lmy.hwahae.ui.utils.FormatPlainToPrice


class DetailViewDialog: BottomSheetDialogFragment() {

    private lateinit var svProduct: ScrollView
    private lateinit var btnCancel: Button
    private lateinit var btnPurchase: Button
    private lateinit var ivProductImage: ImageView
    private lateinit var tvTitle: TextView
    private lateinit var tvPrice: TextView
    private lateinit var tvDescription: TextView
    private lateinit var flBottomMargin: FrameLayout

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        var dialog = AlertDialog.Builder(requireContext()).setView(R.layout.dialog_detail_view).create()

        /* Apply the animation to dialog */
        registerDialogAnimation(dialog)

        return dialog
    }

    override fun onStart() {
        super.onStart()

        /* Hide the status bar */
        hideStatusBar()

        /* Init dialog's views */
        initViews()

        /* Resize the views of dialog dynamically */
        resizeViews()

        /* Set data to dialog's views */
        setData()

        /* Restore the positionY of ScrollView */
        restoreScrollViewState()
    }

    override fun onDismiss(dialog: DialogInterface) {

        /* Save the current positionY of ScrollView */
        DetailViewStatus.currentPositionY = svProduct.scrollY

        super.onDismiss(dialog)
    }

    /**
     * Create and apply the animation to dialog
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
     * Hide the status bar
     */
    private fun hideStatusBar() {
        dialog?.window?.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    /**
     * Init dialog's views
     */
    private fun initViews() {
        svProduct = dialog!!.findViewById(R.id.sv_product) as ScrollView
        btnCancel = dialog!!.findViewById(R.id.btn_cancel) as Button
        btnPurchase = dialog!!.findViewById(R.id.btn_purchase) as Button
        ivProductImage = dialog!!.findViewById(R.id.iv_product_image) as ImageView
        tvTitle = dialog!!.findViewById(R.id.tv_product_title) as TextView
        tvPrice= dialog!!.findViewById(R.id.tv_product_price) as TextView
        tvDescription = dialog!!.findViewById(R.id.tv_product_description) as TextView
        flBottomMargin = dialog!!.findViewById(R.id.fl_product_bottom_margin) as FrameLayout
    }

    /**
     * Resize the dialog dynamically
     */
    private fun resizeViews() {

        /* Resize the width and height of dialog */
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        /* Resize the width and height of Cancel Button */
        btnCancel.layoutParams?.width = (resources.displayMetrics.heightPixels / 17.5).toInt()
        btnCancel.layoutParams?.height = (resources.displayMetrics.heightPixels / 17.5).toInt()
        btnCancel.requestLayout()

        /* Resize the width and height of Thumbnail ImageView */
        ivProductImage.layoutParams?.width = (resources.displayMetrics.heightPixels / 2.25).toInt()
        ivProductImage.layoutParams?.height = (resources.displayMetrics.heightPixels / 2.25).toInt()
        ivProductImage.requestLayout()

        /* Set the bottom margin */
        flBottomMargin.layoutParams?.height = (resources.displayMetrics.heightPixels / 4)
    }

    /**
     * Set data to dialog's views
     */
    private fun setData() {
        Glide.with(dialog!!.context)
            .asBitmap()
            .load(DetailViewStatus.detailViewItem.full_size_image.toUri())
            .into(ivProductImage)
        tvTitle.text = DetailViewStatus.detailViewItem.title
        tvPrice.text = FormatPlainToPrice.start(DetailViewStatus.detailViewItem.price)
        tvDescription.text = DetailViewStatus.detailViewItem.description
    }

    /**
     * Restore the positionY of ScrollView
     */
    private fun restoreScrollViewState() {
        svProduct.post{
            svProduct.scrollTo(0, DetailViewStatus.currentPositionY!!)
        }
    }
}