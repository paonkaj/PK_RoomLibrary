package com.mda.roomlibrary;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.os.Environment;
import java.io.File;
import Model.Person;

@Database(entities = {Person.class},version = 1)
public abstract class PersonDatabase extends RoomDatabase {
    public static final String DATABASE_NAME = "PersonDb";

//    public static final String path = Environment.getExternalStorageDirectory()
//            + File.separator + "/RoomDataBase/" + File.separator
//            + DATABASE_NAME;

    public  abstract PersonDAO PersonDatabase();
}
