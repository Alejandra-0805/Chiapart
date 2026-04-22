package com.alejandra.chiapart.features.home.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alejandra.chiapart.features.home.domain.entities.Product
import com.alejandra.chiapart.features.home.presentation.components.HomeEmptyState
import com.alejandra.chiapart.features.home.presentation.components.HomeErrorState
import com.alejandra.chiapart.features.home.presentation.components.HomeFilterDrawerContent
import com.alejandra.chiapart.features.home.presentation.components.HomeLoadingState
import com.alejandra.chiapart.features.home.presentation.components.HomeProductList
import com.alejandra.chiapart.features.home.presentation.components.HomeSearchBar
import com.alejandra.chiapart.features.home.presentation.components.HomeTopBar
import com.alejandra.chiapart.features.home.presentation.viewmodels.HomeViewModel
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onProductClick: (Int) -> Unit = {},
    onAddProductClick: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // Recarga la lista cada vez que el usuario regresa a Home (resume).
    // Esto cubre: volver de ProductDetails tras eliminar, y volver de AddProduct tras crear.
    val lifecycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current
    val lifecycleState by lifecycleOwner.lifecycle.currentStateFlow.collectAsStateWithLifecycle()
    LaunchedEffect(lifecycleState) {
        if (lifecycleState == Lifecycle.State.RESUMED) {
            viewModel.refresh()
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            HomeFilterDrawerContent(
                categories = uiState.availableCategories,
                regions = uiState.availableRegions,
                selectedCategories = uiState.selectedCategories,
                selectedRegions = uiState.selectedRegions,
                onCategoryToggle = viewModel::onCategoryFilterToggle,
                onRegionToggle = viewModel::onRegionFilterToggle
            )
        }
    ) {
        HomeScreenContent(
            uiState = uiState,
            onSearchQueryChange = viewModel::onSearchQueryChange,
            onRetry = viewModel::onRetry,
            onProductClick = onProductClick,
            onAddProductClick = onAddProductClick,
            onMenuClick = {
                scope.launch {
                    drawerState.open()
                }
            }
        )
    }
}

@Composable
private fun HomeScreenContent(
    uiState: HomeUiState,
    onSearchQueryChange: (String) -> Unit,
    onRetry: () -> Unit,
    onProductClick: (Int) -> Unit,
    onAddProductClick: () -> Unit,
    onMenuClick: () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            HomeTopBar(onMenuClick = onMenuClick)
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddProductClick,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Agregar Producto"
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.surfaceContainerHighest,
                            MaterialTheme.colorScheme.surface
                        )
                    )
                )
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            HomeSearchBar(
                query = uiState.searchQuery,
                onQueryChange = onSearchQueryChange,
                placeholder = "Buscar"
            )

            Spacer(modifier = Modifier.height(16.dp))

            when {
                uiState.isLoading -> {
                    HomeLoadingState()
                }

                uiState.error != null -> {
                    HomeErrorState(
                        message = uiState.error,
                        onRetry = onRetry
                    )
                }

                uiState.products.isEmpty() -> {
                    HomeEmptyState(
                        message = "No hay productos para mostrar"
                    )
                }

                else -> {
                    HomeProductList(
                        products = uiState.products,
                        onProductClick = onProductClick
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreenContent(
        uiState = HomeUiState(
            products = listOf(
                Product(
                    id = 1,
                    name = "Producto de Ejemplo",
                    category = "Categoria",
                    region = "Region 1",
                    description = "Esta es una descripcion de ejemplo para el producto.",
                    imageUrl = "https://via.placeholder.com/150",
                    price = 99.99
                )
            ),
            availableCategories = listOf("Categoría 1", "Categoría 2"),
            availableRegions = listOf("Región 1", "Región 2")
        ),
        onSearchQueryChange = {},
        onRetry = {},
        onProductClick = {},
        onAddProductClick = {},
        onMenuClick = {}
    )
}
