package com.codingwithmitch.foodrecipes;

import android.app.Application;

public class MyApplication extends Application {
    public static final boolean IS_FREE = BuildConfig.IS_FREE; // true=免費版，false=付費版
}