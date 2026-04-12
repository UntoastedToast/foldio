package io.github.untoastedtoast.foldio.persistence.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val M_18_19 =
    object : Migration(18, 19) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL(
                """
                ALTER TABLE Pass
                ADD COLUMN relevantDates TEXT NOT NULL DEFAULT '[]'
                """.trimIndent(),
            )
        }
    }
