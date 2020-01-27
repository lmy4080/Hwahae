package com.lmy.hwahae.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.lmy.hwahae.datasoruce.remote.status.NetworkStatus
import com.lmy.hwahae.ui.status.IndexViewStatus
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Spy
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SharedViewModelTest {

    @Spy
    val sharedViewModel = SharedViewModel()

    @get: Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
    }

    @Test
    fun setSearchKeyword() {

        val testSearchKeyword = "This is a text line"
        sharedViewModel.setSearchKeyword(testSearchKeyword)
        Assert.assertEquals(testSearchKeyword, IndexViewStatus.currentSearchKeyword)
    }

    @Test
    fun setSkinType() {

        var testSkinType = "0"
        sharedViewModel.setSkinType(testSkinType)
        Assert.assertEquals("oily", IndexViewStatus.currentSkinType)

        testSkinType = "1"
        sharedViewModel.setSkinType(testSkinType)
        Assert.assertEquals("oily",IndexViewStatus.currentSkinType)

        testSkinType = "2"
        sharedViewModel.setSkinType(testSkinType)
        Assert.assertEquals("dry", IndexViewStatus.currentSkinType)

        testSkinType = "3"
        sharedViewModel.setSkinType(testSkinType)
        Assert.assertEquals("sensitive", IndexViewStatus.currentSkinType)
    }

    @Test
    fun getNetworkState() {

        val testState = MutableLiveData<NetworkStatus.State>()
        testState.postValue(NetworkStatus.State.LOADING)
        Mockito.`when`(sharedViewModel.getNetworkState()).thenReturn(testState)
        Assert.assertEquals(sharedViewModel.getNetworkState().value, testState.value)

        testState.postValue(NetworkStatus.State.RETRY)
        Mockito.`when`(sharedViewModel.getNetworkState()).thenReturn(testState)
        Assert.assertEquals(sharedViewModel.getNetworkState().value, testState.value)

        testState.postValue(NetworkStatus.State.FAILED)
        Mockito.`when`(sharedViewModel.getNetworkState()).thenReturn(testState)
        Assert.assertEquals(sharedViewModel.getNetworkState().value, testState.value)

        testState.postValue(NetworkStatus.State.DONE)
        Mockito.`when`(sharedViewModel.getNetworkState()).thenReturn(testState)
        Assert.assertEquals(sharedViewModel.getNetworkState().value, testState.value)
    }

    @Test
    fun getIsUpdatedProductDetail() {

        val testUpdateFlag = MutableLiveData<Boolean>()
        testUpdateFlag.postValue(true)
        Mockito.`when`(sharedViewModel.getIsUpdatedProductDetail()).thenReturn(testUpdateFlag)
        Assert.assertEquals(sharedViewModel.getIsUpdatedProductDetail().value, testUpdateFlag.value)

        testUpdateFlag.postValue(false)
        Mockito.`when`(sharedViewModel.getIsUpdatedProductDetail()).thenReturn(testUpdateFlag)
        Assert.assertEquals(sharedViewModel.getIsUpdatedProductDetail().value, testUpdateFlag.value)
    }

    @Test
    fun setIsUpdatedProductDetail() {

        var testFlag = true
        sharedViewModel.setIsUpdatedProductDetail(testFlag)
        Assert.assertEquals(sharedViewModel.getIsUpdatedProductDetail().value, testFlag)

        testFlag = false
        sharedViewModel.setIsUpdatedProductDetail(testFlag)
        Assert.assertEquals(sharedViewModel.getIsUpdatedProductDetail().value, testFlag)
    }
}