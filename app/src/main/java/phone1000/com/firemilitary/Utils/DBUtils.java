package phone1000.com.firemilitary.Utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import phone1000.com.firemilitary.dao.DaoMaster;
import phone1000.com.firemilitary.dao.DaoSession;
import phone1000.com.firemilitary.dao.UserDao;

/**
 * Created by my on 2016/9/12.
 */
public class DBUtils {
    private static UserDao userDao;

    public static UserDao getDao(Context context) {
        if (userDao == null) {
            DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(context, "UserMessage");
            SQLiteDatabase readableDatabase = devOpenHelper.getReadableDatabase();
            DaoMaster daoMaster = new DaoMaster(readableDatabase);
            DaoSession daoSession = daoMaster.newSession();
            userDao = daoSession.getUserDao();
        }
        return userDao;
    }
}
