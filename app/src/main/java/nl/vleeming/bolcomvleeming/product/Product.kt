package nl.vleeming.bolcomvleeming.product



data class Product(
    val title: String,
    val media: List<Media>,
    val offerData: OfferData,
    val attributeGroups: List<AttributeGroup>,
    val shortDescription: String,
    val longDescription: String? = null
)
