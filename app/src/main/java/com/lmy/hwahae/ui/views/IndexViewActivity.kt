package com.lmy.hwahae.ui.views

import android.content.res.Resources
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.appbar.AppBarLayout
import com.lmy.hwahae.R
import com.lmy.hwahae.datasoruce.remote.status.NetworkStatus
import com.lmy.hwahae.ui.adpaters.IndexViewAdapter
import com.lmy.hwahae.ui.adpaters.IndexViewAdapterListener
import com.lmy.hwahae.viewmodel.SharedViewModel
import kotlinx.android.synthetic.main.activity_index_view.*

class IndexViewActivity : AppCompatActivity(), IndexViewAdapterListener {

    private lateinit var mIndexViewModel: SharedViewModel
    private var mAdapter = IndexViewAdapter(this)
    private var isFirstLoaded: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_index_view)

        /**
         * Subscribe Ui
         */
        subscribeUi()

        /**
         * Hide the status bar
         */
        hideStatusBar()

        /**
         * Resize the app bar layout
         */
        resizeAppBarLayout()

        /**
         * Init the spinner
         */
        initSpinner()

        /**
         * Init the searchView
         */
        initSearchView()

        /**
         * Init the recyclerView
         */
        initRecyclerView()

        /**
         * Disable the appbarLayout drag event
         */
        disableAppbarDragEvent()
    }

    private fun subscribeUi() {
        mIndexViewModel = ViewModelProviders.of(this)[SharedViewModel::class.java]

        mIndexViewModel.getProductList().observe(this, Observer { itemList ->
            mAdapter.submitList(itemList)
        })

        mIndexViewModel.getNetworkState().observe(this, Observer { networkState ->
            when(networkState) {
                NetworkStatus.State.LOADING -> showProgressBar()
                NetworkStatus.State.RETRY -> showProgressBar()
                NetworkStatus.State.DONE -> hideProgressBar()
                NetworkStatus.State.FAILED -> {
                    hideProgressBar()
                    showFailedMessage("상품 정보를 받아오는데 실패하였습니다. 잠시 후 다시 시도해주세요.")
                }
                null -> return@Observer
            }
        })

        mIndexViewModel.getIsUpdatedProductDetail().observe(this, Observer { isUpdated ->
            if (isUpdated) {
                showDetailViewDialog()
                mAdapter.resetLastClickTime()
                mIndexViewModel.setIsUpdatedProductDetail(false)
            }
        })
    }

    private fun showFailedMessage(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    private fun hideStatusBar() {

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    private fun resizeAppBarLayout() {

        abl_filter.layoutParams.height = (Resources.getSystem().displayMetrics.heightPixels / 12.5).toInt()
    }

    private fun initSpinner() {

        val spinnerFilterAdapter = ArrayAdapter(this, R.layout.sp_skin_type_item, SpinnerData.getAllSkinTypes())
        spinnerFilterAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice)
        sp_skin_type.adapter = spinnerFilterAdapter

        sp_skin_type.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                println("onItemSelected")

                if(isFirstLoaded) {
                    isFirstLoaded = false
                }
                else {
                    /* Set the current skin type with user's value and fetch new data */
                    mIndexViewModel.setSkinType(position.toString())

                    /* ui processing
                     * clear the search keyword if remained
                     * clear the focus of searchView */
                    sv_search.setQuery("",false)
                    sv_search.clearFocus()
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
                println("onNothingSelected")
            }
        }
    }

    private fun initSearchView() {

        sv_search.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                println("onQueryTextChange")
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {

                /* Set the current search keyword with user's value and fetch new data */
                mIndexViewModel.setSearchKeyword(query)

                /* ui processing
                 * clear the search keyword if remained
                 * expand the header */
                abl_filter.setExpanded(true)
                sv_search.clearFocus()

                return false
            }
        })
    }

    private fun initRecyclerView() {

        rv_product.layoutManager = GridLayoutManager(this, 2)
        rv_product.isNestedScrollingEnabled = true
        rv_product.setHasFixedSize(false)
        rv_product.adapter = mAdapter
    }

    private fun disableAppbarDragEvent() {

        if (abl_filter.layoutParams != null) {
            val layoutParams = abl_filter.getLayoutParams() as CoordinatorLayout.LayoutParams
            val appBarLayoutBehaviour = AppBarLayout.Behavior()

            appBarLayoutBehaviour.setDragCallback(object : AppBarLayout.Behavior.DragCallback() {
                override fun canDrag(appBarLayout: AppBarLayout): Boolean {
                    println("canDrag")
                    return false
                }
            })
            layoutParams.behavior = appBarLayoutBehaviour
        }
    }

    override fun sendProductId(productId: Int?) {
        mIndexViewModel.fetchProductDetail(productId)
    }

    private fun showDetailViewDialog() {
        DetailViewDialog().show(supportFragmentManager, "DetailView")
    }

    private fun showProgressBar() {
        pb_loading_bar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        pb_loading_bar.visibility = View.GONE
    }
}
