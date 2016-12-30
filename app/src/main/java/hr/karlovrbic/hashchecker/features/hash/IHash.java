package hr.karlovrbic.hashchecker.features.hash;

import android.content.Context;

import hr.karlovrbic.hashchecker.dao.models.WebPage;
import hr.karlovrbic.hashchecker.features.shared.IBase;
import hr.karlovrbic.hashchecker.features.shared.interactors.ResponseListener;

/**
 * Defines view, presenter and interactor needed for Hash feature.
 *
 * @author Karlo Vrbic
 * @version 1.0, 29.12.2016.
 * @see IBase
 */
public interface IHash {

    interface View extends IBase.View {

        void showHash(WebPage webPage);
    }

    interface Presenter extends IBase.Presenter {

        void startDownload(Context context, String url);
    }

    interface Interactor extends IBase.Interactor {

        void getWebpage(String url, ResponseListener<WebPage> listener);
    }
}
