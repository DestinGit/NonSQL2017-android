package db.fr.utilitaires;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by formation on 27/10/2017.
 */
public class JSONUtils {
    /**
     * @param is
     * @return
     */
    public static JSONArray jsonIS2JsonArray(InputStream is) throws JSONException {

        /*
        Renvoie un tableau d'objets JSON
        Le tableau est une "copie" du fichier
         */
        JSONArray tableauJSON = null;

        StringBuilder lsbContenu = new StringBuilder();

        try {
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String lsLigne = "";
            while ((lsLigne = br.readLine()) != null) {
                lsbContenu.append(lsLigne);
                lsbContenu.append("\n");
            }
            br.close();
            isr.close();
            is.close();

            // Object 2 JSONArray
            tableauJSON = new JSONArray(lsbContenu.toString());

        } catch (JSONException e) {
            JSONObject objetErreur = new JSONObject();
            objetErreur.put("Erreur JSON", objetErreur);
            tableauJSON.put(objetErreur);
        } catch (IOException e) {
            JSONObject objetErreur = new JSONObject();
            objetErreur.put("Erreur IO", objetErreur);
            tableauJSON.put(objetErreur);
        }
        return tableauJSON;
    } /// jsonIS2JsonArray

    public static void jsonToFile(Context contexte, String fileName, JSONObject objetJSON) {
//        String lsChemin = Contexte.getFilesDir().getAbsolutePath() + "/" + fileName;
        try {
            FileInputStream fis = contexte.openFileInput(fileName);
            JSONArray tableauJSON = jsonIS2JsonArray(fis);
            fis.close();

            tableauJSON.put(objetJSON);

            FileOutputStream fos = contexte.openFileOutput(fileName, Context.MODE_PRIVATE);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
//            BufferedWriter bw;
            osw.write(tableauJSON.toString());
            osw.close();
            fos.close();
//            bw = new BufferedWriter(osw);
//            bw.write(tableauJSON.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public static int deleteItemOnJsonFile(Context contexte, String fileName, String ISO2) {
        int nbDelete = 0;
        try {
            FileInputStream fis = contexte.openFileInput(fileName);
            JSONArray tableauJSON = jsonIS2JsonArray(fis);
            fis.close();

            JSONArray newTabJSON = new JSONArray();

            for (int k = 0; k < tableauJSON.length(); k++) {
                JSONObject jsonObjectPays = (JSONObject) tableauJSON.get(k);
                if (!jsonObjectPays.getString("ISO2").equals(ISO2)){
                    newTabJSON.put(jsonObjectPays);
                } else {
                    nbDelete++;
                }
            }

            FileOutputStream fos = contexte.openFileOutput(fileName, Context.MODE_PRIVATE);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            osw.write(newTabJSON.toString());
            osw.close();
            fos.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return nbDelete;
    }
}
