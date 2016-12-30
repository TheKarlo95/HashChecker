package hr.karlovrbic.hashchecker.features.hash.interactors;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;

import javax.inject.Inject;

import hr.karlovrbic.hashchecker.dao.models.WebPage;
import hr.karlovrbic.hashchecker.features.hash.IHash;
import hr.karlovrbic.hashchecker.features.shared.interactors.ResponseListener;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Interactor for {@code Hash} feature that downloads web pages asynchronously and calculates SHA-1 from their content.
 *
 * @author Karlo Vrbic
 * @version 1.0, 29.12.2016.
 * @see IHash.Interactor
 */
public final class WebpageInteractor implements IHash.Interactor {

    public static final int ERROR_CODE = -1;
    private static final String ERROR_MESSAGE = "Error occured while downloading webpage";

    private OkHttpClient client;
    private Call call;

    @Inject
    public WebpageInteractor(OkHttpClient client) {
        this.client = client;
    }

    @Override
    public void getWebpage(String url, ResponseListener<WebPage> listener) {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Call call = client.newCall(request);
        call.enqueue(new WebPageCallback(url, listener));
    }

    @Override
    public void cancel() {
        if (call != null) {
            call.cancel();
            call = null;
        }
    }

    private static class WebPageCallback implements Callback {

        private String url;
        private ResponseListener<WebPage> listener;

        private WebPageCallback(String url, ResponseListener<WebPage> listener) {
            this.url = url;
            this.listener = listener;
        }

        @Override
        public void onFailure(Call call, IOException e) {
            listener.onError(ERROR_CODE, ERROR_MESSAGE);
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            if (!response.isSuccessful()) {
                listener.onError(response.code(), response.message());
            } else {
                String content = response.body().string();
                String hashcode = new String(Hex.encodeHex(DigestUtils.sha1(content)));

                listener.onSuccess(new WebPage(url, hashcode));
            }
        }
    }
}
