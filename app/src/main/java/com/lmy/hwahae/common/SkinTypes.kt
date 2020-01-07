package com.lmy.hwahae.common

class SkinTypes {
    companion object {
        private var allSkinTypes: Array<String> = arrayOf("모든 피부 타입", "Oily", "Dry", "Sensitive")
        fun getAllSkinTypes() = allSkinTypes
    }
}