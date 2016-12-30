package hr.karlovrbic.hashchecker.dao;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Used by DBFlow to create database.
 *
 * @author Karlo Vrbic
 * @version 1.0, 29.12.2016.
 */
@Database(name = HashDatabase.NAME, version = HashDatabase.VERSION)
public final class HashDatabase {

    public static final String NAME = "HashDB";
    public static final int VERSION = 1;

    public static final String WEB_PAGE_TABLE = "WebPages";
}
