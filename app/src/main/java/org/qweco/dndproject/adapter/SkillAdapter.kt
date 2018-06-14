package org.qweco.dndproject.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import org.qweco.dndproject.R
import org.qweco.dndproject.adapter.itemTouchHelper.ItemTouchHelperAdapter
import org.qweco.dndproject.model.Character
import java.util.*
import android.support.design.widget.Snackbar
import org.qweco.dndproject.CharacterViewActivity
import org.qweco.dndproject.data.Manager
import android.support.v4.view.ViewCompat
import android.support.v4.app.ActivityOptionsCompat
import android.util.Log
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_main.*


abstract class SkillAdapter(private var list:  LinkedHashMap<String, Int?>, val context: Context, private val maxSkillsAmount: Int) : RecyclerView.Adapter<SkillAdapter.ViewHolder>(){
    private var checked = 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pos = holder.adapterPosition
        val skill = arrayOf(list.keys)[0].elementAt(pos)

        when (skill){
            Character.ACROBATICS.toString() -> holder.checkBox.text = context.resources.getString(R.string.Skill_ACROBATICS)
            Character.ANIMAL_HANDLING.toString() -> holder.checkBox.text = context.resources.getString(R.string.Skill_ANIMAL_HANDLING)
            Character.ARCANA.toString() -> holder.checkBox.text = context.resources.getString(R.string.Skill_ARCANA)
            Character.ATHLETICS.toString() -> holder.checkBox.text = context.resources.getString(R.string.Skill_ATHLETICS)
            Character.DECEPTION.toString() -> holder.checkBox.text = context.resources.getString(R.string.Skill_DECEPTION)
            Character.HISTORY.toString() -> holder.checkBox.text = context.resources.getString(R.string.Skill_HISTORY)
            Character.INSIGHT.toString() -> holder.checkBox.text = context.resources.getString(R.string.Skill_INSIGHT)
            Character.INTIMIDATION.toString() -> holder.checkBox.text = context.resources.getString(R.string.Skill_INTIMIDATION)
            Character.INVESTIGATION.toString() -> holder.checkBox.text = context.resources.getString(R.string.Skill_INVESTIGATION)
            Character.MEDICINE.toString() -> holder.checkBox.text = context.resources.getString(R.string.Skill_MEDICINE)
            Character.NATURE.toString() -> holder.checkBox.text = context.resources.getString(R.string.Skill_NATURE)
            Character.PERCEPTION.toString() -> holder.checkBox.text = context.resources.getString(R.string.Skill_PERCEPTION)
            Character.PERFORMANCE.toString() -> holder.checkBox.text = context.resources.getString(R.string.Skill_PERFORMANCE)
            Character.PERSUASION.toString() -> holder.checkBox.text = context.resources.getString(R.string.Skill_PERSUASION)
            Character.RELIGION.toString() -> holder.checkBox.text = context.resources.getString(R.string.Skill_RELIGION)
            Character.SLEIGHT_OF_HAND.toString() -> holder.checkBox.text = context.resources.getString(R.string.Skill_SLEIGHT_OF_HAND)
            Character.STEALTH.toString() -> holder.checkBox.text = context.resources.getString(R.string.Skill_STEALTH)
            Character.SURVIVAL.toString() -> holder.checkBox.text = context.resources.getString(R.string.Skill_SURVIVAL)
        }

        holder.checkBox.setOnCheckedChangeListener({ _: CompoundButton, isChecked: Boolean ->
            // control the amount of checked checkBoxes
            if (isChecked) {
                if (checked+1 > maxSkillsAmount) {
                    holder.checkBox.isChecked = false
                } else {
                    checked++
                }
            }else{
                if (checked-1 <= maxSkillsAmount) {
                    checked--
                }
            }
            selectedAmountChanged(checked)
        })
    }

    abstract fun selectedAmountChanged (amount: Int)

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var checkBox: CheckBox = v.findViewById(R.id.checkBox)
        var value: TextView = v.findViewById(R.id.editText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.skill_item, parent, false)
        return ViewHolder(itemView)
    }
}