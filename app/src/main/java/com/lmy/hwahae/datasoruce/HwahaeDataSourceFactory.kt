package com.lmy.hwahae.datasoruce

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.lmy.hwahae.datasoruce.model.IndexViewProduct

class HwahaeDataSourceFactory: DataSource.Factory<Int, IndexViewProduct>() {

    /**
     * Mutable LiveData holding ProductList
     */
    val mProductListLiveData = MutableLiveData<HwahaeDataSource>()

    /**
     * Called when fetching new data from backend-api server
     */
    override fun create(): DataSource<Int, IndexViewProduct> {
        var productDataSource = HwahaeDataSource()
        mProductListLiveData.postValue(productDataSource)
        return productDataSource
    }
}