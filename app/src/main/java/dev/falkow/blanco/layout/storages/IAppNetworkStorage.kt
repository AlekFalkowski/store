package dev.falkow.blanco.layout.storages

import dev.falkow.blanco.nodes.catalog.storages.ICatalogRemoteStorage
import dev.falkow.blanco.nodes.catalog_product.storages.CatalogProductRemoteStorage
import dev.falkow.blanco.nodes.order.storages.IOrderRemoteStorage

interface IAppNetworkStorage :
    ICatalogRemoteStorage,
    CatalogProductRemoteStorage,
    IOrderRemoteStorage