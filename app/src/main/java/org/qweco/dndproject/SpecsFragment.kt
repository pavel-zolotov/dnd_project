package org.qweco.dndproject

import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.InputFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_character_specs.*
import org.qweco.dndproject.model.Character
import org.qweco.dndproject.utils.InputFilterMinMax

class SpecsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_character_specs, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val character = Character(arguments!!.getInt(ARG_CHARACTER_RACE))
        txtStrengthValue.filters = arrayOf(InputFilterMinMax(1, 100))
        txtStrengthRaceBonus.text = "+ ${character.getRaceBonusStrength(context!!)}"
        txtDexterityValue.filters = arrayOf(InputFilterMinMax(1, 100))
        txtDexterityBonus.text = "+ ${character.getRaceBonusDexterity(context!!)}"
        txtConstitutionValue.filters = arrayOf(InputFilterMinMax(1, 100))
        txtConstitutionBonus.text  = "+ ${character.getRaceBonusConstitution(context!!)}"
        txtIntelligenceValue.filters = arrayOf(InputFilterMinMax(1, 100))
        txtIntelligenceBonus.text = "+ ${character.getRaceBonusIntelligence(context!!)}"
        txtWisdomValue.filters = arrayOf(InputFilterMinMax(1, 100))
        txtWisdomBonus.text = "+ ${character.getRaceBonusWisdom(context!!)}"
        txtCharismaValue.filters = arrayOf(InputFilterMinMax(1, 100))
        txtCharismaBonus.text = "+ ${character.getRaceBonusCharisma(context!!)}"
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