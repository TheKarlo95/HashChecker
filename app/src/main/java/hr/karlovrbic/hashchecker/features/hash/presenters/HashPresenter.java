package hr.karlovrbic.hashchecker.features.hash.presenters;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.apache.commons.validator.routines.UrlValidator;

import javax.inject.Inject;

import hr.karlovrbic.hashchecker.R;
import hr.karlovrbic.hashchecker.dao.DatabaseDAO;
import hr.karlovrbic.hashchecker.dao.PreferencesDAO;
import hr.karlovrbic.hashchecker.dao.models.WebPage;
import hr.karlovrbic.hashchecker.features.hash.IHash;
import hr.karlovrbic.hashchecker.features.hash.interactors.WebpageInteractor;
import hr.karlovrbic.hashchecker.features.shared.interactors.ResponseListener;

/**
 * Presenter for {@code Hash} feature that handles all actions required by hash feature.
 *
 * @author Karlo Vrbic
 * @version 1.0, 29.12.2016.
 * @see IHash.Presenter
 */
public final class HashPresenter implements IHash.Presenter {

    private IHash.View view;
    private IHash.Interactor interactor;

    @Inject
    public HashPresenter(IHash.View view, IHash.Interactor interactor) {
        this.view = view;
        this.interactor = interactor;
    }

    @Override
    public void startDownload(Context context, String url) {
        UrlValidator urlValidator = new UrlValidator();

        if (!isNetworkAvailable(context)) {
            view.showMessage(context.getResources().getString(R.string.error_no_internet));
        } else {
            String fullUrl = getFullURL(url);

            if (!urlValidator.isValid(fullUrl)) {
                view.showMessage(context.getResources().getString(R.string.error_invalid_url));
            } else {
                WebPage webPage = getBackUp(fullUrl);

                if (webPage == null) {
                    view.showProgress();
                    interactor.getWebpage(fullUrl, new WebPageListener(view));
                } else {
                    view.showHash(webPage);
                }
            }
        }
    }

    @Override
    public void cancel() {
        interactor.cancel();
        view.hideProgress();
    }

    private static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private static WebPage getBackUp(String url) {
        WebPage webPage = PreferencesDAO.getInstance().load(url);
        if (webPage == null) {
            webPage = DatabaseDAO.getInstance().load(url);
        }
        return webPage;
    }

    private static String getFullURL(String url) {
        String fullUrl = null;

        if (url != null && url.length() >= 2) {
            fullUrl = url;
            if (!fullUrl.toLowerCase().matches("^\\w+://.*")) {
                if (!fullUrl.startsWith("www.")) {
                    fullUrl = "www." + fullUrl;
                }
                fullUrl = "http://" + fullUrl;
            }
        }

        return fullUrl;
    }

    private static class WebPageListener implements ResponseListener<WebPage> {

        private IHash.View view;

        private WebPageListener(IHash.View view) {
            this.view = view;
        }

        @Override
        public void onSuccess(WebPage result) {
            saveWebpage(result);
            view.hideProgress();
            view.showHash(result);
        }

        @Override
        public void onError(int code, String message) {
            view.hideProgress();
            if (code == WebpageInteractor.ERROR_CODE) {
                view.showMessage(message);
            } else {
                view.showMessage(code + ": " + message);
            }
        }

        private void saveWebpage(WebPage webPage) {
            String firstByte = webPage.getHashcode().substring(0, 2);

            if (isEven(firstByte)) {
                DatabaseDAO.getInstance().save(webPage);
            } else {
                PreferencesDAO.getInstance().save(webPage);
            }
        }

        private static boolean isEven(String hex) {
            return (Long.parseLong(hex, 16) & 1) == 0;
        }
    }
}
