package com.dima6120.core_api.model

enum class GenderModel(val value: String) {

    MALE("male"),
    FEMALE("female"),
    NONE("");

    companion object {

        fun fromValue(value: String?): GenderModel = entries.find { it.value == value } ?: NONE
    }
}