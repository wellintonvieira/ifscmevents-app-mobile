package br.com.ifscmeventsapp.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import br.com.ifscmeventsapp.R
import br.com.ifscmeventsapp.adapter.EventAdapter
import br.com.ifscmeventsapp.database.DatabaseHelper
import br.com.ifscmeventsapp.model.Event
import br.com.ifscmeventsapp.retrofit.RetrofitWebService
import kotlinx.android.synthetic.main.fragment_recycler_view.*

class RecyclerViewFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_recycler_view, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val event = arguments?.getString("event")
        val database = DatabaseHelper(layoutInflater.context)

        if(event != null){

            var events = listOf<Event>()

            if (event == "all") {
                RetrofitWebService().getEvents({
                    if (it.isEmpty()) {
                        Toast.makeText(activity, getString(R.string.message_events), Toast.LENGTH_LONG).show()
                    } else {
                        events = it
                        database.addEvents(events)
                        recyclerViewList(events)
                    }
                }, {
                    events = database.getEvents()
                    if(events.isEmpty()){
                        Toast.makeText(activity, getString(R.string.message_events), Toast.LENGTH_LONG).show()
                    }
                    recyclerViewList(events)
                }, event)
            } else {
                RetrofitWebService().getEvents({
                    if (it.isEmpty()) {
                        Toast.makeText(activity, getString(R.string.message_events), Toast.LENGTH_LONG).show()
                    } else {
                        events = it
                        database.addEvents(events)
                        recyclerViewList(events)
                    }
                }, {
                    events = database.getEvents()
                    if(events.isEmpty()){
                        Toast.makeText(activity, getString(R.string.message_events), Toast.LENGTH_LONG).show()
                    }
                    recyclerViewList(events)
                }, event)
            }
            recyclerViewList(events)
        }else{
            Toast.makeText(activity, getString(R.string.message_erro), Toast.LENGTH_SHORT).show()
        }
    }

    private fun recyclerViewList(events : List<Event>){

        val recyclerView = recyclerViewEvents
        val layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = EventAdapter(events)
    }
}