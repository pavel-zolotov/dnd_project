package com.coolguys.dndproject

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
import com.coolguys.dndproject.adapter.CharacterAdapter
import com.coolguys.dndproject.data.Manager
import com.coolguys.dndproject.model.Character
import android.support.v7.widget.helper.ItemTouchHelper
import com.coolguys.dndproject.adapter.itemTouchHelper.MyItemTouchHelperCallback
import com.google.firebase.auth.FirebaseAuth
import com.firebase.ui.auth.ErrorCodes
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import android.animation.ValueAnimator
import android.support.v4.content.ContextCompat
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.firestore.*
import com.idescout.sql.SqlScoutServer
import android.content.IntentFilter
import android.content.BroadcastReceiver
import android.content.Context


class MainActivity : AppCompatActivity() {
    private var characterList: ArrayList<Character> = ArrayList()
    private lateinit var adapter: CharacterAdapter
    private lateinit var menu: Menu
    private var mReceiver: UpdateUIReceiver? = null

    val ACTION_UPDATE_LIST_INTENT = "ACTION_UPDATE_LIST_INTENT"
    private val RC_SIGN_IN = 123     // Choose an arbitrary request code value
    private val RC_NEW_CHARACTER = 456

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        SqlScoutServer.create(this, getPackageName());

        if (mReceiver == null) mReceiver = UpdateUIReceiver()
        val fltr = IntentFilter(Intent.ACTION_DATE_CHANGED)
        fltr.addAction(ACTION_UPDATE_LIST_INTENT)
        registerReceiver(mReceiver, fltr)

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

        recyclerView.adapter = adapter
        recyclerView.setEmptyView(emptyView)

        if (FirebaseAuth.getInstance().currentUser != null){
            setupRemoteDB()
        }

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
                startActivityForResult(intent, RC_NEW_CHARACTER)
            }
            builder.show()})
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_NEW_CHARACTER) {
            if (resultCode == Activity.RESULT_OK) {
                updateList()
            }
        }else if (requestCode == RC_SIGN_IN) { // RC_SIGN_IN is the request code you passed into startActivityForResult(...) when starting the sign in flow.
            val response = IdpResponse.fromResultIntent(data)

            // Successfully signed in
            if (resultCode == Activity.RESULT_OK) {
                val db = FirebaseFirestore.getInstance()
                val auth = FirebaseAuth.getInstance()

                db.collection(auth.currentUser!!.uid).get().addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        if (task.result.size() > 0) { //if remote data exists, write it to a local db
                            exportRemoteData(task.result)
                        }else{ //otherwise, export all local data to a remote db
                            for (ch in characterList){
                                db.collection(auth.currentUser!!.uid).document(ch.id.toString()).set(ch)
                            }
                        }
                    }
                }

                setupRemoteDB()
            } else {
                // Sign in failed
                if (response == null) {
                    // User pressed back button
                    return
                }

                if (response.error!!.errorCode == ErrorCodes.NO_NETWORK) {
                    showSnackbar(R.string.no_internet_connection)
                    return
                }

                showSnackbar(R.string.unknown_error)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(mReceiver)
        mReceiver = null
    }

    private fun showSnackbar (resString: Int){
        Snackbar.make(contentView, resources.getString(resString), Snackbar.LENGTH_LONG)
                .show()
    }

    private fun updateList(){
        characterList = Manager().loadCharacters(this)
        adapter.setCharactersList(characterList)
        adapter.notifyDataSetChanged()
    }

    private fun exportRemoteData (snapshot: QuerySnapshot){
        Manager().deleteAllCharactersLocalOnly(this) //delete all previous data to prevent duplication
        // (in case user had already signed in, signed out and when signed in once again)
        for (document in snapshot.documents) {
            val character = document.toObject(Character::class.java)!!
            character.id = document.id.toLong()
            Manager().insertCharacterLocalOnlyWithId(this, character)
        }

        updateList()
    }

    private fun setupRemoteDB (){
        // remote db settings
        val db = FirebaseFirestore.getInstance()
        val auth = FirebaseAuth.getInstance()

        FirebaseFirestore.setLoggingEnabled(true)
        val settings = FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build();
        db.firestoreSettings = settings
        db.collection(auth.currentUser!!.uid).addSnapshotListener(EventListener<QuerySnapshot> { //listen for a runtime changes at the remote db
            snapshot: QuerySnapshot?, e: FirebaseFirestoreException? ->
            if (e == null && snapshot != null) {
                exportRemoteData(snapshot)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        this.menu = menu
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_account -> {
                val auth = FirebaseAuth.getInstance()
                if (auth.currentUser == null) {
                    startActivityForResult(
                            // Get an instance of AuthUI based on the default app
                            AuthUI.getInstance().createSignInIntentBuilder()
                                    .setTheme(R.style.AppTheme)
                                    .build(),
                            RC_SIGN_IN);
                }else{
                    if (accountLayout.visibility == View.GONE) {
                        showUserLayout()
                    }else{
                        hideUserLayout()
                    }
                }
                return true
            }
            R.id.action_scan_qr -> {
                val intent = Intent(this, QRScanActivity::class.java)
                startActivityForResult(intent, RC_NEW_CHARACTER)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showUserLayout (){
        val auth = FirebaseAuth.getInstance()
        menu.getItem(1).icon = ContextCompat.getDrawable(this, R.drawable.ic_close_white_24dp)

        accountLayout.visibility = View.VISIBLE
        val anim = ValueAnimator.ofInt(appbar.measuredHeight, 480) //I don't know why 480, but it works
        anim.addUpdateListener { valueAnimator ->
            val `val` = valueAnimator.animatedValue as Int
            val layoutParams = appbar.layoutParams
            layoutParams.height = `val`
            appbar.layoutParams = layoutParams
        }
        anim.duration = 500
        anim.start()

        val user = auth.currentUser!!
        name.text = user.displayName
        email.text = user.email
        if (user.photoUrl != null) {
            Glide.with(this).load(user.photoUrl).apply(RequestOptions.circleCropTransform()).into(profilePic)
        }
        signOut.setOnClickListener({
            AuthUI.getInstance().signOut(this)
            hideUserLayout()
        })
    }

    private fun hideUserLayout() {
        menu.getItem(1).icon = ContextCompat.getDrawable(this, R.drawable.ic_account_circle_white_24dp)

        val anim = ValueAnimator.ofInt(appbar.measuredHeight, 170)
        anim.addUpdateListener { valueAnimator ->
            val `val` = valueAnimator.animatedValue as Int
            val layoutParams = appbar.layoutParams
            layoutParams.height = `val`
            appbar.layoutParams = layoutParams
            if (`val` == 170){
                accountLayout.visibility = View.GONE
            }
        }
        anim.duration = 500
        anim.start()
    }

    private inner class UpdateUIReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (intent.action == ACTION_UPDATE_LIST_INTENT) {
                updateList()
            }
        }
    }
}
