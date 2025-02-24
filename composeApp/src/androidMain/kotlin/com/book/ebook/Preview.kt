package com.book.ebook

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.book.ebook.domain.Book
import com.book.ebook.presentation.book_list.BookListScreen
import com.book.ebook.presentation.book_list.BookListState
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


val books = (1..100).map {
    Book(
        id = it.toString(),
        title = "title $it",
        description = null,
        imageUrl = "https:example.api",
        numPages = 100,
        firstPublishYear = null,
        authors = listOf("Sajib", "Roy"),
        languages = emptyList(),
        averageRating = 25.254512,
        numEditions = 100,
        ratingCount = 2
    )
}

@Preview(showBackground = true)
@Composable
private fun BookListPreview() {
    BookListScreen(
        state = BookListState(),
        onAction = {}
    )
}