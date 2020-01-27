package com.lmy.hwahae.repository

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
class HwahaeRepositoryTest {

    @Spy
    val hwahaeRepository = HwahaeRepository()

    @get: Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
    }

    @Test
    fun setSearchKeyword() {

        val testSearchKeyword = "This is a text line"
        hwahaeRepository.setSearchKeyword(testSearchKeyword)
        Assert.assertEquals(testSearchKeyword, IndexViewStatus.currentSearchKeyword)
    }

    @Test
    fun setSkinType() {

        var testSkinType = "0"
        hwahaeRepository.setSkinType(testSkinType)
        Assert.assertEquals("oily", IndexViewStatus.currentSkinType)

        testSkinType = "1"
        hwahaeRepository.setSkinType(testSkinType)
        Assert.assertEquals("oily",IndexViewStatus.currentSkinType)

        testSkinType = "2"
        hwahaeRepository.setSkinType(testSkinType)
        Assert.assertEquals("dry", IndexViewStatus.currentSkinType)

        testSkinType = "3"
        hwahaeRepository.setSkinType(testSkinType)
        Assert.assertEquals("sensitive", IndexViewStatus.currentSkinType)
    }

    @Test
    fun getState() {

        val testState = MutableLiveData<NetworkStatus.State>()
        testState.postValue(NetworkStatus.State.LOADING)
        Mockito.`when`(hwahaeRepository.getState()).thenReturn(testState)
        Assert.assertEquals(hwahaeRepository.getState().value, testState.value)

        testState.postValue(NetworkStatus.State.RETRY)
        Mockito.`when`(hwahaeRepository.getState()).thenReturn(testState)
        Assert.assertEquals(hwahaeRepository.getState().value, testState.value)

        testState.postValue(NetworkStatus.State.FAILED)
        Mockito.`when`(hwahaeRepository.getState()).thenReturn(testState)
        Assert.assertEquals(hwahaeRepository.getState().value, testState.value)

        testState.postValue(NetworkStatus.State.DONE)
        Mockito.`when`(hwahaeRepository.getState()).thenReturn(testState)
        Assert.assertEquals(hwahaeRepository.getState().value, testState.value)
    }

    @Test
    fun getIsUpdatedProductDetail() {

        val testUpdateFlag = MutableLiveData<Boolean>()
        testUpdateFlag.postValue(true)
        Mockito.`when`(hwahaeRepository.getIsUpdatedProductDetail()).thenReturn(testUpdateFlag)
        Assert.assertEquals(hwahaeRepository.getIsUpdatedProductDetail().value, testUpdateFlag.value)

        testUpdateFlag.postValue(false)
        Mockito.`when`(hwahaeRepository.getIsUpdatedProductDetail()).thenReturn(testUpdateFlag)
        Assert.assertEquals(hwahaeRepository.getIsUpdatedProductDetail().value, testUpdateFlag.value)
    }

    @Test
    fun setIsUpdatedProductDetail() {

        var testFlag = true
        hwahaeRepository.setIsUpdatedProductDetail(testFlag)
        Assert.assertEquals(hwahaeRepository.getIsUpdatedProductDetail().value, testFlag)

        testFlag = false
        hwahaeRepository.setIsUpdatedProductDetail(testFlag)
        Assert.assertEquals(hwahaeRepository.getIsUpdatedProductDetail().value, testFlag)
    }
}