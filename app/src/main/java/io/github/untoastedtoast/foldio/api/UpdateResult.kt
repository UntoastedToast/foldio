package io.github.untoastedtoast.foldio.api

import androidx.annotation.StringRes
import io.github.untoastedtoast.foldio.R
import io.github.untoastedtoast.foldio.persistence.loader.PassLoadResult

sealed class UpdateResult {
    data class Success(
        val content: UpdateContent,
    ) : UpdateResult()

    data object NotUpdated : UpdateResult()

    data class Failed(
        val reason: FailureReason,
    ) : UpdateResult()
}

sealed class UpdateContent {
    data class LoadResult(
        val result: PassLoadResult,
    ) : UpdateContent()

    data class Pass(
        val pass: io.github.untoastedtoast.foldio.model.Pass,
    ) : UpdateContent()
}

sealed class FailureReason(
    @param:StringRes val messageId: Int,
) {
    object Timeout : FailureReason(R.string.timeout)

    data class Exception(
        val exception: kotlin.Exception,
    ) : FailureReason(R.string.exception),
        Detailed

    data class Status(
        val status: Int,
    ) : FailureReason(R.string.status_code),
        Detailed

    data object Forbidden : FailureReason(R.string.status_forbidden)

    interface Detailed
}
