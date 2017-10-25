package db.fr.nonsql2017;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

public class DataFichierEcrire extends AppCompatActivity {
    private TextView textViewMessageEcrire;
    private final String FICHIER_TXT = "tintin.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_fichier_ecrire);

        String lsContenu = "";
        lsContenu += "Tintin\n";
        lsContenu += "Milou\n";
        lsContenu += "Haddock\n";

        textViewMessageEcrire = (TextView) findViewById(R.id.textViewMessageEcrire);

        textViewMessageEcrire.setText(ecrire(this, FICHIER_TXT, lsContenu));

    } // / onCreate

    /**
     *
     * @param contexte
     * @param psFichier
     * @param psContenu
     * @return
     */
    private String ecrire(Context contexte, String psFichier, String psContenu) {

        String lsMessage = "";
        FileOutputStream fos;
        OutputStreamWriter osw;
        BufferedWriter bw;

        try {
            fos = contexte.openFileOutput(psFichier, Context.MODE_PRIVATE);
            osw = new OutputStreamWriter(fos);
            bw = new BufferedWriter(osw);
            bw.write(psContenu);

            bw.close();
            osw.close();
            fos.close();
            lsMessage = "Jusque là tout va bien !\nLe fichier " + FICHIER_TXT + " a été créé et rempli";
        } catch (Exception e) {
            lsMessage = e.getMessage();
        }

        return lsMessage;

    } // / ecrire
}
