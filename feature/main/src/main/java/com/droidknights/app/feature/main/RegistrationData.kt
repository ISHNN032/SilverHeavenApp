package com.droidknights.app.feature.main
data class RegistrationData(
    var name: String? = null,
    var phoneNumber: String? = null,
    var birthday: String? = null,
    var location: String? = null,
    var job: String? = null,
    var hobby: String? = null,
    var email: String? = null,
    var password: String? = null
)

class Sample {
    var name = "이현아"
    var phoneNumber = "1"
    var birthday = "2"
    var location = "3"
    var job = "4"
    var hobby = "5"
    var email = "6"
    var password = "7"
}