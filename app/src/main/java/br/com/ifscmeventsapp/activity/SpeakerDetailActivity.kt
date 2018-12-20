package br.com.ifscmeventsapp.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import br.com.ifscmeventsapp.R
import br.com.ifscmeventsapp.model.Speaker
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_speaker_detail.*
import kotlinx.android.synthetic.main.activity_speaker_detail_content.*
import org.jetbrains.anko.toast

class SpeakerDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_speaker_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        title = getString(R.string.app_name)

        val bundle = intent.getBundleExtra("bundle")
        val speaker = bundle.getSerializable("speaker") as Speaker?

        if(speaker != null){
            textViewName.text = speaker.name
            textViewEmail.text = speaker.email
            textViewFormation.text = speaker.formation
            textViewBio.text = speaker.bio
            val url = "https://ifscmeventsapp-95d5b.appspot.com"+speaker.image
            Picasso.with(this).load(url).resize(1200, 700).into(imageViewImage)
        }else{
            toast(getString(R.string.message_erro))
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean  {
        if(item?.itemId == android. R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
