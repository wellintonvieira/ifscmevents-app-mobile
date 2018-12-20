package br.com.ifscmeventsapp.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import br.com.ifscmeventsapp.R
import br.com.ifscmeventsapp.adapter.TabAdapter
import br.com.ifscmeventsapp.model.Event
import br.com.ifscmeventsapp.model.Talk
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_talk.*
import org.jetbrains.anko.toast

class TalkActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_talk)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val bundle = intent.getBundleExtra("bundle")
        val event = bundle.getSerializable("event") as Event?
        val talk = bundle.getSerializable("talk") as Talk?

        if(talk != null){
            title = talk.name
            if(event != null) {
                val url = "https://ifscmeventsapp-95d5b.appspot.com" + event.image
                Picasso.with(this).load(url).resize(1200, 700).into(imageViewImage)
            }else{
                toast(getString(R.string.message_erro))
            }
            tabSelected(bundle, talk)
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

    private fun tabSelected(bundle: Bundle, talk: Talk){
        bundle.putSerializable("talk", talk)
        val adapter = TabAdapter(supportFragmentManager, this , bundle, talk)
        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)
    }
}