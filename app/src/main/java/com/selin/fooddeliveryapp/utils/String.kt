package com.selin.fooddeliveryapp.utils

fun String?.toSafeInt(): Int {
    return this?.toIntOrNull() ?: 0
}