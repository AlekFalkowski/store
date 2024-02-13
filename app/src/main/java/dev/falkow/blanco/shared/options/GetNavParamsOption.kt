package dev.falkow.blanco.shared.options

import dev.falkow.blanco.shared.display.navigation.NavParams
import dev.falkow.blanco.shared.storages.ISharedDevRemoteStorage
import javax.inject.Inject

class GetNavParamsOption @Inject constructor(
    private val sharedDevRemoteStorage: ISharedDevRemoteStorage,
) {

    suspend operator fun invoke(
        navParams: String,
        queryString: String
    ): NavParams {
        return sharedDevRemoteStorage.getNavParams(navParams, queryString)
    }
}