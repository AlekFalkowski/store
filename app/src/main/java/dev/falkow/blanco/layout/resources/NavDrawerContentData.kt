package dev.falkow.blanco.layout.resources

import dev.falkow.blanco.shared.types.NavDrawerGroup
import dev.falkow.blanco.shared.types.NavDrawerLink

object NavDrawerContentData : INavDrawerContentData {

    override val data = listOf<NavDrawerGroup>(
        NavDrawerGroup(
            // title = "Главная",
            linkList = listOf(
                NavDrawerLink(
                    navDestination = "home",
                    iconSrc = "https://falkow.dev/blanco/icons/home.svg",
                    title = "Главная",
                ),
            )
        ),
        NavDrawerGroup(
            title = "Каталог",
            linkList = listOf(
                NavDrawerLink(
                    navDestination = "catalog/kitchen-sinks",
                    iconSrc = "https://falkow.dev/blanco/icons/sink.svg",
                    title = "Мойки для кухни",
                ),
                NavDrawerLink(
                    navDestination = "catalog/kitchen-taps",
                    iconSrc = "https://falkow.dev/blanco/icons/tap.svg",
                    title = "Смесители",
                ),
                NavDrawerLink(
                    navDestination = "catalog/soap-dispensers",
                    iconSrc = "https://falkow.dev/blanco/icons/pump-soap.svg",
                    title = "Дозаторы",
                ),
                NavDrawerLink(
                    navDestination = "catalog/waste-systems",
                    iconSrc = "https://falkow.dev/blanco/icons/trash.svg",
                    title = "Мусорные системы",
                ),
                NavDrawerLink(
                    navDestination = "catalog/accessories",
                    iconSrc = "https://falkow.dev/blanco/icons/kitchen-set.svg",
                    title = "Аксесуары",
                ),
                NavDrawerLink(
                    navDestination = "catalog/disposers",
                    iconSrc = "https://falkow.dev/blanco/icons/gears.svg",
                    title = "Измельчители",
                ),
                // NavDrawerLink(
                //     navDestination = "catalog/water-drinking-faucets",
                //     iconSrc = "",
                //     title = "Краны питьевой воды",
                // ),
            )
        ),
        NavDrawerGroup(
            title = "О магазине",
            linkList = listOf(
                NavDrawerLink(
                    navDestination = "delivery",
                    iconSrc = "https://falkow.dev/blanco/icons/truck.svg",
                    title = "Доставка",
                ),
                NavDrawerLink(
                    navDestination = "payment",
                    iconSrc = "https://falkow.dev/blanco/icons/payment.svg",
                    title = "Оплата",
                ),
                NavDrawerLink(
                    navDestination = "contacts",
                    iconSrc = "https://falkow.dev/blanco/icons/contacts.svg",
                    title = "Контакты",
                ),
                NavDrawerLink(
                    navDestination = "repair",
                    iconSrc = "https://falkow.dev/blanco/icons/repair.svg",
                    title = "Сервис",
                ),
            )
        ),
        NavDrawerGroup(
            linkList = listOf(
                NavDrawerLink(
                    navDestination = "search",
                    iconSrc = "https://falkow.dev/blanco/icons/search.svg",
                    title = "Поиск",
                ),
                NavDrawerLink(
                    navDestination = "bookmarks",
                    iconSrc = "https://falkow.dev/blanco/icons/bookmarks.svg",
                    title = "Закладки",
                ),
                NavDrawerLink(
                    navDestination = "cart",
                    iconSrc = "https://falkow.dev/blanco/icons/cart.svg",
                    title = "Корзина",
                ),
                NavDrawerLink(
                    navDestination = "chat",
                    iconSrc = "https://falkow.dev/blanco/icons/chat.svg",
                    title = "Чат",
                ),
            )
        ),
        NavDrawerGroup(
            linkList = listOf(
                NavDrawerLink(
                    navDestination = "account",
                    iconSrc = "https://falkow.dev/blanco/icons/account.svg",
                    title = "Аккаунт",
                ),
                NavDrawerLink(
                    navDestination = "settings",
                    iconSrc = "https://falkow.dev/blanco/icons/settings.svg",
                    title = "Настройки",
                ),
            )
        ),
    )
}