package org.qweco.dndproject.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import org.qweco.dndproject.R
import org.qweco.dndproject.adapter.itemTouchHelper.ItemTouchHelperAdapter
import org.qweco.dndproject.model.Character
import java.util.*
import android.support.design.widget.Snackbar
import org.qweco.dndproject.data.Manager


class CharacterAdapter(private var list: ArrayList<Character>, val context: Context, private val parentView: View) : RecyclerView.Adapter<CharacterAdapter.ViewHolder>(), ItemTouchHelperAdapter {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pos = holder.adapterPosition
        val character = list[pos]

        holder.name.text = character.name
        holder.raceAndClass.text = "${character.getStringForRace(context)} ${character.getStringForClass(context)}"
        holder.itemView.setOnClickListener({ Toast.makeText(context, character.toString(), Toast.LENGTH_LONG).show() })
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
        var raceAndClass: TextView

        init {
            name = v.findViewById(R.id.txtName)
            raceAndClass = v.findViewById(R.id.txtRaceAndClass)
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

    override fun onItemDismiss(position: Int) {
        val ch = list.get(position)
        Manager().deleteCharacter(context, ch.id)
        removeItem(position)

        Snackbar.make(parentView, context.resources.getString(R.string.item_deleted), Snackbar.LENGTH_LONG)
                .setAction(context.resources.getString(android.R.string.cancel), {
                        ch.id = Manager().insertCharacter(context, ch)
                        restoreItem(ch, position)
                })
                .show()
    }

    override fun isLongPressDragEnabled(): Boolean {
        return false
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {}

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.character_card, parent, false)
        return ViewHolder(itemView)
    }
}