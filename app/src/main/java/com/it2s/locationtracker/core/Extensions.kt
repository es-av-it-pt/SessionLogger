package com.it2s.locationtracker.core

inline fun <reified T> List<T>.indexOrNull(item: T): Int? = indexOf(item).takeIf { it != -1 }

