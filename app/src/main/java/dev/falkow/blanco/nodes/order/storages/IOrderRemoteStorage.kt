package dev.falkow.blanco.nodes.order.storages

import dev.falkow.blanco.nodes.order.types.StableContent
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.QueryName

interface IOrderRemoteStorage {

    @Headers(
        // "Accept: application/json",
        "Content-Type: application/json",
        // "User-Agent: Retrofit-Sample-App"
    )
    @GET("purchase-orders/{orderNavId}")
    suspend fun getOrderStableContent(
        @Path("orderNavId") orderNavId: String,
        @QueryName(encoded = true) queryString: String?
    ): StableContent
}