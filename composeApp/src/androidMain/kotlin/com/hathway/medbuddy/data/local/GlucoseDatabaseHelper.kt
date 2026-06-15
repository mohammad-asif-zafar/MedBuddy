package com.hathway.medbuddy.data.local

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.hathway.medbuddy.domain.model.GlucoseRecord

class GlucoseDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    
    companion object {
        private const val DATABASE_NAME = "glucose.db"
        private const val DATABASE_VERSION = 4

        private const val TABLE_GLUCOSE_RECORDS = "glucose_records"
        private const val COLUMN_ID = "id"
        private const val COLUMN_DATE = "date"
        private const val COLUMN_BEFORE_BREAKFAST = "before_breakfast"
        private const val COLUMN_AFTER_BREAKFAST = "after_breakfast"
        private const val COLUMN_BEFORE_LUNCH = "before_lunch"
        private const val COLUMN_AFTER_LUNCH = "after_lunch"
        private const val COLUMN_BEFORE_DINNER = "before_dinner"
        private const val COLUMN_AFTER_DINNER = "after_dinner"
        private const val COLUMN_BEDTIME = "bedtime"
        private const val COLUMN_TIME = "time"
        private const val COLUMN_MEAL_TYPE = "meal_type"
        private const val COLUMN_NOTES = "notes"
        private const val COLUMN_CREATED_AT = "created_at"

        private const val CREATE_TABLE = """
            CREATE TABLE $TABLE_GLUCOSE_RECORDS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_DATE TEXT NOT NULL,
                $COLUMN_BEFORE_BREAKFAST INTEGER,
                $COLUMN_AFTER_BREAKFAST INTEGER,
                $COLUMN_BEFORE_LUNCH INTEGER,
                $COLUMN_AFTER_LUNCH INTEGER,
                $COLUMN_BEFORE_DINNER INTEGER,
                $COLUMN_AFTER_DINNER INTEGER,
                $COLUMN_BEDTIME INTEGER,
                $COLUMN_TIME TEXT,
                $COLUMN_MEAL_TYPE TEXT,
                $COLUMN_NOTES TEXT,
                $COLUMN_CREATED_AT INTEGER NOT NULL
            )
        """

        private const val DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_GLUCOSE_RECORDS"
    }
    
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE)
    }
    
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(DROP_TABLE)
        onCreate(db)
    }
    
    fun getAllRecords(): List<GlucoseRecord> {
        val records = mutableListOf<GlucoseRecord>()
        val db = readableDatabase
        val cursor = db.query(
            TABLE_GLUCOSE_RECORDS,
            arrayOf(
                COLUMN_DATE,
                COLUMN_BEFORE_BREAKFAST,
                COLUMN_AFTER_BREAKFAST,
                COLUMN_BEFORE_LUNCH,
                COLUMN_AFTER_LUNCH,
                COLUMN_BEFORE_DINNER,
                COLUMN_AFTER_DINNER,
                COLUMN_BEDTIME,
                COLUMN_TIME,
                COLUMN_MEAL_TYPE,
                COLUMN_NOTES,
                COLUMN_CREATED_AT
            ),
            null, null, null, null,
            "$COLUMN_DATE DESC"
        )

        cursor?.use {
            while (it.moveToNext()) {
                val date = it.getString(it.getColumnIndexOrThrow(COLUMN_DATE))
                val beforeBreakfast = it.getInt(it.getColumnIndexOrThrow(COLUMN_BEFORE_BREAKFAST))
                val afterBreakfast = it.getInt(it.getColumnIndexOrThrow(COLUMN_AFTER_BREAKFAST))
                val beforeLunch = it.getInt(it.getColumnIndexOrThrow(COLUMN_BEFORE_LUNCH))
                val afterLunch = it.getInt(it.getColumnIndexOrThrow(COLUMN_AFTER_LUNCH))
                val beforeDinner = it.getInt(it.getColumnIndexOrThrow(COLUMN_BEFORE_DINNER))
                val afterDinner = it.getInt(it.getColumnIndexOrThrow(COLUMN_AFTER_DINNER))
                val bedtime = it.getInt(it.getColumnIndexOrThrow(COLUMN_BEDTIME))
                val time = it.getString(it.getColumnIndexOrThrow(COLUMN_TIME))
                val mealType = it.getString(it.getColumnIndexOrThrow(COLUMN_MEAL_TYPE))
                val notes = it.getString(it.getColumnIndexOrThrow(COLUMN_NOTES))
                val createdAt = it.getLong(it.getColumnIndexOrThrow(COLUMN_CREATED_AT))

                records.add(
                    GlucoseRecord(
                        date = date,
                        beforeBreakfast = if (it.isNull(it.getColumnIndexOrThrow(COLUMN_BEFORE_BREAKFAST))) null else beforeBreakfast,
                        afterBreakfast = if (it.isNull(it.getColumnIndexOrThrow(COLUMN_AFTER_BREAKFAST))) null else afterBreakfast,
                        beforeLunch = if (it.isNull(it.getColumnIndexOrThrow(COLUMN_BEFORE_LUNCH))) null else beforeLunch,
                        afterLunch = if (it.isNull(it.getColumnIndexOrThrow(COLUMN_AFTER_LUNCH))) null else afterLunch,
                        beforeDinner = if (it.isNull(it.getColumnIndexOrThrow(COLUMN_BEFORE_DINNER))) null else beforeDinner,
                        afterDinner = if (it.isNull(it.getColumnIndexOrThrow(COLUMN_AFTER_DINNER))) null else afterDinner,
                        bedtime = if (it.isNull(it.getColumnIndexOrThrow(COLUMN_BEDTIME))) null else bedtime,
                        time = time ?: "",
                        mealType = mealType ?: "",
                        notes = notes ?: "",
                        createdAt = createdAt
                    )
                )
            }
        }

        return records
    }
    
    fun insertRecord(
        date: String,
        beforeBreakfast: Int?,
        afterBreakfast: Int?,
        beforeLunch: Int?,
        afterLunch: Int?,
        beforeDinner: Int?,
        afterDinner: Int?,
        bedtime: Int?,
        time: String = "",
        mealType: String = "",
        notes: String = ""
    ) {
        val db = writableDatabase
        
        // Check if record exists for this date to avoid duplicates
        val cursor = db.query(TABLE_GLUCOSE_RECORDS, arrayOf(COLUMN_ID), "$COLUMN_DATE = ?", arrayOf(date), null, null, null)
        val exists = cursor != null && cursor.moveToFirst()
        cursor?.close()

        if (exists) {
            updateRecord(date, beforeBreakfast, afterBreakfast, beforeLunch, afterLunch, beforeDinner, afterDinner, bedtime, time, mealType, notes)
            return
        }

        val currentTime = System.currentTimeMillis()

        val stmt = db.compileStatement(
            "INSERT INTO $TABLE_GLUCOSE_RECORDS ($COLUMN_DATE, $COLUMN_BEFORE_BREAKFAST, $COLUMN_AFTER_BREAKFAST, $COLUMN_BEFORE_LUNCH, $COLUMN_AFTER_LUNCH, $COLUMN_BEFORE_DINNER, $COLUMN_AFTER_DINNER, $COLUMN_BEDTIME, $COLUMN_TIME, $COLUMN_MEAL_TYPE, $COLUMN_NOTES, $COLUMN_CREATED_AT) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
        )
        stmt.bindString(1, date)

        if (beforeBreakfast != null) stmt.bindLong(2, beforeBreakfast.toLong()) else stmt.bindNull(2)
        if (afterBreakfast != null) stmt.bindLong(3, afterBreakfast.toLong()) else stmt.bindNull(3)
        if (beforeLunch != null) stmt.bindLong(4, beforeLunch.toLong()) else stmt.bindNull(4)
        if (afterLunch != null) stmt.bindLong(5, afterLunch.toLong()) else stmt.bindNull(5)
        if (beforeDinner != null) stmt.bindLong(6, beforeDinner.toLong()) else stmt.bindNull(6)
        if (afterDinner != null) stmt.bindLong(7, afterDinner.toLong()) else stmt.bindNull(7)
        if (bedtime != null) stmt.bindLong(8, bedtime.toLong()) else stmt.bindNull(8)

        stmt.bindString(9, time)
        stmt.bindString(10, mealType)
        stmt.bindString(11, notes)
        stmt.bindLong(12, currentTime)
        stmt.execute()
        stmt.close()
    }

    fun updateRecord(
        date: String,
        beforeBreakfast: Int?,
        afterBreakfast: Int?,
        beforeLunch: Int?,
        afterLunch: Int?,
        beforeDinner: Int?,
        afterDinner: Int?,
        bedtime: Int?,
        time: String = "",
        mealType: String = "",
        notes: String = ""
    ) {
        val db = writableDatabase
        val currentTime = System.currentTimeMillis()

        val updates = mutableListOf<String>()
        val args = mutableListOf<Any>()

        if (beforeBreakfast != null) { updates.add("$COLUMN_BEFORE_BREAKFAST = ?"); args.add(beforeBreakfast.toLong()) }
        if (afterBreakfast != null) { updates.add("$COLUMN_AFTER_BREAKFAST = ?"); args.add(afterBreakfast.toLong()) }
        if (beforeLunch != null) { updates.add("$COLUMN_BEFORE_LUNCH = ?"); args.add(beforeLunch.toLong()) }
        if (afterLunch != null) { updates.add("$COLUMN_AFTER_LUNCH = ?"); args.add(afterLunch.toLong()) }
        if (beforeDinner != null) { updates.add("$COLUMN_BEFORE_DINNER = ?"); args.add(beforeDinner.toLong()) }
        if (afterDinner != null) { updates.add("$COLUMN_AFTER_DINNER = ?"); args.add(afterDinner.toLong()) }
        if (bedtime != null) { updates.add("$COLUMN_BEDTIME = ?"); args.add(bedtime.toLong()) }

        if (time.isNotEmpty()) { updates.add("$COLUMN_TIME = ?"); args.add(time) }
        if (mealType.isNotEmpty()) { updates.add("$COLUMN_MEAL_TYPE = ?"); args.add(mealType) }
        if (notes.isNotEmpty()) { updates.add("$COLUMN_NOTES = ?"); args.add(notes) }

        if (updates.isNotEmpty()) {
            updates.add("$COLUMN_CREATED_AT = ?")
            args.add(currentTime)
            args.add(date)

            val sql = "UPDATE $TABLE_GLUCOSE_RECORDS SET ${updates.joinToString(", ")} WHERE $COLUMN_DATE = ?"
            db.execSQL(sql, args.toTypedArray())
        }
    }
}
