package org.qweco.dndproject

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_character_specs.*
import org.qweco.dndproject.model.Character

class SpecsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_character_specs, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val character = Character(arguments!!.getInt(ARG_CHARACTER_RACE))
        txtStrengthRace.setText(character.getRaceBonusStrength(context!!).toString())
        txtDexterityRace.setText(character.getRaceBonusDexterity(context!!).toString())
        txtConstitutionRace.setText(character.getRaceBonusConstitution(context!!).toString())
        txtIntelligenceRace.setText(character.getRaceBonusIntelligence(context!!).toString())
        txtWisdomRace.setText(character.getRaceBonusWisdom(context!!).toString())
        txtCharismaRace.setText(character.getRaceBonusCharisma(context!!).toString())
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
        fun newInstance(race: Int, character_class: Int): SpecsFragment {
            val fragment = SpecsFragment()
            val args = Bundle()
            args.putInt(ARG_CHARACTER_RACE, race)
            args.putInt(ARG_CHARACTER_CLASS, character_class)
            fragment.arguments = args
            return fragment
        }
    }
}