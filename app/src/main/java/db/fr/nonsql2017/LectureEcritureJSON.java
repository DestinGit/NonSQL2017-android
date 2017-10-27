package db.fr.nonsql2017;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import db.fr.utilitaires.JSONUtils;

public class LectureEcritureJSON extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener{
    private EditText editTextISO2, editTextNomPays;
    private Button buttonAjouter, buttonSupprimer;
    private Spinner spinnerResultats;

    private JSONArray tableauJSON;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lecture_ecriture_json);

        initDOMVars();
        readFile();

//        editTextISO2.setText(null);
//        editTextNomPays.setText(null);
    }

    private void initDOMVars() {
        spinnerResultats = (Spinner)findViewById(R.id.spinnerResultats);

        editTextISO2 = (EditText)findViewById(R.id.editTextISO2);
        editTextNomPays = (EditText)findViewById(R.id.editTextNomPays);
        buttonAjouter = (Button)findViewById(R.id.buttonAjouter);
        buttonSupprimer = (Button)findViewById(R.id.buttonSupprimer);

        buttonAjouter.setOnClickListener(this);
        buttonSupprimer.setOnClickListener(this);
        // --- Ajout d'un ecouteur a la liste deroulante
        spinnerResultats.setOnItemSelectedListener(this);
    }

    private void readFile() {
        FileInputStream fis;
        try {
            fis = this.openFileInput("pays.json");
            tableauJSON = JSONUtils.jsonIS2JsonArray(fis);
            List<String> listePays = new ArrayList();
            for (int i = 0; i < tableauJSON.length(); i++) {
                JSONObject jsonObject = (JSONObject) tableauJSON.get(i);
                listePays.add(jsonObject.get("nom").toString());
            }

            // --- Le spinner avec les resultats
            ArrayAdapter<String> aaResultats = new ArrayAdapter(this, android.R.layout.simple_spinner_item, listePays);
            aaResultats.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // --- Affectation de l'ArrayAdapter à la liste du spinner
            spinnerResultats.setAdapter(aaResultats);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        if (v==buttonAjouter) {
            JSONObject jsonObjectPays = new JSONObject();
            try {
                jsonObjectPays.put("nom", editTextNomPays.getText().toString());
                jsonObjectPays.put("ISO2", editTextISO2.getText().toString());
                JSONUtils.jsonToFile(this, "pays.json", jsonObjectPays);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if (v==buttonSupprimer) {
            JSONUtils.deleteItemOnJsonFile(this, "pays.json", editTextISO2.getText().toString());
            editTextISO2.setText(null);
            editTextNomPays.setText(null);
        }
        readFile();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        try{
            JSONObject objetJSON = (JSONObject) tableauJSON.get(position);
            editTextISO2.setText(objetJSON.getString("ISO2"));
            editTextNomPays.setText(objetJSON.getString("nom"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
