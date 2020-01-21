package com.lmy.hwahae.datasoruce

import androidx.paging.PageKeyedDataSource
import com.lmy.hwahae.datasoruce.api.HwahaeWebService
import com.lmy.hwahae.datasoruce.status.NetworkStatus
import com.lmy.hwahae.datasoruce.model.IndexViewProduct
import com.lmy.hwahae.ui.status.UiStatus
import kotlinx.coroutines.*
import java.net.SocketTimeoutException

class HwahaeDataSource: PageKeyedDataSource<Int, IndexViewProduct>() {

    /**
     * Initial Page Key
     */
    private val INITIAL_PAGE_KEY = 1

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, IndexViewProduct>) {

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
                HwahaeWebService.service.getProductList(UiStatus.currentSkinType, INITIAL_PAGE_KEY, UiStatus.currentSearchKeyword).apply {
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

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, IndexViewProduct>) {

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
                HwahaeWebService.service.getProductList(UiStatus.currentSkinType, params.key, UiStatus.currentSearchKeyword).apply {
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

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, IndexViewProduct>) { /* Do nothing */ }
}