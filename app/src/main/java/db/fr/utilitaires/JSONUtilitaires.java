package db.fr.utilitaires;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import java.io.*;

// -------------------------
public class JSONUtilitaires {

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

} /// class
