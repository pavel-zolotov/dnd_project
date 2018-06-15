package com.coolguys.dndproject.data

import android.provider.BaseColumns

// the most low-level class of all .data classes, only for internal usage
/**
 * Class describes a database structure, tables and basic features like creating table, dropping table and creating columns
 */
internal class Contract {
    companion object {
        // General database settings
        val DATABASE_VERSION = 1 // If you change the database schema, you must increment the database version!
        val DATABASE_NAME = "main.db"

        val TEXT_TYPE = " TEXT"
        val INTEGER_TYPE = " INTEGER"
        val NOT_NULL = " NOT NULL"
        val COMMA_SEP = ","
    }

    // Inner class that defines the table contents
    abstract class CharactersTable: BaseColumns {
        companion object {
            val TABLE_NAME = "characters"
            // Defining table columns
            val COLUMN_NAME_NAME = "name"
            val COLUMN_NAME_CLASS = "class"
            val COLUMN_NAME_RACE = "race"
            val COLUMN_NAME_LEVEL = "level"
            val COLUMN_NAME_HP = "hp"

            val COLUMN_NAME_SPEED = "speed"
            val COLUMN_NAME_INITIATIVE = "initiative"
            val COLUMN_NAME_HIT_DICE = "hitDice"
            val COLUMN_NAME_ARMOUR_CLASS = "armourClass"
            val COLUMN_NAME_PROFICIENCY = "proficiency"

            val COLUMN_NAME_SKILLS = "skills"
            val COLUMN_NAME_SAVING_THROWS = "savingThrows"

            val COLUMN_NAME_STRENGHT = "strength"
            val COLUMN_NAME_DEXTERITY = "dexterity"
            val COLUMN_NAME_CONSTITUTION = "constitution"
            val COLUMN_NAME_INTELLIGENCE = "intelligence"
            val COLUMN_NAME_WISDOM = "wisdom"
            val COLUMN_NAME_PERCEPTION = "perception"
            val COLUMN_NAME_CHARISMA = "charisma"

            val COLUMN_NAME_EYE_COLOR = "eyeColor"
            val COLUMN_NAME_HAIR_STYLE = "hairStyle"
            val COLUMN_NAME_SKIN_COLOR = "skinColor"

            // Creating table query
            val SQL_CREATE_TABLE = "CREATE TABLE " + Contract.CharactersTable.TABLE_NAME + " (" + /* default table definition syntax */
                BaseColumns._ID + " INTEGER PRIMARY KEY," + /* create ID which will be given to a new entry automatically */
                Contract.CharactersTable.COLUMN_NAME_NAME + TEXT_TYPE + NOT_NULL + COMMA_SEP + //defining all the columns with their types
                Contract.CharactersTable.COLUMN_NAME_CLASS + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
                Contract.CharactersTable.COLUMN_NAME_RACE + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
                Contract.CharactersTable.COLUMN_NAME_LEVEL + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
                Contract.CharactersTable.COLUMN_NAME_HP + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
                Contract.CharactersTable.COLUMN_NAME_SPEED + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
                Contract.CharactersTable.COLUMN_NAME_INITIATIVE + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
                Contract.CharactersTable.COLUMN_NAME_HIT_DICE + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
                Contract.CharactersTable.COLUMN_NAME_ARMOUR_CLASS + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
                Contract.CharactersTable.COLUMN_NAME_PROFICIENCY + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
                Contract.CharactersTable.COLUMN_NAME_SKILLS + TEXT_TYPE + NOT_NULL + COMMA_SEP +
                Contract.CharactersTable.COLUMN_NAME_SAVING_THROWS + TEXT_TYPE + NOT_NULL + COMMA_SEP +
                Contract.CharactersTable.COLUMN_NAME_STRENGHT + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
                Contract.CharactersTable.COLUMN_NAME_DEXTERITY + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
                Contract.CharactersTable.COLUMN_NAME_CONSTITUTION + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
                Contract.CharactersTable.COLUMN_NAME_INTELLIGENCE + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
                Contract.CharactersTable.COLUMN_NAME_WISDOM + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
                Contract.CharactersTable.COLUMN_NAME_PERCEPTION + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
                Contract.CharactersTable.COLUMN_NAME_CHARISMA + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
                Contract.CharactersTable.COLUMN_NAME_EYE_COLOR + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
                Contract.CharactersTable.COLUMN_NAME_HAIR_STYLE + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
                Contract.CharactersTable.COLUMN_NAME_SKIN_COLOR + INTEGER_TYPE + NOT_NULL + " )"

            val SQL_DELETE_TABLE = "DROP TABLE IF EXISTS " + Contract.CharactersTable.TABLE_NAME

            val SQL_ADD_COLUMN = "ALTER TABLE " + Contract.CharactersTable.TABLE_NAME + " ADD COLUMN "
        }
    }
}