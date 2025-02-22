package com.book.ebook.presentation.book_list

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class BookListViewModel : ViewModel() {

    private val _state = MutableStateFlow(BookListState())
    val state = _state.asStateFlow()

    fun onAction(bookListAction: BookListAction){
        when (bookListAction){
            is BookListAction.onBookClick -> {

            }
            is BookListAction.onTabSelected -> {
                _state.update {
                    it.copy(selectedTabIndex = bookListAction.position)
                }
            }
            is BookListAction.onSearchQuqryChanged -> {
                _state.update {
                    it.copy(searchQuery = bookListAction.query)
                }
            }
        }
    }
}