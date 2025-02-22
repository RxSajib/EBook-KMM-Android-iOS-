package com.book.ebook.presentation.book_list

import androidx.compose.ui.unit.TextUnit
import com.book.ebook.domain.Book

data class BookListState(
    val searchQuery : String = "Kotlin",
    val searchResult : List<Book>  = emptyList(),
    val favoritesBook : List<Book> = emptyList(),
    val isLoading : Boolean = false,
    val selectedTabIndex : Int = 0,
    val errorMessage : TextUnit? = null
)
