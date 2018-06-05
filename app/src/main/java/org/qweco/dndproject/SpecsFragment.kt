package org.qweco.dndproject

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_character_appearance.view.*
import org.qweco.dndproject.model.Character

class SpecsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_character_specs, container, false)
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
        fun newInstance(race: Int): SpecsFragment {
            val fragment = SpecsFragment()
            val args = Bundle()
            args.putInt(ARG_CHARACTER_RACE, race)
            fragment.arguments = args
            return fragment
        }
    }
}