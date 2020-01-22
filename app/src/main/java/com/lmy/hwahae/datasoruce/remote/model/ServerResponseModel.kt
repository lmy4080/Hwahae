package com.lmy.hwahae.datasoruce.remote.model

/**
 * Server Response Model for IndexView
 */
data class IndexViewDataModel (
    var statusCode: Int,
    var body: List<IndexViewProduct>
)

data class IndexViewProduct (
    var id: Int,
    var price: String,
    var oily_score: Int,
    var sensitive_score: Int,
    var dry_score: Int,
    var thumbnail_image: String,
    var title: String
)

/**
 * Server Response Model for DetailView
 */
data class DetailViewModel (
    var statusCode: Int,
    var body: DetailViewProduct
)

data class DetailViewProduct (
    var id: Int,
    var full_size_image: String,
    var title: String,
    var description: String,
    var price: String,
    var oily_score: Int,
    var dry_score: Int,
    var sensitive_score: Int
)