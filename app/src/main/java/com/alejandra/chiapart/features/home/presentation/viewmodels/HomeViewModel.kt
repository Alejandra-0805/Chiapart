package com.alejandra.chiapart.features.home.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alejandra.chiapart.features.home.domain.entities.Product
import com.alejandra.chiapart.features.home.domain.usecases.HomeUseCases
import com.alejandra.chiapart.features.home.presentation.screens.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeUseCases: HomeUseCases
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState(isLoading = true))
    val uiState = _uiState.asStateFlow()

    private var searchJob: Job? = null

    init {
        fetchProducts()
    }

    fun onSearchQueryChange(query: String) {
        _uiState.update { currentState ->
            currentState.copy(searchQuery = query)
        }
        applyFilters()
    }

    fun onCategoryFilterToggle(category: String) {
        _uiState.update { currentState ->
            val updatedCategories = if (currentState.selectedCategories.contains(category)) {
                currentState.selectedCategories - category
            } else {
                currentState.selectedCategories + category
            }
            currentState.copy(selectedCategories = updatedCategories)
        }
        applyFilters()
    }

    fun onRegionFilterToggle(region: String) {
        _uiState.update { currentState ->
            val updatedRegions = if (currentState.selectedRegions.contains(region)) {
                currentState.selectedRegions - region
            } else {
                currentState.selectedRegions + region
            }
            currentState.copy(selectedRegions = updatedRegions)
        }
        applyFilters()
    }

    fun clearFilters() {
        _uiState.update { currentState ->
            currentState.copy(
                selectedCategories = emptySet(),
                selectedRegions = emptySet(),
                searchQuery = ""
            )
        }
        applyFilters()
    }

    fun onRetry() {
        fetchProducts()
    }

    private fun fetchProducts() {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(isLoading = true, error = null)
            }

            runCatching {
                homeUseCases.getProducts()
            }.onSuccess { products ->
                val categories = products.map { it.category }.distinct().sorted()
                val regions = products.map { it.region }.distinct().sorted()
                
                _uiState.update { currentState ->
                    currentState.copy(
                        isLoading = false,
                        allProducts = products,
                        products = products,
                        availableCategories = categories,
                        availableRegions = regions,
                        error = null
                    )
                }
            }.onFailure { throwable ->
                _uiState.update { currentState ->
                    currentState.copy(
                        isLoading = false,
                        error = throwable.message ?: "No se pudo cargar la informacion."
                    )
                }
            }
        }
    }

    private fun applyFilters() {
        val currentState = _uiState.value
        val query = currentState.searchQuery.trim().lowercase()
        val selectedCategories = currentState.selectedCategories
        val selectedRegions = currentState.selectedRegions

        val filteredProducts = currentState.allProducts.filter { product ->
            val matchesSearch = query.isBlank() ||
                    product.name.lowercase().contains(query) ||
                    product.category.lowercase().contains(query) ||
                    product.description.lowercase().contains(query)

            val matchesCategory = selectedCategories.isEmpty() ||
                    selectedCategories.contains(product.category)

            val matchesRegion = selectedRegions.isEmpty() ||
                    selectedRegions.contains(product.region)

            matchesSearch && matchesCategory && matchesRegion
        }

        _uiState.update { state ->
            state.copy(products = filteredProducts)
        }
    }
}