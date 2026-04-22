package com.alejandra.chiapart.features.home.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun HomeFilterDrawerContent(
    categories: List<String>,
    regions: List<String>,
    selectedCategories: Set<String>,
    selectedRegions: Set<String>,
    onCategoryToggle: (String) -> Unit,
    onRegionToggle: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxHeight()
            .width(300.dp)
            .background(MaterialTheme.colorScheme.surface)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        
        Text(
            text = "CHIAPART",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
        )

        FilterSectionHeader(title = "Categoría")

        categories.forEach { category ->
            FilterItem(
                label = category,
                isSelected = selectedCategories.contains(category),
                onToggle = { onCategoryToggle(category) }
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        FilterSectionHeader(title = "Región")

        regions.forEach { region ->
            FilterItem(
                label = region,
                isSelected = selectedRegions.contains(region),
                onToggle = { onRegionToggle(region) }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}
