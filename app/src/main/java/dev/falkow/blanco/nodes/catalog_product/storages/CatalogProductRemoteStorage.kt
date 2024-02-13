package dev.falkow.blanco.nodes.catalog_product.storages

import retrofit2.http.GET
import retrofit2.http.Path

interface CatalogProductRemoteStorage {

    @GET("catalogs/{catalogNavId}/products/{catalogProductNavId}")
    suspend fun getCatalogProductContent(
        @Path("catalogNavId") catalogNavId: String,
        @Path("catalogProductNavId") catalogProductNavId: String,
    ): String
}