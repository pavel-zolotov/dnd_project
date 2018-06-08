package org.qweco.dndproject

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.qweco.dndproject.adapter.CharacterAdapter
import org.qweco.dndproject.data.Manager
import org.qweco.dndproject.model.Character
import android.support.v7.widget.helper.ItemTouchHelper
import org.qweco.dndproject.adapter.itemTouchHelper.MyItemTouchHelperCallback



class MainActivity : AppCompatActivity() {
    private var characterList: ArrayList<Character> = ArrayList()
    lateinit var adapter: CharacterAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        recyclerView.setHasFixedSize(true)
        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = llm

        characterList = Manager().loadCharacters(this)
        adapter = CharacterAdapter(characterList, this)
        adapter.setHasStableIds(true)

        val callback = MyItemTouchHelperCallback(adapter)

        val touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView(recyclerView)

        recyclerView.itemAnimator.addDuration = 500
        recyclerView.itemAnimator.removeDuration = 500
        recyclerView.itemAnimator.moveDuration = 500
        recyclerView.itemAnimator.changeDuration = 500

        recyclerView.setAdapter(adapter)
        recyclerView.setEmptyView(emptyView)

        fab.setOnClickListener({
            val builder = AlertDialog.Builder(this, R.style.Base_Theme_AppCompat_Dialog)
            builder.setTitle(resources.getString(R.string.race_label))
            val races = arrayOf(resources.getString(R.string.race_human),
                                resources.getString(R.string.race_dwarf),
                                resources.getString(R.string.race_dwarf1),
                                resources.getString(R.string.race_dwarf2),
                                resources.getString(R.string.race_elf),
                                resources.getString(R.string.race_elf1),
                                resources.getString(R.string.race_elf2),
                                resources.getString(R.string.race_elf3),
                                resources.getString(R.string.race_tifling),
                                resources.getString(R.string.race_half_orc),
                                resources.getString(R.string.race_half_elf),
                                resources.getString(R.string.race_dragonborn),
                                resources.getString(R.string.race_halfling),
                                resources.getString(R.string.race_halfling1),
                                resources.getString(R.string.race_halfling2),
                                resources.getString(R.string.race_gnom),
                                resources.getString(R.string.race_gnom1),
                                resources.getString(R.string.race_gnom2) )
            builder.setItems(races) { _, item ->
                val intent = Intent(this, CharacterSetupActivity::class.java)
                intent.putExtra("race", item)
                startActivityForResult(intent, 1)
            }
            builder.show()})
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK){
            characterList = Manager().loadCharacters(this)
            adapter.setCharactersList(characterList)
            adapter.notifyDataSetChanged()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        /*return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }*/
        return false
    }
}
