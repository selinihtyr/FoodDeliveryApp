package com.selin.fooddeliveryapp.utils.extension

fun String?.toSafeInt(): Int {
    return this?.toIntOrNull() ?: 0
}