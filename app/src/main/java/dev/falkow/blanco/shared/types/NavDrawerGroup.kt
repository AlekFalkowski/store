package dev.falkow.blanco.shared.types

data class NavDrawerGroup(
    val title: String? = null,
    val linkList: List<NavDrawerLink>,
)