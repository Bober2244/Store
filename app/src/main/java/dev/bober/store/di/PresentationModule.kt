package dev.bober.store.di

import dev.bober.store.presentation.MainViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val presentationModule = module {
    viewModelOf(::MainViewModel)

}