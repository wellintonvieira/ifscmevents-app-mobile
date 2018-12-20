package br.com.ifscmeventsapp.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import br.com.ifscmeventsapp.R
import br.com.ifscmeventsapp.adapter.SpeakerAdapter
import br.com.ifscmeventsapp.database.DatabaseHelper
import br.com.ifscmeventsapp.model.Speaker
import br.com.ifscmeventsapp.model.Talk
import br.com.ifscmeventsapp.retrofit.RetrofitWebService
import kotlinx.android.synthetic.main.fragment_speaker.*

class SpeakerFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_speaker, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val talk = arguments?.getSerializable("talk") as Talk?
        var speakers = listOf<Speaker>()
        val database = DatabaseHelper(layoutInflater.context)

        if(talk != null){
            RetrofitWebService().getSpeakers({
                if(it.isEmpty()){
                    Toast.makeText(activity, getString(R.string.message_speakers), Toast.LENGTH_LONG).show()
                }else{
                    speakers = it
                    database.addSpeakers(speakers)
                    recyclerViewList(speakers)
                } },{
                    speakers = database.getSpeakers()
                    if(speakers.isEmpty()){
                        Toast.makeText(activity, getString(R.string.message_speakers), Toast.LENGTH_LONG).show()
                    }
                    recyclerViewList(speakers)
                }, talk.id)
            recyclerViewList(speakers)
        }else{
            Toast.makeText(activity, getString(R.string.message_erro), Toast.LENGTH_SHORT).show()
        }
    }

    private fun recyclerViewList(speakers: List<Speaker>){

        val recyclerView = recyclerViewSpeakers
        val layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = SpeakerAdapter(speakers)

    }
}
