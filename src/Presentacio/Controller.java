package Presentacio;



import Domini.Regles;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.*;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;

public class Controller implements Initializable {

    private static Domini.Controlador ctrlDom = new Domini.Controlador();


    private static Boolean information;
    private static Boolean tancaLlista;
    private static Boolean tancaRegles;
    private static Boolean tancaResultats;

    private static Boolean cancel;

    private static Boolean llistaSelect = false;
    private static Boolean freq= false;
    private static Boolean confi= false;
    private static Boolean percentatge = false;


    @FXML
    private Button LlistesPanel;
    @FXML
    private Button ReglesPanel;
    @FXML
    private Button ResultatsPanel;


    @FXML
    private AnchorPane Panel;
    @FXML
    private AnchorPane PanelInicial;
    @FXML
    private AnchorPane PanelElements;

    @FXML
    private AnchorPane LlistaWindow;
    @FXML
    private AnchorPane ReglesWindow;
    @FXML
    private AnchorPane ResultatsWindow;

    //PANEL LLISTA
    @FXML
    private ComboBox<String> ComboBoxList;
    @FXML
    private ComboBox<String> ComboBoxFitxers;

    @FXML
    private TextArea llistaConsultora;
    @FXML
    private TextField NameList;
    @FXML
    private Label LlistaExistent;
    @FXML
    private Label NoValid;
    ObservableList<ArrayList<String>> data; //files
    private int posFila;

    //PANEL REGLES
    @FXML
    private AnchorPane CrearReglesPanel;
    @FXML
    private AnchorPane EditarReglesPanel;
    @FXML
    private AnchorPane ConsultaReglesPanel;
    @FXML
    private AnchorPane EliminarReglesPanel;
    @FXML
    private ComboBox<String> llistaDeRegles;
    @FXML
    private ChoiceBox<String> llistaPerRegles;
    @FXML
    private TextField Frequencia;
    @FXML
    private TextField atributDiscretitzar;
    @FXML
    private TextField confiansa;
    @FXML
    private TextField tantxcent;
    @FXML
    private ChoiceBox<String> llistaDelete;



    //PANEL RESULTATS
    @FXML
    private ComboBox<String> ComboBoxResultats;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // list = new Llista();
        Panel.setVisible(true);
        PanelInicial.setVisible(true);
        //TextInfo.setVisible(false);
        LlistesPanel.setVisible(true);
        ReglesPanel.setVisible(true);
        ResultatsPanel.setVisible(true);
        PanelElements.setVisible(true);
        LlistaWindow.setVisible(false);
        ReglesWindow.setVisible(false);
        ResultatsWindow.setVisible(false);
        CrearReglesPanel.setVisible(false);
        EditarReglesPanel.setVisible(false);
        ConsultaReglesPanel.setVisible(false);
        EliminarReglesPanel.setVisible(false);
        //HelpButton.setVisible(true);
        //HomeButton.setVisible(true);
        information = false;
        tancaLlista = false;
        tancaRegles = false;
        tancaResultats = false;

        cancel = false;

        data = FXCollections.observableArrayList();
/*
        LlistaWindow.setVisible(false);
        ReglesWindow.setVisible(false);
        ResultatsWindow.setVisible(false);
       /* LlistaWindow.setVisible(false);
        LlistaWindow.setVisible(false);
        LlistaWindow.setVisible(false);
        LlistaWindow.setVisible(false);
        LlistaWindow.setVisible(false);
        LlistaWindow.setVisible(false);
*/


