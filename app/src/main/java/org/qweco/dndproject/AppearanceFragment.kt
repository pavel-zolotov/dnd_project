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
        when (arguments.getInt(ARG_CHARACTER_CLASS)){
            Character.HUMAN -> {
                rootView.txtClassValue.text = context.resources.getString(R.string.class_human)
            }
            Character.DWARF -> {
                rootView.txtClassValue.text = context.resources.getString(R.string.class_dwarf)
            }
            Character.ELF -> {
                rootView.txtClassValue.text = context.resources.getString(R.string.class_elf)
            }
        }
        return rootView
    }

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private val ARG_CHARACTER_CLASS = "character_class"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        fun newInstance(characterClass: Int): AppearanceFragment {
            val fragment = AppearanceFragment()
            val args = Bundle()
            args.putInt(ARG_CHARACTER_CLASS, characterClass)
            fragment.arguments = args
            return fragment
        }
    }
}