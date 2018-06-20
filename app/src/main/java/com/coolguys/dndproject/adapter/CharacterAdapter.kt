package com.coolguys.dndproject.adapter

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.coolguys.dndproject.R
import com.coolguys.dndproject.adapter.itemTouchHelper.ItemTouchHelperAdapter
import com.coolguys.dndproject.model.Character
import java.util.*
import android.support.design.widget.Snackbar
import com.coolguys.dndproject.CharacterViewActivity
import com.coolguys.dndproject.data.Manager
import android.support.v7.app.AppCompatActivity
import android.widget.ImageButton
import android.widget.ImageView
import com.coolguys.dndproject.QRViewFragment
import kotlinx.android.synthetic.main.activity_main.*


class CharacterAdapter(private var list: ArrayList<Character>, val activity: AppCompatActivity) : RecyclerView.Adapter<CharacterAdapter.ViewHolder>(), ItemTouchHelperAdapter {

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
        holder.share.setOnClickListener({
            val bottomSheetFragment = QRViewFragment()
            bottomSheetFragment.character = character
            bottomSheetFragment.show(activity.supportFragmentManager, bottomSheetFragment.tag)
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
        var share: ImageButton = v.findViewById(R.id.imgShare)
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
        val ch = list[position]
        Manager().deleteCharacter(activity, ch.id)
        removeItem(position)

        Snackbar.make(activity.contentView, activity.resources.getString(R.string.item_deleted), Snackbar.LENGTH_LONG)
                .setAction(activity.resources.getString(android.R.string.cancel), {
                        ch.id = Manager().insertCharacterWithId(activity, ch)
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