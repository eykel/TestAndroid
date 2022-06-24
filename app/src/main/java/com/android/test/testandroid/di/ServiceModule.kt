package com.android.test.testandroid.di

import com.android.test.testandroid.ui.helper.Constants.BASE_URL
import com.android.test.testandroid.data.networking.EventNetworking
import com.android.test.testandroid.data.repository.EventRepository
import com.android.test.testandroid.data.service.EventService
import com.android.test.testandroid.ui.view_model.EventDetailViewModel
import com.android.test.testandroid.ui.view_model.EventListViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val serviceModule = module {
    //Retrofit instance
    factory { provideRetrofit() }
    // My Service Instance
    factory { eventNetworking(get()) }

    //Networking and Repository Instance
    single { EventNetworking(get()) }
    factory { EventRepository(get()) }

    // It says by owns name =)
    viewModel {
        EventListViewModel(get())
    }
    viewModel {
        EventDetailViewModel(get())
    }
}

private fun httpInterceptor() = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
}

fun eventNetworking(retrofit: Retrofit): EventService =
    retrofit.create(EventService::class.java)

private fun basicOkHttpClient() = OkHttpClient.Builder().addInterceptor(httpInterceptor()).build()

fun provideRetrofit() : Retrofit {
    return Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create()).client(basicOkHttpClient())
        .baseUrl(BASE_URL)
        .build()
}


