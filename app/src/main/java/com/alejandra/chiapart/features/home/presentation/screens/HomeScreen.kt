package com.alejandra.chiapart.features.home.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alejandra.chiapart.features.home.domain.entities.Product
import com.alejandra.chiapart.features.home.presentation.components.HomeEmptyState
import com.alejandra.chiapart.features.home.presentation.components.HomeErrorState
import com.alejandra.chiapart.features.home.presentation.components.HomeHeader
import com.alejandra.chiapart.features.home.presentation.components.HomeLoadingState
import com.alejandra.chiapart.features.home.presentation.components.HomeProductList
import com.alejandra.chiapart.features.home.presentation.components.HomeSearchBar
import com.alejandra.chiapart.features.home.presentation.viewmodels.HomeViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onProductClick: (Int) -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    HomeScreenContent(
        uiState = uiState,
        onSearchQueryChange = viewModel::onSearchQueryChange,
        onRetry = viewModel::onRetry,
        onProductClick = onProductClick
    )
}

@Composable
private fun HomeScreenContent(
    uiState: HomeUiState,
    onSearchQueryChange: (String) -> Unit,
    onRetry: () -> Unit,
    onProductClick: (Int) -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize()
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
            
            HomeHeader(
                title = "Home",
                subtitle = "Encuentra productos rapido con el buscador"
            )
            
            Spacer(modifier = Modifier.height(16.dp))

            HomeSearchBar(
                query = uiState.searchQuery,
                onQueryChange = onSearchQueryChange,
                placeholder = "Buscar por nombre, categoria o descripcion"
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
                    description = "Esta es una descripcion de ejemplo para el producto.",
                    price = 99.99
                )
            )
        ),
        onSearchQueryChange = {},
        onRetry = {},
        onProductClick = {}
    )
}
