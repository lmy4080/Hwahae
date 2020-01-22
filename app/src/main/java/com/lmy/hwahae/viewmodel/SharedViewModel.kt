package com.lmy.hwahae.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.lmy.hwahae.datasoruce.remote.status.NetworkStatus
import com.lmy.hwahae.datasoruce.remote.model.IndexViewItem
import com.lmy.hwahae.repository.HwahaeRepository

class SharedViewModel: ViewModel() {

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
    fun getProductList(): LiveData<PagedList<IndexViewItem>> = mHwahaeRepository.getProductList()

    /**
     * Return live-data holding the state of network
     */
    fun getNetworkState(): LiveData<NetworkStatus.State> = mHwahaeRepository.getState()

    /**
     * Return live-data holding the update state of product detail
     */
    fun getIsUpdatedProductDetail(): MutableLiveData<Boolean> = mHwahaeRepository.getIsUpdatedProductDetail()

    /**
     * Set live-data holding the update state of product detail with flags
     */
    fun setIsUpdatedProductDetail(flag: Boolean) {
        mHwahaeRepository.setIsUpdatedProductDetail(flag)
    }

    /**
     * Fetch the detail of product
     */
    fun fetchProductDetail(productId: Int?) {
        mHwahaeRepository.fetchProductDetail(productId)
    }
}