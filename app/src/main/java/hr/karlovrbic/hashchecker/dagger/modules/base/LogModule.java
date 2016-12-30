package hr.karlovrbic.hashchecker.dagger.modules.base;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import hr.karlovrbic.hashchecker.BuildConfig;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;


/**
 * Module that provides {@linkplain OkHttpClient}.
 *
 * @author Karlo Vrbic
 * @version 1.0, 29.12.2016.
 */
@Module
public final class LogModule {

    @Provides
    @Singleton
    public HttpLoggingInterceptor.Level provideLogLevel() {
        if (BuildConfig.DEBUG) {
            return HttpLoggingInterceptor.Level.BODY;
        } else {
            return HttpLoggingInterceptor.Level.NONE;
        }
    }

    @Provides
    @Singleton
    public HttpLoggingInterceptor provideLog(HttpLoggingInterceptor.Level level) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(level);
        return interceptor;
    }
}