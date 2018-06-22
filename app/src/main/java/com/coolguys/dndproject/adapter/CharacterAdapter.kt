package com.coolguys.dndproject.adapter

import android.content.Context
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
import kotlinx.android.synthetic.main.content_main.view.*


class CharacterAdapter(private var list: ArrayList<Character>, val activity: AppCompatActivity) : RecyclerView.Adapter<CharacterAdapter.ViewHolder>(), ItemTouchHelperAdapter {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pos = holder.adapterPosition
        val character = list[pos]

        holder.name.text = character.name
        holder.raceAndClass.text = "${character.getStringForRace(activity)} ${character.getStringForClass(activity)}"

        val strengthSum = character.getRaceBonusStrength(activity) + character.strength
        val strengthMod = (strengthSum-10)/2

        val dexteritySum = character.getRaceBonusDexterity(activity) + character.dexterity
        val dexterityMod = (dexteritySum-10)/2

        val constitutionSum = character.getRaceBonusConstitution(activity) + character.constitution
        val constitutionMod = (constitutionSum-10)/2

        val intelligenceSum = character.getRaceBonusIntelligence(activity) + character.intelligence
        val intelligenceMod = (intelligenceSum-10)/2

        val wisdomSum = character.getRaceBonusWisdom(activity) + character.wisdom
        val wisdomMod = (wisdomSum-10)/2

        val charismaSum = character.getRaceBonusCharisma(activity) + character.charisma
        val charismaMod = (charismaSum-10)/2

        val temp = character.getSawethrows(activity)

        when (temp[0]){
            0 -> {
                //txtSavethrow1Label.text = resources.getString(R.string.Savethrow_STR)
                //txtSavethrow1Bonus.text = "+${strengthMod}"
                holder.saveThrow1.text = if (strengthMod > 0) "+${strengthMod}" else strengthMod.toString()
                holder.saveThrow1Label.text = activity.resources.getString(R.string.Savethrow_STR).toString()
            }
            1 -> {
                //txtSavethrow1Label.text = resources.getString(R.string.Savethrow_DEX)
                //txtSavethrow1Bonus.text = "+${dexterityMod}"
                holder.saveThrow1.text = if (dexterityMod > 0) "+${dexterityMod}" else dexterityMod.toString()
                holder.saveThrow1Label.text = activity.resources.getString(R.string.Savethrow_DEX).toString()
            }
            2 -> {
                //txtSavethrow1Label.text = resources.getString(R.string.Savethrow_CON)
                //txtSavethrow1Bonus.text = "+${constitutionMod}"
                holder.saveThrow1.text = if (constitutionMod > 0) "+${constitutionMod}" else constitutionMod.toString()
                holder.saveThrow1Label.text = activity.resources.getString(R.string.Savethrow_CON).toString()
            }
            3 -> {
                //txtSavethrow1Label.text = resources.getString(R.string.Savethrow_INT)
                //txtSavethrow1Bonus.text = "+${intelligenceMod}"
                holder.saveThrow1.text = if (intelligenceMod > 0) "+${intelligenceMod}" else intelligenceMod.toString()
                holder.saveThrow1Label.text = activity.resources.getString(R.string.Savethrow_INT).toString()
            }
            4 -> {
                //txtSavethrow1Label.text = resources.getString(R.string.Savethrow_WIS)
                //txtSavethrow1Bonus.text = "+${wisdomMod}"
                holder.saveThrow1.text = if (wisdomMod > 0) "+${wisdomMod}" else wisdomMod.toString()
                holder.saveThrow1Label.text = activity.resources.getString(R.string.Savethrow_WIS).toString()
            }
            5 -> {
                //txtSavethrow1Label.text = resources.getString(R.string.Savethrow_CHA)
                //txtSavethrow1Bonus.text = "+${charismaMod}"
                holder.saveThrow1.text = if (charismaMod > 0) "+${charismaMod}" else charismaMod.toString()
                holder.saveThrow1Label.text = activity.resources.getString(R.string.Savethrow_CHA).toString()
            }
        }

        when (temp[1]){
            0 -> {
                //txtSavethrow2Label.text = resources.getString(R.string.Savethrow_STR)
                //txtSavethrow2Bonus.text = "+${strengthMod}"
                holder.saveThrow2.text = if (strengthMod > 0) "+${strengthMod}" else strengthMod.toString()
                holder.saveThrow2Label.text = activity.resources.getString(R.string.Savethrow_STR).toString()
            }
            1 -> {
                //txtSavethrow2Label.text = resources.getString(R.string.Savethrow_DEX)
                //txtSavethrow2Bonus.text = "+${dexterityMod}"
                holder.saveThrow2.text = if (dexterityMod > 0) "+${dexterityMod}" else dexterityMod.toString()
                holder.saveThrow2Label.text = activity.resources.getString(R.string.Savethrow_DEX).toString()
            }
            2 -> {
                //txtSavethrow2Label.text = resources.getString(R.string.Savethrow_CON)
                //txtSavethrow2Bonus.text = "+${constitutionMod}"
                holder.saveThrow2.text = if (constitutionMod > 0) "+${constitutionMod}" else constitutionMod.toString()
                holder.saveThrow2Label.text = activity.resources.getString(R.string.Savethrow_CON).toString()

            }
            3 -> {
                //txtSavethrow2Label.text = resources.getString(R.string.Savethrow_INT)
                //txtSavethrow2Bonus.text = "+${intelligenceMod}"
                holder.saveThrow2.text = if (intelligenceMod > 0) "+${intelligenceMod}" else intelligenceMod.toString()
                holder.saveThrow2Label.text = activity.resources.getString(R.string.Savethrow_INT).toString()
            }
            4 -> {
                //txtSavethrow2Label.text = resources.getString(R.string.Savethrow_WIS)
                //txtSavethrow2Bonus.text = "+${wisdomMod}"
                holder.saveThrow2.text = if (wisdomMod > 0) "+${wisdomMod}" else wisdomMod.toString()
                holder.saveThrow2Label.text = activity.resources.getString(R.string.Savethrow_WIS).toString()

            }
            5 -> {
                //txtSavethrow2Label.text = resources.getString(R.string.Savethrow_CHA)
                //txtSavethrow2Bonus.text = "+${charismaMod}"
                holder.saveThrow2.text = if (charismaMod > 0) "+${charismaMod}" else charismaMod.toString()
                holder.saveThrow2Label.text = activity.resources.getString(R.string.Savethrow_CHA).toString()
            }
        }


        holder.perception.text = if (wisdomMod > 0) "+${wisdomMod}" else wisdomMod.toString()
        holder.perceptionLabel.text = activity.resources.getString(R.string.perception)

        holder.image.setImageDrawable(character.getDrawableIconForClass(activity))



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

        var saveThrow1: TextView = v.findViewById(R.id.txtSavethrow1)
        var saveThrow2: TextView = v.findViewById(R.id.txtSavethrow2)
        var perception: TextView = v.findViewById(R.id.txtPerception)

        var saveThrow1Label : TextView = v.findViewById(R.id.txtSavethrow1Label)
        var saveThrow2Label : TextView = v.findViewById(R.id.txtSavethrow2Label)
        var perceptionLabel : TextView = v.findViewById(R.id.txtPerceptionLabel)


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