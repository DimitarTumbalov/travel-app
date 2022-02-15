package com.synergygfs.travelapp.data

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import com.synergygfs.travelapp.data.TravelAppContractContract.CityEntity
import com.synergygfs.travelapp.data.TravelAppContractContract.LandmarkEntity
import com.synergygfs.travelapp.data.models.City
import java.util.*

class DbHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onConfigure(db: SQLiteDatabase) {
        db.setForeignKeyConstraintsEnabled(true)
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_CITY_ENTRIES)
        db.execSQL(SQL_CREATE_LANDMARK_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply discard the data and start over
        db.execSQL(SQL_DELETE_CITY_ENTRIES)
        db.execSQL(SQL_DELETE_LANDMARK_ENTRIES)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    @SuppressLint("Range")
    fun getAllCities(): Vector<City> {
        val cursor =
            this.readableDatabase?.query(CityEntity.TABLE_NAME, null, null, null, null, null, null)

        val citiesCollection = Vector<City>()

        while (cursor?.moveToNext() == true) {
            try {
                val id = cursor.getInt(cursor.getColumnIndex("_id"))
                val name = cursor.getString(cursor.getColumnIndex(CityEntity.COLUMN_NAME_NAME))
                val description =
                    cursor.getString(cursor.getColumnIndex(CityEntity.COLUMN_NAME_DESCRIPTION))

                val city = City(id, name, description)
                citiesCollection.add(city)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        cursor?.close()

        return citiesCollection
    }

    fun insert(tableName: String, values: ContentValues): Long? {
        return this.writableDatabase?.insert(tableName, null, values)
    }

    fun deleteById(tableName: String, id: Int): Int? {
        val selection = "${BaseColumns._ID} LIKE ?"
        val selectionArgs = arrayOf(id.toString())
        return this.writableDatabase?.delete(tableName, selection, selectionArgs)
    }

    companion object {
        // If you change the database schema, you must increment the database version.
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "TravelApp.db"

        private const val SQL_CREATE_CITY_ENTRIES =
            "CREATE TABLE IF NOT EXISTS ${CityEntity.TABLE_NAME} (" +
                    "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                    "${CityEntity.COLUMN_NAME_NAME} TEXT," +
                    "${CityEntity.COLUMN_NAME_DESCRIPTION} TEXT)"

        private const val SQL_CREATE_LANDMARK_ENTRIES =
            "CREATE TABLE IF NOT EXISTS ${LandmarkEntity.TABLE_NAME} (" +
                    "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                    "${LandmarkEntity.COLUMN_NAME_NAME} TEXT," +
                    "${LandmarkEntity.COLUMN_NAME_DESCRIPTION} TEXT," +
                    "FOREIGN KEY (${LandmarkEntity.COLUMN_NAME_CITY_ID}) REFERENCES ${CityEntity.TABLE_NAME}(_id))"

        private const val SQL_DELETE_CITY_ENTRIES = "DROP TABLE IF EXISTS ${CityEntity.TABLE_NAME}"
        private const val SQL_DELETE_LANDMARK_ENTRIES =
            "DROP TABLE IF EXISTS ${LandmarkEntity.TABLE_NAME}"
    }
}