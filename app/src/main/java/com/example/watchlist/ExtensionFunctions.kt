package com.example.watchlist

import java.text.SimpleDateFormat
import java.util.*

fun Date.readableFormat(): String{
    val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
    return dateFormat.format(this)
}