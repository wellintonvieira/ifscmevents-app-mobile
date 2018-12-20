package br.com.ifscmeventsapp.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import br.com.ifscmeventsapp.R
import br.com.ifscmeventsapp.extension.checkInternet
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.toast

class LoginActivity : AppCompatActivity(), GoogleApiClient.OnConnectionFailedListener {

    lateinit var mGoogleApiClient: GoogleApiClient
    lateinit var mFirebaseAuth: FirebaseAuth
    lateinit var mFirebaseAuthListener: FirebaseAuth.AuthStateListener
    private val REQUEST_CODE_SIGN_IN = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        progressBarLoading.visibility = View.INVISIBLE
        signInButtonLogin.visibility = View.VISIBLE

        if(checkInternet(this)){
            toast(getString(R.string.message_internet))
        }

        val mGoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

        mGoogleApiClient = GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, mGoogleSignInOptions)
                .build()

        signInButtonLogin.setOnClickListener {
            signIn()
        }

        mFirebaseAuth = FirebaseAuth.getInstance()
        mFirebaseAuthListener = FirebaseAuth.AuthStateListener {
            val firebaseUser = mFirebaseAuth.currentUser
            if(firebaseUser != null){
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
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

    private fun signIn() {
        val intent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient)
        startActivityForResult(intent, REQUEST_CODE_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_CODE_SIGN_IN){
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            updateUI(result)
        }
    }

    private fun updateUI(result: GoogleSignInResult) {
        if(result.isSuccess){
            progressBarLoading.visibility = View.VISIBLE
            signInButtonLogin.visibility = View.INVISIBLE
            result.signInAccount?.let {
                firebaseWithGoogle(it)
            }
        }else{
            toast(getString(R.string.message_erro))
        }
    }

    private fun firebaseWithGoogle(signInAccount: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(signInAccount.idToken, null)
        mFirebaseAuth.signInWithCredential(credential).addOnCompleteListener {
            if(!it.isSuccessful){
                toast(getString(R.string.message_erro))
                progressBarLoading.visibility = View.INVISIBLE
                signInButtonLogin.visibility = View.VISIBLE
            }
        }
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        toast(getString(R.string.message_erro))
    }
}