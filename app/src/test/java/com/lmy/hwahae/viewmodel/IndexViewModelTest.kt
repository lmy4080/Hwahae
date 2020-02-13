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
class IndexViewModelTest {

    @Spy
    val indexViewModel = IndexViewModel()

    @get: Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
    }

    @Test
    fun setSearchKeyword() {

        val testSearchKeyword = "This is a text line"
        indexViewModel.setSearchKeyword(testSearchKeyword)
        Assert.assertEquals(testSearchKeyword, IndexViewStatus.currentSearchKeyword)
    }

    @Test
    fun setSkinType() {

        var testSkinType = "0"
        indexViewModel.setSkinType(testSkinType)
        Assert.assertEquals("oily", IndexViewStatus.currentSkinType)

        testSkinType = "1"
        indexViewModel.setSkinType(testSkinType)
        Assert.assertEquals("oily",IndexViewStatus.currentSkinType)

        testSkinType = "2"
        indexViewModel.setSkinType(testSkinType)
        Assert.assertEquals("dry", IndexViewStatus.currentSkinType)

        testSkinType = "3"
        indexViewModel.setSkinType(testSkinType)
        Assert.assertEquals("sensitive", IndexViewStatus.currentSkinType)
    }

    @Test
    fun getNetworkState() {

        val testState = MutableLiveData<NetworkStatus.State>()
        testState.postValue(NetworkStatus.State.LOADING)
        Mockito.`when`(indexViewModel.getNetworkState()).thenReturn(testState)
        Assert.assertEquals(indexViewModel.getNetworkState().value, testState.value)

        testState.postValue(NetworkStatus.State.RETRY)
        Mockito.`when`(indexViewModel.getNetworkState()).thenReturn(testState)
        Assert.assertEquals(indexViewModel.getNetworkState().value, testState.value)

        testState.postValue(NetworkStatus.State.FAILED)
        Mockito.`when`(indexViewModel.getNetworkState()).thenReturn(testState)
        Assert.assertEquals(indexViewModel.getNetworkState().value, testState.value)

        testState.postValue(NetworkStatus.State.DONE)
        Mockito.`when`(indexViewModel.getNetworkState()).thenReturn(testState)
        Assert.assertEquals(indexViewModel.getNetworkState().value, testState.value)
    }

    @Test
    fun getIsUpdatedProductDetail() {

        val testUpdateFlag = MutableLiveData<Boolean>()
        testUpdateFlag.postValue(true)
        Mockito.`when`(indexViewModel.getIsUpdatedProductDetail()).thenReturn(testUpdateFlag)
        Assert.assertEquals(indexViewModel.getIsUpdatedProductDetail().value, testUpdateFlag.value)

        testUpdateFlag.postValue(false)
        Mockito.`when`(indexViewModel.getIsUpdatedProductDetail()).thenReturn(testUpdateFlag)
        Assert.assertEquals(indexViewModel.getIsUpdatedProductDetail().value, testUpdateFlag.value)
    }

    @Test
    fun setIsUpdatedProductDetail() {

        var testFlag = true
        indexViewModel.setIsUpdatedProductDetail(testFlag)
        Assert.assertEquals(indexViewModel.getIsUpdatedProductDetail().value, testFlag)

        testFlag = false
        indexViewModel.setIsUpdatedProductDetail(testFlag)
        Assert.assertEquals(indexViewModel.getIsUpdatedProductDetail().value, testFlag)
    }
}