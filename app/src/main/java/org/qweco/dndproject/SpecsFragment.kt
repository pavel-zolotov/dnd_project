package org.qweco.dndproject

import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_character_setup.*
import kotlinx.android.synthetic.main.fragment_character_specs.*
import kotlinx.android.synthetic.main.fragment_character_specs.view.*
import org.qweco.dndproject.model.Character
import org.qweco.dndproject.utils.InputFilterMinMax

class SpecsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_character_specs, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            scrollLayout.setOnScrollChangeListener({ _: View, scrollX: Int, scrollY: Int, oldScrollX:Int, oldScrollY:Int ->
                if (scrollY > oldScrollY) {
                    activity!!.fab.hide()
                }else{
                    activity!!.fab.show()
                }
            })
        }

        txtInitiativeValue.filters = arrayOf(InputFilterMinMax(0, 100))
        txtHpValue.filters = arrayOf(InputFilterMinMax(0, 100))
        txtSpeedValue.filters = arrayOf(InputFilterMinMax(0, 100))
        txtHitDiceValue.filters = arrayOf(InputFilterMinMax(0, 100))
        txtArmourClassValue.filters = arrayOf(InputFilterMinMax(0, 100))
        txtProficiencyValue.filters = arrayOf(InputFilterMinMax(0, 100))

        val character = Character(arguments!!.getInt(ARG_CHARACTER_RACE))
        txtStrengthValue.filters = arrayOf(InputFilterMinMax(0, 100))
        txtStrengthRaceBonus.text = "+ ${character.getRaceBonusStrength(context!!)}"
        txtDexterityValue.filters = arrayOf(InputFilterMinMax(0, 100))
        txtDexterityBonus.text = "+ ${character.getRaceBonusDexterity(context!!)}"
        txtConstitutionValue.filters = arrayOf(InputFilterMinMax(0, 100))
        txtConstitutionBonus.text  = "+ ${character.getRaceBonusConstitution(context!!)}"
        txtIntelligenceValue.filters = arrayOf(InputFilterMinMax(0, 100))
        txtIntelligenceBonus.text = "+ ${character.getRaceBonusIntelligence(context!!)}"
        txtWisdomValue.filters = arrayOf(InputFilterMinMax(0, 100))
        txtWisdomBonus.text = "+ ${character.getRaceBonusWisdom(context!!)}"
        txtCharismaValue.filters = arrayOf(InputFilterMinMax(0, 100))
        txtCharismaBonus.text = "+ ${character.getRaceBonusCharisma(context!!)}"

        txtSpeedValue.text = character.getDefaultSpeed(context!!).toString()
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