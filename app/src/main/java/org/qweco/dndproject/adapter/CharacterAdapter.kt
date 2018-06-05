package org.qweco.dndproject.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import org.qweco.dndproject.R
import org.qweco.dndproject.model.Character
import java.util.*

class CharacterAdapter(var list: ArrayList<Character>, val context: Context) : RecyclerView.Adapter<CharacterAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.character_card, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pos = holder.adapterPosition
        val character = list[pos]

        holder.name.text = character.name

        when (character.race){
            Character.HUMAN -> {
                holder.race.text = context.resources.getString(R.string.race_human)
            }//TODO: change img
            Character.DWARF -> {
                holder.race.text = context.resources.getString(R.string.race_dwarf)
            }//TODO: change img
            Character.ELF -> {
                holder.race.text = context.resources.getString(R.string.race_elf)
            }//TODO: change img
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemId(position: Int): Long {
        return list[position].id
    }

    fun setCharactersList(list: ArrayList<Character>) {
        this.list = list
        notifyDataSetChanged()
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var name: TextView
        var race: TextView

        init {
            name = v.findViewById(R.id.txtName)
            race = v.findViewById(R.id.txtRaceValue)
        }
    }

    fun removeItem(position: Int) {
        list.removeAt(position)
        notifyDataSetChanged()
    }

    fun restoreItem(item: Character, position: Int) {
        list.add(position, item)
        notifyDataSetChanged()
    }
}