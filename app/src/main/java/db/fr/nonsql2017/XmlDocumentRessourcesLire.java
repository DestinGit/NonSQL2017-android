package db.fr.nonsql2017;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;
import java.util.List;

public class XmlDocumentRessourcesLire extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private Spinner spinnerResultats;
    private TextView textViewSelection;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.xml_document_ressources_lire);

        initInterface();

        List<String> listeVilles = lireRessourceXMLDoc(R.xml.villes_doc, "nom_ville");

        // --- Le spinner avec les resultats
        ArrayAdapter<String> aaResultats = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listeVilles);
        aaResultats.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // --- Affectation de l'ArrayAdapter à la liste du spinner
        spinnerResultats.setAdapter(aaResultats);

    } // / onCreate()

    // -----------------------
    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
        textViewSelection.setText(parent.getItemAtPosition(position).toString());
    }

    // --------------------------
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        textViewSelection.setText("");
    }

    /**
     *
     * @param aiRessource
     * @param asBalise
     * @return
     */
    private List<String> lireRessourceXMLDoc(int aiRessource, String asBalise) {
        // --- On cree une liste
        List<String> liste = new ArrayList<String>();

        try {
            // --- On ouvre le document XML
            XmlPullParser xpp = getResources().getXml(aiRessource);

            // --- Tant que le document n'a pas ete analyse jusqu'a la fin
            while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {

                // --- Si c'est une balise ouvrante
                if (xpp.getEventType() == XmlPullParser.START_TAG) {
                    // --- Si la balise s'appelle [XXX]
                    if (xpp.getName().equals(asBalise)) {
                        // --- Pour aller sur le noeud #text
                        liste.add(xpp.nextText());
                    } // / IF balise "XXX" trouvee

                } // / IF start_tag

                // --- On passe a la balise suivante ou element suivant
                xpp.next();

            } // / WHILE parse

        } // / try

        catch (Exception e) {
            liste.add("Erreur" + e.getMessage());
        } // / catch

        return liste;

    } // / lireRessourceXML

    /**
     *
     */
    private void initInterface() {

        // --- On pointe vers le label
        textViewSelection = (TextView) findViewById(R.id.textViewSelection);

        // --- On pointe vers la liste deroulante
        spinnerResultats = (Spinner) findViewById(R.id.spinnerResultats);

        // --- Ajout d'un ecouteur a la liste deroulante
        spinnerResultats.setOnItemSelectedListener(this);

    } // / initInterface
}
