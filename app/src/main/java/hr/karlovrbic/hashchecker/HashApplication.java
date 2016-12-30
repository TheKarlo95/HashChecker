package hr.karlovrbic.hashchecker;

import android.app.Application;
import android.content.Context;

import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

import hr.karlovrbic.hashchecker.dagger.components.AppComponent;
import hr.karlovrbic.hashchecker.dagger.components.DaggerAppComponent;

/**
 * Contains {@linkplain AppComponent} and {@linkplain Context} and initializes {@linkplain FlowManager}.
 *
 * @author Karlo Vrbic
 * @version 1.0, 28.12.2016.
 * @see Application
 */
public final class HashApplication extends Application {

    private static AppComponent appComponent;
    private static Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        FlowManager.init(new FlowConfig.Builder(this).build());
        appComponent = DaggerAppComponent.create();
        appContext = getApplicationContext();
    }

    public static AppComponent getAppComponent() {
        return appComponent;
    }

    public static Context getAppContext() {
        return appContext;
    }
}
