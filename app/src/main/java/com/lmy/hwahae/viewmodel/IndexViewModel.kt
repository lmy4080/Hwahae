package com.lmy.hwahae.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.lmy.hwahae.datasoruce.remote.model.IndexViewItem
import com.lmy.hwahae.datasoruce.remote.status.NetworkStatus
import com.lmy.hwahae.repository.HwahaeRepository

class IndexViewModel: ViewModel() {

    /**
     * Set the typed keyword by user for searching
     */
    fun setSearchKeyword(searchKeyword: String) {
        HwahaeRepository.setSearchKeyword(searchKeyword)
    }

    /**
     * Set the state of whether search-keyword updated or not
     */
    fun setIsSearchKeywordSet(aBoolean: Boolean) {
        HwahaeRepository.setIsSearchKeywordSet(aBoolean)
    }

    /**
     * Return live-data holding the state of whether search-keyword updated or not
     */
    fun getIsSearchKeywordSet() = HwahaeRepository.getIsSearchKeywordSet()

    /**
     * Set the selected skin-type by user for searching
     */
    fun setSkinType(skinType: String) {
        HwahaeRepository.setSkinType(skinType)
    }

    /**
     * Set the state of whether skin-type updated or not
     */
    fun setIsSkinTypeSet(aBoolean: Boolean) {
        HwahaeRepository.setIsSkinTypeSet(aBoolean)
    }

    /**
     * Return live-data holding the state of whether skin-type updated or not
     */
    fun getIsSkinTypeSet() = HwahaeRepository.getIsSkinTypeSet()

    /**
     * Return live-data for product list from HwahaeRepository
     */
    fun getProductList(): LiveData<PagedList<IndexViewItem>> = HwahaeRepository.getProductList()

    /**
     * Return live-data holding the state of network
     */
    fun getNetworkState(): LiveData<NetworkStatus.State> = HwahaeRepository.getState()

    /**
     * Return live-data holding the update state of product detail
     */
    fun getIsUpdatedProductDetail(): MutableLiveData<Boolean> = HwahaeRepository.getIsUpdatedProductDetail()

    /**
     * Set live-data holding the update state of product detail with flags
     */
    fun setIsUpdatedProductDetail(flag: Boolean) {
        HwahaeRepository.setIsUpdatedProductDetail(flag)
    }

    /**
     * Fetch new data
     */
    fun fetchProductList() { HwahaeRepository.fetchProductList() }
}