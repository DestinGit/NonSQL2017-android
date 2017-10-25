package db.fr.nonsql2017;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class ResXmlToFiles extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner spinnerVilles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.res_xml_to_files);

        spinnerVilles = (Spinner) findViewById(R.id.spinnerVilles);

        // Lire le contenu de villes_data.xml
        // sauvegarder le contenu dans StringBuilder lsbContenu
//        StringBuilder lsContent = getDataFromXMLRessources(R.xml.villes_data);
//        StringBuilder lsContent = getXMLRessources(R.xml.villes_data);

        // Ouverture ou création du fichier du villes_data.xml dans data/data/files
        // Copie ou écriture de StringBuilder lsbContenu dans ce fichier
//        createAndSetDatasOnFile("villes_data.xml", lsContent);

//        String fileName = TransferAfileFromRawToXMLFile(R.raw.villes_data);
//        String[] data = readXMLDataFile("villes_data.xml");
//
//        for (int k = 0; k < data.length; k++) {
//            Log.e("data", data[k]);
//        }
        List<String> listeVilles = readXMLDataFile("villes_data.xml");

        remplirLeSpinner(listeVilles);
    }


    private void remplirLeSpinner(List<String> listeVilles) {


        ArrayAdapter<String> villes = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listeVilles);
        //villes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerVilles.setAdapter(villes);
    }

    /**
     * @param psFileName
     * @return
     */
    private List<String> readXMLDataFile(String psFileName) {
//        String[] lsContent;
        // --- On cree une liste
        List<String> listData = new ArrayList();


        try {
            // --- On ouvre le document XML
            InputStream is = this.openFileInput(psFileName);
            XmlPullParserFactory fabrique = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = fabrique.newPullParser();
            xpp.setInput(is, null);

            // --- Tant que le document n'a pas ete analyse jusqu'a la fin
            while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
                if (xpp.getEventType() == XmlPullParser.START_TAG && xpp.getName().equals("ville")) {
                    String lsValeurAttribut = xpp.getAttributeValue(null, "nom_ville");

                    Toast.makeText(this, lsValeurAttribut, Toast.LENGTH_SHORT).show();
                    Log.e("attribut", lsValeurAttribut);
//                        // Code qui ajoute la valeur quelque part
                    if (lsValeurAttribut != null) {
                        listData.add(lsValeurAttribut);
                    }
                }
                xpp.next();
            }
            is.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return listData;
    }

    /**
     * Transferer une Ressource Raw vers le fichier XML dans le device
     * et renvoi le nom du fichier du device
     *
     * @param i
     * @return String
     */
    private String TransferAfileFromRawToXMLFile(int i) {
        String newFilename = "villes_data.xml";

        try {
            InputStream isIN = getResources().openRawResource(i);
            int tailleIN = isIN.available();
            byte[] tOctets = new byte[tailleIN];
            isIN.read(tOctets, 0, tailleIN);

            OutputStream osOUT = openFileOutput(newFilename, this.MODE_PRIVATE);
            osOUT.write(tOctets);
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        return newFilename;
    }

    /**
     * @param i
     * @return
     */
    private StringBuilder getXMLRessources(int i) {
        StringBuilder lsbContenu = new StringBuilder("");
        try {
            // --- On ouvre le document XML
            XmlPullParser xpp = getResources().getXml(i);

            // --- Tant que le document n'a pas ete analyse jusqu'a la fin
            while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
                lsbContenu.append(xpp.getText());
                xpp.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lsbContenu;
    }

    /**
     * Lire le contenu de villes_data.xml
     * sauvegarder le contenu dans StringBuilder lsbContenu
     *
     * @param i
     * @return StringBuilder
     */
    private StringBuilder getDataFromXMLRessources(int i) {
        StringBuilder lsbContenu = new StringBuilder("");

        String lsLigne = "";
        InputStream is;
        InputStreamReader isr;
        BufferedReader br;

        try {
            // Avec un buffer
            is = getBaseContext().getResources().openRawResource(i);
            isr = new InputStreamReader(is);
            br = new BufferedReader(isr);
            while ((lsLigne = br.readLine()) != null) {
                lsbContenu.append(lsLigne);
                lsbContenu.append("\n");
            }
            br.close();
            isr.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lsbContenu;
    }

    /**
     * Ouverture ou création du fichier du villes_data.xml dans data/data/files
     * Copie ou écriture de StringBuilder lsbContenu dans ce fichier
     *
     * @param psFichier
     * @param lsbContent
     */
    private void createAndSetDatasOnFile(String psFichier, StringBuilder lsbContent) {
        FileOutputStream fos;
        OutputStreamWriter osw;
        BufferedWriter bw;

        try {
            fos = this.openFileOutput(psFichier, this.MODE_PRIVATE);
            osw = new OutputStreamWriter(fos);
            bw = new BufferedWriter(osw);

            bw.write(lsbContent.toString());

            bw.close();
            osw.close();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
