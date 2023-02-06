package com.jaehyeon.utilclasscollections.extention

fun <T> Map<T, Int>.isHasSameMaxValue(): Boolean {

    val max = this.maxByOrNull { it.value }?.key
    val pair = max to this[max]

    this.forEach { (t, u) ->
        if (pair.first != t) {
            if (pair.second == u) {
                return true
            }
        }
    }

    return false
}