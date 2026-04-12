package io.github.untoastedtoast.foldio.app

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import androidx.work.WorkManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton
import io.github.untoastedtoast.foldio.persistence.TransactionalExecutor
import io.github.untoastedtoast.foldio.persistence.WalletDb
import io.github.untoastedtoast.foldio.persistence.buildDb
import io.github.untoastedtoast.foldio.persistence.localization.PassLocalizationDao
import io.github.untoastedtoast.foldio.persistence.localization.PassLocalizationRepository
import io.github.untoastedtoast.foldio.persistence.pass.PassDao
import io.github.untoastedtoast.foldio.persistence.pass.PassRepository
import io.github.untoastedtoast.foldio.persistence.tag.TagDao
import io.github.untoastedtoast.foldio.persistence.tag.TagRepository
import java.util.concurrent.Callable

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideLocalizationRepository(localizationDao: PassLocalizationDao): PassLocalizationRepository =
        PassLocalizationRepository(localizationDao)

    @Provides
    fun providePassRepository(
        @ApplicationContext context: Context,
        passDao: PassDao,
    ): PassRepository = PassRepository(context, passDao)

    @Provides
    fun provideTagRepository(
        @ApplicationContext context: Context,
        tagDao: TagDao,
    ): TagRepository = TagRepository(context, tagDao)

    @Provides
    @Singleton
    fun provideWalletDb(
        @ApplicationContext context: Context,
    ): WalletDb = buildDb(context)

    @Provides
    fun providePassDao(walletDb: WalletDb): PassDao = walletDb.passDao()

    @Provides
    fun provideTransactionalExecutor(walletDb: WalletDb): TransactionalExecutor =
        object : TransactionalExecutor {
            override fun <T> runTransactionally(callable: Callable<T>): T = walletDb.runInTransaction(callable)

            override fun runTransactionally(runnable: Runnable) = walletDb.runInTransaction(runnable)
        }

    @Provides
    fun provideLocalizationDAo(walletDb: WalletDb): PassLocalizationDao = walletDb.localizationDao()

    @Provides
    fun provideTagDao(walletDb: WalletDb): TagDao = walletDb.tagDao()

    @Provides
    fun provideWorkManager(
        @ApplicationContext context: Context,
    ): WorkManager = WorkManager.getInstance(context)

    @Provides
    fun provideSharedPrefs(
        @ApplicationContext context: Context,
    ): SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)!!
}
