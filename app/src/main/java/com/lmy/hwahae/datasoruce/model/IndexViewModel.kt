package com.lmy.hwahae.datasoruce.model

data class IndexViewModel (
    var statusCode: Int,
    var body: List<Product>

)

data class Product(
    var id: Int,
    var price: String,
    var oily_score: Int,
    var sensitive_score: Int,
    var dry_score: Int,
    var thumbnail_image: String,
    var title: String
)