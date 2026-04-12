package io.github.untoastedtoast.foldio.ui.card

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import io.github.untoastedtoast.foldio.model.LocalizedPassWithTags
import io.github.untoastedtoast.foldio.model.PassType
import io.github.untoastedtoast.foldio.model.field.PassField
import io.github.untoastedtoast.foldio.ui.card.primary.BoardingPrimary
import io.github.untoastedtoast.foldio.ui.card.primary.GenericPrimary
import io.github.untoastedtoast.foldio.ui.screens.pass.AsyncPassImage

@Composable
fun ShortPassContent(
    localizedPass: LocalizedPassWithTags,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val pass = localizedPass.pass

    Column(
        modifier = modifier.padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        HeaderRow(pass, false)
        when (pass.type) {
            is PassType.Boarding -> BoardingPrimary(pass, pass.type.transitType, false)
            else -> GenericPrimary(pass, false)
        }
        if (pass.primaryFields.empty() && pass.hasStrip) {
            AsyncPassImage(
                model = pass.stripFile(context),
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

private fun List<PassField>.empty(): Boolean = this.isEmpty() || this.all { it.content.isEmpty() }

@Composable
fun PassContent(
    localizedPass: LocalizedPassWithTags,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit = {},
) {
    val context = LocalContext.current
    val pass = localizedPass.pass

    Column(
        modifier = modifier.padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        HeaderRow(pass)
        when (pass.type) {
            is PassType.Boarding -> BoardingPrimary(pass, pass.type.transitType)
            else -> GenericPrimary(pass)
        }
        AsyncPassImage(
            model = pass.stripFile(context),
            modifier = Modifier.fillMaxWidth(),
        )
        FieldsRow(pass.secondaryFields)
        FieldsRow(pass.auxiliaryFields)
        content()
    }
}
