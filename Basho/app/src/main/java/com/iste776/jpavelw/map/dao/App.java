package com.iste776.jpavelw.map.dao;

import android.app.Application;
import com.iste776.jpavelw.map.dao.DaoMaster.DevOpenHelper;

import org.greenrobot.greendao.database.Database;

/**
 * Created by jpavelw on 11/17/16.
 */

public class App extends Application {
    private final boolean ENCRYPTED = true;

    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();

        DevOpenHelper helper = new DevOpenHelper(this, this.ENCRYPTED ? "basho-db-encrypted" : "basho-db");
        Database db = ENCRYPTED ? helper.getEncryptedWritableDb("a9Xy10-_#J80") : helper.getWritableDb();
        this.daoSession = new DaoMaster(db).newSession();
    }

    public DaoSession getDaoSession(){ return this.daoSession; }
}
