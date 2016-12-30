package hr.karlovrbic.hashchecker.dagger.components;

import dagger.Subcomponent;
import hr.karlovrbic.hashchecker.dagger.modules.HashModule;
import hr.karlovrbic.hashchecker.features.hash.views.HashActivity;

/**
 * @author Karlo Vrbic
 * @version 1.0, 29.12.2016.
 */
@Subcomponent(modules = {
        HashModule.class
})
public interface HashComponent {

    void inject(HashActivity activity);
}
