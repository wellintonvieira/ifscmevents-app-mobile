package br.com.ifscmeventsapp.model

import java.io.Serializable

data class Talk(
        val id: Long,
        val idEvent: Long,
        val name: String,
        val category: String,
        val desc: String,
        val maxPeople: Int,
        val date: String,
        val startTime: String,
        val finishTime: String,
        val location: String) : Serializable