package dev.falkow.blanco.nodes.catalog.storages

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryName

interface ICatalogRemoteStorage {

    // @Headers(
    //     "Accept: application/vnd.github.v3.full+json",
    //     "User-Agent: Retrofit-Sample-App"
    // )
    // @GET("$PROJECT_VERSION/$STATIC_CONTENT/{catalogNavId}{queryString}")
    // suspend fun getCatalogStableContent(
    //     @Path("catalogNavId") catalogNavId: String,
    //     @Path("queryString") queryString: String,
    // ): String
    //
    // @GET("$PROJECT_VERSION/$DYNAMIC_CONTENT/{catalogNavId}{queryString}")
    // suspend fun getCatalogDynamicContent(
    //     @Path("catalogNavId") catalogNavId: String,
    // ): String

    @GET("{catalogNavId}")
    suspend fun getCatalogCardList(
        @Path("catalogNavId") catalogNavId: String,
        @QueryName(encoded = true) queryString: String? = ""
    ): String
}