package br.com.ifscmeventsapp.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import br.com.ifscmeventsapp.R
import br.com.ifscmeventsapp.extension.formatDate
import br.com.ifscmeventsapp.model.Event
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home_content.*

class HomeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val event: Event? = arguments?.getSerializable("event") as Event?

        if(event != null){
            textViewName.text = event.name
            textViewDesc.text = event.desc
            val date = event.startDate.formatDate(event.startDate)+" "+getString(R.string.of_item)+" "+event.finishDate.formatDate(event.finishDate)
            textViewDate.text = date
            val url = "https://ifscmeventsapp-95d5b.appspot.com"+event.image
            Picasso.with(context).load(url).resize(1200, 700).into(imageViewImage)
        }else{
            Toast.makeText(activity, getString(R.string.message_erro), Toast.LENGTH_SHORT).show()
        }
    }
}