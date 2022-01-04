package com.javaindo.lautnusantara.di

import com.javaindo.lautnusantara.repository.*
import com.javaindo.lautnusantara.retrofit.API
import com.javaindo.lautnusantara.retrofit.mapperretrofit.*
import com.javaindo.lautnusantara.room.dao.*
import com.javaindo.lautnusantara.room.mapperroom.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideDatakuRepo(
        datakuDao: DatakuDao,
        api: API,
        cacheMapper: DatakuCacheMapper,
        networkMapper: DatakuNetworkMapper
    ): DatakuRepository{
        return DatakuRepository(datakuDao,api,cacheMapper,networkMapper)
    }

    @Provides
    @Singleton
    fun provideOnlineUserRepo(
        datakuDao: OnlineUserDao,
        api: API,
        cacheMapper: OnlineUserCacheMapper,
        networkMapper: OnlineUserNetworkMapper
    ): HomeRepository{
        return HomeRepository(datakuDao,api,cacheMapper,networkMapper)
    }

    @Provides
    @Singleton
    fun provideLatLongRepo(
        latLongDao: LatLongDao,
        api: API,
        cacheMapper : LatLongCacheMapper,
        networkMapper : LatLongNetworkMapper
    ) : LatLongRepository{
        return LatLongRepository(latLongDao,api,cacheMapper,networkMapper)
    }

    @Provides
    @Singleton
    fun provideSettingUserRepo(
        settingUserDao: SettingUserDao,
        api: API,
        cacheMapper : SettingUserCacheMapper,
        networkMapper : SettingUserNetworkMapper
    ) : SettingUserRepository{
        return SettingUserRepository(settingUserDao,api,cacheMapper,networkMapper)
    }

    @Provides
    @Singleton
    fun provideSellingPriceRepo(
        api: API
    ) : SellingPriceRepository{
        return SellingPriceRepository(api)
    }

    @Provides
    @Singleton
    fun provideResultCatchRepo(
        api: API
    ) : ResultCatchRepository{
        return ResultCatchRepository(api)
    }

    @Provides
    @Singleton
    fun provideCalculateFuelRepo(
        calclFuelDao: CalclFuelDao,
        settingUserDao: SettingUserDao,
        api: API,
        cacheMapper : CalclFuelCacheMapper,
        networkMapper : CalclFuelNetworkMapper,
        cacherMapper2 : SettingUserCacheMapper
    ) : CalculateFuelRepository{
        return CalculateFuelRepository(calclFuelDao,settingUserDao,api,cacheMapper,networkMapper,cacherMapper2)
    }

    @Provides
    @Singleton
    fun provideLoginRepo(
        settingUserDao: SettingUserDao,
        api: API,
        cacheMapper : SettingUserCacheMapper,
        networkMapper : SettingUserNetworkMapper
    ) : LoginRepository{
        return LoginRepository(settingUserDao,api,cacheMapper,networkMapper)
    }

}