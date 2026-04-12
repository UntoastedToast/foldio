@file:OptIn(ExperimentalMaterial3Api::class)

package io.github.untoastedtoast.foldio.ui

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.LibraryBooks
import androidx.compose.material.icons.filled.Archive
import androidx.compose.material.icons.filled.ContentPasteGo
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Wallet
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import io.github.untoastedtoast.foldio.R
import io.github.untoastedtoast.foldio.shortcut.Shortcut
import io.github.untoastedtoast.foldio.ui.screens.LibrariesScreen
import io.github.untoastedtoast.foldio.ui.screens.UpdateFailureScreen
import io.github.untoastedtoast.foldio.ui.screens.about.AboutScreen
import io.github.untoastedtoast.foldio.ui.screens.archive.ArchiveScreen
import io.github.untoastedtoast.foldio.ui.screens.create.AdvancedAddScreen
import io.github.untoastedtoast.foldio.ui.screens.create.CreateScreen
import io.github.untoastedtoast.foldio.ui.screens.create.CreateStartMode
import io.github.untoastedtoast.foldio.ui.screens.create.CreateViewModel
import io.github.untoastedtoast.foldio.ui.screens.pass.PassScreen
import io.github.untoastedtoast.foldio.ui.screens.pass.PassViewModel
import io.github.untoastedtoast.foldio.ui.screens.settings.SettingsScreen
import io.github.untoastedtoast.foldio.ui.screens.settings.SettingsViewModel
import io.github.untoastedtoast.foldio.ui.screens.wallet.WalletScreen
import io.github.untoastedtoast.foldio.ui.screens.wallet.WalletViewModel
import io.github.untoastedtoast.foldio.ui.screens.webview.WebviewScreen
import java.net.URLDecoder

sealed class Screen(
    val route: String,
    val icon: ImageVector,
    @param:StringRes val resourceId: Int,
) {
    data object Wallet : Screen("wallet", Icons.Default.Wallet, R.string.wallet)

    data object Archive : Screen("archive", Icons.Default.Archive, R.string.the_archive)

    data object About : Screen("about", Icons.Default.Info, R.string.about)

    data object Settings : Screen("settings", Icons.Default.Settings, R.string.settings)

    data object Libraries : Screen("libraries", Icons.AutoMirrored.Filled.LibraryBooks, R.string.libraries)

    data object Create : Screen("create", Icons.Default.Create, R.string.create_pass)

    data object CreateScan : Screen("create_scan", Icons.Default.QrCodeScanner, R.string.scan_code)

    data object AdvancedAdd : Screen("advanced_add", Icons.Default.MoreHoriz, R.string.advanced)

    data object Web : Screen("webview", Icons.Default.ContentPasteGo, R.string.webview)

    companion object {
        val topLevelScreens = listOf(Wallet, Archive, Settings)
    }
}

@Composable
fun WalletApp(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    createViewModel: CreateViewModel = viewModel(),
    passViewModel: PassViewModel = viewModel(),
    walletViewModel: WalletViewModel = viewModel(),
    settingsViewModel: SettingsViewModel = viewModel(),
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route
    val topLevelRoutes = Screen.topLevelScreens.map { it.route }
    val showBottomBar = currentRoute in topLevelRoutes

    Surface(modifier = modifier.fillMaxSize()) {
        Scaffold(
            bottomBar = {
                AnimatedVisibility(
                    visible = showBottomBar,
                    enter = slideInVertically { it },
                    exit = slideOutVertically { it },
                ) {
                    NavigationBar {
                        Screen.topLevelScreens.forEach { screen ->
                            NavigationBarItem(
                                selected = currentRoute == screen.route,
                                onClick = {
                                    navController.navigate(screen.route) {
                                        popUpTo(Screen.Wallet.route) { saveState = true }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                },
                                icon = {
                                    Icon(
                                        imageVector = screen.icon,
                                        contentDescription = stringResource(screen.resourceId),
                                    )
                                },
                                label = { Text(stringResource(screen.resourceId)) },
                            )
                        }
                    }
                }
            },
        ) { paddingValues ->
            NavHost(
                modifier = Modifier.padding(bottom = paddingValues.calculateBottomPadding()),
                navController = navController,
                startDestination = Screen.Wallet.route,
                enterTransition = {
                    slideIntoContainer(
                        SlideDirection.Start,
                        spring(dampingRatio = Spring.DampingRatioNoBouncy, stiffness = Spring.StiffnessMedium),
                    )
                },
                exitTransition = {
                    slideOutOfContainer(
                        SlideDirection.Start,
                        spring(dampingRatio = Spring.DampingRatioNoBouncy, stiffness = Spring.StiffnessMedium),
                    )
                },
                popEnterTransition = {
                    slideIntoContainer(
                        SlideDirection.End,
                        spring(dampingRatio = Spring.DampingRatioNoBouncy, stiffness = Spring.StiffnessMedium),
                    )
                },
                popExitTransition = {
                    slideOutOfContainer(
                        SlideDirection.End,
                        spring(dampingRatio = Spring.DampingRatioNoBouncy, stiffness = Spring.StiffnessMedium),
                    )
                },
            ) {
                composable(Screen.Wallet.route) {
                    WalletScreen(navController, walletViewModel)
                }
                composable(Screen.Archive.route) {
                    ArchiveScreen(navController, walletViewModel)
                }
                composable(Screen.About.route) {
                    AboutScreen(navController)
                }
                composable(
                    route = "webview/{url}",
                    arguments = listOf(navArgument("url") { type = NavType.StringType }),
                ) { backStackEntry ->
                    val rawUrl = backStackEntry.arguments?.getString("url")!!
                    val url = URLDecoder.decode(rawUrl, Charsets.UTF_8.name())
                    WebviewScreen(navController, walletViewModel, url)
                }
                composable(Screen.Settings.route) {
                    SettingsScreen(navController, settingsViewModel)
                }
                composable(Screen.Libraries.route) {
                    LibrariesScreen(navController)
                }
                composable(
                    route = "${Screen.Create.route}?barcode={barcode}",
                    arguments =
                        listOf(
                            navArgument("barcode") {
                                type = NavType.StringType
                                nullable = true
                                defaultValue = null
                            },
                        ),
                ) { backStackEntry ->
                    CreateScreen(
                        navController,
                        createViewModel,
                        initialBarcode = backStackEntry.arguments?.getString("barcode"),
                    )
                }
                composable(Screen.CreateScan.route) {
                    CreateScreen(navController, createViewModel, startMode = CreateStartMode.Scan)
                }
                composable(Screen.AdvancedAdd.route) {
                    AdvancedAddScreen(navController)
                }
                composable(
                    route = "pass/{passId}",
                    deepLinks =
                        listOf(
                            navDeepLink {
                                uriPattern = "${Shortcut.BASE_URI}/{passId}"
                            },
                        ),
                    arguments = listOf(navArgument("passId") { type = NavType.StringType }),
                ) { backStackEntry ->
                    val passId = backStackEntry.arguments?.getString("passId")!!
                    PassScreen(passId, navController, passViewModel)
                }
                composable(
                    route = "updateFailure/{reason}/{rationale}",
                    arguments =
                        listOf(
                            navArgument("reason") { type = NavType.StringType },
                            navArgument("rationale") { type = NavType.StringType },
                        ),
                ) { backStackEntry ->
                    val reason = backStackEntry.arguments?.getString("reason")!!
                    val rationale = backStackEntry.arguments?.getString("rationale")!!
                    UpdateFailureScreen(reason, rationale, navController)
                }
            }
        }
    }
}
