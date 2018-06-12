package rs.cod3rs.shopifine.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.Objects;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "shopifine.db";
    private static final int DATABASE_VERSION = 1;

    private Dao<ShoppingCartItem, Integer> shoppingCartDAO;

    public DatabaseHelper(final Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(final SQLiteDatabase database, final ConnectionSource connectionSource) {
        try {
            Log.i(getClass().getSimpleName(), "Creating database.");
            TableUtils.createTable(connectionSource, ShoppingCartItem.class);
        } catch (final SQLException e) {
            Log.e(getClass().getSimpleName(), "Can't create database.", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(final SQLiteDatabase database, final ConnectionSource connectionSource, int oldVersion, int newVersion) {
    }

    public Dao<ShoppingCartItem, Integer> getShoppingCartDAO() throws SQLException {
        if (Objects.isNull(shoppingCartDAO)) {
            shoppingCartDAO = getDao(ShoppingCartItem.class);
        }

        return shoppingCartDAO;
    }
}
