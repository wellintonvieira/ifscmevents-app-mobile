package br.com.ifscmeventsapp.activity

import android.os.Bundle
import android.os.Handler
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import br.com.ifscmeventsapp.*
import br.com.ifscmeventsapp.fragment.HomeFragment
import br.com.ifscmeventsapp.fragment.LocationFragment
import br.com.ifscmeventsapp.fragment.TalkFragment
import br.com.ifscmeventsapp.model.Event
import kotlinx.android.synthetic.main.activity_event.*
import org.jetbrains.anko.toast

class EventActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener{

    private var bundle: Bundle? = null
    private var eventName : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        progressBarLoading.visibility = View.INVISIBLE

        val bundle = intent.getBundleExtra("bundle")
        val event = bundle.getSerializable("event") as Event?

        if(bundle != null && event != null){
            title = event.name
            this.eventName = event.name
            bundle.putSerializable("event", event)
            this.bundle = bundle
        }else{
            toast(getString(R.string.message_erro))
        }

        navigation_bar.setOnNavigationItemSelectedListener(this)

        if(savedInstanceState == null) {
            navigation_bar.selectedItemId = R.id.navigation_home
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean  {
        if(item?.itemId == android. R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        val transaction = supportFragmentManager.beginTransaction()
        val attachedFragment = supportFragmentManager.findFragmentById(R.id.layoutFragmentActivityEvent)
        val handler = Handler()

        if(attachedFragment != null) {
            transaction.remove(attachedFragment)
        }

        when (item.itemId) {
            R.id.navigation_home -> {
                val homeFragment = HomeFragment()
                title = eventName
                homeFragment.arguments = bundle
                transaction.add(R.id.layoutFragmentActivityEvent, homeFragment, "home")
            }
            R.id.navigation_talk -> {
                val talkFragment = TalkFragment()
                title = getString(R.string.talks)
                talkFragment.arguments = bundle
                progressBarLoading.visibility = View.VISIBLE
                handler.postDelayed({
                    progressBarLoading.visibility = View.INVISIBLE
                },1000)
                transaction.add(R.id.layoutFragmentActivityEvent, talkFragment, "talk")
            }
            R.id.navigation_location-> {
                val locationFragment = LocationFragment()
                title = getString(R.string.map)
                locationFragment.arguments = bundle
                transaction.add(R.id.layoutFragmentActivityEvent, locationFragment, "location")
            }
        }
        transaction.commit()
        return true
    }
}
