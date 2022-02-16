package com.synergygfs.travelapp

class Constants {

    companion object {

        val VALIDATION_REGEX_NAME =
            Regex("^[\\S\\s]{2,50}$")

        val VALIDATION_REGEX_DESCRIPTION =
            Regex("^[\\S\\s]{1,2000}\$")

        const val DELETION_ERROR_CITY_WITH_LANDMARKS = -2
    }
}