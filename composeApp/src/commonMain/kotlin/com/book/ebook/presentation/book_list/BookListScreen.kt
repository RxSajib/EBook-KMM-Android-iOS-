package com.book.ebook.presentation.book_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.book.ebook.core.presentation.DarkBlue
import com.book.ebook.core.presentation.DesertWhite
import com.book.ebook.core.presentation.SandYellow
import com.book.ebook.domain.Book
import com.book.ebook.presentation.book_list.component.BookList
import com.book.ebook.presentation.book_list.component.BookSearchBar
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import serviceapp.composeapp.generated.resources.Res
import serviceapp.composeapp.generated.resources.favorites
import serviceapp.composeapp.generated.resources.no_favorites_book
import serviceapp.composeapp.generated.resources.no_search_results
import serviceapp.composeapp.generated.resources.search_results

@Composable
fun BookListScreenRoot(
    modifier: Modifier = Modifier,
    viewmodel: BookListViewModel = koinViewModel(),
    onBookClick: (Book) -> Unit
) {
    val state by viewmodel.state.collectAsStateWithLifecycle()
    BookListScreen(
        state = state,
        onAction = { action ->
            when (action) {
                is BookListAction.onBookClick -> onBookClick(action.book)
                else -> Unit
            }
            viewmodel.onAction(action)
        }
    )
}


@Composable
fun BookListScreen(
    state: BookListState,
    onAction: (BookListAction) -> Unit
) {

    val pagerState = rememberPagerState { 2 }
    val keyboardController = LocalSoftwareKeyboardController.current
    val searchResultListState = rememberLazyListState()

    LaunchedEffect(state.searchResult) {
        searchResultListState.animateScrollToItem(0)
    }

    LaunchedEffect(state.selectedTabIndex){
        pagerState.animateScrollToPage(state.selectedTabIndex)
    }

    LaunchedEffect(pagerState.currentPage){
        onAction(BookListAction.onTabSelected(pagerState.currentPage))
    }

    Column(
        modifier = Modifier.fillMaxSize()
            .background(DarkBlue)
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BookSearchBar(
            searchQuery = state.searchQuery,
            onSearchQueryChange = {
                onAction(BookListAction.onSearchQuqryChanged(it))
            },
            onImeSearch = {
                keyboardController?.hide()
            },
            modifier = Modifier.widthIn(max = 400.dp).fillMaxWidth().padding(16.dp)
        )

        Surface(
            modifier = Modifier.weight(1f).fillMaxWidth(),
            color = Color.White,
            shape = RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TabRow(
                    selectedTabIndex = state.selectedTabIndex,
                    modifier = Modifier.padding(vertical = 12.dp).widthIn(max = 700.dp)
                        .fillMaxWidth(),
                    containerColor = DesertWhite,
                    indicator = { tabPosition ->
                        TabRowDefaults.SecondaryIndicator(
                            color = SandYellow,
                            modifier = Modifier.tabIndicatorOffset(tabPosition[state.selectedTabIndex])
                        )
                    }
                ) {
                    Tab(
                        selected = state.selectedTabIndex == 0,
                        onClick = {
                            onAction(BookListAction.onTabSelected(0))
                        },
                        modifier = Modifier.weight(1f),
                        selectedContentColor = SandYellow,
                        unselectedContentColor = Color.Black.copy(alpha = 0.5f)
                    ) {
                        Text(
                            text = stringResource(Res.string.search_results),
                            modifier = Modifier.padding(vertical = 12.dp)
                        )
                    }
                    Tab(
                        selected = state.selectedTabIndex == 1,
                        onClick = {
                            onAction(BookListAction.onTabSelected(1))
                        },
                        modifier = Modifier.weight(1f),
                        selectedContentColor = SandYellow,
                        unselectedContentColor = Color.Black.copy(alpha = 0.5f)
                    ) {
                        Text(
                            text = stringResource(Res.string.favorites),
                            modifier = Modifier.padding(vertical = 12.dp)
                        )
                    }
                }

                Spacer(
                    modifier = Modifier.height(10.dp)
                )

                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxWidth().weight(1f)
                ) { pageIndex ->
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        when (pageIndex) {
                            0 -> {
                                if (state.isLoading) {
                                    CircularProgressIndicator()
                                } else {
                                    when {
                                        state.errorMessage != null -> {
                                            Text(
                                                text = state.errorMessage.toString(),
                                                textAlign = TextAlign.Center,
                                                style = MaterialTheme.typography.headlineSmall,
                                                color = MaterialTheme.colorScheme.error
                                            )
                                        }

                                        state.searchResult.isEmpty() -> {
                                            Text(
                                                text = stringResource(Res.string.no_search_results),
                                                textAlign = TextAlign.Center,
                                                style = MaterialTheme.typography.headlineSmall,
                                                color = MaterialTheme.colorScheme.background
                                            )
                                        }

                                        else -> {
                                            BookList(
                                                modifier = Modifier.fillMaxSize(),
                                                scrollState = searchResultListState,
                                                list = state.searchResult,
                                                onBookClick = {
                                                    onAction(BookListAction.onBookClick(it))
                                                }
                                            )
                                        }
                                    }
                                }
                            }

                            1 -> {
                                if (state.favoritesBook.isEmpty()) {
                                    Text(
                                        text = stringResource(Res.string.no_favorites_book),
                                        textAlign = TextAlign.Center,
                                        style = MaterialTheme.typography.headlineSmall,
                                        color = MaterialTheme.colorScheme.background
                                    )
                                } else {
                                    BookList(
                                        modifier = Modifier.fillMaxSize(),
                                        list = state.favoritesBook,
                                        onBookClick = {
                                            onAction(BookListAction.onBookClick(it))
                                        },
                                    )
                                }
                            }
                        }
                    }

                }
            }
        }
    }
}