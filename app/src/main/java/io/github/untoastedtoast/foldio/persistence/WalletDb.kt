package io.github.untoastedtoast.foldio.persistence

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import io.github.untoastedtoast.foldio.model.Pass
import io.github.untoastedtoast.foldio.model.PassGroup
import io.github.untoastedtoast.foldio.model.PassLocalization
import io.github.untoastedtoast.foldio.model.PassTagCrossRef
import io.github.untoastedtoast.foldio.model.Tag
import io.github.untoastedtoast.foldio.persistence.localization.PassLocalizationDao
import io.github.untoastedtoast.foldio.persistence.migrations.M14_15
import io.github.untoastedtoast.foldio.persistence.migrations.M20_21
import io.github.untoastedtoast.foldio.persistence.migrations.M_17_18
import io.github.untoastedtoast.foldio.persistence.migrations.M_18_19
import io.github.untoastedtoast.foldio.persistence.migrations.M_19_20
import io.github.untoastedtoast.foldio.persistence.migrations.M_9_10
import io.github.untoastedtoast.foldio.persistence.pass.PassDao
import io.github.untoastedtoast.foldio.persistence.tag.TagDao

fun buildDb(context: Context) =
    Room
        .databaseBuilder(context, WalletDb::class.java, "wallet_db")
        .addMigrations(M_9_10)
        .addMigrations(M_17_18)
        .addMigrations(M_18_19)
        .addMigrations(M_19_20)
        .build()

@Database(
    version = 23,
    entities = [Pass::class, PassLocalization::class, PassGroup::class, Tag::class, PassTagCrossRef::class],
    autoMigrations = [
        AutoMigration(from = 4, to = 5),
        AutoMigration(from = 5, to = 6),
        AutoMigration(from = 6, to = 7),
        AutoMigration(from = 7, to = 8),
        AutoMigration(from = 8, to = 9),
        AutoMigration(from = 10, to = 11),
        AutoMigration(from = 11, to = 12),
        AutoMigration(from = 12, to = 13),
        AutoMigration(from = 13, to = 14),
        AutoMigration(from = 14, to = 15, spec = M14_15::class),
        AutoMigration(from = 15, to = 16),
        AutoMigration(from = 16, to = 17),
        AutoMigration(from = 20, to = 21, spec = M20_21::class),
        AutoMigration(from = 21, to = 22),
        AutoMigration(from = 22, to = 23),
    ],
    exportSchema = true,
)
@TypeConverters(io.github.untoastedtoast.foldio.persistence.TypeConverters::class)
abstract class WalletDb : RoomDatabase() {
    abstract fun passDao(): PassDao

    abstract fun localizationDao(): PassLocalizationDao

    abstract fun tagDao(): TagDao
}
