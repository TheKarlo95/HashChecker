package hr.karlovrbic.hashchecker.features.hash.views;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.EditText;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hr.karlovrbic.hashchecker.R;
import hr.karlovrbic.hashchecker.dagger.components.AppComponent;
import hr.karlovrbic.hashchecker.dagger.modules.HashModule;
import hr.karlovrbic.hashchecker.dao.models.WebPage;
import hr.karlovrbic.hashchecker.features.hash.IHash;
import hr.karlovrbic.hashchecker.features.shared.views.BaseView;

/**
 * View for {@code Hash} feature that handles UI/UX.
 *
 * @author Karlo Vrbic
 * @version 1.0, 29.12.2016.
 * @see BaseView
 * @see IHash.View
 */
public final class HashActivity extends BaseView implements IHash.View {

    @Inject
    IHash.Presenter presenter;

    @BindView(R.id.et_website)
    EditText etWebsite;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hash);

        ButterKnife.bind(this);
    }

    @Override
    public void onStop() {
        presenter.cancel();
        hideProgress();
        super.onStop();
    }

    @Override
    public void showHash(WebPage webPage) {
        String firstByte = webPage.getHashcode().substring(0, 2);
        String message = null;
        if (isEven(firstByte)) {
            message = getString(R.string.dialog_message_database, webPage.getUrl(), webPage.getHashcode());
        } else {
            message = getString(R.string.dialog_message_preferences, webPage.getUrl(), webPage.getHashcode());
        }

        showWebPageHash(message);
    }

    private void showWebPageHash(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new AlertDialog.Builder(HashActivity.this)
                        .setCancelable(true)
                        .setMessage(message)
                        .show();
            }
        });
    }

    @OnClick(R.id.btn_download)
    public void downloadButtonClicked() {
        String url = etWebsite.getText().toString();
        presenter.startDownload(getApplicationContext(), url);
    }


    @Override
    protected void injectDependencies(AppComponent appComponent) {
        appComponent.plus(new HashModule(this)).inject(this);
    }

    private static boolean isEven(String hex) {
        return (Long.parseLong(hex, 16) & 1) == 0;
    }
}
