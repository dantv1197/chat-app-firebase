package com.fg.chat.messenger.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {
    // PreferenceManager is now provided via its @Inject constructor
}
