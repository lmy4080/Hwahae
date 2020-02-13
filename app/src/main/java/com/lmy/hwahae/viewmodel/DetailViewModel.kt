package com.lmy.hwahae.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.lmy.hwahae.datasoruce.remote.model.DetailViewItem
import com.lmy.hwahae.repository.HwahaeRepository

class DetailViewModel: ViewModel() {

    /**
     * Fetch the detail of product
     */
    fun fetchProductDetail(productId: Int?): LiveData<DetailViewItem> = HwahaeRepository.fetchProductDetail(productId)
}