package dev.falkow.blanco.nodes.catalog_product.options

import dev.falkow.blanco.nodes.catalog_product.types.ImageGallery
import dev.falkow.blanco.nodes.catalog_product.types.StableContent
import dev.falkow.blanco.nodes.catalog_product.storages.CatalogProductRemoteStorage
import javax.inject.Inject

class GetStableContentOption @Inject constructor(
    private val catalogProductRemoteStorage: CatalogProductRemoteStorage,
) {

    suspend operator fun invoke(
        catalogNavId: String,
        catalogProductNavId: String
    ): StableContent {
        val catalogProductHtmlPage =
            catalogProductRemoteStorage.getCatalogProductContent(catalogNavId, catalogProductNavId)
        val title =
            Regex("<span").replaceFirst(
                Regex("h1>").replaceFirst(
                    Regex("h1>.*?<span").find(catalogProductHtmlPage)?.value ?: "null",
                    ""
                ),
                ""
            )
        val article =
            Regex("</span").replaceFirst(
                Regex("span>&nbsp;").replaceFirst(
                    Regex("span>&nbsp;.*?</span").find(catalogProductHtmlPage)?.value ?: "null",
                    ""
                ),
                ""
            )
        val price =
            Regex("<span").replaceFirst(
                Regex("price'>").replaceFirst(
                    Regex("price'>.*?<span").find(catalogProductHtmlPage)?.value ?: "null",
                    ""
                ),
                ""
            )
        val specification =
            Regex("</table").replaceFirst(
                Regex(">").replaceFirst(
                    Regex("><tr>.*?</table").find(catalogProductHtmlPage)?.value ?: "null",
                    ""
                ),
                ""
            )

        val imageGalleryList = mutableListOf<ImageGallery>()
        val galleryImageListHtmlBlock = Regex("rel='image' href='.*?'").findAll(catalogProductHtmlPage)
        galleryImageListHtmlBlock.forEach {
            val galleryImage =
                Regex("'").replaceFirst(
                    Regex("rel='image' href='").replaceFirst(
                        it.value,
                        ""
                    ),
                    ""
                )
            imageGalleryList.add(
                ImageGallery(
                    src = "https://blanco.moscow$galleryImage",
                    description = galleryImage,
                )
            )
        }

        return StableContent(
            imageGalleryList = imageGalleryList,
            title = title,
            article = article,
            price = price,
            specification = specification,
        )
    }
}