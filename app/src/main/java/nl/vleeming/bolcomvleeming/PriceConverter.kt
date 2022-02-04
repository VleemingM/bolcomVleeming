package nl.vleeming.bolcomvleeming

object PriceConverter {
    fun convertPrice(price: Double): Price? {
        if(price < 0.00){
            return null
        }
        val prices = price.toString().split(".")
        return Price(Integer.parseInt(prices[0]), Integer.parseInt(prices[1]))
    }
}

data class Price(
    val beforeDecimal: Int,
    val afterDecimal: Int
)
