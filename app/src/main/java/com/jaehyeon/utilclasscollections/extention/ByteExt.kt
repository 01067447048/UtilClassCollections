package com.jaehyeon.utilclasscollections.extention

fun Byte.toHexString(prefix: String = "0x"): String {
    return String.format("$prefix$02x", this)
}

fun Byte.toPositiveInt(): Int = this.toInt() and 0xFF

infix fun Byte.and(mask: Int): Int = this.toInt() and mask