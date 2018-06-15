package com.coolguys.dndproject

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_character_view.*
import com.coolguys.dndproject.R.string.skills
import com.coolguys.dndproject.adapter.SkillAdapter
import com.coolguys.dndproject.adapter.SkillAdapterView
import com.coolguys.dndproject.data.Manager
import com.coolguys.dndproject.model.Character
import android.content.Intent.ACTION_DELETE
import android.support.v4.view.accessibility.AccessibilityEventCompat.setAction
import android.content.Intent
import android.view.View


class CharacterViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_view)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val character = intent.extras.getSerializable("data") as Character
        txtName.text = character.name
        txtRaceAndClass.text = "${character.getStringForRace(this)} ${character.getStringForClass(this)}"
        characterImg.setImageDrawable(character.getDrawableForClass(this))

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
                txtSavethrow1Bonus.text = "+${character.dexterity}"
            }
            1 -> {
                txtSavethrow1Label.text = resources.getString(R.string.Savethrow_DEX)
                txtSavethrow1Bonus.text = "+${character.dexterity}"
            }
            2 -> {
                txtSavethrow1Label.text = resources.getString(R.string.Savethrow_CON)
                txtSavethrow1Bonus.text = "+${character.constitution}"
            }
            3 -> {
                txtSavethrow1Label.text = resources.getString(R.string.Savethrow_INT)
                txtSavethrow1Bonus.text = "+${character.intelligence}"
            }
            4 -> {
                txtSavethrow1Label.text = resources.getString(R.string.Savethrow_WIS)
                txtSavethrow1Bonus.text = "+${character.wisdom}"
            }
            5 -> {
                txtSavethrow1Label.text = resources.getString(R.string.Savethrow_CHA)
                txtSavethrow1Bonus.text = "+${character.charisma}"
            }
        }

        when (temp[1]){
            0 -> {
                txtSavethrow2Label.text = resources.getString(R.string.Savethrow_STR)
                txtSavethrow2Bonus.text = "+${character.dexterity}"
            }
            1 -> {
                txtSavethrow2Label.text = resources.getString(R.string.Savethrow_DEX)
                txtSavethrow2Bonus.text = "+${character.dexterity}"
            }
            2 -> {
                txtSavethrow2Label.text = resources.getString(R.string.Savethrow_CON)
                txtSavethrow2Bonus.text = "+${character.constitution}"
            }
            3 -> {
                txtSavethrow2Label.text = resources.getString(R.string.Savethrow_INT)
                txtSavethrow2Bonus.text = "+${character.intelligence}"
            }
            4 -> {
                txtSavethrow2Label.text = resources.getString(R.string.Savethrow_WIS)
                txtSavethrow2Bonus.text = "+${character.wisdom}"
            }
            5 -> {
                txtSavethrow2Label.text = resources.getString(R.string.Savethrow_CHA)
                txtSavethrow2Bonus.text = "+${character.charisma}"
            }
        }


        //set up skills list
        if (character.skills.size > 0){
            val llm = LinearLayoutManager(applicationContext)
            llm.orientation = LinearLayoutManager.VERTICAL
            skillList.layoutManager = llm

            val adapterSkills = SkillAdapterView(character.skills,  applicationContext)
            skillList.adapter = adapterSkills
        }else{
            txtSkillsLabel.text = resources.getString(R.string.no_skills)
        }

        //set up buttons
        if (character.hp == 99) {
            incrHP.visibility = View.INVISIBLE
        }else if(character.hp == 0){
            decrHP.visibility = View.INVISIBLE
        }

        incrHP.setOnClickListener{
            character.hp++
            txtHpValue.text = character.hp.toString()
            Manager().updateCharacter(this, character)
            sendUpdateBroadcast()

            if (character.hp == 99){
                it.visibility = View.INVISIBLE
            }

            decrHP.visibility = View.VISIBLE
        }

        decrHP.setOnClickListener{
            character.hp--
            txtHpValue.text = character.hp.toString()
            Manager().updateCharacter(this, character)
            sendUpdateBroadcast()

            if (character.hp == 0){
                it.visibility = View.INVISIBLE
            }

            incrHP.visibility = View.VISIBLE
        }


        if (character.armourClass == 99) {
            incrArmourClass.visibility = View.INVISIBLE
        }else if(character.armourClass == 0){
            decrArmourClass.visibility = View.INVISIBLE
        }

        incrArmourClass.setOnClickListener{
            character.armourClass++
            txtArmourClassValue.text = character.armourClass.toString()
            Manager().updateCharacter(this, character)
            sendUpdateBroadcast()

            if (character.armourClass == 99){
                it.visibility = View.INVISIBLE
            }

            decrArmourClass.visibility = View.VISIBLE
        }

        decrArmourClass.setOnClickListener{
            character.armourClass--
            txtArmourClassValue.text = character.armourClass.toString()
            Manager().updateCharacter(this, character)
            sendUpdateBroadcast()

            if (character.armourClass == 0){
                it.visibility = View.INVISIBLE
            }

            incrArmourClass.visibility = View.VISIBLE
        }
    }

    private fun sendUpdateBroadcast() {
        val broadcast = Intent()
        broadcast.action = MainActivity().ACTION_UPDATE_LIST_INTENT
        sendBroadcast(broadcast)
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
