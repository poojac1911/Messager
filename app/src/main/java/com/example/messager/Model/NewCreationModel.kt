package com.example.kotlin

data class NewCreationModel(
//    val img_userName: String,
    val userId: Long,
    val userName: String,
    val phoneNumber: String,
    var isRadioButtonVisible: Boolean = false,
    var isRadioButtonChecked: Boolean = false
)
