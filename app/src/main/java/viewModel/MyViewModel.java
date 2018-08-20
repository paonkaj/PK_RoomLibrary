package viewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;

import com.mda.roomlibrary.PersonDAO;

import Model.Person;

public class MyViewModel extends ViewModel {

    public LiveData<PagedList<Person>> userList;

    public MyViewModel() {

    }


    public void init(PersonDAO personDAO,int limit) {
        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder()).setEnablePlaceholders(true)
                        .setPrefetchDistance(10)
                        .setPageSize(20).build();

        userList = (new LivePagedListBuilder(personDAO.personbyName(limit),pagedListConfig))
                .build();

    }
}
