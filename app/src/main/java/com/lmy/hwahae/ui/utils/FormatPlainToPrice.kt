package com.lmy.hwahae.ui.utils

import java.text.NumberFormat
import java.util.*

/**
 * Format plain text to price, '10000' -> '10,000원'
 */
object FormatPlainToPrice {
    fun start(price: String?): String =
        NumberFormat.getNumberInstance(Locale.KOREA).format(price?.toInt()) + "원"
}