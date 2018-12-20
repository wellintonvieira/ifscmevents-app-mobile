package br.com.ifscmeventsapp.adapter

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.Adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.ifscmeventsapp.R
import br.com.ifscmeventsapp.activity.TalkActivity
import br.com.ifscmeventsapp.model.Event
import br.com.ifscmeventsapp.model.Talk
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.adapter_list_talk.view.*

class TalkAdapter(private val talks: List<Talk>,
                  private val event: Event) : Adapter<TalkAdapter.ViewHolder>(){

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val talk = talks[position]
        holder?.let {
            it.bindView(talk, event)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_list_talk, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return talks.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        fun bindView(talk: Talk, event: Event) {

            val image = itemView.imageViewImage
            val name = itemView.textViewName
            val category = itemView.textViewCategory
            val url = "https://ifscmeventsapp-95d5b.appspot.com"+event.image

            name.text = talk.name
            category.text = talk.category
            Picasso.with(image.context).load(url).into(itemView.imageViewImage)

            itemView.setOnClickListener{
                if(itemView != null){
                    val intent = Intent(itemView?.context, TalkActivity::class.java)
                    val bundle = Bundle()
                    bundle.putSerializable("event", event)
                    bundle.putSerializable("talk", talk)
                    intent.putExtra("bundle", bundle)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }
}
