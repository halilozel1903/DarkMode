package com.halil.ozel.darkmode

enum class ThemeMode(val storageValue: Int) {
    Light(storageValue = 0),
    Dark(storageValue = 1),
    System(storageValue = 2);

    companion object {
        fun fromStorageValue(value: Int?): ThemeMode = entries.firstOrNull { it.storageValue == value } ?: System
    }
}
