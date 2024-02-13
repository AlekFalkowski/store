package dev.falkow.blanco.layout.storages

import dev.falkow.blanco.nodes.catalog.storages.ICatalogDevRemoteStorage
import dev.falkow.blanco.shared.storages.ISharedDevRemoteStorage

interface IAppDevNetworkStorage :
    ICatalogDevRemoteStorage,
    ISharedDevRemoteStorage