package nl.vleeming.bolcomvleeming.ui.productdetails.imageCarouselScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.skydoves.landscapist.glide.GlideImage
import nl.vleeming.bolcomvleeming.R
import nl.vleeming.bolcomvleeming.product.Media

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Images(media: List<Media>) {
    Column(Modifier.fillMaxWidth()) {
        val pagerState = rememberPagerState()
        HorizontalPager(
            count = media.size,
            state = pagerState,
        ) { page ->
            GlideImage(
                imageModel = media[page].url,
                modifier = Modifier.aspectRatio((4f / 4f)),
                contentScale = ContentScale.Fit,
                previewPlaceholder = R.drawable.halo_preview
            )
        }
        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp),
            activeColor = MaterialTheme.colors.primary,
            inactiveColor = MaterialTheme.colors.primaryVariant
        )
    }
}

@Preview
@Composable
fun ImagePreview() {
    Images(media = emptyList())
}