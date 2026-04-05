package com.alejandra.chiapart.features.productDetails.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alejandra.chiapart.features.productDetails.domain.entities.Product
import com.alejandra.chiapart.features.productDetails.presentation.components.ProductActionButtons
import com.alejandra.chiapart.features.productDetails.presentation.components.ProductCategoryChip
import com.alejandra.chiapart.features.productDetails.presentation.components.ProductDescriptionSection
import com.alejandra.chiapart.features.productDetails.presentation.components.ProductDetailsTopBar
import com.alejandra.chiapart.features.productDetails.presentation.components.ProductImage
import com.alejandra.chiapart.features.productDetails.presentation.components.ProductInfoSection
import com.alejandra.chiapart.features.productDetails.presentation.components.ProductRegionSection
import com.alejandra.chiapart.features.productDetails.presentation.viewmodels.ProductDetailsViewModel

@Composable
fun ProductDetailsScreen(
    productId: Int,
    onNavigateBack: () -> Unit,
    viewModel: ProductDetailsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ProductDetailsScreen(
        productId = productId,
        uiState = uiState,
        onNavigateBack = onNavigateBack,
        onLoadProduct = { viewModel.loadProductDetails(productId) },
        onDeleteProduct = { viewModel.deleteProduct(productId) },
        onClearState = { viewModel.clearState() },
        onClearMessages = { viewModel.clearMessages() }
    )
}

@Composable
fun ProductDetailsScreen(
    productId: Int,
    uiState: ProductDetailsUiState,
    onNavigateBack: () -> Unit,
    onLoadProduct: () -> Unit,
    onDeleteProduct: () -> Unit,
    onClearState: () -> Unit,
    onClearMessages: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(productId) {
        onLoadProduct()
    }

    LaunchedEffect(uiState.isDeleted) {
        if (uiState.isDeleted) {
            onNavigateBack()
            onClearState()
        }
    }

    LaunchedEffect(uiState.error) {
        uiState.error?.let {
            snackbarHostState.showSnackbar(it)
            onClearMessages()
        }
    }

    LaunchedEffect(uiState.message) {
        uiState.message?.let {
            snackbarHostState.showSnackbar(it)
            onClearMessages()
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            ProductDetailsTopBar(onNavigateBack = onNavigateBack)
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        when {
            uiState.isLoading && uiState.product == null -> {
                LoadingState(modifier = Modifier.padding(paddingValues))
            }
            uiState.error != null && uiState.product == null -> {
                ErrorState(
                    message = uiState.error ?: "Error desconocido",
                    onRetry = onLoadProduct,
                    modifier = Modifier.padding(paddingValues)
                )
            }
            uiState.product != null -> {
                ProductDetailsContent(
                    product = uiState.product!!,
                    isLoading = uiState.isLoading,
                    onEditClick = { /* TODO: Navigate to edit screen */ },
                    onDeleteClick = onDeleteProduct,
                    modifier = Modifier.padding(paddingValues)
                )
            }
        }
    }
}

@Composable
private fun ProductDetailsContent(
    product: Product,
    isLoading: Boolean,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.surfaceContainerHighest,
                        MaterialTheme.colorScheme.surface
                    )
                )
            )
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        ProductImage(
            imageUrl = product.imageUrl,
            contentDescription = product.name
        )

        Spacer(modifier = Modifier.height(16.dp))

        ProductCategoryChip(category = product.category)

        Spacer(modifier = Modifier.height(12.dp))

        ProductInfoSection(
            name = product.name,
            price = product.price
        )

        Spacer(modifier = Modifier.height(16.dp))

        ProductRegionSection(region = product.region)

        Spacer(modifier = Modifier.height(12.dp))

        ProductDescriptionSection(description = product.description)

        Spacer(modifier = Modifier.height(32.dp))

        ProductActionButtons(
            onEditClick = onEditClick,
            onDeleteClick = onDeleteClick,
            isLoading = isLoading
        )

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
private fun LoadingState(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
private fun ErrorState(
    message: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.error
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextButton(onClick = onRetry) {
            Text(
                text = "Reintentar",
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProductDetailsScreenPreview() {
    ProductDetailsScreen(
        productId = 1,
        uiState = ProductDetailsUiState(
            product = Product(
                id = 1,
                name = "Ejemplo de Producto",
                category = "Categoría",
                region = "Región",
                description = "Esta es una descripción de ejemplo para el preview.",
                price = 100.0,
                imageUrl = ""
            )
        ),
        onNavigateBack = {},
        onLoadProduct = {},
        onDeleteProduct = {},
        onClearState = {},
        onClearMessages = {}
    )
}
