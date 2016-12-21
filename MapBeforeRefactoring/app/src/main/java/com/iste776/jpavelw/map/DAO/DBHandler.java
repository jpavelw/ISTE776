package com.iste776.jpavelw.map.DAO;

import android.app.Application;

/**
 * Created by jpavelw on 11/17/16.
 */

public class DBHandler {

    private static CategoryDao categoryDao;
    private static PlaceDao placeDao;
    private Application app;

    private DBHandler(Application app){
        getCategoryDao(app);
        getPlaceDao(app);
        this.app = app;
    }

    public static CategoryDao getCategoryDao(Application app){
        if(categoryDao == null){
            synchronized (DBHandler.class){
                if(categoryDao == null){
                    DaoSession daoSession = ((App) app).getDaoSession();
                    categoryDao = daoSession.getCategoryDao();
                }
            }
        }
        return categoryDao;
    }

    public static PlaceDao getPlaceDao(Application app){
        if(placeDao == null){
            synchronized (DBHandler.class){
                if(placeDao == null){
                    DaoSession daoSession = ((App) app).getDaoSession();
                    placeDao = daoSession.getPlaceDao();
                }
            }
        }
        return placeDao;
    }
}
