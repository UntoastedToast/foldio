package io.github.untoastedtoast.foldio.model

import android.location.Location
import io.github.untoastedtoast.foldio.model.field.PassContent
import io.github.untoastedtoast.foldio.model.field.PassField
import io.github.untoastedtoast.foldio.utils.Hash
import java.time.Instant
import java.time.ZonedDateTime

object PassCreator {
    const val FORMAT_VERSION = 1
    const val ORGANIZATION = "io.github.untoastedtoast.foldio"

    fun create(
        name: String,
        type: PassType,
        barCode: BarCode,
        organization: String = ORGANIZATION,
        serialNumber: String = "",
        logoText: String = "",
        colors: PassColors? = null,
        location: Location? = null,
        relevantDates: List<PassRelevantDate> = emptyList(),
        expirationDate: ZonedDateTime? = null,
    ): Pass? =
        create(
            name = name,
            type = type,
            barCodes = listOf(barCode),
            organization = organization,
            serialNumber = serialNumber,
            logoText = logoText,
            colors = colors,
            location = location,
            relevantDates = relevantDates,
            expirationDate = expirationDate,
        )

    fun create(
        name: String,
        type: PassType,
        barCodes: List<BarCode>,
        organization: String = ORGANIZATION,
        serialNumber: String = "",
        logoText: String = "",
        colors: PassColors? = null,
        location: Location? = null,
        relevantDates: List<PassRelevantDate> = emptyList(),
        expirationDate: ZonedDateTime? = null,
    ): Pass? {
        if (barCodes.isEmpty()) {
            return null
        }

        if (barCodes.any {
                try {
                    it.encodeAsBitmap(100, 100, false)
                    false
                } catch (_: IllegalArgumentException) {
                    true
                }
            }
        ) {
            return null
        }

        val id = Hash.sha256(barCodes.joinToString("|") { it.toString() })

        val nameField =
            PassField(
                key = "main",
                label = "",
                content = PassContent.Plain(name),
            )

        return Pass(
            id = id,
            description = name,
            formatVersion = FORMAT_VERSION,
            organization = organization.ifBlank { ORGANIZATION },
            serialNumber = serialNumber.ifBlank { id },
            type = type,
            barCodes = LinkedHashSet(barCodes),
            addedAt = Instant.now(),
            logoText = logoText.ifBlank { null },
            colors = colors,
            locations = location?.let { listOf(it) } ?: emptyList(),
            relevantDates = relevantDates,
            expirationDate = expirationDate,
            primaryFields = listOf(nameField),
        )
    }
}
