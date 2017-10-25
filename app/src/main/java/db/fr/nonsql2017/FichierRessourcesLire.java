package db.fr.nonsql2017;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FichierRessourcesLire extends AppCompatActivity {

    private TextView textViewFichier;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fichier_ressources_lire);

        textViewFichier = (TextView)findViewById(R.id.textViewFichier);

        StringBuilder lsbContenu = new StringBuilder("");
        String lsLigne = "";
        InputStream is;
        InputStreamReader isr;
        BufferedReader br;

        try {
            // --- Avec un buffer
            is = getBaseContext().getResources().openRawResource(R.raw.repertoire);
            isr = new InputStreamReader(is);
            br = new BufferedReader(isr);
            while ((lsLigne = br.readLine()) != null) {
                lsbContenu.append(lsLigne);
                lsbContenu.append("\n");
            }
            br.close();
            isr.close();
            is.close();
        }
        catch(Exception e) {
            lsbContenu.append(e.getMessage());
        }
        textViewFichier.setText(lsbContenu.toString());
    } /// on Create
}
