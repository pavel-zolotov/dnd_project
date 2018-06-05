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
import kotlinx.android.synthetic.main.fragment_character_appearance.*
import org.qweco.dndproject.data.Manager
import org.qweco.dndproject.model.Character

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
    var race: Int = Character.HUMAN //Инициализация
    var character: Character? = null;

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

        race = intent.extras.getInt("race")

        fab.setOnClickListener { view ->
            // get fragment 1 & fragment 2
            val fragment1 = supportFragmentManager.findFragmentByTag("android:switcher:" + container.getId() + ":0") as AppearanceFragment
            val fragment2 = supportFragmentManager.findFragmentByTag("android:switcher:" + container.getId() + ":1") as SpecsFragment

            if (fragment1.editText.text == null || fragment1.editText.text.toString() == ""){
                Toast.makeText(this, "pls fill the name", Toast.LENGTH_LONG).show()
            }else {
                character = Character(-1, fragment1.editText.text.toString(), Character.WIZARD, race, 10, 2, 10, 5, 3, 2, 3, mapOf(0 to 0, 1 to 1), mapOf(0 to 0, 1 to 1), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
                Manager().insertCharacter(this, character!!)
                val intent = Intent()
                setResult(RESULT_OK, intent)
                finish()
            }
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

        if (id == R.id.action_settings) {
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
                0-> return AppearanceFragment.newInstance(race)
                else-> return SpecsFragment.newInstance(race)
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
