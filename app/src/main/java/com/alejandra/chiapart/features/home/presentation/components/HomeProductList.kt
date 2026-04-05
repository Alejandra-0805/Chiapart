package com.alejandra.chiapart.features.home.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alejandra.chiapart.features.home.domain.entities.Product

@Composable
fun HomeProductList(
    products: List<Product>,
    onProductClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        items(
            items = products,
            key = { product -> product.id }
        ) { product ->
            HomeProductCard(
                product = product,
                onClick = { onProductClick(product.id) }
            )
        }
    }
}
