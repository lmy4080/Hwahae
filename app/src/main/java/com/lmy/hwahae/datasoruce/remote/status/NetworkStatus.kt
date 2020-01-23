package com.lmy.hwahae.datasoruce.remote.status

import androidx.lifecycle.MutableLiveData

/**
 * Network Status
 *
 * LOADING - Starting to fetch new data
 * RETRY - Restarting to fetch new data
 * FAILED - Failed to fetch new data
 * DONE - Finished the network job
 */
object NetworkStatus {

    enum class State {
        LOADING, RETRY, FAILED, DONE
    }

    var mState: MutableLiveData<State> = MutableLiveData()

    fun updateNetworkState(state: State) {
        mState.postValue(state)
    }
}