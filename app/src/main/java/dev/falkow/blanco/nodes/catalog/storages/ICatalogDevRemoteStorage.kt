package dev.falkow.blanco.nodes.catalog.storages

import dev.falkow.blanco.nodes.catalog.types.StableContent
import dev.falkow.blanco.shared.config.Features
import dev.falkow.blanco.shared.config.PROJECT_VERSION
import dev.falkow.blanco.shared.config.STABLE_CONTENT
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryName

interface ICatalogDevRemoteStorage {

    // @Headers(
    //     "Accept: application/vnd.github.v3.full+json",
    //     "User-Agent: Retrofit-Sample-App"
    // )
    @GET("$PROJECT_VERSION/$STABLE_CONTENT/${Features.CATALOG}/{catalogNavId}/")
    suspend fun getCatalogStableContent(
        @Path("catalogNavId") catalogNavId: String,
        @QueryName(encoded = true) queryString: String? = ""
    ): StableContent
}