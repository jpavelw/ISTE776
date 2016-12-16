package com.iste776.jpavelw.dao;

import android.app.Application;

import org.greenrobot.greendao.database.Database;
import com.iste776.jpavelw.dao.DaoMaster.DevOpenHelper;


/**
 * Created by jpavelw on 11/15/16.
 */

public class App extends Application {

    public static final boolean ENCRYPTED = false;

    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();

        DevOpenHelper helper = new DevOpenHelper(this, ENCRYPTED ? "notes-db-encrypted" : "notes-db");
        Database db = ENCRYPTED ? helper.getEncryptedWritableDb("super-secret") : helper.getWritableDb();
        this.daoSession = new DaoMaster(db).newSession();
    }

    public DaoSession getDaoSession(){
        return this.daoSession;
    }
}
