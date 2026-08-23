package haptixgolf.sports.haptixsportkit.di

import haptixgolf.sports.haptixsportkit.data.datastore.YJIJWOnboardingPrefs
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataStoreModule = module {
    single { YJIJWOnboardingPrefs(androidContext()) }
}