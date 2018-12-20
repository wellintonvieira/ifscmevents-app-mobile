package br.com.ifscmeventsapp.model

import java.io.Serializable

data class Event(
        val id: Long,
        val name: String,
        val desc: String,
        val startDate: String,
        val finishDate: String,
        val image: String,
        val latitude: String,
        val longitude: String) : Serializable