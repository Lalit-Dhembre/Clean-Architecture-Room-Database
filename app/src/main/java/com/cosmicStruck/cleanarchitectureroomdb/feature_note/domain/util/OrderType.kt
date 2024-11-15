package com.cosmicStruck.cleanarchitectureroomdb.feature_note.domain.util

sealed class OrderType{
    object Ascending : OrderType()
    object Descending : OrderType()
}