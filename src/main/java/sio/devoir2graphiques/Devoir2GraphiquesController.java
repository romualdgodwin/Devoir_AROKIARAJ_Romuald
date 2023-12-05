package sio.devoir2graphiques;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import sio.devoir2graphiques.Tools.ConnexionBDD;
import sio.devoir2graphiques.Tools.GraphiqueController;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class Devoir2GraphiquesController implements Initializable
{


    ConnexionBDD maCnx;
    GraphiqueController graphiqueController;

    HashMap<String,Integer> datasGraphique;

    XYChart.Series<String,Number> serieGraph1;
    XYChart.Series<String,Number> serieGraph2;
    XYChart.Series<String,Number> serieGraph3;

    @FXML
    private Button btnGraph1;
    @FXML
    private Button btnGraph2;
    @FXML
    private Button btnGraph3;
    @FXML
    private AnchorPane apGraph1;
    @FXML
    private LineChart graph1;
    @FXML
    private Label lblTitre;
    @FXML
    private AnchorPane apGraph2;
    @FXML
    private AnchorPane apGraph3;
    @FXML
    private PieChart graph3;
    @FXML
    private BarChart graph2;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblTitre.setText("Devoir : Graphique n°1");
        apGraph1.toFront();

        try {
            maCnx = new ConnexionBDD();
            graphiqueController=  new GraphiqueController();
            datasGraphique = graphiqueController.GetDatasGraphique1();
            Graphique1();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }

    @FXML
    public void menuClicked(Event event) throws SQLException {
        if(event.getSource() == btnGraph1)
        {

            lblTitre.setText("Devoir : Graphique n°1");
            apGraph1.toFront();
            Graphique1();
        }

        else if(event.getSource() == btnGraph2)
        {
            lblTitre.setText("Devoir : Graphique n°2");
            apGraph2.toFront();
            graph2.getData().clear();

            serieGraph2 = new XYChart.Series<>();

            ObservableList<BarChart.Data> datasGraph2 = FXCollections.observableArrayList();
            HashMap<String, ArrayList<String>> datasGraphique2 =  graphiqueController.GetDatasGraphique2();
            for (String valeur : datasGraphique2.keySet()) {
                serieGraph2.setName(valeur);

                for (int i = 0; i < datasGraphique2.get(valeur).size(); i += 2) {
                    serieGraph2.getData().add(new XYChart.Data<>(datasGraphique2.get(valeur).get(i), Integer.parseInt(datasGraphique2.get(valeur).get(i + 1))));
                }

                graph2.getData().add(serieGraph2);
                serieGraph2 = new XYChart.Series<>();
            }
        }
        else
        {
            lblTitre.setText("Devoir : Graphique n°3");
            apGraph3.toFront();
            graph3.toFront();
            graph3.getData().clear();

            ObservableList<PieChart.Data> datasGraph3 = FXCollections.observableArrayList();
            HashMap<String,Integer> datasGraphique3 =  graphiqueController.GetDatasGraphique3();

            for (String valeur : datasGraphique3.keySet())
            {
                datasGraph3.add(new PieChart.Data(valeur,datasGraphique3.get(valeur) ));
            }
            graph3.setData(datasGraph3);

            for (PieChart.Data entry : graph3.getData()) {
                Tooltip t = new Tooltip(entry.getPieValue()+ " : "+entry.getName());
                t.setStyle("-fx-background-color:#3D9ADA");
                Tooltip.install(entry.getNode(), t);
            }


        }
    }


    public void Graphique1() {

        serieGraph1 = new XYChart.Series();
        serieGraph1.setName("Moyenne");
        graph1.getData().clear();

        for (String age : datasGraphique.keySet()) {
            serieGraph1.getData().add(new XYChart.Data(age, datasGraphique.get(age)));
        }

        graph1.getData().add(serieGraph1);


    }}