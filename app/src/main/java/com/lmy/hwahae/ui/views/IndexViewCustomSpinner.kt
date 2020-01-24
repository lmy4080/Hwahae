package com.lmy.hwahae.ui.views

import android.content.Context
import android.util.AttributeSet
import android.widget.Spinner

/**
 * Customize the spinner to make it work on the same index
 */
class IndexViewCustomSpinner : Spinner {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)

    override fun setSelection(position: Int, animate: Boolean) {
        val sameSelected = position == selectedItemPosition
        super.setSelection(position, animate)
        if (sameSelected) {
            onItemSelectedListener!!.onItemSelected(this, selectedView, position, selectedItemId)
        }
    }

    override fun setSelection(position: Int) {
        val sameSelected = position == selectedItemPosition
        super.setSelection(position)
        if (sameSelected) {
            onItemSelectedListener!!.onItemSelected(this, selectedView, position, selectedItemId)
        }
    }

}