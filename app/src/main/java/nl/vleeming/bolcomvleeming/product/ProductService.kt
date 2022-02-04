package nl.vleeming.bolcomvleeming.product

import nl.vleeming.bolcomvleeming.BuildConfig
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductService {

    @GET("products/{productId}/?apikey=${BuildConfig.API_TOKEN}")
    fun getProduct(@Path("productId") productId: Long) : Call<Products>
}