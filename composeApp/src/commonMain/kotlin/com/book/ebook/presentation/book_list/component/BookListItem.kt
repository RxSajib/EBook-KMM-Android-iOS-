package com.book.ebook.presentation.book_list.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.book.ebook.core.presentation.LightBlue
import com.book.ebook.core.presentation.SandYellow
import com.book.ebook.domain.Book
import org.jetbrains.compose.resources.painterResource
import serviceapp.composeapp.generated.resources.Res
import serviceapp.composeapp.generated.resources.stop_svgrepo_com
import kotlin.math.round

@Composable
fun BookListItem(
    modifier: Modifier = Modifier,
    onclick: () -> Unit,
    book: Book
) {
    Surface(
        shape = RoundedCornerShape(32.dp),
        modifier = modifier.clickable {
            onclick()
        },
        color = LightBlue.copy(alpha = 0.2f)
    ) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth().height(IntrinsicSize.Min),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.height(100.dp), contentAlignment = Alignment.Center){
                var imageLoadResult by remember {
                    mutableStateOf<Result<Painter>?>(null)
                }
                val painter = rememberAsyncImagePainter(
                    model = book.imageUrl,
                    onSuccess = {
                        if(it.painter.intrinsicSize.width > 1 && it.painter.intrinsicSize.height > 1){
                            Result.success(it.painter)
                        }else{
                            Result.failure(Exception("invalid image size"))
                        }
                    },
                    onError = {
                        it.result.throwable.printStackTrace()
                        imageLoadResult = Result.failure(it.result.throwable)
                    }
                )
                when(val result = imageLoadResult){
                    null -> CircularProgressIndicator()
                    else -> {
                        Image(
                            painter = if(result.isSuccess) painter else painterResource(Res.drawable.stop_svgrepo_com),
                            contentDescription = null,
                            contentScale = if(result.isSuccess) {
                                ContentScale.Crop
                            }else {
                                ContentScale.Fit
                            },
                            modifier = Modifier.aspectRatio(ratio = .6f, matchHeightConstraintsFirst = true),
                        )
                    }
                }
            }
            Spacer(
                modifier = Modifier.width(10.dp)
            )

            Column(
                modifier = Modifier.fillMaxHeight().weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = book.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                book.authors.firstOrNull()?.let { author ->
                    Text(
                        text = author,
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                book.averageRating?.let { rating ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${round(rating * 10) / 10.0}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = SandYellow
                        )
                    }

                }
            }

            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = null,
                modifier = Modifier.size(36.dp)
            )
        }
    }
}