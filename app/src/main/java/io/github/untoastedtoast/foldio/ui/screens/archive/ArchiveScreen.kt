package io.github.untoastedtoast.foldio.ui.screens.archive

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Archive
import androidx.compose.material.icons.outlined.Deselect
import androidx.compose.material.icons.outlined.SelectAll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.mutableStateSetOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import io.github.untoastedtoast.foldio.R
import io.github.untoastedtoast.foldio.model.LocalizedPassWithTags
import io.github.untoastedtoast.foldio.ui.WalletScaffold
import io.github.untoastedtoast.foldio.ui.screens.wallet.SelectionActions
import io.github.untoastedtoast.foldio.ui.screens.wallet.WalletView
import io.github.untoastedtoast.foldio.ui.screens.wallet.WalletViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArchiveScreen(
    navController: NavHostController,
    walletViewModel: WalletViewModel,
) {
    val listState = rememberLazyListState()
    val selectedPasses = remember { mutableStateSetOf<LocalizedPassWithTags>() }
    val visiblePasses = remember { mutableStateOf<Set<LocalizedPassWithTags>>(emptySet()) }
    val allVisibleSelected = visiblePasses.value.isNotEmpty() && visiblePasses.value.all { selectedPasses.contains(it) }

    WalletScaffold(
        navController = navController,
        title = stringResource(id = R.string.the_archive),
        toolWindow = false,
        actions = {
            if (selectedPasses.isNotEmpty()) {
                IconButton(
                    onClick = {
                        if (allVisibleSelected) {
                            selectedPasses.removeAll(visiblePasses.value)
                        } else {
                            selectedPasses.addAll(visiblePasses.value)
                        }
                    },
                    enabled = visiblePasses.value.isNotEmpty(),
                ) {
                    Icon(
                        imageVector = if (allVisibleSelected) Icons.Outlined.Deselect else Icons.Outlined.SelectAll,
                        contentDescription =
                            if (allVisibleSelected) {
                                stringResource(R.string.clear_selection)
                            } else {
                                stringResource(R.string.select_all)
                            },
                    )
                }
            }
        },
        floatingActionButton = {
            if (selectedPasses.isNotEmpty()) {
                SelectionActions(
                    true,
                    selectedPasses,
                    listState,
                    walletViewModel,
                )
            }
        },
    ) { scrollBehavior ->
        WalletView(
            navController = navController,
            walletViewModel = walletViewModel,
            archive = true,
            emptyIcon = Icons.Default.Archive,
            listState = listState,
            scrollBehavior = scrollBehavior,
            selectedPasses = selectedPasses,
            onVisiblePassesChanged = { visiblePasses.value = it },
        )
    }
}
