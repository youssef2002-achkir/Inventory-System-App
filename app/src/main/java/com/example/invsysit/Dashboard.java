package com.example.invsysit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.transform.sax.SAXResult;

public class Dashboard extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    public double montant_count;
    String gg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        List<String> categories = new ArrayList<String>();

        TextView  I_dossier=(TextView) findViewById(R.id.txt_bundle);

        Bundle bundle=getIntent().getExtras();
        String data=bundle.get("data").toString();

        EditText montant_initial = (EditText) findViewById(R.id.montant_initial);
        EditText montant_retenue = (EditText) findViewById(R.id.montant_retenue);
        EditText montant_finale = (EditText) findViewById(R.id.montant_final);
        EditText montant_commande = (EditText) findViewById(R.id.montant_cmd);

        EditText ao = (EditText) findViewById(R.id.AO);
        EditText da = (EditText) findViewById(R.id.DA);
        EditText cmd = (EditText) findViewById(R.id.num_cmd);

        EditText etape = (EditText) findViewById(R.id.etape_courante);
        EditText situation = (EditText) findViewById(R.id.situation);
        TextView sf = (TextView) findViewById(R.id.SF);


        EditText Date_limite = (EditText) findViewById(R.id.date_limite);
        EditText Date_promesse = (EditText) findViewById(R.id.date_promesse);
        EditText Fournisseur = (EditText) findViewById(R.id.fournisseur);

        da.setText(data);

        List<String> montants = new ArrayList<String>();




        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference usersRef = rootRef.child("appro");

        Query q = usersRef.orderByChild("DA").equalTo(data);
        q.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot ds : dataSnapshot.getChildren()) {
                        System.out.println(ds.getChildrenCount());
                        String montant_i = ds.child("Montant").getValue().toString();
                        String code_ao = ds.child("AO").getValue().toString();
                        String montant_r = ds.child("Montant retenue").getValue().toString();
                        String montant_f = ds.child("Montant finale").getValue().toString();
                        String sousF = ds.child("SF").getValue().toString();
                        String date_lami = ds.child("Date AMI").getValue().toString();
                        String date_lao = ds.child("Date AO").getValue().toString();
                        String phase = ds.child("Etape").getValue().toString();

                        String code_art = ds.child("Designation").getValue().toString();
                        String intitule = ds.child("Date").getValue().toString();

                        //montant_initial.setText(montant_i);
                        montant_retenue.setText(montant_r);
                        montant_finale.setText(montant_f);
                        I_dossier.setText(intitule);

                        ao.setText(code_ao);

                        etape.setText(phase);
                        sf.setText(sousF);


                        if (etape.getText().toString() == "AMI"){
                            Date_limite.setText(date_lami);
                        }
                        if (etape.getText().toString() == "AO"){
                            Date_limite.setText(date_lao);
                        }


                        String nn = montant_i.replace(",","");
                        montant_count += Double.parseDouble(nn);
                        montant_initial.setText(String.valueOf(montant_count));
                        categories.add(code_art);




                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        final Spinner articles = (Spinner) findViewById(R.id.ART_spinner);
        articles.setOnItemSelectedListener(this);
        categories.add("Choisir L'article desiree");
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        articles.setAdapter(dataAdapter);

        Button add = (Button) findViewById(R.id.add_btn);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Designation = String.valueOf(articles.getSelectedItem().toString());
                //cmd.setText(Designation);

                Query qq = usersRef.orderByChild("Designation").equalTo(Designation);
                qq.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                       for (DataSnapshot dss: dataSnapshot.getChildren()){
                           String da_test = dss.child("DA").getValue().toString();
                           String code_cmd = dss.child("Cmd").getValue().toString();
                           String mt_cmd = dss.child("Prix").getValue().toString();
                           String dt_promesse = dss.child("Date promesse").getValue().toString();
                           String fr_cmd = dss.child("Fournisseur").getValue().toString();
                           String situ = dss.child("Phase").getValue().toString();
                           ///

                           situation.setText(situ);
                           cmd.setText(code_cmd);
                           montant_commande.setText(mt_cmd);
                           Date_promesse.setText(dt_promesse);
                           Fournisseur.setText(fr_cmd);









                       }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }
        });

        Button edit = (Button) findViewById(R.id.edit_btn);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Dashboard.this,Modification.class);
                intent.putExtra("Dossier",data);
                startActivity(intent);
            }
        });

        Button stockage = (Button) findViewById(R.id.tostock_btn);
        stockage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent stock = new Intent(Dashboard.this,Stockage.class);
                startActivity(stock);
            }
        });
        Button backk = (Button) findViewById(R.id.back_btn_dash);
        backk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ch = new Intent(Dashboard.this, Choice_act.class);
                startActivity(ch);
            }
        });
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        ((TextView) parent.getChildAt(0)).setTextColor(Color.BLUE);
        ((TextView) parent.getChildAt(0)).setTextSize(8);



        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }

}