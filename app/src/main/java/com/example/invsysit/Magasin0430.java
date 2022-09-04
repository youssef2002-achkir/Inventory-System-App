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

public class Magasin0430 extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magasin0430);

        TextView ART_CODE = (TextView) findViewById(R.id.article_code_k043);
        TextView ART_DESC = (TextView) findViewById(R.id.article_designation_k043);
        TextView ART_PU = (TextView) findViewById(R.id.article_pu_k043);
        TextView ART_DESC_LONG = (TextView) findViewById(R.id.article_designation_k043_L);

        TextView QT_STOCK_K043 = (TextView) findViewById(R.id.Qte_S_k043);
        TextView QT_STOCK_K0320 = (TextView) findViewById(R.id.Qte_S_k0320);

        TextView V_STOCK_K043 = (TextView) findViewById(R.id.valeur_S_k043);
        TextView V_STOCK_K0320 = (TextView) findViewById(R.id.valeur_S_k0320);

        TextView T_STOCK = (TextView) findViewById(R.id.Qte_S_total);
        TextView V_STOCK_T = (TextView) findViewById(R.id.valeur_S_total);


        Button GO_BACK = (Button) findViewById(R.id.back_btn_k043);
        Button GETINFO = (Button) findViewById(R.id.sub_btn_k043);
        // Spinner element
        final Spinner article_names_k043 = (Spinner) findViewById(R.id.sp_articles_k043);

        // Spinner click listener
        article_names_k043.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> article_data_k043 = new ArrayList<String>();
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference usersRef = rootRef.child("K043");

        for (int i=0;i<=129;i++){
            DatabaseReference items = usersRef.child(String.valueOf(i));
            DatabaseReference Desc = items.child("Designation");
            Desc.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()){
                        String article = dataSnapshot.getValue().toString();
                        article_data_k043.add(article);


                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        article_data_k043.add("L'article...");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter_articles_k043 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, article_data_k043);

        // Drop down layout style - list view with radio button
        dataAdapter_articles_k043.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        article_names_k043.setAdapter(dataAdapter_articles_k043);

        GETINFO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String information = String.valueOf(article_names_k043.getSelectedItem().toString());

                ART_DESC.setText(information);
                Query query = usersRef.orderByChild("Designation").equalTo(information);

                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot dataa: dataSnapshot.getChildren()){
                            String code = dataa.child("Code_article").getValue().toString();
                            String pu = dataa.child("PU").getValue().toString();
                            String desc_long = dataa.child("Designation_longue").getValue().toString();
                            String qte_stock_043 = dataa.child("Qte_stock K0340").getValue().toString();
                            String qte_stock_0320 = dataa.child("Stock_K0320").getValue().toString();
                            String valeur_stock_043 = dataa.child("Val_Stock_K0340").getValue().toString();
                            String valeur_stock_0320 = dataa.child("Val_Stock_K0320").getValue().toString();
                            String valeur_stock_total = dataa.child("Val_Stock_Totale").getValue().toString();
                            String desc_long_mod = desc_long.replace(";", "\n\n");
                            int tot = Integer.parseInt(qte_stock_043) + Integer.parseInt(qte_stock_0320);

                            ART_CODE.setText(code);
                            ART_PU.setText(pu);
                            ART_DESC_LONG.setText(desc_long_mod);

                            QT_STOCK_K043.setText(qte_stock_043);
                            QT_STOCK_K0320.setText(qte_stock_0320);

                            V_STOCK_K043.setText(valeur_stock_043);
                            V_STOCK_K0320.setText(valeur_stock_0320);

                            V_STOCK_T.setText(valeur_stock_total);
                            T_STOCK.setText(String.valueOf(tot));
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        GO_BACK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent stockk = new Intent(Magasin0430.this, Stockage.class);
                startActivity(stockk);
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