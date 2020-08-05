package me.r1411.finances;

import android.app.Application;
import android.content.Context;

import androidx.room.Room;

import java.lang.ref.WeakReference;

import me.r1411.finances.utils.FinancesDatabase;

public class FinancesApp extends Application {

    private static FinancesApp instance;
    private static WeakReference<Context> mContext;
    private FinancesDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        mContext = new WeakReference<Context>(this);
        database = Room.databaseBuilder(this, FinancesDatabase.class, "database").build();
    }

    public static FinancesApp getInstance() {
        return instance;
    }

    public static Context getContext() {
        return mContext.get();
    }

    public FinancesDatabase getDatabase() {
        return database;
    }
}
