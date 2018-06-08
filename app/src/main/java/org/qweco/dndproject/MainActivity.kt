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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.firebase.ui.auth.ErrorCodes
import android.R.attr.data
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import android.view.ViewGroup
import android.animation.ValueAnimator
import android.util.TypedValue
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import org.qweco.dndproject.view.SlideAnimation


class MainActivity : AppCompatActivity() {
    private var characterList: ArrayList<Character> = ArrayList()
    private lateinit var adapter: CharacterAdapter
    private val RC_SIGN_IN = 123     // Choose an arbitrary request code value
    private val RC_NEW_CHARACTER = 456

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

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val auth = FirebaseAuth.getInstance()
        if (auth.currentUser != null) {
            //updateAuthUI ()
            // already signed in
        } else {

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_NEW_CHARACTER) {
            if (resultCode == Activity.RESULT_OK) {
                characterList = Manager().loadCharacters(this)
                adapter.setCharactersList(characterList)
                adapter.notifyDataSetChanged()
            }
        }else if (requestCode == RC_SIGN_IN) { // RC_SIGN_IN is the request code you passed into startActivityForResult(...) when starting the sign in flow.
            val response = IdpResponse.fromResultIntent(data)

            // Successfully signed in
            if (resultCode == Activity.RESULT_OK) {
                //updateAuthUI ()
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

    fun showSnackbar (resString: Int){
        Snackbar.make(contentView, resources.getString(resString), Snackbar.LENGTH_LONG)
                .show()
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
                    val px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100f, resources.displayMetrics)

                    if (accountLayout.visibility == View.GONE) {
                        /*accountLayout.visibility = View.VISIBLE
                        val slide_down = AnimationUtils.loadAnimation(this, R.anim.slide_down)
                        val slide_up = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up)

                        // Start animation
                        accountLayout.startAnimation(slide_down);*/

                        accountLayout.visibility = View.VISIBLE
                        // Start the animation
                        contentMain.animate()
                                .translationY(accountLayout.height.toFloat())
                                .setListener(null)

                        /*val animation = SlideAnimation(0, px.toInt(), accountLayout);

                        // this interpolator only speeds up as it keeps going
                        animation.interpolator = AccelerateInterpolator()
                        animation.duration = 300
                        accountLayout.animation = animation
                        accountLayout.startAnimation(animation)*/

                        /*val anim = ValueAnimator.ofInt(accountLayout.getMeasuredHeight(), px.toInt())
                        anim.addUpdateListener { valueAnimator ->
                            val `val` = valueAnimator.animatedValue as Int
                            val layoutParams = accountLayout.getLayoutParams()
                            layoutParams.height = `val`
                            accountLayout.setLayoutParams(layoutParams)
                        }
                        anim.duration = 500
                        anim.start()*/

                        val user = auth.currentUser!!
                        name.text = user.displayName
                        email.text = user.email
                        Glide.with(this).load(user.photoUrl).apply(RequestOptions.circleCropTransform()).into(profilePic);
                    }else{
                        contentMain.animate()
                            .translationY(0.toFloat())
                            .alpha(0.0f)
                            .setListener(object : AnimatorListenerAdapter() {
                                override fun onAnimationEnd(animation: Animator) {
                                    super.onAnimationEnd(animation)
                                    accountLayout.visibility = View.GONE
                                }
                            })

                        /*val animation = SlideAnimation(px.toInt(), 0, accountLayout);

                        // this interpolator only speeds up as it keeps going
                        animation.interpolator = AccelerateInterpolator()
                        animation.duration = 300
                        accountLayout.animation = animation
                        accountLayout.startAnimation(animation)*/


                        /*val anim = ValueAnimator.ofInt(accountLayout.getMeasuredHeight(), (-1*px).toInt())
                        anim.addUpdateListener { valueAnimator ->
                            val `val` = valueAnimator.animatedValue as Int
                            val layoutParams = accountLayout.getLayoutParams()
                            layoutParams.height = `val`
                            if (`val` == 1){
                                accountLayout.visibility = View.GONE
                            }
                            accountLayout.setLayoutParams(layoutParams)
                        }
                        anim.duration = 1000 //but.. why? why duration 500 at the start lasts as 1000 at the end?
                        // but the most important - it works!
                        anim.start()*/
                    }
                }
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
        return false
    }
}
