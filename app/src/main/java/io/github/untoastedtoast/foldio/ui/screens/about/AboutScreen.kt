package io.github.untoastedtoast.foldio.ui.screens.about

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import io.github.untoastedtoast.foldio.R
import io.github.untoastedtoast.foldio.ui.WalletScaffold

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(navController: NavHostController) {
    WalletScaffold(
        navController = navController,
        toolWindow = true,
        title = stringResource(id = R.string.about),
    ) {
        AboutView(navController)
    }
}
