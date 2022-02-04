package nl.vleeming.bolcomvleeming.product

data class Offers(
    var id: Long,
    var condition: String, //Could be extracted to Enum
    var price: Double,
    var availabilityDescription: String

)
