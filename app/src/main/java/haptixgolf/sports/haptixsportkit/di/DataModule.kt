package haptixgolf.sports.haptixsportkit.di

import haptixgolf.sports.haptixsportkit.data.repository.CartRepository
import haptixgolf.sports.haptixsportkit.data.repository.YJIJWOnboardingRepo
import haptixgolf.sports.haptixsportkit.data.repository.OrderRepository
import haptixgolf.sports.haptixsportkit.data.repository.ProductRepository

import org.koin.core.qualifier.named
import org.koin.dsl.module

val dataModule = module {
    includes(databaseModule, dataStoreModule)

    single {
        YJIJWOnboardingRepo(
            yjijwOnboardingStoreManager = get(),
            coroutineDispatcher = get(named("IO"))
        )
    }

    single { ProductRepository() }

    single {
        CartRepository(
            cartItemDao = get(),
            coroutineDispatcher = get(named("IO"))
        )
    }

    single {
        OrderRepository(
            orderDao = get(),
            coroutineDispatcher = get(named("IO"))
        )
    }
}