package com.meinvodafone.testflow.di

import com.meinvodafone.testflow.data.repository.remote.IRemoteRepository
import com.meinvodafone.testflow.data.repository.remote.RemoteRepositoryImpl
import com.meinvodafone.testflow.data.repository.remote.service.MoviesAPI
import com.meinvodafone.testflow.presentation.viewmodel.MovieListViewModel
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import retrofit2.Retrofit

val moviesModule = module {
    single { get<Retrofit>().create(MoviesAPI::class.java) }
    factory<IRemoteRepository> { RemoteRepositoryImpl(get()) }
    viewModel { MovieListViewModel(get()) }
}