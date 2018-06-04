package org.qweco.dndproject

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
import org.qweco.dndproject.model.Character

class MainActivity : AppCompatActivity() {
    private var characterList: ArrayList<Character> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        recyclerView.setHasFixedSize(true)
        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = llm

        characterList.add(Character(0, "my first character", Character.HUMAN, 0, 10, 2, 2, 10,5, 3, 2, 3, null, null, 0, 0, 0,0, 0, 0, 0, 0, 0, 0))
        characterList.add(Character(1, "my second character", Character.ELF, 0, 10, 2, 2, 10,5, 3, 2, 3, null, null, 0, 0, 0,0, 0, 0, 0, 0 ,0, 0))
        val adapter = CharacterAdapter(characterList, this)
        adapter.setHasStableIds(true)
        recyclerView.setAdapter(adapter)
        recyclerView.setEmptyView(emptyView)

        fab.setOnClickListener({
            val builder = AlertDialog.Builder(this, R.style.Base_Theme_AppCompat_Dialog)
            builder.setTitle(resources.getString(R.string.class_label))
            val classes = arrayOf(resources.getString(R.string.class_human), resources.getString(R.string.class_dwarf), resources.getString(R.string.class_elf))
            builder.setItems(classes) { _, item ->
                val intent = Intent(this, CharacterSetupActivity::class.java)
                intent.putExtra("class", item)
                startActivity(intent)
            }
            builder.show()})
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
