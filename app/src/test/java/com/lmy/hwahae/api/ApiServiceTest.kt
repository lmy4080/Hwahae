package com.lmy.hwahae.api

import com.lmy.hwahae.datasoruce.remote.api.HwahaeWebService
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import java.net.HttpURLConnection.HTTP_OK

@RunWith(MockitoJUnitRunner::class)
class ApiServiceTest {

    var hwahaeWebService = HwahaeWebService

    @Test
    fun test_getProductList() {

        val testSkinType = "oily"
        val testInitialPageKey = 1
        val expectedScannedCount = 20

        runBlocking {
            val indexViewDataModel= hwahaeWebService.service.getProductList(testSkinType, testInitialPageKey, null)

            if(indexViewDataModel.statusCode == HTTP_OK) {
                println("$indexViewDataModel")

                assertEquals(indexViewDataModel.statusCode, HTTP_OK)
                assertEquals(indexViewDataModel.scanned_count, expectedScannedCount)
            }
        }
    }

    @Test
    fun test_getProductList_withSkinType() {

        val testSkinType = "sensitive"
        val testInitialPageKey = 1

        runBlocking {
            val indexViewDataModel= hwahaeWebService.service.getProductList(testSkinType, testInitialPageKey, null)

            if(indexViewDataModel.statusCode == HTTP_OK) {
                println("$indexViewDataModel")

                assertEquals(indexViewDataModel.statusCode, HTTP_OK)

                val productList = indexViewDataModel.body
                for(product in productList) {
                    val isTrue = product.sensitive_score > 0
                    assertEquals(isTrue, true)
                }
            }
        }
    }

    @Test
    fun test_getProductList_withSearchKeyword() {

        val testDefaultSkinType = "oily"
        val testSearchKeyword = "150ml"
        val testInitialPageKey = 1

        runBlocking {
            val indexViewDataModel= hwahaeWebService.service.getProductList(testDefaultSkinType, testInitialPageKey, testSearchKeyword)

            if(indexViewDataModel.statusCode == HTTP_OK) {
                println("$indexViewDataModel")

                assertEquals(indexViewDataModel.statusCode, HTTP_OK)

                val productList = indexViewDataModel.body
                for(product in productList) {
                    assertEquals(product.title.contains(testSearchKeyword), true)
                }
            }
        }
    }

    @Test
    fun test_getProductDetail_withProductId() {

        val testProductId = 536

        runBlocking {
            val detailViewModel = hwahaeWebService.service.getProductDetail(testProductId)

            if(detailViewModel.statusCode == HTTP_OK) {
                println("$detailViewModel")

                assertEquals(detailViewModel.statusCode, HTTP_OK)
                assertEquals(detailViewModel.body.id, testProductId)
            }
        }
    }
}