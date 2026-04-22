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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

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
        onEditProduct = { viewModel.editProduct(it) },
        onSetEditing = { viewModel.setEditing(it) },
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
    onEditProduct: (Product) -> Unit,
    onSetEditing: (Boolean) -> Unit,
    onClearState: () -> Unit,
    onClearMessages: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    var showDeleteConfirmation by remember { mutableStateOf(false) }

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
                    product = uiState.product,
                    isLoading = uiState.isLoading,
                    isOwner = uiState.isOwner,
                    onEditClick = { onSetEditing(true) },
                    onDeleteClick = { showDeleteConfirmation = true },
                    modifier = Modifier.padding(paddingValues)
                )
                
                if (showDeleteConfirmation) {
                    AlertDialog(
                        onDismissRequest = { showDeleteConfirmation = false },
                        title = { Text("Eliminar Producto") },
                        text = { Text("¿Estás seguro de que deseas eliminar este producto? Esta acción no se puede deshacer y solo puedes eliminarlo si tú lo creaste.") },
                        confirmButton = {
                            TextButton(
                                onClick = {
                                    showDeleteConfirmation = false
                                    onDeleteProduct()
                                }
                            ) {
                                Text("Eliminar", color = MaterialTheme.colorScheme.error)
                            }
                        },
                        dismissButton = {
                            TextButton(onClick = { showDeleteConfirmation = false }) {
                                Text("Cancelar")
                            }
                        }
                    )
                }

                if (uiState.isEditing) {
                    EditProductDialog(
                        product = uiState.product,
                        onDismiss = { onSetEditing(false) },
                        onConfirm = { editedProduct ->
                            onEditProduct(editedProduct)
                            onSetEditing(false)
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun EditProductDialog(
    product: Product,
    onDismiss: () -> Unit,
    onConfirm: (Product) -> Unit
) {
    var name by remember { mutableStateOf(product.name) }
    var description by remember { mutableStateOf(product.description) }
    var price by remember { mutableStateOf(product.price.toString()) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Editar Producto") },
        text = {
            Column {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Nombre") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Descripción") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = price,
                    onValueChange = { price = it },
                    label = { Text("Precio") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    val newPrice = price.toDoubleOrNull() ?: product.price
                    val editedProduct = product.copy(
                        name = name,
                        description = description,
                        price = newPrice
                    )
                    onConfirm(editedProduct)
                }
            ) {
                Text("Guardar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}

@Composable
private fun ProductDetailsContent(
    product: Product,
    isLoading: Boolean,
    isOwner: Boolean,
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

        if (isOwner) {
            ProductActionButtons(
                onEditClick = onEditClick,
                onDeleteClick = onDeleteClick,
                isLoading = isLoading
            )
            Spacer(modifier = Modifier.height(32.dp))
        }
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
                categoryId = 1,
                category = "Categoría",
                regionId = 1,
                region = "Región",
                description = "Esta es una descripción de ejemplo para el preview.",
                price = 100.0,
                imageUrl = ""
            )
        ),
        onNavigateBack = {},
        onLoadProduct = {},
        onDeleteProduct = {},
        onEditProduct = {},
        onSetEditing = {},
        onClearState = {},
        onClearMessages = {}
    )
}
