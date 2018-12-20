package br.com.ifscmeventsapp.model

import java.io.Serializable

data class Speaker(
        val id: Long,
        val name: String,
        val email: String,
        val formation: String,
        val bio: String,
        val image: String) : Serializable