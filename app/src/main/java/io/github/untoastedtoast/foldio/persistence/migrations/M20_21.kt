package io.github.untoastedtoast.foldio.persistence.migrations

import androidx.room.DeleteColumn
import androidx.room.migration.AutoMigrationSpec

@Suppress("ClassName")
@DeleteColumn(
    tableName = "Pass",
    columnName = "relevantDate",
)
class M20_21 : AutoMigrationSpec
