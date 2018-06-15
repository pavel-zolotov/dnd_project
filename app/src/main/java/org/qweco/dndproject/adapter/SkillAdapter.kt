package org.qweco.dndproject.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.qweco.dndproject.R
import org.qweco.dndproject.model.Character
import java.util.*
import android.widget.*
import org.qweco.dndproject.utils.InputFilterMinMax


abstract class SkillAdapter(var list:  HashMap<String, Int?>, val context: Context, private val maxSkillsAmount: Int) : RecyclerView.Adapter<SkillAdapter.ViewHolder>(){
    var checked = 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pos = holder.adapterPosition
        val skill = arrayOf(list.keys)[0].elementAt(pos)

        holder.value.filters = arrayOf(InputFilterMinMax(0, 100))

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
                if (checked+1 > maxSkillsAmount) { //discard changes
                    holder.checkBox.isChecked = false
                } else { //allow check
                    checked++
                    holder.value.isEnabled = true
                }
            }else{
                if (checked-1 <= maxSkillsAmount) { //allow uncheck
                    checked--
                    holder.value.isEnabled = false
                    holder.value.text.clear()
                }
            }
            selectedAmountChanged(checked)

            holder.value.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun afterTextChanged(p0: Editable?) {
                    if (p0.toString() == ""){
                        list[skill] = null
                    }else{
                        list[skill] = p0.toString().toInt()
                    }
                }
            })
        })
    }

    abstract fun selectedAmountChanged (amount: Int)

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var checkBox: CheckBox = v.findViewById(R.id.checkBox)
        var value: EditText = v.findViewById(R.id.editText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.skill_item, parent, false)
        return ViewHolder(itemView)
    }
}