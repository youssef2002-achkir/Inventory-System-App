package com.example.invsysit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Modification extends AppCompatActivity {

    private DatabaseReference myDB;
    private TextView data;
    String KEY;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modification);

        TextView  DA_DOSSIER=(TextView) findViewById(R.id.edit_main);
        Bundle bundle=getIntent().getExtras();
        String dossier_da=bundle.get("Dossier").toString();
        DA_DOSSIER.setText(dossier_da);

        EditText ETAPE = (EditText) findViewById(R.id.edit_etape);
        EditText MONTANT_RETENUE = (EditText) findViewById(R.id.edit_mt_retenue);
        EditText DATE_LIMIT_AMI = (EditText) findViewById(R.id.edit_dlami);
        EditText DATE_LIMIT_AO = (EditText) findViewById(R.id.edit_dlao);
        EditText MONTANT_FINAL = (EditText) findViewById(R.id.edit_mt_finale);
        EditText SITUATION = (EditText) findViewById(R.id.edit_situation);
        Button PUSH = (Button) findViewById(R.id.update_btn);

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference usersRef = rootRef.child("appro");

        Query q = usersRef.orderByChild("DA").equalTo(dossier_da);

        PUSH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                q.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot dds: dataSnapshot.getChildren()){
                            KEY = dds.getKey();
                            if (ETAPE.getText().toString() != "") {
                                usersRef.child(KEY).child("Etape").setValue(ETAPE.getText().toString());
                            }

                            if (MONTANT_RETENUE.getText().toString() != "") {
                                usersRef.child(KEY).child("Montant retenue").setValue(MONTANT_RETENUE.getText().toString());
                            }

                            if (DATE_LIMIT_AMI.getText().toString() != "") {
                                usersRef.child(KEY).child("Date AMI").setValue(DATE_LIMIT_AMI.getText().toString());
                            }

                            if (DATE_LIMIT_AO.getText().toString() != "") {
                                usersRef.child(KEY).child("Date AO").setValue(DATE_LIMIT_AO.getText().toString());
                            }

                            if (MONTANT_FINAL.getText().toString() != "") {
                                usersRef.child(KEY).child("Montant finale").setValue(MONTANT_FINAL.getText().toString());
                            }

                            if (SITUATION.getText().toString() != "") {
                                usersRef.child(KEY).child("Phase").setValue(SITUATION.getText().toString());
                            }
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


                ETAPE.setText("");
                MONTANT_FINAL.setText("");
                MONTANT_RETENUE.setText("");
                DATE_LIMIT_AMI.setText("");
                DATE_LIMIT_AO.setText("");
                SITUATION.setText("");
            }
        });





    }


}
