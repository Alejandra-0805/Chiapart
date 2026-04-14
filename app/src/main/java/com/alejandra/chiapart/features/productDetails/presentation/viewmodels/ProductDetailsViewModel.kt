package com.alejandra.chiapart.features.productDetails.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alejandra.chiapart.core.storage.TokenDataStore
import com.alejandra.chiapart.features.productDetails.domain.entities.Product
import com.alejandra.chiapart.features.productDetails.domain.usecases.ProductDetailsUseCases
import com.alejandra.chiapart.features.productDetails.presentation.screens.ProductDetailsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val productDetailsUseCases: ProductDetailsUseCases,
    private val tokenDataStore: TokenDataStore
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductDetailsUiState())
    val uiState: StateFlow<ProductDetailsUiState> = _uiState.asStateFlow()

    fun loadProductDetails(productId: Int) {
        _uiState.update {
            it.copy(
                isLoading = true,
                error = null
            )
        }

        viewModelScope.launch {
            val result = productDetailsUseCases.getProductDetails(productId)
            val currentUserId = tokenDataStore.currentUserIdFlow.firstOrNull()

            result.fold(
                onSuccess = { product ->
                    _uiState.update { currentState ->
                        currentState.copy(
                            isLoading = false,
                            product = product,
                            isSuccess = true,
                            isOwner = (currentUserId != null && currentUserId == product.usuarioId)
                        )
                    }
                },
                onFailure = { error ->
                    _uiState.update { currentState ->
                        currentState.copy(
                            isLoading = false,
                            error = error.message ?: "Error desconocido al cargar el producto"
                        )
                    }
                }
            )
        }
    }

    fun editProduct(product: Product) {
        _uiState.update {
            it.copy(
                isLoading = true,
                error = null
            )
        }

        viewModelScope.launch {
            val result = productDetailsUseCases.editProduct(product)

            result.fold(
                onSuccess = { updatedProduct ->
                    _uiState.update { currentState ->
                        currentState.copy(
                            isLoading = false,
                            product = updatedProduct,
                            isSuccess = true,
                            message = "Producto actualizado exitosamente"
                        )
                    }
                },
                onFailure = { error ->
                    _uiState.update { currentState ->
                        currentState.copy(
                            isLoading = false,
                            error = error.message ?: "Error al actualizar el producto"
                        )
                    }
                }
            )
        }
    }

    fun deleteProduct(productId: Int) {
        _uiState.update {
            it.copy(
                isLoading = true,
                error = null
            )
        }

        viewModelScope.launch {
            val result = productDetailsUseCases.deleteProduct(productId)

            result.fold(
                onSuccess = { success ->
                    _uiState.update { currentState ->
                        currentState.copy(
                            isLoading = false,
                            isDeleted = success,
                            message = if (success) "Producto eliminado exitosamente" else "No se pudo eliminar el producto"
                        )
                    }
                },
                onFailure = { error ->
                    _uiState.update { currentState ->
                        currentState.copy(
                            isLoading = false,
                            error = error.message ?: "Error al eliminar el producto"
                        )
                    }
                }
            )
        }
    }

    fun clearState() {
        _uiState.value = ProductDetailsUiState()
    }

    fun clearMessages() {
        _uiState.update {
            it.copy(
                message = null,
                error = null
            )
        }
    }

    fun setEditing(isEditing: Boolean) {
        _uiState.update {
            it.copy(isEditing = isEditing)
        }
    }
}