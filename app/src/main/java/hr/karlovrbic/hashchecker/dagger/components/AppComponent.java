package hr.karlovrbic.hashchecker.dagger.components;

import javax.inject.Singleton;

import dagger.Component;
import hr.karlovrbic.hashchecker.dagger.modules.HashModule;
import hr.karlovrbic.hashchecker.dagger.modules.base.ClientModule;
import hr.karlovrbic.hashchecker.dagger.modules.base.LogModule;

/**
 * @author Karlo Vrbic
 * @version 1.0, 29.12.2016.
 */
@Component(modules = {
        ClientModule.class,
        LogModule.class
})
@Singleton
public interface AppComponent {

    HashComponent plus(HashModule module);
}
