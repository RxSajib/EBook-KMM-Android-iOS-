package com.book.ebook.presentation.book_list

import androidx.compose.ui.unit.TextUnit
import com.book.ebook.domain.Book

data class BookListState(
    val searchQuery : String = "Kotlin",
    val searchResult : List<Book>  = books,
    val favoritesBook : List<Book> = emptyList(),
    val isLoading : Boolean = false,
    val selectedTabIndex : Int = 0,
    val errorMessage : TextUnit? = null
)

val books = (1..100).map {
    Book(
        id = it.toString(),
        title = "title $it",
        description = null,
        imageUrl = "https:example.api",
        numPages = 100,
        firstPublishYear = null,
        authors = listOf("My name is sajib", "Roy"),
        languages = emptyList(),
        averageRating = 25.254512,
        numEditions = 100,
        ratingCount = 2
    )
}
