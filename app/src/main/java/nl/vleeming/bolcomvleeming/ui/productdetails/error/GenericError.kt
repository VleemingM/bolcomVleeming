package nl.vleeming.bolcomvleeming.ui.productdetails.error

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import nl.vleeming.bolcomvleeming.R

@Composable
fun GenericErrorScreen() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(id = R.string.error_message),
            color = MaterialTheme.colors.error,
            fontSize = 26.sp,
            textAlign = TextAlign.Center
        )
    }
}


@Preview(showSystemUi = true)
@Composable
fun ErrorScreenPreview() {
    GenericErrorScreen()
}