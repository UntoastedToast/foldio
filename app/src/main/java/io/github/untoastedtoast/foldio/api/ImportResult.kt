package io.github.untoastedtoast.foldio.api

sealed class ImportResult {
    object New : ImportResult()

    object Replaced : ImportResult()
}
