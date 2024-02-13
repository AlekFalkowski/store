package dev.falkow.blanco.nodes.catalog.types

import kotlinx.serialization.Serializable

// types:
// text // Email, Password, Tel number
// quantityInInteger
// quantityInReal
// rangeOfIntegers
// rangeOfReals
// singleChoice
// multiChoice

// order[options][prices][number]=asc
// order[options][product][baseUnitWidth][number]=asc
// order[options][product][cupDepth][number]=asc
// order[options][product][length][number]=asc

// prices[number][min]=10
// prices[number][max]=40000

// product[cupDepth][unit][id]=8
// product[length][unit][id]=8
// product[width][unit][id]=8

// product[baseUnitWidth][0][number]=30.00
// product[baseUnitWidth][3][number]=50.00
// product[baseUnitWidth][6][number]=90.00

// product[installationMethods][id][]=669
// product[installationMethods][id][]=667
// product[installationMethods][id][]=668

// prices[number][min]=
// prices[number][max]=
// product[drainer][id][]=887
// product[drainer][id][]=886
// product[baseUnitWidth][0][number]=30.00
// product[baseUnitWidth][1][number]=40.00
// product[cupDepth][number][min]=&product[cupDepth][number][max]=&product[cupDepth][unit][id]=8&product[length][number][min]=&product[length][number][max]=&product[length][unit][id]=8&product[width][number][min]=&product[width][number][max]=&product[width][unit][id]=8

// New Types:
// text // Email, Password, Tel number
// range
// singleChoice
// multiChoice

@Serializable
data class Field(
    val type: String,
    val label: String,
    val name: String,
    val endName: String? = null, // Name for the second field in the range.
    val value: String? = null, // Either value or options.
    val options: List<Option>? = null,
    //
    val valueType: String? = null,
    val units: String? = null,
    val minVal: Double? = null,
    val maxVal: Double? = null,
    val numberOfDigitsAfterDot: Int? = null,
    //
    val placeholder: String? = null,
    val endPlaceholder: String? = null,
    val helpText: String? = null,
    val endHelpText: String? = null,
    val quickInfo: String? = null,
)
