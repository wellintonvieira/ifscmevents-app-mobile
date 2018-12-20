package br.com.ifscmeventsapp.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import br.com.ifscmeventsapp.R
import br.com.ifscmeventsapp.extension.formatDate
import br.com.ifscmeventsapp.extension.formatHour
import br.com.ifscmeventsapp.model.Talk
import kotlinx.android.synthetic.main.fragment_talk_content.*

class TalkDetailFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_talk_content, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val talk = arguments?.getSerializable("talk") as Talk?

        if(talk != null){
            textViewName.text = talk.name
            textViewCategory.text = talk.category
            textViewDate.text = talk.date!!.formatDate(talk.date)
            val time = talk.startTime.formatHour(talk.startTime)+" "+getString(R.string.of_item)+" "+talk.finishTime.formatHour(talk.finishTime)
            textViewTime.text = time
            textViewMaxPeople.text = talk.maxPeople.toString()
            textViewLocation.text = talk.location
            textViewDesc.text = talk.desc
        }else{
            Toast.makeText(activity, getString(R.string.message_erro), Toast.LENGTH_SHORT).show()
        }
    }
}
