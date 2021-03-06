package com.coolguys.dndproject

import android.content.Intent
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.view.Menu
import android.view.MenuItem

import kotlinx.android.synthetic.main.activity_character_setup.*
import com.coolguys.dndproject.data.Manager
import com.coolguys.dndproject.model.Character
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.fragment_character_specs.*
import kotlinx.android.synthetic.main.fragment_character_specs.view.*
import com.coolguys.dndproject.adapter.SkillAdapter


class CharacterSetupActivity : AppCompatActivity() {

    /**
     * The [android.support.v4.view.PagerAdapter] that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * [android.support.v4.app.FragmentStatePagerAdapter].
     */
    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null
    lateinit var character: Character

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_setup)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)

        // Set up the ViewPager with the sections adapter.
        container.adapter = mSectionsPagerAdapter

        tabs.setupWithViewPager(container)

        //show bas on selected tab change
        container.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))
        tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                fab.show()
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        character = Character(intent.extras.getInt("race"))

        fab.setOnClickListener { view ->
            // get fragment 1 & fragment 2
            val fragment1 = supportFragmentManager.findFragmentByTag("android:switcher:" + container.getId() + ":0") as AppearanceFragment
            val fragment2 = supportFragmentManager.findFragmentByTag("android:switcher:" + container.getId() + ":1") as SpecsFragment

            if (editText.text == null || editText.text.toString() == ""){
                showFillTheSnackbar(R.string.name)
            }else if (txtInitiativeValue.text == null || txtInitiativeValue.text.toString() == "") {
                showFillTheSnackbar(R.string.initiative)
            }else if (txtHpValue.text == null || txtHpValue.text.toString() == "") {
                showFillTheSnackbar(R.string.hp)
            }else if (txtSpeedValue.text == null || txtSpeedValue.text.toString() == "") {
                showFillTheSnackbar(R.string.speed)
            }else if (txtHitDiceValue.text == null || txtHitDiceValue.text.toString() == "") {
                showFillTheSnackbar(R.string.hit_dice)
            }else if (txtArmourClassValue.text == null || txtArmourClassValue.text.toString() == "") {
                showFillTheSnackbar(R.string.armor_class)
            }else if (txtProficiencyValue.text == null || txtProficiencyValue.text.toString() == "") {
                showFillTheSnackbar(R.string.proficiency)
            }else if (txtStrengthValue.text == null || txtStrengthValue.text.toString() == "") {
                showFillTheSnackbar(R.string.strength)
            }else if (txtDexterityValue.text == null || txtDexterityValue.text.toString() == "") {
                showFillTheSnackbar(R.string.dexterity)
            }else if (txtConstitutionValue.text == null || txtConstitutionValue.text.toString() == "") {
                showFillTheSnackbar(R.string.constitution)
            }else if (txtIntelligenceValue.text == null || txtIntelligenceValue.text.toString() == "") {
                showFillTheSnackbar(R.string.intelligence)
            }else if (txtWisdomValue.text == null || txtWisdomValue.text.toString() == "") {
                showFillTheSnackbar(R.string.wisdom)
            }else if (txtCharismaValue.text == null || txtCharismaValue.text.toString() == "") {
                showFillTheSnackbar(R.string.charisma)
            }else{
                //check skills list, prevent writing null values for a skill
                var counter = 0
                for(data in (skillList.adapter as SkillAdapter).list){
                    if (data.value != null){
                        counter++
                    }
                }

                if (counter < (skillList.adapter as SkillAdapter).checked){
                    Snackbar.make(contentView, resources.getString(R.string.fill_all_skills_values), Snackbar.LENGTH_LONG).show()
                }else {
                    character.name = editText.text.toString()
                    character.eyeColor = fragment1.eye_color
                    character.skinColor = Character.SKIN_COLOR_LIGHT //TODO: delete
                    character.initiative = txtInitiativeValue.text.toString().toInt()
                    character.hp = txtHpValue.text.toString().toInt()
                    character.speed = txtSpeedValue.text.toString().toInt()
                    character.hitDice = txtHitDiceValue.text.toString().toInt()
                    character.armourClass = txtArmourClassValue.text.toString().toInt()
                    character.proficiency = txtProficiencyValue.text.toString().toInt()
                    character.strength = txtStrengthValue.text.toString().toInt()
                    character.dexterity = txtDexterityValue.text.toString().toInt()
                    character.constitution = txtConstitutionValue.text.toString().toInt()
                    character.intelligence = txtIntelligenceValue.text.toString().toInt()
                    character.wisdom = txtWisdomValue.text.toString().toInt()
                    character.charisma = txtCharismaValue.text.toString().toInt()

                    character.skills = (skillList.adapter as SkillAdapter).list

                    Manager().insertCharacter(this, character)
                    val intent = Intent()
                    setResult(RESULT_OK, intent)
                    finish()
                }
            }
        }

        txtRaceValue.text = character.getStringForRace(this)

        val data = arrayOf(resources.getString(R.string.class_wizard),
                resources.getString(R.string.class_sorcerer),
                resources.getString(R.string.class_warlock),
                resources.getString(R.string.class_warrior),
                resources.getString(R.string.class_barbarian),
                resources.getString(R.string.class_druid),
                resources.getString(R.string.class_bard),
                resources.getString(R.string.class_ranger),
                resources.getString(R.string.class_thief),
                resources.getString(R.string.class_monk),
                resources.getString(R.string.class_paladin),
                resources.getString(R.string.class_cleric))
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinner.adapter = adapter
        spinner.prompt = resources.getString(R.string.race_label)
        spinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?,
                                        position: Int, id: Long) {
                //set class value
                character.character_class = position

                //set hit dice value
                txtHitDiceValue.text = character.getHitDiceForClass(applicationContext ).toString()

                //set up skills list
                val llm = LinearLayoutManager(applicationContext)
                llm.orientation = LinearLayoutManager.VERTICAL
                skillList.layoutManager = llm

                val skills = HashMap<String, Int?>()
                for (skill in character.getAvailableSkills(applicationContext)){ //load all available skills
                    skills[skill.toString()] = null
                }
                val adapterSkills = object:SkillAdapter(skills,  applicationContext, character.getAmountOfSkills(applicationContext)){
                    override fun selectedAmountChanged (amount: Int){
                        //change label args
                        txtSkillsLabel.text = resources.getString(R.string.skills, amount, character.getAmountOfSkills(applicationContext))
                    }
                }
                txtSkillsLabel.text = resources.getString(R.string.skills, 0, character.getAmountOfSkills(applicationContext))
                skillList.adapter = adapterSkills

                //set character image
                val fragment1 = supportFragmentManager.findFragmentByTag("android:switcher:" + container.getId() + ":0") as AppearanceFragment
                fragment1.changeImg()
            }

            override fun onNothingSelected(arg0: AdapterView<*>) {}
        }
    }

    fun showFillTheSnackbar (resString: Int) {
        Snackbar.make(contentView, resources.getString(R.string.fill_the, resources.getString(resString)), Snackbar.LENGTH_LONG).show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        // menuInflater.inflate(R.menu.menu_character_setup, menu)
        return true
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


    /**
     * A [FragmentPagerAdapter] that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            // getItem is called to instantiate the fragment for the given page.
            when (position){
                0-> return AppearanceFragment.newInstance(character.race, character.character_class)
                else-> return SpecsFragment.newInstance(character.race, character.character_class)
            }
        }

        override fun getCount(): Int {
            // Show 2 total pages.
            return 2
        }

        override fun getPageTitle(position: Int): CharSequence {
            when (position){
                0-> return resources.getString(R.string.tab_appearance)
                else-> return resources.getString(R.string.tab_specs)
            }
        }
    }
}
