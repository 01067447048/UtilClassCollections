package com.jaehyeon.utilclasscollections.utils

sealed class ErrorEvent(val message: UiText) {

    class ShowToastError(message: UiText): ErrorEvent(message)
    class ShowDialogError(message: UiText): ErrorEvent(message)

}
