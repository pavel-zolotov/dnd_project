package org.qweco.dndproject

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_character_view.*
import org.qweco.dndproject.R.string.charisma
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

        txtInitiativeValue.text = character.initiative.toString()
        txtHpValue.text = character.hp.toString()
        txtSpeedValue.text = character.speed.toString()
        txtHitDiceValue.text = character.hitDice.toString()
        txtArmourClassValue.text = character.armourClass.toString()
        txtProficiencyValue.text = character.proficiency.toString()

        val strengthSum = character.getRaceBonusStrength(this) + character.strength
        val strengthMod = (strengthSum-10)/2
        txtStrengthValue.text = strengthSum.toString()
        txtStrengthBonus.text = if (strengthMod > 0) "+${strengthMod}" else strengthMod.toString()


        val dexteritySum = character.getRaceBonusDexterity(this) + character.dexterity
        val dexterityMod = (dexteritySum-10)/2
        txtDexterityValue.text = dexteritySum.toString()
        txtDexterityBonus.text = if (dexterityMod > 0) "+${dexterityMod}" else dexterityMod.toString()

        val constitutionSum = character.getRaceBonusConstitution(this) + character.constitution
        val constitutionMod = (constitutionSum-10)/2
        txtConstitutionValue.text = constitutionSum.toString()
        txtConstitutionBonus.text = if (constitutionMod > 0) "+${constitutionMod}" else constitutionMod.toString()

        val intelligenceSum = character.getRaceBonusIntelligence(this) + character.intelligence
        val intelligenceMod = (intelligenceSum-10)/2
        txtIntelligenceValue.text = intelligenceSum.toString()
        txtIntelligenceBonus.text = if (intelligenceMod > 0) "+${intelligenceMod}" else intelligenceMod.toString()

        val wisdomSum = character.getRaceBonusWisdom(this) + character.wisdom
        val wisdomMod = (wisdomSum-10)/2
        txtWisdomValue.text = wisdomSum.toString()
        txtWisdomBonus.text = if (wisdomMod > 0) "+${wisdomMod}" else wisdomMod.toString()

        val charismaSum = character.getRaceBonusCharisma(this) + character.charisma
        val charismaMod = (charismaSum-10)/2
        txtCharismaValue.text = charismaSum.toString()
        txtCharismaBonus.text = if (charismaMod > 0) "+${charismaMod}" else charismaMod.toString()

        val temp = character.getSawethrows(applicationContext)

        when (temp[0]){
            0 -> {
                txtSavethrow1Label.text = resources.getString(R.string.Savethrow_STR)
            }
            1 -> {
                txtSavethrow1Label.text = resources.getString(R.string.Savethrow_DEX)
            }
            2 -> {
                txtSavethrow1Label.text = resources.getString(R.string.Savethrow_CON)
            }
            3 -> {
                txtSavethrow1Label.text = resources.getString(R.string.Savethrow_INT)
            }
            4 -> {
                txtSavethrow1Label.text = resources.getString(R.string.Savethrow_WIS)
            }
            5 -> {
                txtSavethrow1Label.text = resources.getString(R.string.Savethrow_CHA)
            }

        }

        when (temp[1]){
            0 -> {
                txtSavethrow2Label.text = resources.getString(R.string.Savethrow_STR)
            }
            1 -> {
                txtSavethrow2Label.text = resources.getString(R.string.Savethrow_DEX)
            }
            2 -> {
                txtSavethrow2Label.text = resources.getString(R.string.Savethrow_CON)
            }
            3 -> {
                txtSavethrow2Label.text = resources.getString(R.string.Savethrow_INT)
            }
            4 -> {
                txtSavethrow2Label.text = resources.getString(R.string.Savethrow_WIS)
            }
            5 -> {
                txtSavethrow2Label.text = resources.getString(R.string.Savethrow_CHA)
            }

        }

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
