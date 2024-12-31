package com.dima6120.core_api.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class DispatcherIO

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class DispatcherMain

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class DispatcherDefault