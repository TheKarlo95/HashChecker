package hr.karlovrbic.hashchecker.features.shared.views;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import hr.karlovrbic.hashchecker.HashApplication;
import hr.karlovrbic.hashchecker.R;
import hr.karlovrbic.hashchecker.dagger.components.AppComponent;
import hr.karlovrbic.hashchecker.features.shared.IBase;

/**
 * Created by thekarlo95 on 27.12.16..
 */

public abstract class BaseView extends AppCompatActivity implements IBase.View {

    private ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injectDependencies(HashApplication.getAppComponent());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showMessage(@StringRes int message) {
        showMessage(getString(message));
    }

    @Override
    public void showMessage(final String message) {
        runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void showProgress() {
        runOnUiThread(new Runnable() {
            public void run() {
                initProgressDialog();

                if (!progressDialog.isShowing()) {
                    progressDialog.show();
                }
            }
        });
    }

    @Override
    public void hideProgress() {
        runOnUiThread(new Runnable() {
            public void run() {
                if (progressDialog != null) {
                    progressDialog.hide();
                    progressDialog = null;
                }
            }
        });
    }

    protected abstract void injectDependencies(AppComponent appComponent);

    private void initProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.webpage_loading));
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
    }
}
