package com.abhay.alumniconnect.data.di

import com.abhay.alumniconnect.data.remote.AlumniApi
import com.abhay.alumniconnect.data.remote.FakeAlumniApi
import com.abhay.alumniconnect.data.repository.SessionManager
import com.abhay.alumniconnect.data.repository.SessionManagerImpl
import com.abhay.alumniconnect.di.AppModule
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [AppModule::class]
)
abstract class FakeNetworkModule {
    @Binds
    abstract fun bindAlumniApi(fakeAlumniApi: FakeAlumniApi): AlumniApi

    @Binds
    abstract fun provideSessionManager(sessionManager: SessionManagerImpl): SessionManager
}