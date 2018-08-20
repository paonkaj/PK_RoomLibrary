package com.mda.roomlibrary;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.arch.persistence.room.Room;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import Model.Person;
import viewModel.MyViewModel;

public class MainActivity extends AppCompatActivity {

    TextView txt_show;
    ListView lv;
    PersonDatabase appDatabase;
    PersonDAO personDAO;
    Button load_more;

    int offset = 1;
    int limit = 10;

    SwipeRefreshLayout mySwipeRefreshLayout;
    RecyclerView recyclerView;
    private final Executor executor = Executors.newFixedThreadPool(2);
    final DBValue dbValue = new DBValue();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mySwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.sw_refresh);
        recyclerView = findViewById(R.id.userList);

        appDatabase = DatabaseCreator.getPersonDatabase(this);




//        load_more = (Button)findViewById(R.id.btn_load_more);
//        appDatabase = Room.databaseBuilder(MainActivity.this, PersonDatabase.class, PersonDatabase.DATABASE_NAME).build();
        personDAO = appDatabase.PersonDatabase();

// option 1
        insertUser(appDatabase);

     //   option 2
        executor.execute(()->{
            appDatabase.PersonDatabase().insertPerson(dbValue.getRandomUserList());
            appDatabase.PersonDatabase().insertPerson(dbValue.getRandomUserList());

        });



//        load_more.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                LinearLayoutManager llm = new LinearLayoutManager(MainActivity.this);
//                llm.setOrientation(LinearLayoutManager.VERTICAL);
//                recyclerView.setLayoutManager(llm);
//
//
//                MyViewModel viewModel = ViewModelProviders.of(MainActivity.this).get(MyViewModel.class);
//                viewModel.init(personDAO,limit,offset);
//                final PersonAdapter userUserAdapter = new PersonAdapter();
////
//                viewModel.userList.observe(MainActivity.this, pagedList -> {
//                    userUserAdapter.setList(pagedList);
//                });
//
//                recyclerView.setAdapter(userUserAdapter);
//            //    userUserAdapter.notifyItemInserted(limit);
//
//                // Scroll to bottom on new messages
//                userUserAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
//                    @Override
//                    public void onItemRangeInserted(int positionStart, int itemCount) {
//                        llm.smoothScrollToPosition(recyclerView, null, userUserAdapter.getItemCount());
//                    }
//                });
//                offset = limit;
//                limit = limit + 10;
//
//
//            }
//        });

        mySwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {

                        doYourUpdate();

                    }
                }
        );

    }


    private void doYourUpdate() {
        // TODO implement a refresh
        mySwipeRefreshLayout.setRefreshing(false); // Disables the refresh icon

        LinearLayoutManager llm = new LinearLayoutManager(MainActivity.this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);


        MyViewModel viewModel = ViewModelProviders.of(MainActivity.this).get(MyViewModel.class);
        viewModel.init(personDAO, limit);
        final PersonAdapter userUserAdapter = new PersonAdapter();
//
        viewModel.userList.observe(MainActivity.this, pagedList -> {
            userUserAdapter.setList(pagedList);
        });

        recyclerView.setAdapter(userUserAdapter);
        //    userUserAdapter.notifyItemInserted(limit);

        // Scroll to bottom on new messages
        userUserAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                llm.smoothScrollToPosition(recyclerView, null, 0);
            }
        });
//        offset = limit;
        limit = limit + 10;


    }

    public void insertUser(final PersonDatabase appDatabase) {


        new AsyncTask<Void, Integer, Void>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                mySwipeRefreshLayout.setRefreshing(true);
            }

            @Override
            protected Void doInBackground(Void... voids) {
                appDatabase.PersonDatabase().insertPerson(dbValue.getRandomUserList());
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                doYourUpdate();
            }
        }.execute();
    }
}
