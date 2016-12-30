package hr.karlovrbic.hashchecker.dao;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import hr.karlovrbic.hashchecker.HashApplication;
import hr.karlovrbic.hashchecker.dao.models.WebPage;

/**
 * DAO for loading and saving {@linkplain WebPage} objects to shared preferences.
 *
 * @author Karlo Vrbic
 * @version 1.0, 29.12.2016.
 */
public final class PreferencesDAO implements IDAO {

    private static final String NAME = "HashPreferences";
    private static final String WEBPAGE_LIST_KEY = "web_page_list";

    private static PreferencesDAO instance;

    public static PreferencesDAO getInstance() {
        if (instance == null) {
            instance = new PreferencesDAO();
        }
        return instance;
    }

    private SharedPreferences preferences;
    private Gson gson;

    private PreferencesDAO() {
        preferences = HashApplication.getAppContext().getSharedPreferences(NAME, Context.MODE_PRIVATE);
        gson = new Gson();
    }

    @Override
    public WebPage save(WebPage webPage) {
        ArrayList<WebPage> webPages = getWebPageList(WEBPAGE_LIST_KEY);

        webPage.setId(webPages.size() + 1);
        webPages.add(webPage);

        putWebPageList(WEBPAGE_LIST_KEY, webPages);
        return webPage;
    }

    @Override
    public WebPage load(String url) {
        ArrayList<WebPage> webPages = getWebPageList(WEBPAGE_LIST_KEY);
        WebPage match = null;

        for (WebPage webPage : webPages) {
            if (webPage.getUrl().equals(url)) {
                match = webPage;
                break;
            }
        }

        return match;
    }

    private ArrayList<WebPage> getWebPageList(@NonNull String key) {
        List<String> stringList = Arrays.asList(TextUtils.split(preferences.getString(key, ""), "‚‗‚"));

        ArrayList<WebPage> webPages = new ArrayList<>();
        for (String webPageJson : stringList) {
            WebPage webPage = gson.fromJson(webPageJson, WebPage.class);
            webPages.add(webPage);
        }

        return webPages;
    }

    private void putWebPageList(@NonNull String key, List<WebPage> webPages) {
        if (webPages != null && !webPages.isEmpty()) {
            ArrayList<String> stringList = new ArrayList<>();
            for (WebPage webPage : webPages) {
                stringList.add(gson.toJson(webPage));
            }

            String[] stringArray = stringList.toArray(new String[stringList.size()]);
            preferences.edit().putString(key, TextUtils.join("‚‗‚", stringArray)).apply();
        }
    }
}
