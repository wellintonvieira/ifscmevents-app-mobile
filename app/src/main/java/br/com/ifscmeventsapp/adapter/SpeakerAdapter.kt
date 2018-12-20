package br.com.ifscmeventsapp.adapter

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.Adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.ifscmeventsapp.R
import br.com.ifscmeventsapp.activity.SpeakerDetailActivity
import br.com.ifscmeventsapp.model.Speaker
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.adapter_list_speaker.view.*

class SpeakerAdapter(private val speakers: List<Speaker>) : Adapter<SpeakerAdapter.ViewHolder>(){

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val speaker = speakers[position]
        holder?.let {
            it.bindView(speaker)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_list_speaker, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return speakers.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        fun bindView(speaker: Speaker){

            val name = itemView.textViewName
            val formation = itemView.textViewFormation
            val image = itemView.imageViewImage
            val url = "https://ifscmeventsapp-95d5b.appspot.com"+speaker.image

            name.text = speaker.name
            formation.text = speaker.formation
            Picasso.with(image.context).load(url).into(itemView.imageViewImage)

            itemView.setOnClickListener{
                if(itemView != null){
                    val intent = Intent(itemView?.context, SpeakerDetailActivity::class.java)
                    val bundle = Bundle()
                    bundle.putSerializable("speaker", speaker)
                    intent.putExtra("bundle", bundle)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }
}
