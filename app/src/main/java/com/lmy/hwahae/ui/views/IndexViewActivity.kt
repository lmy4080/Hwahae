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
import com.lmy.hwahae.ui.adpaters.IndexViewAdapter
import com.lmy.hwahae.ui.adpaters.IndexViewAdapterListener
import com.lmy.hwahae.viewmodel.IndexViewModel
import kotlinx.android.synthetic.main.activity_main.*

class IndexViewActivity : AppCompatActivity(), IndexViewAdapterListener {

    private lateinit var mIndexViewModel: IndexViewModel
    private var mAdapter = IndexViewAdapter(this)
    private var isFirstLoaded: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
        mIndexViewModel = ViewModelProviders.of(this)[IndexViewModel::class.java]

        mIndexViewModel.getProductList().observe(this, Observer { itemList ->
            mAdapter.submitList(itemList)
        })

        mIndexViewModel.getNetworkState().observe(this, Observer { networkState ->
            Toast.makeText(this, networkState.toString(), Toast.LENGTH_LONG).show()
        })

        mIndexViewModel.getProductDetail().observe(this, Observer { productDetail ->
            println("changed=${productDetail}")
        })
    }

    private fun hideStatusBar() {

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    private fun resizeAppBarLayout() {

        abl_filter.layoutParams.height = Resources.getSystem().displayMetrics.heightPixels / 15
    }

    private fun initSpinner() {

        val spinnerFilterAdapter = ArrayAdapter(this, R.layout.sp_skin_type_item, SpinnerData.getAllSkinTypes())
        spinnerFilterAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice)
        sp_skin_type.adapter = spinnerFilterAdapter

        sp_skin_type.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
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
        mIndexViewModel.updateProductDetail(productId)
    }
}
