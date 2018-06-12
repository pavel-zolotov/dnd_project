package org.qweco.dndproject.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.provider.BaseColumns
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.ArrayList
import org.qweco.dndproject.model.Character
import org.qweco.dndproject.utils.MapToJsonConverter

// high-level class, available outside of .data package
/**
 * Class performs such useful actions like insert, update, delete and load characters from database
 * (for more details about database implementation, look at Contact (low-level class, just describes the db structure) & Helper (medium-level class))
 * Actually, we have a sort of a hamburger of a .data classes, where Manager is the most high-level
 */
class Manager {
    /* Insert into database and give an id automatically */
    fun insertCharacter (context: Context, ch: Character): Long {
        // 1. get reference to writable DB
        val db = Helper(context).writableDatabase

        // 2. insert and auto generate id
        val id = db.insert(Contract.CharactersTable.TABLE_NAME, null, generateContentValues(ch))

        // 3. close
        db.close()

        val auth = FirebaseAuth.getInstance() // add entry to a Firestore if user is signed in
        if (auth.currentUser != null) {
            val db = FirebaseFirestore.getInstance()
            db.collection(auth.currentUser!!.uid).document(id.toString()).set(ch)
        }
        return id
    }

    /* Insert into local database and give a specified id
    * Not for common use! */
    fun insertCharacterLocalOnlyWithId (context: Context, ch: Character): Long {
        // 1. get reference to writable DB
        val db = Helper(context).writableDatabase

        // 2. insert character WITH specified id
        val id = db.insert(Contract.CharactersTable.TABLE_NAME, null, generateContentValuesWithId(ch))

        // 3. close
        db.close()

        return id
    }

    /* Update a row from database*/
    fun updateCharacter (context: Context, ch: Character): Long {
        if (ch.id == (-1).toLong()) { //check if character already exists
            return insertCharacter (context, ch)
        } else {
            // 1. get reference to writable DB
            val db = Helper(context).writableDatabase

            // 2. update
            db.update(Contract.CharactersTable.TABLE_NAME, generateContentValues(ch), "_id" + " = ? ", arrayOf((ch.id).toString()))

            // 3. close
            db.close()

            val auth = FirebaseAuth.getInstance() // update entry to a Firestore if user is signed in
            if (auth.currentUser != null) {
                val db = FirebaseFirestore.getInstance()
                db.collection(auth.currentUser!!.uid).document(ch.id.toString()).set(ch)
            }
            return ch.id
        }
    }

    /* Delete a row from database*/
    fun deleteCharacter (context: Context, id: Long) {
        // 1. get reference to writable DB
        val db = Helper(context).writableDatabase

        // 2. delete
        db.delete(Contract.CharactersTable.TABLE_NAME, "_id" + " = ?", arrayOf(id.toString()))

        // 3. close
        db.close()

        val auth = FirebaseAuth.getInstance() // delete entry to a Firestore if user is signed in
        if (auth.currentUser != null) {
            val db = FirebaseFirestore.getInstance()
            db.collection(auth.currentUser!!.uid).document(id.toString()).delete()
        }
    }

    /* Delete all rows from database*/
    fun deleteAllCharactersLocalOnly (context: Context) {
        // 1. get reference to writable DB
        val db = Helper(context).writableDatabase

        // 2. delete
        db.delete(Contract.CharactersTable.TABLE_NAME, null, null)

        // 3. close
        db.close()
    }

    /* Retrieve data from database */
    fun loadCharacters(context: Context): ArrayList<Character> {
        // 1. get reference to readable DB
        val db = Helper(context).readableDatabase

        // 2. forming query
        val query = "select * from " + Contract.CharactersTable.TABLE_NAME

        // 3. performing query
        val cursor = db.rawQuery(query, null)
        val characters = makeCharactersListWithCursor(cursor)

        // 4. close
        db.close()
        return characters
    }

