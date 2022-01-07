package com.javaindo.lautnusantara.hi

import android.content.Context
import android.content.SharedPreferences
import com.javaindo.lautnusantara.BaseApplication
import com.javaindo.lautnusantara.utility.PrefHelper
import com.javaindo.lautnusantara.utility.SharedPrefKeys
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context) : BaseApplication {
        return app as BaseApplication
    }

    @Singleton
    @Provides
    fun provideSharePrefences(@ApplicationContext context: Context) : SharedPreferences{
        return context.getSharedPreferences(SharedPrefKeys.sharedName(),Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideSessionManager(sharedPreferences: SharedPreferences) : PrefHelper{
        return PrefHelper(sharedPreferences)
    }

}