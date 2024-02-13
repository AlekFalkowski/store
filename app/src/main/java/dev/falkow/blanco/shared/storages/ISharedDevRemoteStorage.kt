package dev.falkow.blanco.shared.storages

import dev.falkow.blanco.shared.config.Features
import dev.falkow.blanco.shared.config.PROJECT_VERSION
import dev.falkow.blanco.shared.config.STABLE_CONTENT
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryName

interface ISharedDevRemoteStorage {

    // @Headers(
    //     "Accept: application/vnd.github.v3.full+json",
    //     "User-Agent: Retrofit-Sample-App"
    // )
    @GET("$PROJECT_VERSION/$STABLE_CONTENT/${Features.CATALOG}/{navString}/")
    suspend fun getNavParams(
        @Path("navString") navString: String,
        @QueryName(encoded = true) queryString: String? = ""
    ): dev.falkow.blanco.shared.display.navigation.NavParams
}