package nl.vleeming.bolcomvleeming

import android.widget.TextView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetState
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProductDescription(shortDescription: String, onClickAction: () -> Unit = {}) {
    TextButton(
        onClick = onClickAction, Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .semantics { testTag = "Product Description View" },
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = stringResource(id = R.string.product_description),
                color = MaterialTheme.colors.onBackground
            )
            Text(
                text = shortDescription,
                maxLines = 3,
                color = Color.LightGray
            )
        }
    }


}

@Composable
fun FullProductDescription(html: String) {
    val scroll = rememberScrollState(0)

    AndroidView(
        modifier = Modifier
            .verticalScroll(scroll)
            .padding(horizontal = 8.dp)
            .semantics { testTag = "Bottom Sheet Dialog" },
        factory = { context -> TextView(context) },
        update = { it.text = HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_LEGACY) }
    )
}

@Preview
@Composable
fun ProductDescriptionPreview() {
    ProductDescription(
        "Inhoud HALO - The Master Chief Collection Halo: Combat Evolved Anniversary Geremasterde Halo 2: Anniversary Halo 3 Halo 4 Nieuwe digitale series: Halo: Nightfall Toegang tot Halo 5: Guardians Beta. De ultieme Halo ervaring! Geoptimiseerd voor Xbox Series X beschikt over Smart Delivery en speelt af in 4K Ultra HD met 60 frames per seconde. Speelt op Xbox Series S, Xbox Series X en Xbox One. Het complete Master Chief-verhaal Het complete verhaal over deze iconische held en zijn epische reis wordt nu uitgebracht als The Master Chief Collection. Halo: Combat Evolved Anniversary, Halo 2: Anniversary, Halo 3, en Halo 4 zijn uitgevoerd met de 60fps beeldkwaliteit van de Xbox One, en bevatten in totaal 45 campagnemissies en meer dan 100 multiplayer (inclusief de originele Halo Combat Evolved-kaarten) en Spartan Ops-kaarten. Samen met de nieuwe proloog- en epiloogscènes als voorbode op Halo 5: Guardians, is dit de collectie voor de Xbox One waarop Halo-fans hebben gewacht. Halo 2: Anniversary De iconische held is na 10 jaar terug. Met een volledig geremasterde campagne met ‘Classic Mode’ waarmee je onmiddellijk kunt switchentussen de geremasterde versie en de originele game uit 2004. Ontdek de nieuwe Halo 5: Guardians-verhaalelementen in verborgen terminal-video’s en gebruik alle nieuwe skulls om de campagne op een hele nieuwe manier te ervaren. Ook alle 23 originele multiplayer-kaarten zitten erbij, naast 6 volledig opnieuw uitgevoerde kaarten. Speel het spel dat een hele nieuwe dimensie gaf aan multiplayer-games opconsoles, en bereid je voor op het volgende Halo-hoofdstuk. Master Menu Navigeer moeiteloos door het verhaal van The Master Chief met het volledig nieuwe Master Menu. Speel alle vier de volledig gedeblokkeerde campagnes van het begin tot het eind of duik middenin de actie. De game-ontwerpers voegden hun eigen playlists van campagnes toe voor nieuwe, spannende uitdagingen. Vind en speel moeiteloos je favoriete multiplayer-games uit meer dan 100 multiplayer en Spartan Ops-kaarten. Dat is alles wat er in Halo, op jouw voorwaarden, vereenvoudigd werd. New Halo: Nightfall Live Action Digital Series Een vreemde, vijandige wereld waarin UNSC-topagenten aan een nog groter gevaar worden blootgesteld in deze live actieserie van 343 Industries en Scott Free Productions. Met Ridley Scott en de directeur van Scott Free TV, David Zucker, als uitvoerend producenten en geregisseerd door Sergio Mimica-Gezzan (“Battlestar Galactica”, “Pillars of the Earth”, “Heroes”), is Halo: Nightfall een spannend nieuw verhaal voor oude én nieuwe Halo-fans. Halo 5: Guardians Multiplayer Beta Wees één van de eersten die de nieuwe generatie Halo-multiplayer speelt in Halo 5: Guardians Beta. Bereid je voor op de Guardians beta door de exclusieve content in The Master Chief Collection en Halo: Nightfall vrij te spelen. Deze exclusieve items neem je mee naar Halo 5: Guardians.",
    )
}
