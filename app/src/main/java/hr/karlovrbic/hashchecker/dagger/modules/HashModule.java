package hr.karlovrbic.hashchecker.dagger.modules;

import dagger.Module;
import dagger.Provides;
import hr.karlovrbic.hashchecker.features.hash.IHash;
import hr.karlovrbic.hashchecker.features.hash.interactors.WebpageInteractor;
import hr.karlovrbic.hashchecker.features.hash.presenters.HashPresenter;

/**
 * Module that provides view, presenter and interactor for Hash feature.
 *
 * @author Karlo Vrbic
 * @version 1.0, 29.12.2016.
 */
@Module
public final class HashModule {

    private IHash.View view;

    public HashModule(IHash.View view) {
        this.view = view;
    }

    @Provides
    public IHash.View provideView() {
        return view;
    }

    @Provides
    public IHash.Presenter providePresenter(HashPresenter presenter) {
        return presenter;
    }

    @Provides
    public IHash.Interactor provideForecastInteractor(WebpageInteractor interactor) {
        return interactor;
    }
}
