package com.lmy.hwahae.datasoruce.remote.status

import androidx.lifecycle.MutableLiveData

/**
 * Network Status
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