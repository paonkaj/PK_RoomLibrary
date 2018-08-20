package Model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.DiffCallback;
import android.support.v7.util.DiffUtil;

@Entity
public class Person {

    public static DiffCallback<Person> DIFF_CALLBACK = new DiffCallback<Person>() {
        @Override
        public boolean areItemsTheSame(@NonNull Person oldItem, @NonNull Person newItem) {
            return oldItem.userId == newItem.userId;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Person oldItem, @NonNull Person newItem) {
            return oldItem.equals(newItem);
        }
    };

    @ColumnInfo(name = "user_id")
    public long userId;
    @ColumnInfo(name = "first_name")
    public String firstName;
    @PrimaryKey
    @NonNull
    public String address;



    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        Person person = (Person) obj;

        return person.userId == this.userId && person.firstName == this.firstName;
    }

}
