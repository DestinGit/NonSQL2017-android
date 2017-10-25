package db.fr.nonsql2017;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class DataFichierLire extends AppCompatActivity {
    private TextView textViewLecture;
    private final String FICHIER_TXT = "tintin.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_fichier_lire);

        textViewLecture = (TextView) findViewById(R.id.textViewLecture);

        textViewLecture.setText(lire(this, FICHIER_TXT));

    } // / onCreate

    /**
     *
     * @param contexte
     * @param psFichier
     * @return
     */
    private String lire(Context contexte, String psFichier) {

        File f;
        String lsChemin = contexte.getFilesDir().getAbsolutePath() + "/" + psFichier;
        FileInputStream fis;
        InputStreamReader isr;
        BufferedReader br;
        StringBuilder lsb = new StringBuilder();
        String lsLigne = "";

        try {
            f = new File(lsChemin);
            if (f.exists()) {
                fis = contexte.openFileInput(psFichier);
                isr = new InputStreamReader(fis);
                br = new BufferedReader(isr);

                while ((lsLigne = br.readLine()) != null) {
                    if(lsLigne.trim().length() > 0) {
                        lsb.append(lsLigne);
                        lsb.append("\n");
                    }
                }

                br.close();
                isr.close();
                fis.close();
            } else {
                lsb.append("Le fichier ");
                lsb.append(psFichier);
                lsb.append(" n'existe pas");
            }
        } catch (FileNotFoundException e) {
            lsb.append(e.getMessage());
        } catch (IOException e) {
            lsb.append(e.getMessage());
        }
        return lsb.toString();
    } // / lire

}
