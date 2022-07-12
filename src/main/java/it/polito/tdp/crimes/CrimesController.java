/**
 * Sample Skeleton for 'Crimes.fxml' Controller Class
 */

package it.polito.tdp.crimes;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.crimes.model.Adiacenza;
import it.polito.tdp.crimes.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class CrimesController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxCategoria"
    private ComboBox<String> boxCategoria; // Value injected by FXMLLoader

    @FXML // fx:id="boxGiorno"
    private ComboBox<String> boxGiorno; // Value injected by FXMLLoader

    @FXML // fx:id="btnAnalisi"
    private Button btnAnalisi; // Value injected by FXMLLoader

    @FXML // fx:id="boxArco"
    private ComboBox<Adiacenza> boxArco; // Value injected by FXMLLoader

    @FXML // fx:id="btnPercorso"
    private Button btnPercorso; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	//txtResult.appendText("Crea grafo...\n");
    	String c=boxCategoria.getValue();
    	String d=boxGiorno.getValue();
    	if(c==null || d==null) {
    		txtResult.appendText("Seleziona tutti i parametri");
    		return;
    	}
    	model.creaGrafo(c, d);
    	txtResult.appendText("#Vertici: " +model.nVertici()+" \n");
    	txtResult.appendText("#Archi: " +model.nArchi()+" \n");
    	for(Adiacenza a:model.getArchiMinMediano()) {
    		txtResult.appendText("\n" + a.toString());
    	}
    	boxArco.getItems().addAll(model.getArchi());
    	
    }

    @FXML
    void doCalcolaPercorso(ActionEvent event) {
    	
    	//txtResult.appendText("Calcola percorso...\n");
    	txtResult.clear();
    	/*Adiacenza c=boxArco.getValue();
    	if(c==null) {
    		txtResult.appendText("seleziona arco");
    		return;
    	}*/
    	txtResult.appendText(model.cerca_cammino(boxArco.getValue()));
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxCategoria != null : "fx:id=\"boxCategoria\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert boxGiorno != null : "fx:id=\"boxGiorno\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert btnAnalisi != null : "fx:id=\"btnAnalisi\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert boxArco != null : "fx:id=\"boxArco\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert btnPercorso != null : "fx:id=\"btnPercorso\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Crimes.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	this.boxCategoria.getItems().addAll(model.getCategory());
    	this.boxGiorno.getItems().addAll(model.getDay());
    	
    }
}
