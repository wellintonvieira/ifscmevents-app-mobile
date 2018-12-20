package br.com.ifscmeventsapp.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import br.com.ifscmeventsapp.R
import br.com.ifscmeventsapp.adapter.TalkAdapter
import br.com.ifscmeventsapp.database.DatabaseHelper
import br.com.ifscmeventsapp.model.Event
import br.com.ifscmeventsapp.model.Talk
import br.com.ifscmeventsapp.retrofit.RetrofitWebService
import kotlinx.android.synthetic.main.fragment_talk.*

class TalkFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_talk, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val event = arguments?.getSerializable("event") as Event?
        var talks = listOf<Talk>()
        val database = DatabaseHelper(layoutInflater.context)

        if(event != null){
            RetrofitWebService().getTalks({
                if(it.isEmpty()){
                    Toast.makeText(activity, getString(R.string.message_talks), Toast.LENGTH_LONG).show()
                }else{
                    talks = it
                    database!!.addTalks(talks, event.id)
                    recyclerViewList(talks, event)
                }},{
                    talks = database!!.getTalks(event.id)
                    if(talks.isEmpty()){
                        Toast.makeText(activity, getString(R.string.message_talks), Toast.LENGTH_LONG).show()
                    }
                    recyclerViewList(talks, event)
                }, event.id)
            recyclerViewList(talks, event)
        }else{
            Toast.makeText(activity, getString(R.string.message_erro), Toast.LENGTH_SHORT).show()
        }
    }

    private fun recyclerViewList(talks : List<Talk>, event: Event){

        val recyclerView = recyclerViewTalks
        val layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = TalkAdapter(talks, event)
    }
}



