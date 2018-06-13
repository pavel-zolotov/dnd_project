package org.qweco.dndproject

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_character_view.*
import org.qweco.dndproject.model.Character

class CharacterViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_view)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val character = intent.extras.getSerializable("data") as Character
        txtName.text = character.name
        txtRaceAndClass.text = "${character.getStringForRace(this)} ${character.getStringForClass(this)}"

        val StrSum = character.getRaceBonusStrength(this) + character.strength
        val StrMod = (StrSum-10)/2

        txtStrengthRace.text = StrSum.toString()
        txtStrengthPlayer.text = StrMod.toString()


        val DexSum = character.getRaceBonusDexterity(this) + character.dexterity
        val DexMod = (DexSum-10)/2

        val ConstSum = character.getRaceBonusConstitution(this) + character.constitution
        val ConstMod = (ConstSum-10)/2

        val IntSum = character.getRaceBonusIntelligence(this) + character.intelligence
        val IntMod = (IntSum-10)/2

        val WisSum = character.getRaceBonusWisdom(this) + character.wisdom
        val WisMod = (WisSum-10)/2

        val CharSum = character.getRaceBonusCharisma(this) + character.charisma
        val CharMod = (CharSum-10)/2

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            finish()
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}
