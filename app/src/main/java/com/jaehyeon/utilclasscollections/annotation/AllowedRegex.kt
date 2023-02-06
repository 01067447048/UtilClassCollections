package com.jaehyeon.utilclasscollections.annotation

@Target(AnnotationTarget.FIELD, AnnotationTarget.FUNCTION)
annotation class AllowedRegex(val regex: String)
