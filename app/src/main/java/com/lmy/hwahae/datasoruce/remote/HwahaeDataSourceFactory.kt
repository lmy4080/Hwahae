package com.lmy.hwahae.datasoruce.remote

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.lmy.hwahae.datasoruce.remote.model.IndexViewItem

class HwahaeDataSourceFactory: DataSource.Factory<Int, IndexViewItem>() {

    /**
     * Mutable LiveData holding ProductList
     */
    val mProductListLiveData = MutableLiveData<HwahaeDataSource>()

    /**
     * Called to fetch new data from backend-api server
     */
    override fun create(): DataSource<Int, IndexViewItem> {
        var productDataSource = HwahaeDataSource()
        mProductListLiveData.postValue(productDataSource)
        return productDataSource
    }
}