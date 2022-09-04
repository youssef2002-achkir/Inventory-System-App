package com.example.invsysit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class Stockage extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stockage);

        TextView article_code = (TextView) findViewById(R.id.article_code);
        TextView article_desc = (TextView) findViewById(R.id.article_description);
        TextView article_desc_long = (TextView) findViewById(R.id.article_description_L);

        TextView sf_code = (TextView) findViewById(R.id.sf_code);
        TextView sf_desc = (TextView) findViewById(R.id.sf_designation);

        TextView K0340 = (TextView) findViewById(R.id.k0340);
        TextView K0422 = (TextView) findViewById(R.id.k0422);
        TextView K0423 = (TextView) findViewById(R.id.k0423);
        TextView K0424 = (TextView) findViewById(R.id.k0424);


        TextView destin = (TextView) findViewById(R.id.destination);
        TextView ligne_bud = (TextView) findViewById(R.id.ligne_budgetaire);
        TextView Qte_installed = (TextView) findViewById(R.id.Qte_installe);

        Button BACK = (Button) findViewById(R.id.back_btn);
        Button INFO = (Button) findViewById(R.id.sub_btn);
        Button K043_btn = (Button) findViewById(R.id.to_k043_btn);

        // Spinner element
        final Spinner article_names = (Spinner) findViewById(R.id.sp_articles);

        // Spinner click listener
        article_names.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> article_data = new ArrayList<String>();


        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference usersRef = rootRef.child("Stock");

        for (int i=0;i<=1151;i++){
            DatabaseReference items = usersRef.child(String.valueOf(i));
            DatabaseReference Desc = items.child("Description");
            Desc.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()){
                        String article = dataSnapshot.getValue().toString();
                        article_data.add(article);


                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        article_data.add("L'article...");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter_articles = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, article_data);

        // Drop down layout style - list view with radio button
        dataAdapter_articles.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        article_names.setAdapter(dataAdapter_articles);

        INFO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Description = String.valueOf(article_names.getSelectedItem().toString());
                article_desc.setText(Description);
                Query query = usersRef.orderByChild("Description").equalTo(Description);
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot data: dataSnapshot.getChildren()){
                            String AC = data.child("Code_Article").getValue().toString();
                            String SFC = data.child("SF").getValue().toString();
                            String SFD = data.child("Designation_SF").getValue().toString();
                            String k43 = data.child("k0340").getValue().toString();
                            String k22 = data.child("k0422").getValue().toString();
                            String k23 = data.child("k0423").getValue().toString();
                            String k24 = data.child("k0424").getValue().toString();
                            String DESC_LONG = data.child("Description_longue").getValue().toString();
                            String dest = data.child("Destinantion").getValue().toString();
                            String LB = data.child("Ligne_bidgetaire").getValue().toString();
                            String QI = data.child("Qte_installee").getValue().toString();

                            article_code.setText(AC);
                            sf_code.setText(SFC);
                            sf_desc.setText(SFD);
                            K0340.setText(k43);
                            K0422.setText(k22);
                            K0423.setText(k23);
                            K0424.setText(k24);
                            String splitdesc = DESC_LONG.replace(";", "\n");
                            article_desc_long.setText(splitdesc);
                            destin.setText(dest);
                            ligne_bud.setText(LB);
                            Qte_installed.setText(QI);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        K043_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent extra = new Intent(Stockage.this,Magasin0430.class);
                startActivity(extra);
            }
        });


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
        ((TextView) parent.getChildAt(0)).setTextSize(11);


        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }

}