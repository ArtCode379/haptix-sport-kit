package haptixgolf.sports.haptixsportkit.di

import haptixgolf.sports.haptixsportkit.ui.viewmodel.AppViewModel
import haptixgolf.sports.haptixsportkit.ui.viewmodel.CartViewModel
import haptixgolf.sports.haptixsportkit.ui.viewmodel.CheckoutViewModel
import haptixgolf.sports.haptixsportkit.ui.viewmodel.YJIJWOnboardingVM
import haptixgolf.sports.haptixsportkit.ui.viewmodel.OrderViewModel
import haptixgolf.sports.haptixsportkit.ui.viewmodel.ProductDetailsViewModel
import haptixgolf.sports.haptixsportkit.ui.viewmodel.ProductViewModel
import haptixgolf.sports.haptixsportkit.ui.viewmodel.YJIJWSplashVM
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModule = module {
    viewModel {
        AppViewModel(
            cartRepository = get()
        )
    }

    viewModel {
        YJIJWSplashVM(
            onboardingRepository = get()
        )
    }

    viewModel {
        YJIJWOnboardingVM(
            onboardingRepository = get()
        )
    }

    viewModel {
        ProductViewModel(
            productRepository = get(),
            cartRepository = get(),
        )
    }

    viewModel {
        ProductDetailsViewModel(
            productRepository = get(),
            cartRepository = get(),
        )
    }

    viewModel {
        CheckoutViewModel(
            cartRepository = get(),
            productRepository = get(),
            orderRepository = get(),
        )
    }

    viewModel {
        CartViewModel(
            cartRepository = get(),
            productRepository = get(),
        )
    }

    viewModel {
        OrderViewModel(
            orderRepository = get(),
        )
    }
}