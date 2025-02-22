package com.lmy.hwahae.ui.views

import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.core.widget.NestedScrollView
import androidx.dynamicanimation.animation.SpringForce
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.lmy.hwahae.R
import com.lmy.hwahae.datasoruce.remote.model.DetailViewItem
import com.lmy.hwahae.ui.status.DetailViewStatus
import com.lmy.hwahae.ui.utils.AnimationHelper
import com.lmy.hwahae.ui.utils.FormatPlainToPrice
import com.lmy.hwahae.viewmodel.DetailViewModel

class DetailViewDialog constructor(private val productId: Int) : DialogFragment() {

    private lateinit var mDetailViewModel: DetailViewModel

    private lateinit var mView: View
    private lateinit var svProduct: NestedScrollView
    private lateinit var btnCancel: Button
    private lateinit var btnPurchase: Button
    private lateinit var ivProductImage: ImageView
    private lateinit var tvTitle: TextView
    private lateinit var tvPrice: TextView
    private lateinit var tvDescription: TextView
    private lateinit var flBottomMargin: FrameLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mView = inflater.inflate(R.layout.dialog_detail_view, container, false)

        /* Subscribe Ui */
        subscribeUi()

        /* Apply the animation to this dialog */
        registerDialogAnimation()

        /* Hide the status bar */
        hideStatusBar()

        /* Init dialog's views */
        initViews()

        /* Register event listeners */
        registerViewEvents()

        /* Resize the views of dialog dynamically */
        resizeChildViews()

        return mView
    }

    override fun onStart() {
        super.onStart()
        resizeDialog()
    }

    override fun onDismiss(dialog: DialogInterface) {

        /* Save the current positionY of ScrollView */
        saveScrollViewPositionY()
        super.onDismiss(dialog)
    }

    /**
     * Resize the width and height of dialog
     */
    private fun resizeDialog() {

        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    /**
     * Subscribe Ui
     */
    private fun subscribeUi() {
        mDetailViewModel = ViewModelProviders.of(this)[DetailViewModel::class.java]

        mDetailViewModel.fetchProductDetail(productId).observe(this, Observer { productDetail ->
            setScrollY(productDetail.id)
            setData(productDetail)
        })
    }

    /**
     * Restore the position Y of scrollView
     */
    private fun setScrollY(newProductId: Int) {
        if(DetailViewStatus.oldProductId == newProductId) {
            restoreScrollViewState(DetailViewStatus.oldScrollY)
        }
        else {
            restoreScrollViewState(0)
        }
    }

    /**
     * Create and apply the animation to dialog
     */
    private fun registerDialogAnimation() {

        AnimationHelper.getSpringAnimation(
            mView,
            (resources.displayMetrics.heightPixels * 2).toFloat(),
            AnimationHelper.getSpringForce(((resources.displayMetrics.heightPixels / 15).toFloat()), 0.725f, SpringForce.STIFFNESS_VERY_LOW)
        ).addEndListener { _, _, _, _ ->
            btnPurchase = mView.findViewById(R.id.btn_purchase)
            btnPurchase.visibility = View.VISIBLE

            AnimationHelper.getSpringAnimation(
                btnPurchase,
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
        svProduct = mView.findViewById(R.id.sv_product) as NestedScrollView
        btnCancel = mView.findViewById(R.id.btn_cancel) as Button
        btnPurchase = mView.findViewById(R.id.btn_purchase)
        btnPurchase = mView.findViewById(R.id.btn_purchase) as Button
        ivProductImage = mView.findViewById(R.id.iv_product_image) as ImageView
        tvTitle = mView.findViewById(R.id.tv_product_title) as TextView
        tvPrice= mView.findViewById(R.id.tv_product_price) as TextView
        tvDescription = mView.findViewById(R.id.tv_product_description) as TextView
        flBottomMargin = mView.findViewById(R.id.fl_product_bottom_margin) as FrameLayout
    }

    /**
     * Register onClickListener on cancel button
     */
    private fun registerViewEvents() {

        /* Dismiss this dialog when clicking on 'btnCancel' */
        btnCancel.setOnClickListener {
            saveScrollViewPositionY()
            dismiss()
        }
    }

    /**
     * Resize the dialog dynamically
     */
    private fun resizeChildViews() {

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
    private fun setData(productDetail: DetailViewItem) {
        Glide.with(dialog!!.context)
            .asBitmap()
            .load(productDetail.full_size_image.toUri())
            .into(ivProductImage)
        tvTitle.text = productDetail.title
        tvPrice.text = FormatPlainToPrice.start(productDetail.price)
        tvDescription.text = productDetail.description
    }

    /**
     * Restore the positionY of ScrollView
     */
    private fun restoreScrollViewState(scrollY: Int?) {
        svProduct.post{
            svProduct.scrollTo(0, scrollY!!)
        }
    }

    /**
     * Save the positionY of ScrollView
     */
    private fun saveScrollViewPositionY() {
        DetailViewStatus.oldProductId = productId
        DetailViewStatus.oldScrollY = svProduct.scrollY
    }
}
