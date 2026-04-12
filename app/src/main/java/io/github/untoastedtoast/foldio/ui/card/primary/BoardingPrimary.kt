package io.github.untoastedtoast.foldio.ui.card.primary

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import io.github.untoastedtoast.foldio.R
import io.github.untoastedtoast.foldio.model.Pass
import io.github.untoastedtoast.foldio.model.TransitType
import io.github.untoastedtoast.foldio.model.field.PassField
import io.github.untoastedtoast.foldio.ui.card.AutoSizePassFields
import io.github.untoastedtoast.foldio.ui.card.PassField

@Composable
fun BoardingPrimary(
    pass: Pass,
    transitType: TransitType,
    isSelectable: Boolean = true,
) {
    val departureField = pass.primaryFields.getOrElse(0) { PassField.Empty }
    val destinationField = pass.primaryFields.getOrElse(1) { PassField.Empty }

    val iconWidth = 40.dp
    val space = 10.dp

    AutoSizePassFields(
        fields = listOf(departureField, destinationField),
        modifier = Modifier.height(70.dp),
        spacing = iconWidth + space * 2,
        useFixedWidth = true,
    ) { fontSize ->
        Row(
            horizontalArrangement = Arrangement.spacedBy(space),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            PassField(
                field = departureField,
                modifier = Modifier.weight(1f),
                fontSize = fontSize,
                isSelectable = isSelectable,
            )

            Column {
                // The text correctly spaces the icon
                Text("", style = MaterialTheme.typography.labelMedium)
                Icon(
                    imageVector = transitType.icon,
                    contentDescription = stringResource(R.string.to),
                    modifier = Modifier.width(iconWidth).fillMaxHeight(),
                )
            }

            PassField(
                field = destinationField,
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.End,
                fontSize = fontSize,
                isSelectable = isSelectable,
            )
        }
    }
}
