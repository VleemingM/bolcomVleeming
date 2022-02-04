package nl.vleeming.bolcomvleeming.product

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@HiltViewModel
class ProductViewModel @Inject constructor() : ViewModel() {
    val product = MutableLiveData<Product>()
    val error = MutableLiveData(false)

    init {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.bol.com/catalog/v4/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        val productService = retrofit.create(ProductService::class.java)

        val product1 = productService.getProduct(9200000028828094)
        product1.enqueue(object : Callback<Products> {
            override fun onResponse(call: Call<Products>, response: Response<Products>) {
                product.value = response.body()?.products?.get(0)
            }

            override fun onFailure(call: Call<Products>, t: Throwable) {

            }
        })
        Log.d("MICHIEL", "onCreate: $product1")
    }
}