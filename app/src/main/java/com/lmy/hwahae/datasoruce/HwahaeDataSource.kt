package com.lmy.hwahae.datasoruce

import androidx.paging.PageKeyedDataSource
import com.lmy.hwahae.datasoruce.model.IndexViewProduct

class HwahaeDataSource: PageKeyedDataSource<Int, IndexViewProduct>() {
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, IndexViewProduct>
    ) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, IndexViewProduct>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, IndexViewProduct>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}