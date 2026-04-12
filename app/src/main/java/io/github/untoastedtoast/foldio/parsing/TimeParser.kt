package io.github.untoastedtoast.foldio.parsing

import java.time.ZonedDateTime

object TimeParser {
    fun parse(value: String): ZonedDateTime = ZonedDateTime.parse(value)
}
