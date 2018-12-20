package br.com.ifscmeventsapp.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInitializer {

    private val retrofit = Retrofit.Builder()

            .baseUrl("https://ifscmeventsapp-95d5b.appspot.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    fun retrofitService() = retrofit.create(RetrofitInterface::class.java)!!
}
