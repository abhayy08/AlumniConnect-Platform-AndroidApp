package com.abhay.alumniconnect.di

import com.abhay.alumniconnect.data.repository.AlumniAuthRepositoryImpl
import com.abhay.alumniconnect.data.repository.AlumniRemoteRepositoryImpl
import com.abhay.alumniconnect.data.repository.JobsRepositoryImpl
import com.abhay.alumniconnect.domain.repository.AlumniAccountRepository
import com.abhay.alumniconnect.domain.repository.AlumniRemoteRepository
import com.abhay.alumniconnect.domain.repository.JobsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAlumniAccountRepository(
        impl: AlumniAuthRepositoryImpl
    ): AlumniAccountRepository

    @Binds
    @Singleton
    abstract fun bindAlumniRemoteRepository(
        impl: AlumniRemoteRepositoryImpl
    ): AlumniRemoteRepository

    @Binds
    @Singleton
    abstract fun bindJobsRepository(
        impl: JobsRepositoryImpl
    ): JobsRepository

}