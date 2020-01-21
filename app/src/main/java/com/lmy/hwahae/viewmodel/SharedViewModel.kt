package com.lmy.hwahae.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.lmy.hwahae.datasoruce.status.NetworkStatus
import com.lmy.hwahae.datasoruce.model.DetailViewProduct
import com.lmy.hwahae.datasoruce.model.IndexViewProduct
import com.lmy.hwahae.repository.HwahaeRepository

class SharedViewModel : ViewModel() {

    private val mHwahaeRepository = HwahaeRepository()

    /**
     * Set the typed keyword by user for searching
     */
    fun setSearchKeyword(searchKeyword: String) {
        mHwahaeRepository.setSearchKeyword(searchKeyword)
    }

    /**
     * Set the selected skin-type by user for searching
     */
    fun setSkinType(skinType: String) {
        mHwahaeRepository.setSkinType(skinType)
    }

    /**
     * Return live-data for product list from HwahaeRepository
     */
    fun getProductList(): LiveData<PagedList<IndexViewProduct>> = mHwahaeRepository.mProductList

    /**
     * Return live-data holding the state of network
     */
    fun getNetworkState(): LiveData<NetworkStatus.State> = mHwahaeRepository.getState()

    /**
     * Return live-data holding the detail of product
     */
    fun getProductDetail(): MutableLiveData<DetailViewProduct> = mHwahaeRepository.getProductDetail()

    /**
     * Update the detail of product
     */
    fun updateProductDetail(productId: Int?) {
        mHwahaeRepository.updateProductDetail(productId)
    }
}