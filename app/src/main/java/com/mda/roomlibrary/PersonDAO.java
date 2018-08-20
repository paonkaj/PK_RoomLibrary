package com.mda.roomlibrary;

import android.arch.lifecycle.LiveData;
import android.arch.paging.DataSource;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import Model.Person;

@Dao
public interface PersonDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertPerson(List<Person> person);

    @Query("SELECT * FROM person")
    public LiveData<List<Person>> getAllPersons();

    @Query("SELECT * FROM person LIMIT :limit")
    public abstract DataSource.Factory<Integer, Person> personbyName(int limit);

}
