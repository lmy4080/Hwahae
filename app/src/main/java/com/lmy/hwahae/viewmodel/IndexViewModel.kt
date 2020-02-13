package com.lmy.hwahae.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.lmy.hwahae.datasoruce.remote.status.NetworkStatus
import com.lmy.hwahae.datasoruce.remote.model.IndexViewItem
import com.lmy.hwahae.repository.HwahaeRepository

class IndexViewModel: ViewModel() {

    private val mHwahaeRepository = HwahaeRepository()

    /**
     * Set the typed keyword by user for searching
     */
    fun setSearchKeyword(searchKeyword: String) {
        mHwahaeRepository.setSearchKeyword(searchKeyword)
    }

    /**
     * Set the state of whether search-keyword updated or not
     */
    fun setIsSearchKeywordSet(aBoolean: Boolean) {
        mHwahaeRepository.setIsSearchKeywordSet(aBoolean)
    }

    /**
     * Return live-data holding the state of whether search-keyword updated or not
     */
    fun getIsSearchKeywordSet() = mHwahaeRepository.getIsSearchKeywordSet()

    /**
     * Set the selected skin-type by user for searching
     */
    fun setSkinType(skinType: String) {
        mHwahaeRepository.setSkinType(skinType)
    }

    /**
     * Set the state of whether skin-type updated or not
     */
    fun setIsSkinTypeSet(aBoolean: Boolean) {
        mHwahaeRepository.setIsSkinTypeSet(aBoolean)
    }

    /**
     * Return live-data holding the state of whether skin-type updated or not
     */
    fun getIsSkinTypeSet() = mHwahaeRepository.getIsSkinTypeSet()

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

    /**
     * Fetch new data
     */
    fun fetchProductList() { mHwahaeRepository.fetchProductList() }
}