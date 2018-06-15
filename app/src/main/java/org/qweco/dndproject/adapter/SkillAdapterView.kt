package com.coolguys.dndproject.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.coolguys.dndproject.R
import com.coolguys.dndproject.model.Character
import java.util.*
import android.widget.*
import com.coolguys.dndproject.utils.InputFilterMinMax


class SkillAdapterView(var list:  HashMap<String, Int?>, val context: Context) : RecyclerView.Adapter<SkillAdapterView.ViewHolder>(){

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pos = holder.adapterPosition
        val skill = arrayOf(list.keys)[0].elementAt(pos)

        holder.value.text = list[skill].toString()

        when (skill){
            Character.ACROBATICS.toString() -> holder.label.text = context.resources.getString(R.string.Skill_ACROBATICS)
            Character.ANIMAL_HANDLING.toString() -> holder.label.text = context.resources.getString(R.string.Skill_ANIMAL_HANDLING)
            Character.ARCANA.toString() -> holder.label.text = context.resources.getString(R.string.Skill_ARCANA)
            Character.ATHLETICS.toString() -> holder.label.text = context.resources.getString(R.string.Skill_ATHLETICS)
            Character.DECEPTION.toString() -> holder.label.text = context.resources.getString(R.string.Skill_DECEPTION)
            Character.HISTORY.toString() -> holder.label.text = context.resources.getString(R.string.Skill_HISTORY)
            Character.INSIGHT.toString() -> holder.label.text = context.resources.getString(R.string.Skill_INSIGHT)
            Character.INTIMIDATION.toString() -> holder.label.text = context.resources.getString(R.string.Skill_INTIMIDATION)
            Character.INVESTIGATION.toString() -> holder.label.text = context.resources.getString(R.string.Skill_INVESTIGATION)
            Character.MEDICINE.toString() -> holder.label.text = context.resources.getString(R.string.Skill_MEDICINE)
            Character.NATURE.toString() -> holder.label.text = context.resources.getString(R.string.Skill_NATURE)
            Character.PERCEPTION.toString() -> holder.label.text = context.resources.getString(R.string.Skill_PERCEPTION)
            Character.PERFORMANCE.toString() -> holder.label.text = context.resources.getString(R.string.Skill_PERFORMANCE)
            Character.PERSUASION.toString() -> holder.label.text = context.resources.getString(R.string.Skill_PERSUASION)
            Character.RELIGION.toString() -> holder.label.text = context.resources.getString(R.string.Skill_RELIGION)
            Character.SLEIGHT_OF_HAND.toString() -> holder.label.text = context.resources.getString(R.string.Skill_SLEIGHT_OF_HAND)
            Character.STEALTH.toString() -> holder.label.text = context.resources.getString(R.string.Skill_STEALTH)
            Character.SURVIVAL.toString() -> holder.label.text = context.resources.getString(R.string.Skill_SURVIVAL)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var label: TextView = v.findViewById(R.id.label)
        var value: TextView = v.findViewById(R.id.value)
    }

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.skill_item_view, parent, false)
        return ViewHolder(itemView)
    }
}