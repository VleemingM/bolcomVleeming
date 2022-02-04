package nl.vleeming.bolcomvleeming.ui.productdetails

import android.app.Activity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetState
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.gson.Gson
import kotlinx.coroutines.launch
import nl.vleeming.bolcomvleeming.AddToCart
import nl.vleeming.bolcomvleeming.FullProductDescription
import nl.vleeming.bolcomvleeming.ProductDescription
import nl.vleeming.bolcomvleeming.ProductDetails
import nl.vleeming.bolcomvleeming.ProductWrapper
import nl.vleeming.bolcomvleeming.product.Product
import nl.vleeming.bolcomvleeming.product.Products
import nl.vleeming.bolcomvleeming.ui.productdetails.imageCarouselScreen.Images
import nl.vleeming.bolcomvleeming.ui.theme.BolComVleemingTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProductScreen(product: Product) {
    var itemAddedToCart by remember { mutableStateOf(false) }

    val bottomSheetState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
    )

    val coroutineScope = rememberCoroutineScope()

    product.let { item ->
        BottomSheetScaffold(
            sheetContent = {
                FullProductDescription(item.longDescription ?: item.shortDescription)
            },
            topBar = { TopAppBarContent(itemAddedToCart) },
            content = {
                ProductInformation(
                    product = item,
                    openBottomSheetDialog = {
                        coroutineScope.launch {
                            if (bottomSheetState.bottomSheetState.isCollapsed) {
                                bottomSheetState.bottomSheetState.expand()
                            } else {
                                bottomSheetState.bottomSheetState.collapse()
                            }
                        }
                    },
                    addToCartClickAction = {
                        itemAddedToCart = true
                    }
                )
            },
            modifier = Modifier.padding(horizontal = 8.dp),
            sheetPeekHeight = 0.dp,
            scaffoldState = bottomSheetState,
        )


    }

}

@Composable
fun ProductInformation(
    product: Product,
    openBottomSheetDialog: () -> Unit,
    addToCartClickAction: () -> Unit
) {
    Column {
        Images(product.media)
        ProductDetails(product)
        AddToCart(Modifier.fillMaxWidth(), addToCartClickAction)
        ProductDescription(
            shortDescription = product.shortDescription,
            openBottomSheetDialog
        )
    }
}

@Composable
fun TopAppBarContent(addedToCart: Boolean = false) {
    var favorited by remember { mutableStateOf(false) }
    val activity = LocalContext.current
    TopAppBar(title = {},
        backgroundColor = Color.Transparent,
        elevation = 0.dp,
        navigationIcon = {
            IconButton(onClick = { (activity as? Activity)?.finish() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Go back",
                    tint = MaterialTheme.colors.onBackground
                )
            }
        },
        actions = {
            AnimatedVisibility(
                visible = addedToCart,
                enter = fadeIn(),
            ) {
                IconButton(onClick = { /* goToCart() */ }) {
                    Icon(
                        imageVector = Icons.Filled.ShoppingCart,
                        contentDescription = "Shopping cart",
                        tint = MaterialTheme.colors.onBackground,
                        modifier = Modifier.semantics {
                            contentDescription = "Shopping cart"
                            testTag = "123"

                        }
                    )
                }
            }
            IconButton(onClick = { favorited = !favorited }) {
                Icon(
                    imageVector = if (favorited) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = "Add to favorites",
                    tint = MaterialTheme.colors.onBackground,
                    modifier = Modifier.semantics {
                        contentDescription = "Favorite"
                        stateDescription = favorited.toString()
                    }

                )
            }
            IconButton(onClick = { /* shareItem() */ }) {
                Icon(
                    imageVector = Icons.Filled.Share,
                    contentDescription = "Share",
                    tint = MaterialTheme.colors.onBackground,
                    modifier = Modifier.semantics { contentDescription = "Share" }
                )
            }
        })
}

