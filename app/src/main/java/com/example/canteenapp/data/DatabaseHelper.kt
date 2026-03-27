package com.example.canteenapp.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "canteen_app.db"
        private const val DATABASE_VERSION = 1

        const val TABLE_ORDERS = "orders"
        const val COLUMN_ID = "id"
        const val COLUMN_ITEMS_SUMMARY = "items_summary"
        const val COLUMN_TOTAL_PRICE = "total_price"
        const val COLUMN_DATE = "date"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createOrdersTable = ("CREATE TABLE " + TABLE_ORDERS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_ITEMS_SUMMARY + " TEXT,"
                + COLUMN_TOTAL_PRICE + " REAL,"
                + COLUMN_DATE + " TEXT" + ")")
        db.execSQL(createOrdersTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDERS)
        onCreate(db)
    }

    /**
     * Inserts a new order into the database.
     * Returns the row ID of the newly inserted row, or -1 if an error occurred.
     */
    fun insertOrder(itemsSummary: String, totalPrice: Double, date: String): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_ITEMS_SUMMARY, itemsSummary)
            put(COLUMN_TOTAL_PRICE, totalPrice)
            put(COLUMN_DATE, date)
        }
        val success = db.insert(TABLE_ORDERS, null, values)
        db.close()
        return success
    }
}
