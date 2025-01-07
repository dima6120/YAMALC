package com.dima6120.core_api.di

import com.dima6120.core_api.navigation.Route
import dagger.MapKey
import kotlin.reflect.KClass

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class RouteKey(val value: KClass<out Route>)
