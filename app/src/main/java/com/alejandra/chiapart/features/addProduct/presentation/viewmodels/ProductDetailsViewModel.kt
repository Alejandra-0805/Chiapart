package com.alejandra.chiapart.features.addProduct.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alejandra.chiapart.features.addProduct.domain.entities.Category
import com.alejandra.chiapart.features.addProduct.domain.entities.CreateProductRequest
import com.alejandra.chiapart.features.addProduct.domain.entities.Region
import com.alejandra.chiapart.features.addProduct.domain.usecases.AddProductUseCases
import com.alejandra.chiapart.features.addProduct.presentation.screens.AddProductUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddProductViewModel @Inject constructor(
    private val addProductUseCases: AddProductUseCases
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddProductUiState())
    val uiState: StateFlow<AddProductUiState> = _uiState.asStateFlow()

    init {
        loadInitialData()
    }

    private fun loadInitialData() {
        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            val categoriesResult = addProductUseCases.getCategories()
            val regionsResult = addProductUseCases.getRegions()

            categoriesResult.fold(
                onSuccess = { categories ->
                    _uiState.update { it.copy(categories = categories) }
                },
                onFailure = { error ->
                    _uiState.update {
                        it.copy(error = error.message ?: "Error al cargar categorías")
                    }
                }
            )

            regionsResult.fold(
                onSuccess = { regions ->
                    _uiState.update { it.copy(regions = regions, isLoading = false) }
                },
                onFailure = { error ->
                    _uiState.update {
                        it.copy(
                            error = error.message ?: "Error al cargar regiones",
                            isLoading = false
                        )
                    }
                }
            )
        }
    }

    fun onNameChange(name: String) {
        _uiState.update {
            it.copy(
                name = name,
                nameError = if (name.isBlank()) "El nombre es requerido" else null
            )
        }
    }

    fun onPriceChange(price: String) {
        val priceError = when {
            price.isBlank() -> "El precio es requerido"
            price.toDoubleOrNull() == null -> "Ingrese un precio válido"
            price.toDouble() <= 0 -> "El precio debe ser mayor a 0"
            else -> null
        }
        _uiState.update { it.copy(price = price, priceError = priceError) }
    }

    fun onDescriptionChange(description: String) {
        val descriptionError = when {
            description.isBlank() -> "La descripción es requerida"
            description.length > 200 -> "La descripción no puede exceder 200 caracteres"
            else -> null
        }
        _uiState.update { it.copy(description = description, descriptionError = descriptionError) }
    }

    fun onCategorySelected(category: Category) {
        _uiState.update { it.copy(selectedCategory = category, categoryError = null) }
    }

    fun onRegionSelected(region: Region) {
        _uiState.update { it.copy(selectedRegion = region, regionError = null) }
    }

    fun onImageSelected(uri: String?, bytes: ByteArray?) {
        _uiState.update { it.copy(imageUri = uri, imageBytes = bytes) }
    }

    fun onSubmit() {
        if (!validateForm()) return

        val state = _uiState.value
        val request = CreateProductRequest(
            name = state.name,
            price = state.price.toDouble(),
            description = state.description,
            categoryId = state.selectedCategory!!.id,
            regionId = state.selectedRegion!!.id
        )

        _uiState.update { it.copy(isSubmitting = true, error = null) }

        viewModelScope.launch {
            val result = addProductUseCases.createProduct(request, state.imageBytes)

            result.fold(
                onSuccess = { response ->
                    _uiState.update {
                        it.copy(
                            isSubmitting = false,
                            isSuccess = response.success,
                            successMessage = if (response.success) "Producto creado exitosamente" else response.message
                        )
                    }
                },
                onFailure = { error ->
                    _uiState.update {
                        it.copy(
                            isSubmitting = false,
                            error = error.message ?: "Error al crear el producto"
                        )
                    }
                }
            )
        }
    }

    private fun validateForm(): Boolean {
        val state = _uiState.value
        var isValid = true

        val nameError = if (state.name.isBlank()) {
            isValid = false
            "El nombre es requerido"
        } else null

        val priceError = when {
            state.price.isBlank() -> {
                isValid = false
                "El precio es requerido"
            }
            state.price.toDoubleOrNull() == null -> {
                isValid = false
                "Ingrese un precio válido"
            }
            state.price.toDouble() <= 0 -> {
                isValid = false
                "El precio debe ser mayor a 0"
            }
            else -> null
        }

        val descriptionError = when {
            state.description.isBlank() -> {
                isValid = false
                "La descripción es requerida"
            }
            state.description.length > 200 -> {
                isValid = false
                "La descripción no puede exceder 200 caracteres"
            }
            else -> null
        }

        val categoryError = if (state.selectedCategory == null) {
            isValid = false
            "Seleccione una categoría"
        } else null

        val regionError = if (state.selectedRegion == null) {
            isValid = false
            "Seleccione una región"
        } else null

        _uiState.update {
            it.copy(
                nameError = nameError,
                priceError = priceError,
                descriptionError = descriptionError,
                categoryError = categoryError,
                regionError = regionError
            )
        }

        return isValid
    }

    fun clearMessages() {
        _uiState.update { it.copy(error = null, successMessage = null) }
    }

    fun resetForm() {
        _uiState.update {
            AddProductUiState(
                categories = it.categories,
                regions = it.regions
            )
        }
    }

    fun retry() {
        loadInitialData()
    }
}