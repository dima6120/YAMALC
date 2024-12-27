package com.dima6120.core_api

import android.content.Context

interface ApplicationContextProvider {

    fun provideApplicationContext(): Context
}