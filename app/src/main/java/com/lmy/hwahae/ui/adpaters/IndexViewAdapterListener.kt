package com.lmy.hwahae.ui.adpaters

/**
 * Call back interface for user to click the item to check it in detail
 * IndexViewAdapter -> IndexViewActivity with ProductId
 */
interface IndexViewAdapterListener {
    fun sendProductId(productId: Int?)
}