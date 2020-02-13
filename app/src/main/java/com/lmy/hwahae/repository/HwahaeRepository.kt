package com.lmy.hwahae.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.lmy.hwahae.datasoruce.remote.HwahaeDataSourceFactory
import com.lmy.hwahae.datasoruce.remote.api.HwahaeWebService
import com.lmy.hwahae.datasoruce.remote.model.DetailViewItem
import com.lmy.hwahae.datasoruce.remote.model.IndexViewItem
import com.lmy.hwahae.datasoruce.remote.status.NetworkStatus
import com.lmy.hwahae.ui.status.IndexViewStatus
import kotlinx.coroutines.*
import java.util.concurrent.Executors

object HwahaeRepository {

    private var mProductList: LiveData<PagedList<IndexViewItem>>
    private var mIsUpdatedProductDetail = MutableLiveData<Boolean>()
    private val mProductDataSourceFactory = HwahaeDataSourceFactory()
    private var mIsSkinTypeSet = MutableLiveData<Boolean>()
    private var mIsSearchKeywordSet = MutableLiveData<Boolean>()

    /**
     * Create live-data for product list from HwahaeDataSourceFactory
     */
    init {
        val executor = Executors.newFixedThreadPool(5)

        val config = PagedList.Config.Builder()
            .setPageSize(20)
            .setPrefetchDistance(2)
            .setEnablePlaceholders(false)
            .build()

        mProductList = LivePagedListBuilder<Int, IndexViewItem>(mProductDataSourceFactory , config)
            .setFetchExecutor(executor)
            .build()
    }

    /**
     * Set the typed keyword for searching
     */
    fun setSearchKeyword(searchKeyword: String) {
        IndexViewStatus.currentSearchKeyword = searchKeyword
        mIsSearchKeywordSet.postValue(true)
    }

    /**
     * Set the state of whether search-keyword updated or not
     */
    fun setIsSearchKeywordSet(aBoolean: Boolean) {
        mIsSearchKeywordSet.postValue(aBoolean)
    }

    /**
     * Return live-data holding the state of whether search-keyword updated or not
     */
    fun getIsSearchKeywordSet() = mIsSearchKeywordSet

    /**
     * Set the selected skin-type for searching
     */
    fun setSkinType(skinType: String) {
        IndexViewStatus.currentSkinType = skinType
        IndexViewStatus.currentSearchKeyword = null
        mIsSkinTypeSet.postValue(true)
    }

    /**
     * Set the state of whether skin-type updated or not
     */
    fun setIsSkinTypeSet(aBoolean: Boolean) {
        mIsSkinTypeSet.postValue(aBoolean)
    }

    /**
     * Return live-data holding the state of whether skin-type updated or not
     */
    fun getIsSkinTypeSet() = mIsSkinTypeSet

    /**
     * Return live-data for product list from HwahaeRepository
     */
    fun getProductList(): LiveData<PagedList<IndexViewItem>> = mProductList

    /**
     * Return live-data holding the state of network
     */
    fun getState(): LiveData<NetworkStatus.State> = NetworkStatus.mState

    /**
     * Return live-data holding the update state of product detail
     */
    fun getIsUpdatedProductDetail(): MutableLiveData<Boolean> = mIsUpdatedProductDetail

    /**
     * Set live-data holding the update state of product detail with flags
     */
    fun setIsUpdatedProductDetail(flag: Boolean) {
        mIsUpdatedProductDetail.postValue(flag)
    }

    /**
     * Fetch the detail of product
     */
    fun fetchProductDetail(productId: Int?): LiveData<DetailViewItem> {

        var productDetail = MutableLiveData<DetailViewItem>()

        NetworkStatus.updateNetworkState(NetworkStatus.State.LOADING)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                HwahaeWebService.service.getProductDetail(productId).apply {
                    withContext(Dispatchers.Main) {
                        NetworkStatus.updateNetworkState(NetworkStatus.State.DONE)
                        productDetail.value = this@apply.body
                    }
                }
            } catch (throwable: Throwable) {
                throwable.printStackTrace()
                NetworkStatus.updateNetworkState(NetworkStatus.State.FAILED)
            }
        }

        return productDetail
    }

    /**
     * Fetch new data
     */
    fun fetchProductList() { mProductList.value?.dataSource?.invalidate() }
}


