package com.lmy.hwahae

import android.content.res.Resources
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout
import com.lmy.hwahae.common.SkinTypes
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /**
         * hide the status bar
         */
        hideStatusBar()

        /**
         * resize the app bar layout
         */
        resizeAppBarLayout()

        /**
         * init the spinner
         */
        initSpinner()

        /**
         * init the searchView
         */
        initSearchView()

        /**
         * disable the appbarLayout drag event
         */
        disableAppbarDragEvent()
    }

    private fun hideStatusBar() {

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    private fun resizeAppBarLayout() {

        abl_filter.layoutParams.height = Resources.getSystem().displayMetrics.heightPixels / 15
    }

    private fun initSpinner() {

        val spinnerFilterAdapter = ArrayAdapter(this, R.layout.sp_skin_type_item, SkinTypes.getAllSkinTypes())
        spinnerFilterAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice)
        sp_skin_type.adapter = spinnerFilterAdapter

        sp_skin_type.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                println("onItemSelected")
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
                println("onQueryTextSubmit")
                return false
            }
        })
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
}
