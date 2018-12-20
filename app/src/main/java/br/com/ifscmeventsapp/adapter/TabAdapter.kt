package br.com.ifscmeventsapp.adapter

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import br.com.ifscmeventsapp.R
import br.com.ifscmeventsapp.fragment.SpeakerFragment
import br.com.ifscmeventsapp.fragment.TalkDetailFragment
import br.com.ifscmeventsapp.model.Talk

class TabAdapter(fragment: FragmentManager?,
                 context: Context,
                 bundle: Bundle,
                 talk: Talk) : FragmentPagerAdapter(fragment) {

    private var bundle = bundle
    private var talk = talk
    private var context = context

    override fun getItem(position: Int): Fragment {

        bundle.putSerializable("talk", talk)

        return when (position){
            0 -> {
                val talkDetailFragment = TalkDetailFragment()
                talkDetailFragment.arguments = bundle
                talkDetailFragment
            }
            else -> {
                val speakerFragment = SpeakerFragment()
                speakerFragment.arguments = bundle
                speakerFragment
            }
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position){
            0 -> {
                context.getString(R.string.desc)
            }
            else -> {
                context.getString(R.string.speakers)
            }
        }
    }
}