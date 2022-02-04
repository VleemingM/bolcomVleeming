package nl.vleeming.bolcomvleeming

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import nl.vleeming.bolcomvleeming.product.Attributes
import nl.vleeming.bolcomvleeming.product.Media
import nl.vleeming.bolcomvleeming.product.OfferData
import nl.vleeming.bolcomvleeming.product.Offers
import nl.vleeming.bolcomvleeming.product.Product
import nl.vleeming.bolcomvleeming.ui.theme.BolComVleemingTheme
import nl.vleeming.bolcomvleeming.ui.theme.PriceRed
import nl.vleeming.bolcomvleeming.ui.theme.onShoppingCart
import nl.vleeming.bolcomvleeming.ui.theme.shoppingCart

@Composable
fun ProductDetails(product: Product) {
    Column {
        ProductInformation(product)
    }

}

@Composable
fun ProductInformation(product: Product) {
    Price(price = product.offerData.offers[0].price)

    product.attributeGroups.forEach { attributeGroup ->
        attributeGroup.attributes.firstNotNullOfOrNull { attributes ->
            if (attributes.key == "BRAND") {
                attributes
            } else {
                null
            }
        }?.let {
            BrandName(brand = it)
        }
    }
    Title(product.title)
}

@Composable
fun Title(title: String) {
    Text(
        text = title,
        fontSize = 36.sp
    )
}

@Composable
fun BrandName(brand: Attributes) {
    Text(
        text = brand.value,
        fontSize = 12.sp,
        color = Color.LightGray
    )
}


@Composable
fun Price(price: Double) {
    val priceObject = PriceConverter.convertPrice(price)
    val afterDecimal = if (priceObject?.afterDecimal.toString().equals("0")) {
        "-"
    } else {
        priceObject?.afterDecimal.toString()
    }
    Row(modifier = Modifier.padding(vertical = 6.dp)) {
        Text(text = priceObject?.beforeDecimal.toString() + ",", color = PriceRed, fontSize = 20.sp)
        Text(text = afterDecimal, color = PriceRed, fontSize = 16.sp)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    BolComVleemingTheme {
        val testProduct = Product(
            "Google Chromecast 3 - Media Streamer",
            listOf(Media("abc", "abc", "")),
            OfferData(listOf(Offers(0, "Nieuw", 46.99, "Voor 23:59 besteld morgen in huis"))),
            emptyList(),
            ""
        )
        ProductDetails(product = testProduct)

    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DarkModePreview() {
    BolComVleemingTheme(darkTheme = true) {
        val testProduct = Product(
            "Google Chromecast 3 - Media Streamer",
            listOf(Media("abc", "abc", "")),
            OfferData(listOf(Offers(0, "Nieuw", 46.99, "Voor 23:59 besteld morgen in huis"))),
            emptyList(),
            ""
        )
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            ProductDetails(product = testProduct)
        }
    }
}

@Composable
fun AddToCart(modifier: Modifier,onClickAction: () -> Unit = {}) {

    val mainButtonColor = ButtonDefaults.buttonColors(
        backgroundColor = MaterialTheme.colors.shoppingCart,
        contentColor = MaterialTheme.colors.onShoppingCart
    )
    Button(
        onClick = onClickAction,
        modifier = modifier,
        colors = mainButtonColor,


    ) {
        Text(text = stringResource(id = R.string.add_to_cart))
    }

}

@Preview
@Composable
fun AddToCart() {
    AddToCart(Modifier.padding(8.dp))
}