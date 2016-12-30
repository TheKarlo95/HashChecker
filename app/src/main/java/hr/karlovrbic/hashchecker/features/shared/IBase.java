package hr.karlovrbic.hashchecker.features.shared;

import android.support.annotation.IdRes;

/**
 * Defines base view, presenter and interactor used by all features of this application.
 *
 * @author Karlo Vrbic
 * @version 1.0, 28.12.2016.
 * @see IBase
 */
public interface IBase {

    interface View {
        void showMessage(@IdRes int resId);

        void showMessage(String message);

        void showProgress();

        void hideProgress();
    }

    interface Presenter {
        void cancel();
    }

    interface Interactor {
        void cancel();
    }
}
