package hr.karlovrbic.hashchecker.dao;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import hr.karlovrbic.hashchecker.dao.models.WebPage;
import hr.karlovrbic.hashchecker.dao.models.WebPage_Table;

/**
 * DAO for loading and saving {@linkplain WebPage} objects to database.
 *
 * @author Karlo Vrbic
 * @version 1.0, 29.12.2016.
 */
public final class DatabaseDAO implements IDAO {

    private static DatabaseDAO instance;

    public static DatabaseDAO getInstance() {
        if (instance == null) {
            instance = new DatabaseDAO();
        }
        return instance;
    }

    private DatabaseDAO() {
    }

    @Override
    public WebPage save(WebPage webPage) {
        webPage.save();
        return webPage;
    }

    @Override
    public WebPage load(String url) {
        return SQLite.select()
                .from(WebPage.class)
                .where(WebPage_Table.url.eq(url))
                .querySingle();
    }
}
