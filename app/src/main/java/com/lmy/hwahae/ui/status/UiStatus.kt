package com.lmy.hwahae.ui.status

object UiStatus {

    /**
     * Save the current status of Ui - filter value, search query
     */
    private val allSkinTypes = arrayOf("oily" /* default */, "oily", "dry", "sensitive")
    var currentSkinType = allSkinTypes[0] /* default */
        set(skinTypeIndex) {
            allSkinTypes[skinTypeIndex.toInt()]
        }
    var currentSearchKeyword: String? = null
}