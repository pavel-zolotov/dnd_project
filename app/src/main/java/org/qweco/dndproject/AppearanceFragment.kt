package org.qweco.dndproject

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_character_appearance.view.*
import org.qweco.dndproject.model.Character

class AppearanceFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_character_appearance, container, false)
        when (arguments.getInt(ARG_CHARACTER_RACE)){
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
        return rootView
    }

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private val ARG_CHARACTER_RACE = "race"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        fun newInstance(race: Int): AppearanceFragment {
            val fragment = AppearanceFragment()
            val args = Bundle()
            args.putInt(ARG_CHARACTER_RACE, race)
            fragment.arguments = args
            return fragment
        }
    }
}