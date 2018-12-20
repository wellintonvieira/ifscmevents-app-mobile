package br.com.ifscmeventsapp.retrofit

import br.com.ifscmeventsapp.model.Event
import br.com.ifscmeventsapp.model.Speaker
import br.com.ifscmeventsapp.model.Talk
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitInterface {

    @GET("get/event/")
    fun getAllEvents(): Call<List<Event>>

    @GET("get/event/next")
    fun getNextEvents(): Call<List<Event>>

    @GET("get/talk/{id}/speaker")
    fun getSpeakers(@Path("id") id: Long): Call<List<Speaker>>

    @GET("get/event/{id}/talk")
    fun getTalks(@Path("id") id: Long) : Call<List<Talk>>
}