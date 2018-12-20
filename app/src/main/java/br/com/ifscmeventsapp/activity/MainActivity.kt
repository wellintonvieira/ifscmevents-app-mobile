package br.com.ifscmeventsapp.activity

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import br.com.ifscmeventsapp.R
import br.com.ifscmeventsapp.fragment.FeedbackFragment
import br.com.ifscmeventsapp.fragment.RecyclerViewFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main_app_bar.*
import android.os.Handler
import android.view.View
import br.com.ifscmeventsapp.extension.checkInternet
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main_content.*
import kotlinx.android.synthetic.main.activity_main_nav_header.*
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, GoogleApiClient.OnConnectionFailedListener {

    private var bundle = Bundle()
    private var handler = Handler()
    lateinit var mGoogleApiClient: GoogleApiClient
    lateinit var mFirebaseAuth: FirebaseAuth
    lateinit var mFirebaseAuthListener: FirebaseAuth.AuthStateListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setSupportActionBar(toolbar)

        title = getString(R.string.app_name)
        progressBarLoading.visibility = View.INVISIBLE

        val toggle = ActionBarDrawerToggle(this, navigation_drawer, toolbar, R.string.app_name, R.string.app_name)
        navigation_drawer.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)

        if(checkInternet(this)){
            toast(getString(R.string.message_internet))
        }

        val mGoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()

        mGoogleApiClient = GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, mGoogleSignInOptions)
                .build()

        mFirebaseAuth = FirebaseAuth.getInstance()
        mFirebaseAuthListener = FirebaseAuth.AuthStateListener {
            val firebaseUser = mFirebaseAuth.currentUser
            if(firebaseUser != null){
                updateUI(firebaseUser)
            }else{
                logout()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        mFirebaseAuth.addAuthStateListener(mFirebaseAuthListener)
    }

    override fun onStop() {
        super.onStop()
        if(mFirebaseAuth != null){
            mFirebaseAuth.removeAuthStateListener(mFirebaseAuthListener)
        }
    }

    private fun updateUI(firebaseUser: FirebaseUser) {
        progressBarLoading.visibility = View.VISIBLE
        handler.postDelayed({
            textViewUser.text = firebaseUser.displayName.toString()
            textViewEmail.text = firebaseUser.email.toString()
            Picasso.with(this).load(firebaseUser.photoUrl.toString()).into(imageViewUser)
            progressBarLoading.visibility = View.INVISIBLE
        }, 1000)
    }

    private fun logout(){
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    private fun revoke(){
        mFirebaseAuth.signOut()
        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback {
            if(it.isSuccess){
                logout()
            }
        }
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        toast(getString(R.string.message_erro))
    }

    override fun onBackPressed() {
        if (navigation_drawer.isDrawerOpen(GravityCompat.START)) {
            navigation_drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        val transaction = supportFragmentManager.beginTransaction()
        val attachedFragment = supportFragmentManager.findFragmentById(R.id.layoutFragmentActivityMain)

        if(attachedFragment != null) {
            transaction.remove(attachedFragment)
        }

        when (item.itemId) {
            R.id.nav_next_events -> {
                loading(handler)
                val recyclerViewFragment = RecyclerViewFragment()
                title = getString(R.string.next_events)
                bundle.putString("event", "next")
                recyclerViewFragment.arguments = bundle
                transaction.add(R.id.layoutFragmentActivityMain, recyclerViewFragment, "next")
            }
            R.id.nav_all_events -> {
                loading(handler)
                val recyclerViewFragment = RecyclerViewFragment()
                title = getString(R.string.all_events)
                bundle.putString("event", "all")
                recyclerViewFragment.arguments = bundle
                transaction.add(R.id.layoutFragmentActivityMain, recyclerViewFragment, "all")
            }
            R.id.nav_feedback -> {
                loading(handler)
                val feedbackFragment = FeedbackFragment()
                title = getString(R.string.feedback)
                transaction.add(R.id.layoutFragmentActivityMain, feedbackFragment, "feedback")
            }
            R.id.nav_logout -> {
                mFirebaseAuth.signOut()
                Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback {
                    if(it.isSuccess){
                        revoke()
                    }
                }
            }
        }
        navigation_drawer.closeDrawer(GravityCompat.START)
        transaction.commit()
        return true
    }

    private fun loading(handler : Handler){
        progressBarLoading.visibility = View.VISIBLE
        handler.postDelayed({
            progressBarLoading.visibility = View.INVISIBLE
        }, 1000)
    }
}
