package com.coolguys.dndproject.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

// medium-level class, only for internal usage
/**
 * Class used to establish connection between Manager & database, knows the actual state of db and performs necessary actions to make db ready to work
 * (even database is ready to work, but you aren't)
 */
internal class Helper(context: Context?, name: String?, factory: SQLiteDatabase.CursorFactory?, version: Int) : SQLiteOpenHelper(context, name, factory, version) {
    lateinit var context: Context

    constructor(context: Context): this(context, Contract.DATABASE_NAME, null, Contract.DATABASE_VERSION)

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(Contract.CharactersTable.SQL_CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }
}