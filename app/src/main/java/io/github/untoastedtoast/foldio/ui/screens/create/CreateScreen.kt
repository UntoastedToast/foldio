package io.github.untoastedtoast.foldio.ui.screens.create

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import io.github.untoastedtoast.foldio.ui.Screen
import io.github.untoastedtoast.foldio.ui.WalletScaffold

enum class CreateStartMode {
    Manual,
    Scan,
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateScreen(
    navController: NavHostController,
    createViewModel: CreateViewModel,
    startMode: CreateStartMode = CreateStartMode.Manual,
    initialBarcode: String? = null,
) {
    WalletScaffold(
        navController = navController,
        toolWindow = true,
        title = stringResource(id = Screen.Create.resourceId),
    ) {
        CreateView(
            navController,
            createViewModel,
            startMode = startMode,
            initialBarcode = initialBarcode,
        )
    }
}
