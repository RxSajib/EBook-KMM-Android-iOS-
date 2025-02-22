package com.book.ebook.presentation.book_list

import com.book.ebook.domain.Book

sealed interface BookListAction {

    data class onSearchQuqryChanged(val query : String) : BookListAction
    data class onBookClick(val book: Book) : BookListAction
    data class onTabSelected(val position : Int) : BookListAction
}