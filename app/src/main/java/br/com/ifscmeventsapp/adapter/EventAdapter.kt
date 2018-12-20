package br.com.ifscmeventsapp.adapter

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.Adapter
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.ifscmeventsapp.R
import br.com.ifscmeventsapp.activity.EventActivity
import br.com.ifscmeventsapp.extension.formatDate
import br.com.ifscmeventsapp.model.Event
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.adapter_list_event.view.*

class EventAdapter(private val events: List<Event>) : Adapter<EventAdapter.ViewHolder>(){

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val event = events[position]
        holder?.let {
            it.bindView(event)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_list_event, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return events.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        fun bindView(event: Event){

            val name = itemView.textViewName
            val date = itemView.textViewDate
            val image = itemView.imageViewImage
            val url = "https://ifscmeventsapp-95d5b.appspot.com"+event.image

            name.text = event.name
            date.text = event.startDate.formatDate(event.startDate)
            Picasso.with(image.context).load(url).into(itemView.imageViewImage)

            itemView.setOnClickListener{
                if(itemView != null){
                    val intent = Intent(itemView?.context, EventActivity::class.java)
                    val bundle = Bundle()
                    bundle.putSerializable("event", event)
                    intent.putExtra("bundle", bundle)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }
}
