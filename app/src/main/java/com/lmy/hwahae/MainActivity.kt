package com.lmy.hwahae

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout
import com.lmy.hwahae.common.SkinTypes
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        hideStatusBar()
        initSpinner()
        initSearchView()
        disableAppbarDragEvent()
    }

    // hide the status bar
    private fun hideStatusBar() {

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    // init the spinner
    private fun initSpinner() {

        val spinnerAdapter = ArrayAdapter(this, R.layout.sp_skin_type_item, SkinTypes.getAllSkinTypes())
        spinnerAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice)
        sp_skin_type.adapter = spinnerAdapter

        sp_skin_type.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {

                //아이템이 클릭 되면 맨 위부터 position 0번부터 순서대로 동작하게 됩니다.
                when(position) {
                    0   ->  {
                    }
                    1   ->  {
                    }
                    2   -> {
                    }
                    3   -> {
                    }
                    //...
                    else -> {
                    }
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }
    }

    // init the searchView
    private fun initSearchView() {

        sv_search_item.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                Log.d("DEBUG", newText)
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                // task HERE
                Log.d("DEBUG", query)
                return false
            }
        })
    }

    // disable the appbarLayout drag event
    private fun disableAppbarDragEvent() {

        if (abl.getLayoutParams() != null) {
            val layoutParams = abl.getLayoutParams() as CoordinatorLayout.LayoutParams
            val appBarLayoutBehaviour = AppBarLayout.Behavior()
            appBarLayoutBehaviour.setDragCallback(object : AppBarLayout.Behavior.DragCallback() {
                override fun canDrag(appBarLayout: AppBarLayout): Boolean {
                    return false
                }
            })
            layoutParams.behavior = appBarLayoutBehaviour
        }
    }
}
