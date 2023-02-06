package com.jaehyeon.utilclasscollections.utils

/**
 * Created by Jaehyeon on 2023/02/06.
 */
sealed class DefaultException(message: String = ""): Exception(message) {

    class NotFoundError(log: String = ""): DefaultException(log)
    class UnAuthorizationError(log: String = ""): DefaultException(log)

}