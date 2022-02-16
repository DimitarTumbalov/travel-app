package com.synergygfs.travelapp

class Constants {

    companion object {

        val VALIDATION_REGEX_NAME =
            Regex("^[a-zA-Z0-9-', ]{2,50}$")

        val VALIDATION_REGEX_DESCRIPTION =
            Regex("^[\\S\\s]{1,2000}\$")

    }
}