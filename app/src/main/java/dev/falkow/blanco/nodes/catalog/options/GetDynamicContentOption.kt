package dev.falkow.blanco.nodes.catalog.options

import dev.falkow.blanco.nodes.catalog.types.DynamicContent
import dev.falkow.blanco.nodes.catalog.storages.ICatalogRemoteStorage
import javax.inject.Inject

internal class GetDynamicContentOption @Inject constructor(
    private val catalogRemoteStorage: ICatalogRemoteStorage,
) {

    suspend operator fun invoke(catalogNavId: String, queryString: String): DynamicContent {

        val catalogHtmlPage = catalogRemoteStorage.getCatalogCardList(catalogNavId, queryString)

        val catalogList = mutableListOf<dev.falkow.blanco.shared.types.FinishedProductCard>()
        val catalogListHtmlBlock =
            Regex("'products-item'.*?</a></div>").findAll(catalogHtmlPage)

        catalogListHtmlBlock.forEach {
            val catalogNavId =
                Regex("/").replaceFirst(
                    Regex("catalogs/").replaceFirst(
                        Regex("catalogs/.*?/").find(it.value)?.value ?: "null",
                        ""
                    ),
                    ""
                )
            val catalogProductNavId =
                Regex("'").replaceFirst(
                    Regex("products/").replaceFirst(
                        Regex("products/.*?'").find(it.value)?.value ?: "null",
                        ""
                    ),
                    ""
                )
            val imageSrc = "https://blanco.moscow" +
                    Regex("'").replaceFirst(
                        Regex("src='").replaceFirst(
                            Regex("src='.*?'").find(it.value)?.value ?: "null",
                            ""
                        ),
                        ""
                    )
            val title =
                Regex("</a").replaceFirst(
                    Regex("name'.*?>").replaceFirst(
                        Regex("name'.*?</a").find(it.value)?.value ?: "null",
                        ""
                    ),
                    ""
                )
            val price =
                Regex("<span").replaceFirst(
                    Regex("price'>").replaceFirst(
                        Regex("price'>.*?<span").find(it.value)?.value ?: "null",
                        ""
                    ),
                    ""
                )
            catalogList.add(
                dev.falkow.blanco.shared.types.FinishedProductCard(
                    catalogNavId,
                    catalogProductNavId,
                    title,
                    price,
                    imageSrc
                )
            )
        }

        return DynamicContent(catalogList.toList())
    }
}