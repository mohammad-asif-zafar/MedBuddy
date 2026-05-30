package com.hathway.medbuddy.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.hathway.medbuddy.data.GlucoseRecord

class GlucoseDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    
    companion object {
        private const val DATABASE_NAME = "glucose.db"
        private const val DATABASE_VERSION = 3

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
                $COLUMN_CREATED_AT INTEGER NOT NULL
            )
        """

        private const val DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_GLUCOSE_RECORDS"
    }
    
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE)
        // Demo data removed - only user-inserted data will be shown
    }
    
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(DROP_TABLE)
        onCreate(db)
    }
    
/*    private fun insertDemoData(db: SQLiteDatabase) {
        val currentTime = System.currentTimeMillis()
        val demoRecords = listOf(
            "9 Jan" to listOf(185, 180, 158, 140),
            "10 Jan" to listOf(140, 137, 144, null),
            "11 Jan" to listOf(156, 196, 178, 100),
            "12 Jan" to listOf(130, 178, 195, 170),
            "13 Jan" to listOf(140, 147, 125, 132),
            "14 Jan" to listOf(153, 168, null, null),
            "15 Jan" to listOf(141, 187, 165, 147),
            "16 Jan" to listOf(146, 232, 136, 125),
            "17 Jan" to listOf(187, 125, 113, 156),
            "18 Jan" to listOf(157, 129, 55, 140),
            "19 Jan" to listOf(237, 217, 186, 153),
            "20 Jan" to listOf(175, 184, 147, null),
            "21 Jan" to listOf(165, 193, 131, null),
            "22 Jan" to listOf(170, 191, 157, 185),
            "23 Jan" to listOf(182, 187, 138, null),
            "24 Jan" to listOf(187, 211, 208, 147),
            "25 Jan" to listOf(152, 185, 165, 179),
            "26 Jan" to listOf(225, 164, 191, 185),
            "27 Jan" to listOf(137, 210, 131, 205),
            "28 Jan" to listOf(176, 185, 203, 195),
            "29 Jan" to listOf(223, 186, 164, 203),
            "30 Jan" to listOf(164, 182, 139, 207),
            "31 Jan" to listOf(182, 208, 168, null),
            "1 Feb" to listOf(140, 234, 275, null),
            "2 Feb" to listOf(161, 207, 164, 200),
            "3 Feb" to listOf(177, 223, 193, 207),
            "4 Feb" to listOf(179, 189, 168, null),
            "5 Feb" to listOf(153, 213, 197, null),
            "6 Feb" to listOf(145, null, null, null),
            "7 Feb" to listOf(157, 205, 192, null),
            "8 Feb" to listOf(146, null, null, 205),
            "9 Feb" to listOf(145, null, null, 187)
        )
        
        demoRecords.forEach { (date, values) ->
            val stmt = db.compileStatement(
                "INSERT INTO $TABLE_GLUCOSE_RECORDS ($COLUMN_DATE, $COLUMN_FASTING, $COLUMN_BREAKFAST, $COLUMN_LUNCH, $COLUMN_DINNER, $COLUMN_CREATED_AT) VALUES (?, ?, ?, ?, ?, ?)"
            )
            stmt.bindString(1, date)
            stmt.bindLong(2, values[0]?.toLong() ?: 0)
            stmt.bindLong(3, values[1]?.toLong() ?: 0)
            stmt.bindLong(4, values[2]?.toLong() ?: 0)
            stmt.bindLong(5, values[3]?.toLong() ?: 0)
            stmt.bindLong(6, currentTime)
            stmt.execute()
            stmt.close()
        }
    }*/
    
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
                COLUMN_BEDTIME
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

                records.add(
                    GlucoseRecord(
                        date = date,
                        beforeBreakfast = if (it.isNull(it.getColumnIndexOrThrow(COLUMN_BEFORE_BREAKFAST))) null else beforeBreakfast,
                        afterBreakfast = if (it.isNull(it.getColumnIndexOrThrow(COLUMN_AFTER_BREAKFAST))) null else afterBreakfast,
                        beforeLunch = if (it.isNull(it.getColumnIndexOrThrow(COLUMN_BEFORE_LUNCH))) null else beforeLunch,
                        afterLunch = if (it.isNull(it.getColumnIndexOrThrow(COLUMN_AFTER_LUNCH))) null else afterLunch,
                        beforeDinner = if (it.isNull(it.getColumnIndexOrThrow(COLUMN_BEFORE_DINNER))) null else beforeDinner,
                        afterDinner = if (it.isNull(it.getColumnIndexOrThrow(COLUMN_AFTER_DINNER))) null else afterDinner,
                        bedtime = if (it.isNull(it.getColumnIndexOrThrow(COLUMN_BEDTIME))) null else bedtime
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
        bedtime: Int?
    ) {
        val db = writableDatabase
        val currentTime = System.currentTimeMillis()

        val stmt = db.compileStatement(
            "INSERT INTO $TABLE_GLUCOSE_RECORDS ($COLUMN_DATE, $COLUMN_BEFORE_BREAKFAST, $COLUMN_AFTER_BREAKFAST, $COLUMN_BEFORE_LUNCH, $COLUMN_AFTER_LUNCH, $COLUMN_BEFORE_DINNER, $COLUMN_AFTER_DINNER, $COLUMN_BEDTIME, $COLUMN_CREATED_AT) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)"
        )
        stmt.bindString(1, date)

        if (beforeBreakfast != null) {
            stmt.bindLong(2, beforeBreakfast.toLong())
        } else {
            stmt.bindNull(2)
        }

        if (afterBreakfast != null) {
            stmt.bindLong(3, afterBreakfast.toLong())
        } else {
            stmt.bindNull(3)
        }

        if (beforeLunch != null) {
            stmt.bindLong(4, beforeLunch.toLong())
        } else {
            stmt.bindNull(4)
        }

        if (afterLunch != null) {
            stmt.bindLong(5, afterLunch.toLong())
        } else {
            stmt.bindNull(5)
        }

        if (beforeDinner != null) {
            stmt.bindLong(6, beforeDinner.toLong())
        } else {
            stmt.bindNull(6)
        }

        if (afterDinner != null) {
            stmt.bindLong(7, afterDinner.toLong())
        } else {
            stmt.bindNull(7)
        }

        if (bedtime != null) {
            stmt.bindLong(8, bedtime.toLong())
        } else {
            stmt.bindNull(8)
        }

        stmt.bindLong(9, currentTime)
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
        bedtime: Int?
    ) {
        val db = writableDatabase
        val currentTime = System.currentTimeMillis()

        // Build the update SQL dynamically based on which values are provided
        // Only update fields that are not null - preserve existing values for null fields
        val updates = mutableListOf<String>()
        val args = mutableListOf<Any>()

        if (beforeBreakfast != null) {
            updates.add("$COLUMN_BEFORE_BREAKFAST = ?")
            args.add(beforeBreakfast.toLong())
        }
        if (afterBreakfast != null) {
            updates.add("$COLUMN_AFTER_BREAKFAST = ?")
            args.add(afterBreakfast.toLong())
        }
        if (beforeLunch != null) {
            updates.add("$COLUMN_BEFORE_LUNCH = ?")
            args.add(beforeLunch.toLong())
        }
        if (afterLunch != null) {
            updates.add("$COLUMN_AFTER_LUNCH = ?")
            args.add(afterLunch.toLong())
        }
        if (beforeDinner != null) {
            updates.add("$COLUMN_BEFORE_DINNER = ?")
            args.add(beforeDinner.toLong())
        }
        if (afterDinner != null) {
            updates.add("$COLUMN_AFTER_DINNER = ?")
            args.add(afterDinner.toLong())
        }
        if (bedtime != null) {
            updates.add("$COLUMN_BEDTIME = ?")
            args.add(bedtime.toLong())
        }

        if (updates.isNotEmpty()) {
            updates.add("$COLUMN_CREATED_AT = ?")
            args.add(currentTime)
            args.add(date)

            val sql = "UPDATE $TABLE_GLUCOSE_RECORDS SET ${updates.joinToString(", ")} WHERE $COLUMN_DATE = ?"
            db.execSQL(sql, args.toTypedArray())
        }
    }
}
