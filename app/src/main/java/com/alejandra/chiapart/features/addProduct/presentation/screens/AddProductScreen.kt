package com.alejandra.chiapart.features.addProduct.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alejandra.chiapart.features.addProduct.domain.entities.Category
import com.alejandra.chiapart.features.addProduct.domain.entities.Region
import com.alejandra.chiapart.features.addProduct.presentation.components.AddProductTopBar
import com.alejandra.chiapart.features.addProduct.presentation.components.CategoryDropdown
import com.alejandra.chiapart.features.addProduct.presentation.components.ErrorContent
import com.alejandra.chiapart.features.addProduct.presentation.components.LoadingContent
import com.alejandra.chiapart.features.addProduct.presentation.components.ProductImagePicker
import com.alejandra.chiapart.features.addProduct.presentation.components.ProductTextField
import com.alejandra.chiapart.features.addProduct.presentation.components.RegionDropdown
import com.alejandra.chiapart.features.addProduct.presentation.components.SubmitButton
import com.alejandra.chiapart.features.addProduct.presentation.viewmodels.AddProductViewModel

@Composable
fun AddProductScreen(
    onNavigateBack: () -> Unit,
    onProductCreated: () -> Unit,
    viewModel: AddProductViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    AddProductScreen(
        uiState = uiState,
        onNavigateBack = onNavigateBack,
        onProductCreated = onProductCreated,
        onNameChange = viewModel::onNameChange,
        onPriceChange = viewModel::onPriceChange,
        onDescriptionChange = viewModel::onDescriptionChange,
        onCategorySelected = viewModel::onCategorySelected,
        onRegionSelected = viewModel::onRegionSelected,
        onImageSelected = viewModel::onImageSelected,
        onSubmit = viewModel::onSubmit,
        onClearMessages = viewModel::clearMessages,
        onRetry = viewModel::retry
    )
}

@Composable
fun AddProductScreen(
    uiState: AddProductUiState,
    onNavigateBack: () -> Unit,
    onProductCreated: () -> Unit,
    onNameChange: (String) -> Unit,
    onPriceChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onCategorySelected: (Category) -> Unit,
    onRegionSelected: (Region) -> Unit,
    onImageSelected: (String?, ByteArray?) -> Unit,
    onSubmit: () -> Unit,
    onClearMessages: () -> Unit,
    onRetry: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess) {
            uiState.successMessage?.let {
                snackbarHostState.showSnackbar(it)
            }
            onProductCreated()
        }
    }

    LaunchedEffect(uiState.error) {
        uiState.error?.let {
            snackbarHostState.showSnackbar(it)
            onClearMessages()
        }
    }

    LaunchedEffect(uiState.successMessage) {
        uiState.successMessage?.let {
            snackbarHostState.showSnackbar(it)
            onClearMessages()
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AddProductTopBar(onNavigateBack = onNavigateBack)
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        when {
            uiState.isLoading && uiState.categories.isEmpty() -> {
                LoadingContent(modifier = Modifier.padding(paddingValues))
            }
            uiState.error != null && uiState.categories.isEmpty() -> {
                ErrorContent(
                    message = uiState.error ?: "Error desconocido",
                    onRetry = onRetry,
                    modifier = Modifier.padding(paddingValues)
                )
            }
            else -> {
                AddProductFormContent(
                    uiState = uiState,
                    onNameChange = onNameChange,
                    onPriceChange = onPriceChange,
                    onDescriptionChange = onDescriptionChange,
                    onCategorySelected = onCategorySelected,
                    onRegionSelected = onRegionSelected,
                    onImageSelected = onImageSelected,
                    onSubmit = onSubmit,
                    modifier = Modifier.padding(paddingValues)
                )
            }
        }
    }
}

@Composable
private fun AddProductFormContent(
    uiState: AddProductUiState,
    onNameChange: (String) -> Unit,
    onPriceChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onCategorySelected: (Category) -> Unit,
    onRegionSelected: (Region) -> Unit,
    onImageSelected: (String?, ByteArray?) -> Unit,
    onSubmit: () -> Unit,
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

        ProductImagePicker(
            imageUri = uiState.imageUri,
            onImageSelected = onImageSelected
        )

        Spacer(modifier = Modifier.height(24.dp))

        ProductTextField(
            value = uiState.name,
            onValueChange = onNameChange,
            label = "Nombre del producto",
            error = uiState.nameError
        )

        Spacer(modifier = Modifier.height(16.dp))

        ProductTextField(
            value = uiState.price,
            onValueChange = onPriceChange,
            label = "Precio",
            error = uiState.priceError,
            keyboardType = KeyboardType.Decimal
        )

        Spacer(modifier = Modifier.height(16.dp))

        ProductTextField(
            value = uiState.description,
            onValueChange = onDescriptionChange,
            label = "Descripción",
            error = uiState.descriptionError,
            singleLine = false,
            maxLines = 4,
            maxLength = 200
        )

        Spacer(modifier = Modifier.height(16.dp))

        CategoryDropdown(
            categories = uiState.categories,
            selectedCategory = uiState.selectedCategory,
            onCategorySelected = onCategorySelected,
            error = uiState.categoryError
        )

        Spacer(modifier = Modifier.height(16.dp))

        RegionDropdown(
            regions = uiState.regions,
            selectedRegion = uiState.selectedRegion,
            onRegionSelected = onRegionSelected,
            error = uiState.regionError
        )

        Spacer(modifier = Modifier.height(32.dp))

        SubmitButton(
            text = "Crear Producto",
            onClick = onSubmit,
            isLoading = uiState.isSubmitting,
            enabled = uiState.isFormValid
        )

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun AddProductScreenPreview() {
    AddProductScreen(
        uiState = AddProductUiState(
            categories = listOf(
                Category(1, "Artesanías"),
                Category(2, "Textiles")
            ),
            regions = listOf(
                Region(1, "Centro"),
                Region(2, "Norte")
            )
        ),
        onNavigateBack = {},
        onProductCreated = {},
        onNameChange = {},
        onPriceChange = {},
        onDescriptionChange = {},
        onCategorySelected = {},
        onRegionSelected = {},
        onImageSelected = { _, _ -> },
        onSubmit = {},
        onClearMessages = {},
        onRetry = {}
    )
}
