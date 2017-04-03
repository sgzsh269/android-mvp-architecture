package com.sagarnileshshah.carouselmvp.data.local;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * The class defining the local SQlite DB managed by DBFlow lib.
 */
@Database(name = LocalDatabase.NAME, version = LocalDatabase.VERSION)
public class LocalDatabase {
    public static final String NAME = "CarouselDb";

    public static final int VERSION = 2;
}
