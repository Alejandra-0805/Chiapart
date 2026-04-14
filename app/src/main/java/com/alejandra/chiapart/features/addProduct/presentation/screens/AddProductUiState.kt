package com.alejandra.chiapart.features.addProduct.presentation.screens

import com.alejandra.chiapart.features.addProduct.domain.entities.Category
import com.alejandra.chiapart.features.addProduct.domain.entities.Region

data class AddProductUiState(
    val isLoading: Boolean = false,
    val isSubmitting: Boolean = false,
    val isSuccess: Boolean = false,
    val categories: List<Category> = emptyList(),
    val regions: List<Region> = emptyList(),
    val name: String = "",
    val price: String = "",
    val description: String = "",
    val selectedCategory: Category? = null,
    val selectedRegion: Region? = null,
    val imageUri: String? = null,
    val imageBytes: ByteArray? = null,
    val nameError: String? = null,
    val priceError: String? = null,
    val descriptionError: String? = null,
    val categoryError: String? = null,
    val regionError: String? = null,
    val error: String? = null,
    val successMessage: String? = null
) {
    val isFormValid: Boolean
        get() = name.isNotBlank() &&
                price.isNotBlank() &&
                price.toDoubleOrNull() != null &&
                description.isNotBlank() &&
                description.length <= 200 &&
                selectedCategory != null &&
                selectedRegion != null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AddProductUiState

        if (isLoading != other.isLoading) return false
        if (isSubmitting != other.isSubmitting) return false
        if (isSuccess != other.isSuccess) return false
        if (categories != other.categories) return false
        if (regions != other.regions) return false
        if (name != other.name) return false
        if (price != other.price) return false
        if (description != other.description) return false
        if (selectedCategory != other.selectedCategory) return false
        if (selectedRegion != other.selectedRegion) return false
        if (imageUri != other.imageUri) return false
        if (imageBytes != null) {
            if (other.imageBytes == null) return false
            if (!imageBytes.contentEquals(other.imageBytes)) return false
        } else if (other.imageBytes != null) return false
        if (nameError != other.nameError) return false
        if (priceError != other.priceError) return false
        if (descriptionError != other.descriptionError) return false
        if (categoryError != other.categoryError) return false
        if (regionError != other.regionError) return false
        if (error != other.error) return false
        if (successMessage != other.successMessage) return false

        return true
    }

    override fun hashCode(): Int {
        var result = isLoading.hashCode()
        result = 31 * result + isSubmitting.hashCode()
        result = 31 * result + isSuccess.hashCode()
        result = 31 * result + categories.hashCode()
        result = 31 * result + regions.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + price.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + (selectedCategory?.hashCode() ?: 0)
        result = 31 * result + (selectedRegion?.hashCode() ?: 0)
        result = 31 * result + (imageUri?.hashCode() ?: 0)
        result = 31 * result + (imageBytes?.contentHashCode() ?: 0)
        result = 31 * result + (nameError?.hashCode() ?: 0)
        result = 31 * result + (priceError?.hashCode() ?: 0)
        result = 31 * result + (descriptionError?.hashCode() ?: 0)
        result = 31 * result + (categoryError?.hashCode() ?: 0)
        result = 31 * result + (regionError?.hashCode() ?: 0)
        result = 31 * result + (error?.hashCode() ?: 0)
        result = 31 * result + (successMessage?.hashCode() ?: 0)
        return result
    }
}