        //NameFile.addEventFilter(KeyEvent.ANY.validaText);
    }

    public void quit() {
        Platform.exit();
        System.exit(0);
    }

    public void setInfo() {
        //si li donem al boto imageView Info then

        information = !information;
        if (information) {
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Informacio sobre la interface");
            alerta.setContentText("Aquesta interface explica:" +
                    "una merda i " +
                    "merda per doquier");
            alerta.showAndWait();
            information = false;
        }

    }

    public void goHome() {
        if (tancaLlista || tancaRegles || tancaResultats) {
            Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
            alerta.setTitle("Confirmacio");
            alerta.setContentText("Aquesta accio no et desara els canvis anterior, desitja continuar?");
            //alerta.showAndWait();
            Optional<ButtonType> result = alerta.showAndWait();
            if (result.get() == ButtonType.OK) {
                Panel.setVisible(true);
                PanelInicial.setVisible(true);
                //TextInfo.setVisible(false);
                LlistesPanel.setVisible(true); //son buttons!!
                ReglesPanel.setVisible(true);
                ResultatsPanel.setVisible(true);
                PanelElements.setVisible(true);
                CrearReglesPanel.setVisible(false);
                EditarReglesPanel.setVisible(false);
                ConsultaReglesPanel.setVisible(false);
                EliminarReglesPanel.setVisible(false);
                information = false;

                LlistaWindow.setVisible(false);
                ReglesWindow.setVisible(false);
                ResultatsWindow.setVisible(false);
                tancaLlista = false;
                tancaRegles = false;
                tancaResultats = false;
                cancel = false;
            } else cancel = true;
        } else if (!cancel) {
            Panel.setVisible(true);
            PanelInicial.setVisible(true);
            // TextInfo.setVisible(false);
            LlistesPanel.setVisible(true); //son buttons!!
            ReglesPanel.setVisible(true);
            ResultatsPanel.setVisible(true);
            PanelElements.setVisible(true);
            CrearReglesPanel.setVisible(false);
            EditarReglesPanel.setVisible(false);
            ConsultaReglesPanel.setVisible(false);
            EliminarReglesPanel.setVisible(false);
            information = false;

            LlistaWindow.setVisible(false);
            ReglesWindow.setVisible(false);
            ResultatsWindow.setVisible(false);

            tancaLlista = false;
            tancaRegles = false;
            tancaResultats = false;
        }
    }

    public void ButtonLlistes() {
        if (tancaLlista || tancaRegles || tancaResultats) {
            Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
            alerta.setTitle("Confirmacio");
            alerta.setContentText("Aquesta accio no et desara els canvis anterior, desitja continuar?");
            //alerta.showAndWait();
            Optional<ButtonType> result = alerta.showAndWait();
            if (result.get() == ButtonType.OK) {
                //a més, guardem.
                tancaLlista = true;
                LlistaWindow.setVisible(true);
                ReglesWindow.setVisible(false);
                ResultatsWindow.setVisible(false);
                PanelElements.setVisible(false);
                LlistaExistent.setVisible(false);
                NoValid.setVisible(false);
                CrearReglesPanel.setVisible(false);
                EditarReglesPanel.setVisible(false);
                ConsultaReglesPanel.setVisible(false);
                EliminarReglesPanel.setVisible(false);
                cancel = false;
                information = false;
            } else cancel = true;
        } else if (!cancel) {
            tancaLlista = true;
            LlistaWindow.setVisible(true);
            ReglesWindow.setVisible(false);
            ResultatsWindow.setVisible(false);
            PanelElements.setVisible(false);
            LlistaExistent.setVisible(false);
            NoValid.setVisible(false);
            CrearReglesPanel.setVisible(false);
            EditarReglesPanel.setVisible(false);
            ConsultaReglesPanel.setVisible(false);
            EliminarReglesPanel.setVisible(false);
            information = false;
        }
    }

    public void ButtonRegles() {
        if (tancaLlista || tancaRegles || tancaResultats) {
            Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
            alerta.setTitle("Confirmacio");
            alerta.setContentText("Aquesta accio no et desara els canvis anterior, desitja continuar?");
            //alerta.showAndWait();
            Optional<ButtonType> result = alerta.showAndWait();
            if (result.get() == ButtonType.OK) {
                tancaRegles = true;
                LlistaWindow.setVisible(false);
                ReglesWindow.setVisible(true);
                ResultatsWindow.setVisible(false);
                PanelElements.setVisible(false);
                CrearReglesPanel.setVisible(false);
                EditarReglesPanel.setVisible(false);
                ConsultaReglesPanel.setVisible(false);
                EliminarReglesPanel.setVisible(false);
                cancel = false;
                information = false;
            } else cancel = true;
        } else if (!cancel) {

            tancaRegles = true;
            LlistaWindow.setVisible(false);
            ReglesWindow.setVisible(true);
            ResultatsWindow.setVisible(false);
            PanelElements.setVisible(false);
            CrearReglesPanel.setVisible(false);
            EditarReglesPanel.setVisible(false);
            ConsultaReglesPanel.setVisible(false);
            EliminarReglesPanel.setVisible(false);
            information = false;
        }
    }

    public void ButtonResultats() {

        if (tancaLlista || tancaRegles || tancaResultats) {
            Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
            alerta.setTitle("Confirmacio");
            alerta.setContentText("Aquesta accio no et desara els canvis anterior, desitja continuar?");
            //alerta.showAndWait();
            Optional<ButtonType> result = alerta.showAndWait();
            if (result.get() == ButtonType.OK) {
                tancaResultats = true;
                LlistaWindow.setVisible(false);
                ReglesWindow.setVisible(false);
                ResultatsWindow.setVisible(true);
                PanelElements.setVisible(false);
                CrearReglesPanel.setVisible(false);
                EditarReglesPanel.setVisible(false);
                ConsultaReglesPanel.setVisible(false);
                EliminarReglesPanel.setVisible(false);
                cancel = false;
                information = false;
            } else cancel = true;
        } else if (!cancel) {
            tancaResultats = false;
            LlistaWindow.setVisible(false);
            ReglesWindow.setVisible(false);
            ResultatsWindow.setVisible(true);
            PanelElements.setVisible(false);
            CrearReglesPanel.setVisible(false);
            EditarReglesPanel.setVisible(false);
            ConsultaReglesPanel.setVisible(false);
            EliminarReglesPanel.setVisible(false);
            information = false;
        }
    }


    //PANEL LLISTA:
    public void selectList() throws IOException {
        if (LlistaWindow.isVisible()) ComboBoxList.setItems(FXCollections.observableArrayList(ctrlDom.getLlistaNoms()));
        if (ConsultaReglesPanel.isVisible())
            llistaDeRegles.setItems(FXCollections.observableArrayList(ctrlDom.getLlistaNoms()));

        if (CrearReglesPanel.isVisible()) {
            llistaPerRegles.setItems(FXCollections.observableArrayList(ctrlDom.getLlistaNoms()));
            llistaSelect = true;
        }
        //ComboBoxList.setItems(list.getNomsLlista(i));
    }



    public void ButtonNoDiscret() throws IOException {
        //depenen de la llista que haguem seleccionat anteriorment, fem setText de la llista seleccionada.
        /*si no s'ha seleccionat la llista:
        public void ButtonNoDiscret() {
       */
        String s = ComboBoxList.getValue();
        if (s == null) {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Error");
            alerta.setContentText("Ei! Selecciona la llista a consultar");
            alerta.showAndWait();
        } else {
            llistaConsultora = new TextArea();
            System.out.print("hey");
            ctrlDom.carregaLlista(s);
            ArrayList<String> consulta = ctrlDom.mostraLlistaAtribs();
            System.out.print("hey");
            for (int i = 0; i < consulta.size(); i++) {
                llistaConsultora.setText(consulta.get(i));
            }
        }
    }

    public void ButtonDiscret() {
        //depenen de la llista que haguem seleccionat anteriorment, fem setText de la llista seleccionada.
        /*si no s'ha seleccionat la llista:
        public void ButtonNoDiscret() {
       */
        String s = ComboBoxList.getValue();
        if (s == null) {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Error");
            alerta.setContentText("Ei! Selecciona la llista a consultar");
            alerta.showAndWait();
        } else {


        }
    }

    public void saveButton() throws IOException {
        //if ContingutList.getText() no es valid per que no es compatible amb atribut{
        /*
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle("Error al guardar les dades");
        alerta.setContentText("Aquesta modificacio no es valida per a la llista corresponent");
        alerta.showAndWait();*/

            //s'ha de guardar nova modificació(modificar list)
            Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
            alerta.setTitle("Confirmació");
            alerta.setContentText("Estas segur d'efectuar els canvis?");
            Optional<ButtonType> result = alerta.showAndWait();
            if (result.get() == ButtonType.OK) {
                ctrlDom.guardaLlista();
                Alert alerta2 = new Alert(Alert.AlertType.INFORMATION);
                alerta2.setTitle("Canvis guardats");
                alerta2.setContentText("S'han guardat els canvis correctament.");
                alerta2.showAndWait();
                tancaLlista = false;
                tancaRegles = false;
                tancaResultats = false;
            }
    }


    public void EnterNewList() throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, CloneNotSupportedException {
        int a = ctrlDom.creaLlista(NameList.getText(), ComboBoxFitxers.getValue());

        if (a == -1) {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("ERROR");
            alerta.setContentText("Ei! La llista ja existeix, si us plau, assigna-li un altre nom");
            alerta.showAndWait();
        }

        if (a == 0) {
            llistaConsultora = new TextArea();
            System.out.print("hey");
            ArrayList<String> consulta = ctrlDom.mostraLlistaAtribs();
            System.out.print("he fet algo");
            for (int i = 0; i < consulta.size(); i++) {
                llistaConsultora.setText(consulta.get(i));
            }
        }
        if (a == 1) {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("ERROR");
            alerta.setContentText("Ei! El fitxer no ha estat seleccionat");
            alerta.showAndWait();
        }

        //llegeix el text introduit al costat del Enter (no se com)
        //COMPROVAR QUE LA LLISTA NO EXISTEIX
        /*
        boolean b = false;
        for (int i = 0; i < list.size() and !b; ++i) {
            String s = list.getNomsLlista(i);
            si s == llista introduida al text del costat {
                b = true;
                LlistaExistent.setVisible(true);
                LlistaExistent.setText("La llista amb aquest nom ja existeix, si us plau, prova un altre nom.");
            }
        }
        if (!b) {
            LlistaExistent.setVisible(true);
            //Fer que es guardi la llista insertada en el text
            LlistaExistent.setText("Llista creada correctament");
        else {
            NoValid.setVisible(true);
            NoValid.setText("Opció no valida, si us plau, torna-ho a intentar.");
        }

         */

    }

    public void selectFitxer() throws IOException{
        ComboBoxFitxers.setItems(FXCollections.observableArrayList(ctrlDom.getFitxerNoms()));
    }

    //PANEL REGLES:
    public void ButtonCrearRegles() {
        ReglesWindow.setVisible(false);
        CrearReglesPanel.setVisible(true);
    }

    public void ButtonEditarRegles() {
        ReglesWindow.setVisible(false);
        EditarReglesPanel.setVisible(true);

        //ResultatsGuardats.setVisible(false);
    }

    //Scanner?Vull llegir el q posa per pantalla
    public void calculnovesregles() {
        //llegeix el valor del TextField i fa el càlcul de les noves regles d'associacio
        //if ((ChoiceBox)event.e ==

        //si no hem seleccionat el textField i frequencia (else) {
        if (llistaSelect && freq && confi && percentatge) {
            //crear text i set;

            Frequencia.clear();
            confiansa.clear();
            tantxcent.clear();
            freq = false;
            confi = false;
            percentatge = false;

        }
        else {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Error");
            alerta.setContentText("Ei! Selecciona els parametres necessaris per calcular les noves regles d'associacio");
            alerta.showAndWait();
        }

    }

    public void assignafreq() {
        freq = true;
    }
    public void assignaconfi() {
        confi = true;
    }
    public void assignapercentatge(){
        percentatge = true;
    }

    public void ButtonConsultaRegles() {
        EditarReglesPanel.setVisible(false);
        ConsultaReglesPanel.setVisible(true);
        tancaLlista = false;
        tancaRegles = false;
        tancaResultats = false;

    }

    public void guardatCorrectamentRegles() {
        //llegeix el valor del TextField i fa el càlcul de les noves regles d'associacio

        //si tot es correcte fes aixo
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Confirmacio");
        alerta.setContentText("Estas segur de guardar els canvis seguents?");
        //alerta.showAndWait();
        Optional<ButtonType> result = alerta.showAndWait();
        if (result.get() == ButtonType.OK) {
            //A MES GUARDEM.
            Alert alerta2 = new Alert(Alert.AlertType.INFORMATION);
            alerta2.setTitle("Canvis guardats");
            alerta2.setContentText("S'han guardat els canvis correctament.");
            alerta2.showAndWait();
            tancaLlista = false;
            tancaRegles = false;
            tancaResultats = false;
        }
    }

    public void ButtonEliminaRegles() {
        EditarReglesPanel.setVisible(false);
        EliminarReglesPanel.setVisible(true);
    }
    /*
        public void ButtonGuardaResultats(ActionEvent event) {
            ResultatsGuardats.setText("Els resultats s'han guardat correctament!");
            ResultatsGuardats.setVisible(true);
        }
    */
    public void eliminaResultatsDesat() {
            ObservableList<ArrayList<String>> filaselect, allfiles;
           // allfiles = tableNovesRegles.getItems();
            //filaselect = tableNovesRegles.getSelectionModel().getSelectedItems();
            Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
            alerta.setTitle("Confirmacio");
            alerta.setContentText("Estas segur de borrar aquest cas?");
            Optional<ButtonType> result = alerta.showAndWait();
            if (result.get() == ButtonType.OK) {

                Alert alerta2 = new Alert(Alert.AlertType.INFORMATION);
                alerta2.setTitle("Exit");
                alerta2.setContentText("S'han eliminat les regles correctament");
                alerta2.showAndWait();
                tancaLlista = false;
                tancaRegles = false;
                tancaResultats = false;
            }
            //si tot es correcte fes aixo:
            //data.remove(posFila);
        /* Missatge confirmació:
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Confirmació");
        alerta.setContentText("Estas segur d'eliminar les regles seleccionades?");
        Optional<ButtonType> result = alerta.showAndWait();
        if (result.get() == ButtonType.OK) {
            Alert alerta2 = new Alert(Alert.AlertType.INFORMATION);
            alerta2.setTitle("Canvis guardats");
            alerta2.setContentText("S'han guardat els canvis correctament.");
            alerta2.showAndWait();
            tancaLlista = false;
            tancaRegles = false;
            tancaResultats = false;
        }


         */

    }


    public void mostraRegles() {

        String s = llistaDeRegles.getValue();
        if (s == null) {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Error");
            alerta.setContentText("Ei! Selecciona la llista a consultar");
            alerta.showAndWait();
        } else {
            //posar les dades

            /*if tot OK then {
            ObservableList<TreeTableColumn> hola = FXCollections.observableList(hola);
            TreeTableColumn tree = new TreeTableColumn();
            hola.add(tree);
            //ContingutList.getColumns
            //s'afegeix el contingut a les columnes

            ObservableList<ArrayList> content = .getCasos();
            hola.add(content)
            System.out.print(s);

             */
        }

        //mira la llista que vol consultar i a partir d'aquí surt la consulta de totes les sever regles
        tancaRegles = false;
    }

    public void afegeixRegles() {
        //mira la llista que ha escollit per afegir regles i escriu les regles que vol afegir

    }

    public void mostraResultats() {
        /*si no s'ha seleccionat la llista:
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle("Error");
        alerta.setContentText("Ei! Selecciona la llista a mostrar resultats");
        alerta.showAndWait();

        */
        //else

        tancaResultats=false;
    }








};
