package cl.anpetrus.smartprice.views.splash;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import cl.anpetrus.smartprice.data.Nodes;

public class BetaValidator {

    public static final int VERSION_BETA = 1;
    private BetaCallBack callBack;

    public BetaValidator(BetaCallBack callBack) {
        this.callBack = callBack;
    }

    public void init() {
        new Nodes().beta().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Integer beta = dataSnapshot.getValue(Integer.class);
                if (beta != null && beta == VERSION_BETA) {
                    callBack.versionBetaOK();
                } else {
                    callBack.versionBetaNOK();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callBack.versionBetaOK();
            }
        });
    }
}