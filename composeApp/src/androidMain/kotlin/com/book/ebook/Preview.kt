package com.book.ebook

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.book.ebook.presentation.book_list.component.BookSearchBar

@Preview(showBackground = true)
@Composable
private fun Preview() {
    BookSearchBar(
        searchQuery = "Kotlin",
        onSearchQueryChange = {

        },
        modifier = Modifier.fillMaxWidth(),
        onImeSearch = {}
    )
}