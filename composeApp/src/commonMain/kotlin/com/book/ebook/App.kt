package com.book.ebook

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import com.book.ebook.presentation.book_list.BookListScreenRoot
import com.book.ebook.presentation.book_list.BookListViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
       BookListScreenRoot(
           viewmodel = remember { BookListViewModel() },
           onBookClick = {}
       )
    }
}