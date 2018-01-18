package cl.anpetrus.smartprice.data;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

import cl.anpetrus.smartprice.models.User;


/**
 * Created by Petrus on 31-08-2017.
 */

public class UserReference {

    public UserReference() {
    }

    public void saveCurrentUser() {
        final CurrentUser currentUser = new CurrentUser();

        final String key = EmailProcessor.sanitizedEmail(currentUser.email());

        new Nodes().user(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user;
                if (dataSnapshot.exists()) {
                    user = dataSnapshot.getValue(User.class);
                } else {
                    user = new User();
                    user.setCreate(new Date().getTime());
                }
                user.setEmail(currentUser.email());
                user.setName(currentUser.getCurrentUser().getDisplayName());
                user.setUid(key);
                new Nodes().user(key).setValue(user);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}