package com.coolguys.dndproject

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_character_appearance.*
import com.coolguys.dndproject.model.Character
import kotlinx.android.synthetic.main.activity_character_setup.*

class AppearanceFragment : Fragment() {
    var eye_color = Character.EYE_COLOR_BLUE
    var skin_color = Character.SKIN_COLOR_LIGHT

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_character_appearance, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        eyeColorBlue.setOnClickListener({changeEyeTickVisibility(0)
            eye_color = Character.EYE_COLOR_BLUE
            changeImg()})
        eyeColorRed.setOnClickListener({changeEyeTickVisibility(1)
            eye_color = Character.EYE_COLOR_RED
            changeImg()})
        eyeColorAmber.setOnClickListener({changeEyeTickVisibility(2)
            eye_color = Character.EYE_COLOR_AMBER
            changeImg()})
        eyeColorGreen.setOnClickListener({changeEyeTickVisibility(3)
            eye_color = Character.EYE_COLOR_GREEN
            changeImg()})
    }

    fun changeImg (){
        val character = Character(arguments!!.getInt(ARG_CHARACTER_RACE))
        character.character_class = activity!!.spinner.selectedItemPosition
        character.eyeColor = eye_color
        activity!!.characterImg.setImageDrawable(character.getDrawableForClass(context!!))
    }

    private fun changeEyeTickVisibility (pos: Int){
        eyeTickBlue.visibility = if (pos == 0) View.VISIBLE else View.GONE
        eyeTickRed.visibility = if (pos == 1) View.VISIBLE else View.GONE
        eyeTickAmber.visibility = if (pos == 2) View.VISIBLE else View.GONE
        eyeTickGreen.visibility = if (pos == 3) View.VISIBLE else View.GONE
        //eyeTickWhite.visibility = if (pos == 4) View.VISIBLE else View.GONE
    }

    /*private fun changeSkinTickVisibility (pos: Int) {
        skinTickLight.visibility = if (pos == 0) View.VISIBLE else View.GONE
        skinTickDark.visibility = if (pos == 1) View.VISIBLE else View.GONE
    }*/

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