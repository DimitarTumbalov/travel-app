package com.synergygfs.travelapp

class Constants {

    companion object {

        val VALIDATION_REGEX_CITY_NAME =
            Regex("^[a-zA-Z0-9-' ]{2,50}$")

        val VALIDATION_REGEX_CITY_DESCRIPTION =
            Regex("^[\\S\\s]{1,500}\$")

    }
}