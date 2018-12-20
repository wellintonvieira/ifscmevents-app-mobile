package br.com.ifscmeventsapp.retrofit

import br.com.ifscmeventsapp.extension.callback
import br.com.ifscmeventsapp.model.Event
import br.com.ifscmeventsapp.model.Speaker
import br.com.ifscmeventsapp.model.Talk

class RetrofitWebService{

    fun getEvents(success : (events : List<Event>) -> Unit,
                  failure : (throwable : Throwable) -> Unit,
                  event : String){
        if(event == "all"){
            val call = RetrofitInitializer().retrofitService().getAllEvents()
            call.enqueue(callback({ response ->
                response?.body()?.let {
                    success(it)
                }
            }, { throwable ->
                throwable?.let {
                    failure(it)
                }
            }))
        }else{
            val call = RetrofitInitializer().retrofitService().getNextEvents()
            call.enqueue(callback({ response ->
                response?.body()?.let {
                    success(it)
                }
            }, { throwable ->
                throwable?.let {
                    failure(it)
                }
            }))
        }
    }

    fun getSpeakers(success : (speakers : List<Speaker>) -> Unit,
                    failure : (throwable : Throwable) -> Unit,
                    id : Long){

        val call = RetrofitInitializer().retrofitService().getSpeakers(id)
        call.enqueue(callback({ response ->
            response?.body()?.let {
                success(it)
            }
        }, { throwable ->
            throwable?.let {
                failure(it)
            }
        }))
    }

    fun getTalks(success : (talks : List<Talk>) -> Unit,
                 failure : (throwable : Throwable) -> Unit,
                 id : Long){

        val call = RetrofitInitializer().retrofitService().getTalks(id)
        call.enqueue(callback({ response ->
            response?.body()?.let {
                success(it)
            }
        }, { throwable ->
            throwable?.let {
                failure(it)
            }
        }))
    }
}