@Preview
@Composable
fun ProductScreenPreview() {
    val product = Gson().fromJson(
        "{\"products\":[{\"id\":\"9200000028828094\",\"ean\":\"0885370863987\",\"gpc\":\"EGAME\",\"title\":\"Halo - The Master Chief Collection - Xbox One\",\"specsTag\":\"Microsoft\",\"rating\":48,\"shortDescription\":\"Inhoud HALO - The Master Chief Collection Halo: Combat Evolved Anniversary Geremasterde Halo 2: Anniversary Halo 3 Halo 4 Nieuwe digitale series: Halo: Nightfall Toegang tot Halo 5: Guardians Beta. De ultieme Halo ervaring! Geoptimiseerd voor Xbox Series X beschikt over Smart Delivery en speelt af in 4K Ultra HD met 60 frames per seconde. Speelt op Xbox Series S, Xbox Series X en Xbox One. Het complete Master Chief-verhaal Het complete verhaal over deze iconische held en zijn epische reis wordt nu uitgebracht als The Master Chief Collection. Halo: Combat Evolved Anniversary, Halo 2: Anniversary, Halo 3, en Halo 4 zijn uitgevoerd met de 60fps beeldkwaliteit van de Xbox One, en bevatten in totaal 45 campagnemissies en meer dan 100 multiplayer (inclusief de originele Halo Combat Evolved-kaarten) en Spartan Ops-kaarten. Samen met de nieuwe proloog- en epiloogscènes als voorbode op Halo 5: Guardians, is dit de collectie voor de Xbox One waarop Halo-fans hebben gewacht. Halo 2: Anniversary De iconische held is na 10 jaar terug. Met een volledig geremasterde campagne met ‘Classic Mode’ waarmee je onmiddellijk kunt switchentussen de geremasterde versie en de originele game uit 2004. Ontdek de nieuwe Halo 5: Guardians-verhaalelementen in verborgen terminal-video’s en gebruik alle nieuwe skulls om de campagne op een hele nieuwe manier te ervaren. Ook alle 23 originele multiplayer-kaarten zitten erbij, naast 6 volledig opnieuw uitgevoerde kaarten. Speel het spel dat een hele nieuwe dimensie gaf aan multiplayer-games opconsoles, en bereid je voor op het volgende Halo-hoofdstuk. Master Menu Navigeer moeiteloos door het verhaal van The Master Chief met het volledig nieuwe Master Menu. Speel alle vier de volledig gedeblokkeerde campagnes van het begin tot het eind of duik middenin de actie. De game-ontwerpers voegden hun eigen playlists van campagnes toe voor nieuwe, spannende uitdagingen. Vind en speel moeiteloos je favoriete multiplayer-games uit meer dan 100 multiplayer en Spartan Ops-kaarten. Dat is alles wat er in Halo, op jouw voorwaarden, vereenvoudigd werd. New Halo: Nightfall Live Action Digital Series Een vreemde, vijandige wereld waarin UNSC-topagenten aan een nog groter gevaar worden blootgesteld in deze live actieserie van 343 Industries en Scott Free Productions. Met Ridley Scott en de directeur van Scott Free TV, David Zucker, als uitvoerend producenten en geregisseerd door Sergio Mimica-Gezzan (“Battlestar Galactica”, “Pillars of the Earth”, “Heroes”), is Halo: Nightfall een spannend nieuw verhaal voor oude én nieuwe Halo-fans. Halo 5: Guardians Multiplayer Beta Wees één van de eersten die de nieuwe generatie Halo-multiplayer speelt in Halo 5: Guardians Beta. Bereid je voor op de Guardians beta door de exclusieve content in The Master Chief Collection en Halo: Nightfall vrij te spelen. Deze exclusieve items neem je mee naar Halo 5: Guardians.\",\"longDescription\":\"<strong>Inhoud HALO - The Master Chief Collection</strong><br />\\n<ul>\\n<li>Halo: Combat Evolved Anniversary</li>\\n<li>Geremasterde Halo 2: Anniversary</li>\\n<li>Halo 3 </li>\\n<li>Halo 4</li>\\n<li>Nieuwe digitale series: Halo: Nightfall</li>\\n<li>Toegang tot Halo 5: Guardians Beta. De ultieme Halo ervaring!</li>\\n</ul>\\n<strong>Geoptimiseerd voor Xbox Series X</strong><br />beschikt over Smart Delivery en speelt af in 4K Ultra HD met 60 frames per seconde. Speelt op Xbox Series S, Xbox Series X en Xbox One.<br /><br /><strong>Het complete Master Chief-verhaal</strong><br />Het complete verhaal over deze iconische held en zijn epische reis wordt nu uitgebracht als The Master Chief Collection. Halo: Combat Evolved Anniversary, Halo 2: Anniversary, Halo 3, en Halo 4 zijn uitgevoerd met de 60fps beeldkwaliteit van de Xbox One, en bevatten in totaal 45 campagnemissies en meer dan 100 multiplayer (inclusief de originele Halo Combat Evolved-kaarten) en Spartan Ops-kaarten. Samen met de nieuwe proloog- en epiloogscènes als voorbode op Halo 5: Guardians, is dit de collectie voor de Xbox One waarop Halo-fans hebben gewacht.<br /><br /><strong>Halo 2: Anniversary</strong><br />De iconische held is na 10 jaar terug. Met een volledig geremasterde campagne met ‘Classic Mode’ waarmee je onmiddellijk kunt switchentussen de geremasterde versie en de originele game uit 2004. Ontdek de nieuwe Halo 5: Guardians-verhaalelementen in verborgen terminal-video’s en gebruik alle nieuwe skulls om de campagne op een hele nieuwe manier te ervaren. Ook alle 23 originele multiplayer-kaarten zitten erbij, naast 6 volledig opnieuw uitgevoerde kaarten. Speel het spel dat een hele nieuwe dimensie gaf aan multiplayer-games opconsoles, en bereid je voor op het volgende Halo-hoofdstuk.<br /><br /><strong>Master Menu</strong><br />Navigeer moeiteloos door het verhaal van The Master Chief met het volledig nieuwe Master Menu. Speel alle vier de volledig gedeblokkeerde campagnes van het begin tot het eind of duik middenin de actie. De game-ontwerpers voegden hun eigen playlists van campagnes toe voor nieuwe, spannende uitdagingen. Vind en speel moeiteloos je favoriete multiplayer-games uit meer dan 100 multiplayer en Spartan Ops-kaarten. Dat is alles wat er in Halo, op jouw voorwaarden, vereenvoudigd werd.<br /><br /><strong>New Halo: Nightfall Live Action Digital Series</strong><br />Een vreemde, vijandige wereld waarin UNSC-topagenten aan een nog groter gevaar worden blootgesteld in deze live actieserie van 343 Industries en Scott Free Productions. Met Ridley Scott en de directeur van Scott Free TV, David Zucker, als uitvoerend producenten en geregisseerd door Sergio Mimica-Gezzan (“Battlestar Galactica”, “Pillars of the Earth”, “Heroes”), is Halo: Nightfall een spannend nieuw verhaal voor oude én nieuwe Halo-fans.<br /><br /><strong>Halo 5: Guardians Multiplayer Beta</strong><br />Wees één van de eersten die de nieuwe generatie Halo-multiplayer speelt in Halo 5: Guardians Beta. Bereid je voor op de Guardians beta door de exclusieve content in The Master Chief Collection en Halo: Nightfall vrij te spelen. Deze exclusieve items neem je mee naar Halo 5: Guardians.\",\"attributeGroups\":[{\"title\":\"Productinformatie\",\"attributes\":[{\"key\":\"BRAND\",\"label\":\"Merk\",\"value\":\"Microsoft\"},{\"key\":\"PLATFORM\",\"label\":\"Platform\",\"value\":\"Xbox One\"},{\"label\":\"Genre\",\"value\":\"Actie\"},{\"label\":\"Speleditie\",\"value\":\"Standard edition\"},{\"key\":\"CHARACTER\",\"label\":\"Personage\",\"value\":\"Halo\"},{\"label\":\"Format\",\"value\":\"Blu-ray\"}]},{\"title\":\"Doelgroep\",\"attributes\":[{\"label\":\"Doelgroep\",\"value\":\"Fanatieke gamer\"},{\"label\":\"Minimale leeftijd\",\"value\":\"18\"},{\"label\":\"Inhoud leeftijdsindicatie\",\"value\":\"Geweld | Grof taalgebruik\"}]},{\"title\":\"Spelopties\",\"attributes\":[{\"label\":\"Online abonnement vereist\",\"value\":\"Xbox Live Gold\"},{\"label\":\"Online optie\",\"value\":\"Ja\"},{\"label\":\"Online multiplayer\",\"value\":\"Ja\"},{\"label\":\"Splitscreen\",\"value\":\"Ja\"},{\"key\":\"NUMBER_OF_PLAYERS\",\"label\":\"Aantal spelers\",\"value\":\"2\"},{\"label\":\"Aantal spelers offline\",\"value\":\"1\"}]},{\"title\":\"Technische gegevens\",\"attributes\":[{\"key\":\"REGION\",\"label\":\"Regio\",\"value\":\"Region Free\"},{\"key\":\"SYSTEM_REQUIREMENTS\",\"label\":\"Systeemeisen\",\"value\":\"Microsoft Xbox One console + controller(s). Werkt niet op een Xbox of Xbox 360!\"}]},{\"title\":\"Levering\",\"attributes\":[{\"label\":\"Retour- en annuleerbeleid\",\"value\":\"Klik hiervoor op Retourneren onderaan deze pagina\"},{\"key\":\"SGE_PACKAGING\",\"label\":\"Verpakking\",\"value\":\"Amaray\"}]},{\"title\":\"Je vindt dit artikel in\",\"attributes\":[{\"key\":\"CHARACTER\",\"label\":\"Personage\",\"value\":\"Halo\"}]},{\"title\":\"Overige kenmerken\",\"attributes\":[{\"key\":\"4K Game Support\",\"label\":\"4K-gaming ondersteuning\",\"value\":\"Nee\"},{\"key\":\"Warranty Validity Time\",\"label\":\"Fabrieksgarantie termijn\",\"value\":\"3 maanden\"},{\"key\":\"Weight\",\"label\":\"Gewicht\",\"value\":\"85 g\"},{\"key\":\"Award\",\"label\":\"Gewonnen prijzen\",\"value\":\"Geen\"},{\"key\":\"Subtitles\",\"label\":\"Ondertitels\",\"value\":\"Geen ondertiteling\"},{\"key\":\"Original Release Date\",\"label\":\"Oorspronkelijke releasedatum\",\"value\":\"2014-12-31\"},{\"key\":\"Warranty Type\",\"label\":\"Reparatie type\",\"value\":\"Carry-in\"},{\"key\":\"Game Type\",\"label\":\"Speltype\",\"value\":\"First Person\"},{\"key\":\"Languages Container\",\"label\":\"Talen container\",\"value\":\"nl\"},{\"key\":\"Languages Product\",\"label\":\"Talen product\",\"value\":\"en\"},{\"key\":\"Warranty Exceptions\",\"label\":\"Uitzonderingen fabrieksgarantie\",\"value\":\"Niet\"},{\"key\":\"Width\",\"label\":\"Verpakking breedte\",\"value\":\"134 mm\"},{\"key\":\"Height\",\"label\":\"Verpakking hoogte\",\"value\":\"15 mm\"},{\"key\":\"Length\",\"label\":\"Verpakking lengte\",\"value\":\"171 mm\"},{\"key\":\"Whats In The Box\",\"label\":\"Verpakkingsinhoud\",\"value\":\"Halo The Master Chief Collection\"},{\"key\":\"VR Optional\",\"label\":\"Virtual Reality mogelijkheden\",\"value\":\"Niet geschikt voor VR\"}]}],\"entityGroups\":[{\"title\":\"Acteurs\"},{\"title\":\"Artiesten\"},{\"title\":\"Auteurs\"},{\"title\":\"Regisseur\"},{\"title\":\"Regisseur\"},{\"title\":\"Uitgever\",\"entities\":[{\"id\":\"1551048\",\"value\":\"Microsoft\"}]}],\"urls\":[{\"key\":\"DESKTOP\",\"value\":\"https://www.bol.com/nl/nl/p/halo-the-master-chief-collection-xbox-one/9200000028828094\"},{\"key\":\"MOBILE\",\"value\":\"https://www.bol.com/nl/nl/p/halo-the-master-chief-collection-xbox-one/9200000028828094\"}],\"images\":[{\"type\":\"IMAGE\",\"key\":\"XS\",\"url\":\"https://media.s-bol.com/K83QQkwjyy1n/47x60.jpg\"},{\"type\":\"IMAGE\",\"key\":\"S\",\"url\":\"https://media.s-bol.com/K83QQkwjyy1n/97x123.jpg\"},{\"type\":\"IMAGE\",\"key\":\"M\",\"url\":\"https://media.s-bol.com/K83QQkwjyy1n/122x155.jpg\"},{\"type\":\"IMAGE\",\"key\":\"L\",\"url\":\"https://media.s-bol.com/K83QQkwjyy1n/166x210.jpg\"},{\"type\":\"IMAGE\",\"key\":\"XL\",\"url\":\"https://media.s-bol.com/K83QQkwjyy1n/550x695.jpg\"},{\"type\":\"IMAGE\",\"key\":\"XXL\",\"url\":\"https://media.s-bol.com/K83QQkwjyy1n/949x1200.jpg\"}],\"media\":[{\"type\":\"IMAGE\",\"key\":\"XL\",\"url\":\"https://media.s-bol.com/BxGk3GYvVjY/550x237.jpg\"},{\"type\":\"IMAGE\",\"key\":\"XL\",\"url\":\"https://media.s-bol.com/K83QQkwjyy1n/550x695.jpg\"},{\"type\":\"IMAGE\",\"key\":\"XL\",\"url\":\"https://media.s-bol.com/N9Xl9L5kZl68/550x385.jpg\"},{\"type\":\"IMAGE\",\"key\":\"XL\",\"url\":\"https://media.s-bol.com/OyLmyM5Y1m0E/550x309.jpg\"},{\"type\":\"IMAGE\",\"key\":\"XL\",\"url\":\"https://media.s-bol.com/RRy5jPq83DL/550x309.jpg\"},{\"type\":\"IMAGE\",\"key\":\"XL\",\"url\":\"https://media.s-bol.com/grWlm88JLG3/550x309.jpg\"},{\"type\":\"IMAGE\",\"key\":\"XL\",\"url\":\"https://media.s-bol.com/myD813VJPWyR/550x706.jpg\"},{\"type\":\"IMAGE\",\"key\":\"XL\",\"url\":\"https://media.s-bol.com/qGWyzGvE6lG/550x309.jpg\"},{\"type\":\"IMAGE\",\"key\":\"XL\",\"url\":\"https://media.s-bol.com/xPWJJD4Lp4J/550x309.jpg\"},{\"type\":\"IMAGE\",\"key\":\"XL\",\"url\":\"https://media.s-bol.com/xkqq9kQqrq7E/550x209.jpg\"}],\"offerData\":{\"bolCom\":0,\"nonProfessionalSellers\":1,\"professionalSellers\":0,\"offers\":[{\"id\":\"1001035374559819\",\"condition\":\"Als nieuw\",\"price\":46.0,\"availabilityCode\":\"-1\",\"availabilityDescription\":\"Op voorraad. Voor 16:00 uur besteld, morgen in huis\",\"comment\":\"Op voorraad. Voor 16:00 uur besteld, morgen in huis\",\"seller\":{\"id\":\"1435442\",\"sellerType\":\"Kleinzakelijke verkoper\",\"displayName\":\"PSGameShopper.nl\",\"topSeller\":false,\"sellerRating\":{\"ratingMethod\":\"THREE_MONTHS\",\"sellerRating\":\"8.2\",\"productInformationRating\":\"9.3\",\"deliveryTimeRating\":\"8.4\",\"shippingRating\":\"8.6\",\"serviceRating\":\"-\"},\"recentReviewCounts\":{\"positiveReviewCount\":7,\"neutralReviewCount\":2,\"negativeReviewCount\":1,\"totalReviewCount\":10},\"useWarrantyRepairConditions\":false,\"approvalPercentage\":\"87.5\",\"registrationDate\":\"2019-03-13T01:00:00.000+01:00\"},\"bestOffer\":true}]},\"parentCategoryPaths\":[{\"parentCategories\":[{\"id\":\"3135\",\"name\":\"Games\"},{\"id\":\"18200\",\"name\":\"Videogames\"}]},{\"parentCategories\":[{\"id\":\"3135\",\"name\":\"Games\"}]},{\"parentCategories\":[{\"id\":\"3135\",\"name\":\"Games\"},{\"id\":\"18200\",\"name\":\"Videogames\"},{\"id\":\"38905\",\"name\":\"Xbox One-games\"}]}]}]}",
        Products::class.java
    )
    BolComVleemingTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            ProductScreen(product.products[0])
        }
    }
}

