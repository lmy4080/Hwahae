package com.lmy.hwahae.datasoruce.remote

import androidx.paging.PageKeyedDataSource
import com.lmy.hwahae.datasoruce.remote.api.HwahaeWebService
import com.lmy.hwahae.datasoruce.remote.model.IndexViewItem
import com.lmy.hwahae.datasoruce.remote.status.NetworkStatus
import com.lmy.hwahae.ui.status.IndexViewStatus
import kotlinx.coroutines.*
import java.net.SocketTimeoutException

class HwahaeDataSource: PageKeyedDataSource<Int, IndexViewItem>() {

    /**
     * Initial Page Key
     */
    private val INITIAL_PAGE_KEY = 1

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, IndexViewItem>) {

        /**
         * Define the coroutine exception handler
         */
        val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->

            when(throwable){
                /* Socket Time-Out Exception */
                is SocketTimeoutException -> loadInitial(params,callback)

                /* other Exceptions */

            }
            throwable.printStackTrace()
        }

        /**
         * Fetch data from backend-api server
         */
        NetworkStatus.updateNetworkState(NetworkStatus.State.LOADING)
        CoroutineScope(Dispatchers.IO + coroutineExceptionHandler).launch {
            try {
                HwahaeWebService.service.getProductList(IndexViewStatus.currentSkinType, INITIAL_PAGE_KEY, IndexViewStatus.currentSearchKeyword).apply {
                    withContext(Dispatchers.Main) {
                        callback.onResult(this@apply.body, 0, this@apply.body.size, null, INITIAL_PAGE_KEY+1)
                        NetworkStatus.updateNetworkState(NetworkStatus.State.DONE)
                    }
                }
            }
            catch(throwable: Throwable) {
                withContext(Dispatchers.Main) {
                    NetworkStatus.updateNetworkState(NetworkStatus.State.FAILED)
                }
                throw throwable
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, IndexViewItem>) {

        /**
         * Define the coroutine exception handler
         */
        val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->

            when(throwable){
                /* Socket Time-Out Exception */
                is SocketTimeoutException -> loadAfter(params,callback)

                /* other Exceptions */

            }
            throwable.printStackTrace()
        }

        /**
         * Fetch data from backend-api server
         */
        NetworkStatus.updateNetworkState(NetworkStatus.State.LOADING)
        CoroutineScope(Dispatchers.IO + coroutineExceptionHandler).launch {
            try {
                HwahaeWebService.service.getProductList(IndexViewStatus.currentSkinType, params.key, IndexViewStatus.currentSearchKeyword).apply {
                    withContext(Dispatchers.Main) {
                        callback.onResult(this@apply.body, params.key+1)
                        NetworkStatus.updateNetworkState(NetworkStatus.State.DONE)
                    }
                }
            }
            catch(throwable: Throwable) {
                withContext(Dispatchers.Main) {
                    NetworkStatus.updateNetworkState(NetworkStatus.State.FAILED)
                }
                throw throwable
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, IndexViewItem>) { /* Do nothing */ }
}