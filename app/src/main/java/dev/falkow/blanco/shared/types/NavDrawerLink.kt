package dev.falkow.blanco.shared.types

data class NavDrawerLink(
    val navDestination: String,
    val iconSrc: String? = null,
    val title: String,
)