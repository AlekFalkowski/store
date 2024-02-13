package dev.falkow.blanco.nodes.home.options

import dev.falkow.blanco.shared.types.CatalogListCard
import dev.falkow.blanco.nodes.home.types.StableContent
import dev.falkow.blanco.nodes.home.resources.HomeContentLocalResource
import javax.inject.Inject

internal class GetStableContentOption @Inject constructor(
    private val localResource: HomeContentLocalResource,
) {

    suspend operator fun invoke(): StableContent {
        return StableContent(
            kitchenSinksCardList = localResource.kitchenSinksCardList.map {
                CatalogListCard(
                    catalogNavId = it.catalogNavId,
                    title = it.title,
                    description = it.description,
                    imageResId = it.imageResId,
                )
            }
        )
    }
}