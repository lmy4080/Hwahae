package com.lmy.hwahae

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.lmy.hwahae.common.SkinTypes
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initSpinner()
    }

    private fun initSpinner() {

        val spinnerAdapter = ArrayAdapter(this, R.layout.main_spinner_main_item, SkinTypes.getAllSkinTypes())
        spinnerAdapter.setDropDownViewResource(R.layout.main_spinner_dropdown_item)
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
}
