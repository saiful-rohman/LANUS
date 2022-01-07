package com.javaindo.lautnusantara.hi

import android.content.Context
import androidx.room.Room
import com.javaindo.lautnusantara.room.DBLautNunasantar
import com.javaindo.lautnusantara.room.dao.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) : DBLautNunasantar{
        return Room.databaseBuilder(
            context,DBLautNunasantar::class.java,DBLautNunasantar.DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideDao(dbLautNunasantar: DBLautNunasantar) : DatakuDao{
        return dbLautNunasantar.datakuDao()
    }

    @Provides
    @Singleton
    fun provideOnlineUserDao(dbLautNunasantar: DBLautNunasantar) : OnlineUserDao{
        return dbLautNunasantar.onlineUserDao()
    }

    @Provides
    @Singleton
    fun privideLatLongDao(dbLautNunasantar: DBLautNunasantar) : LatLongDao{
        return dbLautNunasantar.latLongDao()
    }

    @Provides
    @Singleton
    fun provideSettingUserDao(dbLautNunasantar: DBLautNunasantar) : SettingUserDao{
        return dbLautNunasantar.settingUserDao()
    }

    @Provides
    @Singleton
    fun provideCalculateFuelDao(dbLautNunasantar: DBLautNunasantar) : CalclFuelDao{
        return dbLautNunasantar.calculateFuelDao()
    }

}