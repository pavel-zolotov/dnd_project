package org.qweco.dndproject

import android.content.Intent
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast

import kotlinx.android.synthetic.main.activity_character_setup.*
import org.qweco.dndproject.data.Manager
import org.qweco.dndproject.model.Character
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.Spinner
import android.R.attr.data
import android.view.View
import android.widget.ArrayAdapter


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

        character = Character(-1, "", Character.WIZARD, Character.HUMAN, 10, 2, 10, 5, 3, 2, 3, mapOf(0 to 0, 1 to 1), mapOf(0 to 0, 1 to 1), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)

        character.race = intent.extras.getInt("race")

        fab.setOnClickListener { view ->
            // get fragment 1 & fragment 2
            val fragment1 = supportFragmentManager.findFragmentByTag("android:switcher:" + container.getId() + ":0") as AppearanceFragment
            val fragment2 = supportFragmentManager.findFragmentByTag("android:switcher:" + container.getId() + ":1") as SpecsFragment

            if (editText.text == null || editText.text.toString() == ""){
                Snackbar.make(contentView, resources.getString(R.string.fill_the_name), Snackbar.LENGTH_LONG).show()
            }else {
                character.name = editText.text.toString()
                character.eyeColor = fragment1.eye_color
                character.skinColor = fragment1.skin_color
                Manager().insertCharacter(this, character)
                val intent = Intent()
                setResult(RESULT_OK, intent)
                finish()
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
            override fun onItemSelected(parent: AdapterView<*>, view: View,
                                        position: Int, id: Long) {
                character.character_class = position
            }

            override fun onNothingSelected(arg0: AdapterView<*>) {}
        }

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
