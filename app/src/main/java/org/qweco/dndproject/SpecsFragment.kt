package org.qweco.dndproject

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_character_specs.view.*
import org.qweco.dndproject.model.Character

class SpecsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_character_specs, container, false)
        /*when (arguments.getInt(ARG_CHARACTER_RACE)){
            Character.HUMAN -> {
                rootView.txtRaceValue.text = context.resources.getString(R.string.race_human)
            }
            Character.DWARF -> {
                rootView.txtRaceValue.text = context.resources.getString(R.string.race_dwarf)
            }
            Character.ELF -> {
                rootView.txtRaceValue.text = context.resources.getString(R.string.race_elf)
            }
            Character.TIFLING -> {
                rootView.txtRaceValue.text = context.resources.getString(R.string.race_tifling)
            }
            Character.HALF_ORC -> {
                rootView.txtRaceValue.text = context.resources.getString(R.string.race_half_orc)
            }
            Character.HALF_ELF -> {
                rootView.txtRaceValue.text = context.resources.getString(R.string.race_half_elf)
            }
            Character.DRAGONBORN -> {
                rootView.txtRaceValue.text = context.resources.getString(R.string.race_dragonborn)
            }
            Character.HALFLING -> {
                rootView.txtRaceValue.text = context.resources.getString(R.string.race_halfling)
            }
            Character.GNOM -> {
                rootView.txtRaceValue.text = context.resources.getString(R.string.race_gnom)
            }
        }

        when (arguments.getInt(ARG_CHARACTER_CLASS)){
            Character.WIZARD -> {
                rootView.txtClassValue.text = context.resources.getString(R.string.class_wizard)
            }
            Character.WARLOCK -> {
                rootView.txtClassValue.text = context.resources.getString(R.string.class_warlock)
            }
            Character.SORCERER -> {
                rootView.txtClassValue.text = context.resources.getString(R.string.class_sorcerer)
            }
            Character.WARRIOR -> {
                rootView.txtClassValue.text = context.resources.getString(R.string.class_warrior)
            }
            Character.MONK -> {
                rootView.txtClassValue.text = context.resources.getString(R.string.class_monk)
            }
            Character.RANGER -> {
                rootView.txtClassValue.text = context.resources.getString(R.string.class_ranger)
            }
            Character.THIEF -> {
                rootView.txtClassValue.text = context.resources.getString(R.string.class_thief)
            }
            Character.CLERIC -> {
                rootView.txtClassValue.text = context.resources.getString(R.string.class_cleric)
            }
            Character.PALADIN -> {
                rootView.txtClassValue.text = context.resources.getString(R.string.class_paladin)
            }
            Character.BARBARIAN -> {
                rootView.txtClassValue.text = context.resources.getString(R.string.class_barbarian)
            }
            Character.DRUID -> {
                rootView.txtClassValue.text = context.resources.getString(R.string.class_druid)
            }
            Character.BARD -> {
                rootView.txtClassValue.text = context.resources.getString(R.string.class_bard)
            }
        }*/


        return rootView
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