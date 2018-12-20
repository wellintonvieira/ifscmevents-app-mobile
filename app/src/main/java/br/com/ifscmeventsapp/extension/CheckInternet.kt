package br.com.ifscmeventsapp.extension

import android.content.Context
import android.net.ConnectivityManager

fun checkInternet(context: Context) : Boolean{
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val netInfo = connectivityManager.activeNetworkInfo
    if(netInfo != null && netInfo.isConnected){
        return false
    }
    return true
}