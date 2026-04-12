package io.github.untoastedtoast.foldio.ui.screens.settings

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import io.github.untoastedtoast.foldio.R
import io.github.untoastedtoast.foldio.ui.Screen
import io.github.untoastedtoast.foldio.ui.WalletScaffold

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController: NavHostController,
    settingsViewModel: SettingsViewModel,
) {
    WalletScaffold(
        navController = navController,
        toolWindow = false,
        actions = {
            IconButton(onClick = {
                navController.navigate(Screen.About.route)
            }) {
                Icon(imageVector = Screen.About.icon, contentDescription = stringResource(R.string.about))
            }
        },
        title = stringResource(id = R.string.settings),
    ) {
        SettingsView(settingsViewModel)
    }
}
