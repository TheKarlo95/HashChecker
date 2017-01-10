package hr.karlovrbic.hashchecker.dagger.modules.base;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Module that provides {@linkplain OkHttpClient}.
 *
 * @author Karlo Vrbic
 * @version 1.0, 29.12.2016.
 */
@Module
public final class ClientModule {

    private static final int NETWORK_TIMEOUT_SECONDS = 5;

    @Provides
    @Singleton
    public Integer provideNetworkTimeout() {
        return NETWORK_TIMEOUT_SECONDS;
    }

    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient(HttpLoggingInterceptor logger, Integer networkTimoutSeconds) {
        return new OkHttpClient.Builder()
                .readTimeout(networkTimoutSeconds, TimeUnit.SECONDS)
                .connectTimeout(networkTimoutSeconds, TimeUnit.SECONDS)
                .addInterceptor(logger)
                .build();
    }
}