    private fun makeCharactersListWithCursor (cursor: Cursor): ArrayList<Character> {
        val modelList = ArrayList<Character>()
        if (cursor.moveToFirst()) {
            do {
                // Fill the character with the following data
                val ch = Character(cursor.getLong(0), //id
                        cursor.getString(1), //name
                        cursor.getInt(2), //class
                        cursor.getInt(3), //race
                        cursor.getInt(4), //etc
                        cursor.getInt(5),
                        cursor.getInt(6),
                        cursor.getInt(7),
                        cursor.getInt(8),
                        cursor.getInt(9),
                        cursor.getInt(10),
                        MapToJsonConverter().revert(cursor.getString(11)), //we store Map of integers as a JSON string
                        MapToJsonConverter().revert(cursor.getString(12)), //here the same
                        cursor.getInt(13),
                        cursor.getInt(14),
                        cursor.getInt(15),
                        cursor.getInt(16),
                        cursor.getInt(17),
                        cursor.getInt(18),
                        cursor.getInt(19),
                        cursor.getInt(20),
                        cursor.getInt(21),
                        cursor.getInt(22))
                modelList.add(ch)
            } while (cursor.moveToNext())
        }
        return modelList
    }

    private fun generateContentValues (ch: Character): ContentValues {
        val values = ContentValues()
        values.put(Contract.CharactersTable.COLUMN_NAME_NAME, ch.name)
        values.put(Contract.CharactersTable.COLUMN_NAME_CLASS, ch.character_class)
        values.put(Contract.CharactersTable.COLUMN_NAME_RACE, ch.race)
        values.put(Contract.CharactersTable.COLUMN_NAME_LEVEL, ch.lvl)
        values.put(Contract.CharactersTable.COLUMN_NAME_HP, ch.hp)
        values.put(Contract.CharactersTable.COLUMN_NAME_SPEED, ch.speed)
        values.put(Contract.CharactersTable.COLUMN_NAME_INITIATIVE, ch.initiative)
        values.put(Contract.CharactersTable.COLUMN_NAME_HIT_DICE, ch.hitDice)
        values.put(Contract.CharactersTable.COLUMN_NAME_ARMOUR_CLASS, ch.armourClass)
        values.put(Contract.CharactersTable.COLUMN_NAME_PROFICIENCY, ch.proficiency)
        values.put(Contract.CharactersTable.COLUMN_NAME_SKILLS, MapToJsonConverter().convert(ch.skills)) //convert to a JSON string as we are not able to store Map in database
        values.put(Contract.CharactersTable.COLUMN_NAME_SAVING_THROWS, MapToJsonConverter().convert(ch.savingThrows)) //here the same
        values.put(Contract.CharactersTable.COLUMN_NAME_STRENGHT, ch.strength)
        values.put(Contract.CharactersTable.COLUMN_NAME_DEXTERITY, ch.dexterity)
        values.put(Contract.CharactersTable.COLUMN_NAME_CONSTITUTION, ch.constitution)
        values.put(Contract.CharactersTable.COLUMN_NAME_INTELLIGENCE, ch.intelligence)
        values.put(Contract.CharactersTable.COLUMN_NAME_WISDOM, ch.wisdom)
        values.put(Contract.CharactersTable.COLUMN_NAME_PERCEPTION, ch.perception)
        values.put(Contract.CharactersTable.COLUMN_NAME_CHARISMA, ch.charisma)
        values.put(Contract.CharactersTable.COLUMN_NAME_EYE_COLOR, ch.eyeColor)
        values.put(Contract.CharactersTable.COLUMN_NAME_HAIR_STYLE, ch.hairStyle)
        values.put(Contract.CharactersTable.COLUMN_NAME_SKIN_COLOR, ch.skinColor)
        return values
    }

    private fun generateContentValuesWithId (ch: Character): ContentValues {
        val values = generateContentValues(ch)
        values.put(BaseColumns._ID, ch.id)
        return values
    }
}