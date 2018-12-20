package br.com.ifscmeventsapp.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import br.com.ifscmeventsapp.R
import kotlinx.android.synthetic.main.fragment_feedback.*
import android.content.Intent

class FeedbackFragment : Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_feedback, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val editTextFeedback = editTextFeedBack
        val feedback = buttonFeedBack

        feedback.setOnClickListener{

            val feedbackText = editTextFeedback.text

            if(feedbackText.isEmpty()){
                Toast.makeText(activity, getString(R.string.feedback_text), Toast.LENGTH_LONG).show()
            }else{
                val email = Intent(Intent.ACTION_SEND)
                email.putExtra(Intent.EXTRA_EMAIL, arrayOf(getString(R.string.email_contact)))
                email.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.feedback))
                email.putExtra(Intent.EXTRA_TEXT, feedbackText)
                email.setType("message/rfc822");
                startActivity(Intent.createChooser(email, getString(R.string.choose_client_email)));
            }
        }
    }
}
