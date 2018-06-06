package org.qweco.dndproject

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_character_appearance.*
import org.qweco.dndproject.model.Character

class AppearanceFragment : Fragment() {
    var eye_color = Character.EYE_COLOR_BLUE
    var skin_color = Character.SKIN_COLOR_LIGHT

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_character_appearance, container, false)
        return rootView
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        eyeColorBlue.setOnClickListener({changeEyeTickVisibility(0)
            eye_color = Character.EYE_COLOR_BLUE})
        eyeColorRed.setOnClickListener({changeEyeTickVisibility(1)
            eye_color = Character.EYE_COLOR_RED})
        eyeColorAmber.setOnClickListener({changeEyeTickVisibility(2)
            eye_color = Character.EYE_COLOR_AMBER})
        eyeColorGreen.setOnClickListener({changeEyeTickVisibility(3)
            eye_color = Character.EYE_COLOR_GREEN})
        eyeColorWhite.setOnClickListener({changeEyeTickVisibility(4)
            eye_color = Character.EYE_COLOR_WHITE})

        skinColorLight.setOnClickListener({changeSkinTickVisibility(0)
            skin_color = Character.SKIN_COLOR_LIGHT})
        skinColorDark.setOnClickListener({changeSkinTickVisibility(1)
            skin_color = Character.SKIN_COLOR_DARK})
    }

    private fun changeEyeTickVisibility (pos: Int){
        eyeTickBlue.visibility = if (pos == 0) View.VISIBLE else View.GONE
        eyeTickRed.visibility = if (pos == 1) View.VISIBLE else View.GONE
        eyeTickAmber.visibility = if (pos == 2) View.VISIBLE else View.GONE
        eyeTickGreen.visibility = if (pos == 3) View.VISIBLE else View.GONE
        eyeTickWhite.visibility = if (pos == 4) View.VISIBLE else View.GONE
    }

    private fun changeSkinTickVisibility (pos: Int) {
        skinTickLight.visibility = if (pos == 0) View.VISIBLE else View.GONE
        skinTickDark.visibility = if (pos == 1) View.VISIBLE else View.GONE
    }

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private val ARG_CHARACTER_RACE = "race"

        private val ARG_CHARACTER_CLASS = "character_class"
        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        fun newInstance(race: Int, character_class: Int): AppearanceFragment {
            val fragment = AppearanceFragment()
            val args = Bundle()
            args.putInt(ARG_CHARACTER_RACE, race)
            args.putInt(ARG_CHARACTER_CLASS, character_class)
            fragment.arguments = args
            return fragment
        }
    }
}