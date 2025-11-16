package dev.bober.store.di

import dev.bober.store.presentation.MainViewModel
import dev.bober.store.presentation.categories.CategoriesViewModel
import dev.bober.store.presentation.home.HomeViewModel
import dev.bober.store.presentation.home.details.AppDetailsViewModel
import dev.bober.store.presentation.onboarding.OnboardingViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val presentationModule = module {
    viewModelOf(::MainViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::AppDetailsViewModel)
    viewModelOf(::CategoriesViewModel)
    viewModelOf(::OnboardingViewModel)
}