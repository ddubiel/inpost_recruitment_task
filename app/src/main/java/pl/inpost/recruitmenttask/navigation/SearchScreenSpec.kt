package pl.inpost.recruitmenttask.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import pl.inpost.recruitmenttask.features.shipments.viewmodel.ShipmentsViewModel


data object ShipmentsScreenSpec : ScreenSpec {
    override val route: String
        get() = "shipments"

    @Composable
    override fun Content(navController: NavController, navBackStackEntry: NavBackStackEntry) {
        SearchDestination()
    }

}

@Composable
fun SearchDestination(
    viewModel: ShipmentsViewModel = hiltViewModel()
) {
    /*val viewState: State<SearchViewState> = viewModel.uiState.collectAsState()
    SearchView(viewModel, viewState)*/
}