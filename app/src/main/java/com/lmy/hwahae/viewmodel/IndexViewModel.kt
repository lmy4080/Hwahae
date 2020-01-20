package com.lmy.hwahae.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.lmy.hwahae.datasoruce.api.NetworkState
import com.lmy.hwahae.datasoruce.model.IndexViewProduct
import com.lmy.hwahae.repository.HwahaeRepository

class IndexViewModel : ViewModel() {

    private val mHwahaeRepository = HwahaeRepository()

    /**
     * Return live-data for product list from HwahaeRepository
     */
    fun getProductList(): LiveData<PagedList<IndexViewProduct>> = mHwahaeRepository.mProductList

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
     * Return live-data holding the state of network
     */
    fun getNetworkState(): LiveData<NetworkState> = mHwahaeRepository.getState()
}