@Preview
@Composable
fun ProductScreenPreviewDarkMode() {
    val product = Gson().fromJson(
        "{\"products\":[{\"id\":\"9200000028828094\",\"ean\":\"0885370863987\",\"gpc\":\"EGAME\",\"title\":\"Halo - The Master Chief Collection - Xbox One\",\"specsTag\":\"Microsoft\",\"rating\":48,\"shortDescription\":\"Inhoud HALO - The Master Chief Collection Halo: Combat Evolved Anniversary Geremasterde Halo 2: Anniversary Halo 3 Halo 4 Nieuwe digitale series: Halo: Nightfall Toegang tot Halo 5: Guardians Beta. De ultieme Halo ervaring! Geoptimiseerd voor Xbox Series X beschikt over Smart Delivery en speelt af in 4K Ultra HD met 60 frames per seconde. Speelt op Xbox Series S, Xbox Series X en Xbox One. Het complete Master Chief-verhaal Het complete verhaal over deze iconische held en zijn epische reis wordt nu uitgebracht als The Master Chief Collection. Halo: Combat Evolved Anniversary, Halo 2: Anniversary, Halo 3, en Halo 4 zijn uitgevoerd met de 60fps beeldkwaliteit van de Xbox One, en bevatten in totaal 45 campagnemissies en meer dan 100 multiplayer (inclusief de originele Halo Combat Evolved-kaarten) en Spartan Ops-kaarten. Samen met de nieuwe proloog- en epiloogscènes als voorbode op Halo 5: Guardians, is dit de collectie voor de Xbox One waarop Halo-fans hebben gewacht. Halo 2: Anniversary De iconische held is na 10 jaar terug. Met een volledig geremasterde campagne met ‘Classic Mode’ waarmee je onmiddellijk kunt switchentussen de geremasterde versie en de originele game uit 2004. Ontdek de nieuwe Halo 5: Guardians-verhaalelementen in verborgen terminal-video’s en gebruik alle nieuwe skulls om de campagne op een hele nieuwe manier te ervaren. Ook alle 23 originele multiplayer-kaarten zitten erbij, naast 6 volledig opnieuw uitgevoerde kaarten. Speel het spel dat een hele nieuwe dimensie gaf aan multiplayer-games opconsoles, en bereid je voor op het volgende Halo-hoofdstuk. Master Menu Navigeer moeiteloos door het verhaal van The Master Chief met het volledig nieuwe Master Menu. Speel alle vier de volledig gedeblokkeerde campagnes van het begin tot het eind of duik middenin de actie. De game-ontwerpers voegden hun eigen playlists van campagnes toe voor nieuwe, spannende uitdagingen. Vind en speel moeiteloos je favoriete multiplayer-games uit meer dan 100 multiplayer en Spartan Ops-kaarten. Dat is alles wat er in Halo, op jouw voorwaarden, vereenvoudigd werd. New Halo: Nightfall Live Action Digital Series Een vreemde, vijandige wereld waarin UNSC-topagenten aan een nog groter gevaar worden blootgesteld in deze live actieserie van 343 Industries en Scott Free Productions. Met Ridley Scott en de directeur van Scott Free TV, David Zucker, als uitvoerend producenten en geregisseerd door Sergio Mimica-Gezzan (“Battlestar Galactica”, “Pillars of the Earth”, “Heroes”), is Halo: Nightfall een spannend nieuw verhaal voor oude én nieuwe Halo-fans. Halo 5: Guardians Multiplayer Beta Wees één van de eersten die de nieuwe generatie Halo-multiplayer speelt in Halo 5: Guardians Beta. Bereid je voor op de Guardians beta door de exclusieve content in The Master Chief Collection en Halo: Nightfall vrij te spelen. Deze exclusieve items neem je mee naar Halo 5: Guardians.\",\"longDescription\":\"<strong>Inhoud HALO - The Master Chief Collection</strong><br />\\n<ul>\\n<li>Halo: Combat Evolved Anniversary</li>\\n<li>Geremasterde Halo 2: Anniversary</li>\\n<li>Halo 3 </li>\\n<li>Halo 4</li>\\n<li>Nieuwe digitale series: Halo: Nightfall</li>\\n<li>Toegang tot Halo 5: Guardians Beta. De ultieme Halo ervaring!</li>\\n</ul>\\n<strong>Geoptimiseerd voor Xbox Series X</strong><br />beschikt over Smart Delivery en speelt af in 4K Ultra HD met 60 frames per seconde. Speelt op Xbox Series S, Xbox Series X en Xbox One.<br /><br /><strong>Het complete Master Chief-verhaal</strong><br />Het complete verhaal over deze iconische held en zijn epische reis wordt nu uitgebracht als The Master Chief Collection. Halo: Combat Evolved Anniversary, Halo 2: Anniversary, Halo 3, en Halo 4 zijn uitgevoerd met de 60fps beeldkwaliteit van de Xbox One, en bevatten in totaal 45 campagnemissies en meer dan 100 multiplayer (inclusief de originele Halo Combat Evolved-kaarten) en Spartan Ops-kaarten. Samen met de nieuwe proloog- en epiloogscènes als voorbode op Halo 5: Guardians, is dit de collectie voor de Xbox One waarop Halo-fans hebben gewacht.<br /><br /><strong>Halo 2: Anniversary</strong><br />De iconische held is na 10 jaar terug. Met een volledig geremasterde campagne met ‘Classic Mode’ waarmee je onmiddellijk kunt switchentussen de geremasterde versie en de originele game uit 2004. Ontdek de nieuwe Halo 5: Guardians-verhaalelementen in verborgen terminal-video’s en gebruik alle nieuwe skulls om de campagne op een hele nieuwe manier te ervaren. Ook alle 23 originele multiplayer-kaarten zitten erbij, naast 6 volledig opnieuw uitgevoerde kaarten. Speel het spel dat een hele nieuwe dimensie gaf aan multiplayer-games opconsoles, en bereid je voor op het volgende Halo-hoofdstuk.<br /><br /><strong>Master Menu</strong><br />Navigeer moeiteloos door het verhaal van The Master Chief met het volledig nieuwe Master Menu. Speel alle vier de volledig gedeblokkeerde campagnes van het begin tot het eind of duik middenin de actie. De game-ontwerpers voegden hun eigen playlists van campagnes toe voor nieuwe, spannende uitdagingen. Vind en speel moeiteloos je favoriete multiplayer-games uit meer dan 100 multiplayer en Spartan Ops-kaarten. Dat is alles wat er in Halo, op jouw voorwaarden, vereenvoudigd werd.<br /><br /><strong>New Halo: Nightfall Live Action Digital Series</strong><br />Een vreemde, vijandige wereld waarin UNSC-topagenten aan een nog groter gevaar worden blootgesteld in deze live actieserie van 343 Industries en Scott Free Productions. Met Ridley Scott en de directeur van Scott Free TV, David Zucker, als uitvoerend producenten en geregisseerd door Sergio Mimica-Gezzan (“Battlestar Galactica”, “Pillars of the Earth”, “Heroes”), is Halo: Nightfall een spannend nieuw verhaal voor oude én nieuwe Halo-fans.<br /><br /><strong>Halo 5: Guardians Multiplayer Beta</strong><br />Wees één van de eersten die de nieuwe generatie Halo-multiplayer speelt in Halo 5: Guardians Beta. Bereid je voor op de Guardians beta door de exclusieve content in The Master Chief Collection en Halo: Nightfall vrij te spelen. Deze exclusieve items neem je mee naar Halo 5: Guardians.\",\"attributeGroups\":[{\"title\":\"Productinformatie\",\"attributes\":[{\"key\":\"BRAND\",\"label\":\"Merk\",\"value\":\"Microsoft\"},{\"key\":\"PLATFORM\",\"label\":\"Platform\",\"value\":\"Xbox One\"},{\"label\":\"Genre\",\"value\":\"Actie\"},{\"label\":\"Speleditie\",\"value\":\"Standard edition\"},{\"key\":\"CHARACTER\",\"label\":\"Personage\",\"value\":\"Halo\"},{\"label\":\"Format\",\"value\":\"Blu-ray\"}]},{\"title\":\"Doelgroep\",\"attributes\":[{\"label\":\"Doelgroep\",\"value\":\"Fanatieke gamer\"},{\"label\":\"Minimale leeftijd\",\"value\":\"18\"},{\"label\":\"Inhoud leeftijdsindicatie\",\"value\":\"Geweld | Grof taalgebruik\"}]},{\"title\":\"Spelopties\",\"attributes\":[{\"label\":\"Online abonnement vereist\",\"value\":\"Xbox Live Gold\"},{\"label\":\"Online optie\",\"value\":\"Ja\"},{\"label\":\"Online multiplayer\",\"value\":\"Ja\"},{\"label\":\"Splitscreen\",\"value\":\"Ja\"},{\"key\":\"NUMBER_OF_PLAYERS\",\"label\":\"Aantal spelers\",\"value\":\"2\"},{\"label\":\"Aantal spelers offline\",\"value\":\"1\"}]},{\"title\":\"Technische gegevens\",\"attributes\":[{\"key\":\"REGION\",\"label\":\"Regio\",\"value\":\"Region Free\"},{\"key\":\"SYSTEM_REQUIREMENTS\",\"label\":\"Systeemeisen\",\"value\":\"Microsoft Xbox One console + controller(s). Werkt niet op een Xbox of Xbox 360!\"}]},{\"title\":\"Levering\",\"attributes\":[{\"label\":\"Retour- en annuleerbeleid\",\"value\":\"Klik hiervoor op Retourneren onderaan deze pagina\"},{\"key\":\"SGE_PACKAGING\",\"label\":\"Verpakking\",\"value\":\"Amaray\"}]},{\"title\":\"Je vindt dit artikel in\",\"attributes\":[{\"key\":\"CHARACTER\",\"label\":\"Personage\",\"value\":\"Halo\"}]},{\"title\":\"Overige kenmerken\",\"attributes\":[{\"key\":\"4K Game Support\",\"label\":\"4K-gaming ondersteuning\",\"value\":\"Nee\"},{\"key\":\"Warranty Validity Time\",\"label\":\"Fabrieksgarantie termijn\",\"value\":\"3 maanden\"},{\"key\":\"Weight\",\"label\":\"Gewicht\",\"value\":\"85 g\"},{\"key\":\"Award\",\"label\":\"Gewonnen prijzen\",\"value\":\"Geen\"},{\"key\":\"Subtitles\",\"label\":\"Ondertitels\",\"value\":\"Geen ondertiteling\"},{\"key\":\"Original Release Date\",\"label\":\"Oorspronkelijke releasedatum\",\"value\":\"2014-12-31\"},{\"key\":\"Warranty Type\",\"label\":\"Reparatie type\",\"value\":\"Carry-in\"},{\"key\":\"Game Type\",\"label\":\"Speltype\",\"value\":\"First Person\"},{\"key\":\"Languages Container\",\"label\":\"Talen container\",\"value\":\"nl\"},{\"key\":\"Languages Product\",\"label\":\"Talen product\",\"value\":\"en\"},{\"key\":\"Warranty Exceptions\",\"label\":\"Uitzonderingen fabrieksgarantie\",\"value\":\"Niet\"},{\"key\":\"Width\",\"label\":\"Verpakking breedte\",\"value\":\"134 mm\"},{\"key\":\"Height\",\"label\":\"Verpakking hoogte\",\"value\":\"15 mm\"},{\"key\":\"Length\",\"label\":\"Verpakking lengte\",\"value\":\"171 mm\"},{\"key\":\"Whats In The Box\",\"label\":\"Verpakkingsinhoud\",\"value\":\"Halo The Master Chief Collection\"},{\"key\":\"VR Optional\",\"label\":\"Virtual Reality mogelijkheden\",\"value\":\"Niet geschikt voor VR\"}]}],\"entityGroups\":[{\"title\":\"Acteurs\"},{\"title\":\"Artiesten\"},{\"title\":\"Auteurs\"},{\"title\":\"Regisseur\"},{\"title\":\"Regisseur\"},{\"title\":\"Uitgever\",\"entities\":[{\"id\":\"1551048\",\"value\":\"Microsoft\"}]}],\"urls\":[{\"key\":\"DESKTOP\",\"value\":\"https://www.bol.com/nl/nl/p/halo-the-master-chief-collection-xbox-one/9200000028828094\"},{\"key\":\"MOBILE\",\"value\":\"https://www.bol.com/nl/nl/p/halo-the-master-chief-collection-xbox-one/9200000028828094\"}],\"images\":[{\"type\":\"IMAGE\",\"key\":\"XS\",\"url\":\"https://media.s-bol.com/K83QQkwjyy1n/47x60.jpg\"},{\"type\":\"IMAGE\",\"key\":\"S\",\"url\":\"https://media.s-bol.com/K83QQkwjyy1n/97x123.jpg\"},{\"type\":\"IMAGE\",\"key\":\"M\",\"url\":\"https://media.s-bol.com/K83QQkwjyy1n/122x155.jpg\"},{\"type\":\"IMAGE\",\"key\":\"L\",\"url\":\"https://media.s-bol.com/K83QQkwjyy1n/166x210.jpg\"},{\"type\":\"IMAGE\",\"key\":\"XL\",\"url\":\"https://media.s-bol.com/K83QQkwjyy1n/550x695.jpg\"},{\"type\":\"IMAGE\",\"key\":\"XXL\",\"url\":\"https://media.s-bol.com/K83QQkwjyy1n/949x1200.jpg\"}],\"media\":[{\"type\":\"IMAGE\",\"key\":\"XL\",\"url\":\"https://media.s-bol.com/BxGk3GYvVjY/550x237.jpg\"},{\"type\":\"IMAGE\",\"key\":\"XL\",\"url\":\"https://media.s-bol.com/K83QQkwjyy1n/550x695.jpg\"},{\"type\":\"IMAGE\",\"key\":\"XL\",\"url\":\"https://media.s-bol.com/N9Xl9L5kZl68/550x385.jpg\"},{\"type\":\"IMAGE\",\"key\":\"XL\",\"url\":\"https://media.s-bol.com/OyLmyM5Y1m0E/550x309.jpg\"},{\"type\":\"IMAGE\",\"key\":\"XL\",\"url\":\"https://media.s-bol.com/RRy5jPq83DL/550x309.jpg\"},{\"type\":\"IMAGE\",\"key\":\"XL\",\"url\":\"https://media.s-bol.com/grWlm88JLG3/550x309.jpg\"},{\"type\":\"IMAGE\",\"key\":\"XL\",\"url\":\"https://media.s-bol.com/myD813VJPWyR/550x706.jpg\"},{\"type\":\"IMAGE\",\"key\":\"XL\",\"url\":\"https://media.s-bol.com/qGWyzGvE6lG/550x309.jpg\"},{\"type\":\"IMAGE\",\"key\":\"XL\",\"url\":\"https://media.s-bol.com/xPWJJD4Lp4J/550x309.jpg\"},{\"type\":\"IMAGE\",\"key\":\"XL\",\"url\":\"https://media.s-bol.com/xkqq9kQqrq7E/550x209.jpg\"}],\"offerData\":{\"bolCom\":0,\"nonProfessionalSellers\":1,\"professionalSellers\":0,\"offers\":[{\"id\":\"1001035374559819\",\"condition\":\"Als nieuw\",\"price\":46.0,\"availabilityCode\":\"-1\",\"availabilityDescription\":\"Op voorraad. Voor 16:00 uur besteld, morgen in huis\",\"comment\":\"Op voorraad. Voor 16:00 uur besteld, morgen in huis\",\"seller\":{\"id\":\"1435442\",\"sellerType\":\"Kleinzakelijke verkoper\",\"displayName\":\"PSGameShopper.nl\",\"topSeller\":false,\"sellerRating\":{\"ratingMethod\":\"THREE_MONTHS\",\"sellerRating\":\"8.2\",\"productInformationRating\":\"9.3\",\"deliveryTimeRating\":\"8.4\",\"shippingRating\":\"8.6\",\"serviceRating\":\"-\"},\"recentReviewCounts\":{\"positiveReviewCount\":7,\"neutralReviewCount\":2,\"negativeReviewCount\":1,\"totalReviewCount\":10},\"useWarrantyRepairConditions\":false,\"approvalPercentage\":\"87.5\",\"registrationDate\":\"2019-03-13T01:00:00.000+01:00\"},\"bestOffer\":true}]},\"parentCategoryPaths\":[{\"parentCategories\":[{\"id\":\"3135\",\"name\":\"Games\"},{\"id\":\"18200\",\"name\":\"Videogames\"}]},{\"parentCategories\":[{\"id\":\"3135\",\"name\":\"Games\"}]},{\"parentCategories\":[{\"id\":\"3135\",\"name\":\"Games\"},{\"id\":\"18200\",\"name\":\"Videogames\"},{\"id\":\"38905\",\"name\":\"Xbox One-games\"}]}]}]}",
        Products::class.java
    )
    BolComVleemingTheme(true) {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            ProductScreen(product.products[0])

        }
    }
}