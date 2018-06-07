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
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_main.*


class CharacterAdapter(private var list: ArrayList<Character>, val activity: Activity) : RecyclerView.Adapter<CharacterAdapter.ViewHolder>(), ItemTouchHelperAdapter {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pos = holder.adapterPosition
        val character = list[pos]

        holder.name.text = character.name
        holder.raceAndClass.text = "${character.getStringForRace(activity)} ${character.getStringForClass(activity)}"
        holder.itemView.setOnClickListener({
            val intent = Intent(activity, CharacterViewActivity::class.java)
            intent.putExtra("data", character)
            activity.startActivity(intent)
        })
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
        var name: TextView = v.findViewById(R.id.txtName)
        var raceAndClass: TextView = v.findViewById(R.id.txtRaceAndClass)
        var image: ImageView = v.findViewById(R.id.characterImg)
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
        Manager().deleteCharacter(activity, ch.id)
        removeItem(position)

        Snackbar.make(activity.contentView, activity.resources.getString(R.string.item_deleted), Snackbar.LENGTH_LONG)
                .setAction(activity.resources.getString(android.R.string.cancel), {
                        ch.id = Manager().insertCharacter(activity, ch)
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