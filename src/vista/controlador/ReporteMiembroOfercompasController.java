package vista.controlador;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import modelo.MiembroDetalleDenuncias;
import modelo.MiembroOfercompas;
import vista.MainController;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class ReporteMiembroOfercompasController implements Initializable {
    @FXML
    public PieChart graficoPastel;
    @FXML
    public Label labelNickname;
    @FXML
    public BarChart graficaDeBarras;
    @FXML
    public Label lbTotalPublicaciones;
    @FXML
    public Label lbPublicacionesDenunciadas;
    @FXML
    public Label lbPuntuacionTotal;
    @FXML
    public Label lbPublicacionesPositivas;
    @FXML
    public Label lbPublicacionesNegativas;

    private MiembroDetalleDenuncias miembroDetalleDenuncias;
    private int numeroOfertasDenunciadas;
    private int numeroOfertasSinDenuncias;
    private int numueroOfertasTotales;
    private int puntuacionTotal;
    private int publicacionesPositivas;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarMiembro();
        cargarGraficoPastel();
        cargarGraficoBarras();
        cargarInformacionLabels();
    }

    private void cargarMiembro() {
        this.miembroDetalleDenuncias = (MiembroDetalleDenuncias) MainController.get("miembroDetalleDenuncias");
        labelNickname.setText(miembroDetalleDenuncias.getNickname());
        try {
            HashMap reporte = this.miembroDetalleDenuncias.obtenerReporte();

            if ((int) reporte.get("status") == 200) {
                HashMap payLoad = (HashMap) reporte.get("json");

                Double numeroDenuncias = (Double) payLoad.get("numeroDenuncias");
                Double numeroOfertas = (Double) payLoad.get("numeroTotalPublicaciones");
                Double puntuacionTotalDb = (Double) payLoad.get("puntuacionTotal");
                Double totalpublicacionesPositivasDb = (Double) payLoad.get("publicacionesPositivas");


                this.numeroOfertasDenunciadas = numeroDenuncias.intValue();
                this.numueroOfertasTotales = numeroOfertas.intValue();
                this.puntuacionTotal = puntuacionTotalDb.intValue();
                this.numeroOfertasSinDenuncias = this.numueroOfertasTotales - this.numeroOfertasDenunciadas;
                this.publicacionesPositivas = totalpublicacionesPositivasDb.intValue();
                System.out.println(puntuacionTotal);

            }
            System.out.println(reporte.toString());
        } catch (IOException e) {
            MainController.alert(Alert.AlertType.ERROR, "Error de conexión", "Error al conectar con el " +
                    "servidor");
        }
    }

    private void cargarGraficoPastel() {
        ObservableList<PieChart.Data> lista = FXCollections.observableArrayList
                (new PieChart.Data("Total de publicaciones denunciadas", this.numeroOfertasDenunciadas),
                        new PieChart.Data("Total de publicaciones NO denunciadas", this.numeroOfertasSinDenuncias));
        graficoPastel.setData(lista);
        graficoPastel.setTitle("Reporte de publicaciones \ndenunciadas");
    }

    private void cargarGraficoBarras() {
        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Publicaciones con puntuacion positiva");
        series1.getData().add(new XYChart.Data("Publicaciones positivas", publicacionesPositivas));
        System.out.println("publicaciones \npositivas: " + publicacionesPositivas);

        XYChart.Series series2 = new XYChart.Series();
        series2.setName("Publicaciones con puntuacion negativa");
        int publicacionesNegativas = numueroOfertasTotales - publicacionesPositivas;
        series2.getData().add(new XYChart.Data("Publicaciones \nnegativas", publicacionesNegativas));
        System.out.println("publicaciones negativas: " + publicacionesNegativas);

        graficaDeBarras.getData().addAll(series1, series2);


    }

    private void cargarInformacionLabels() {
        int publicacionesNegativas = numueroOfertasTotales - publicacionesPositivas;
        this.lbTotalPublicaciones.setText(String.valueOf(this.numueroOfertasTotales));
        this.lbPublicacionesDenunciadas.setText(String.valueOf(numeroOfertasDenunciadas));
        this.lbPuntuacionTotal.setText(String.valueOf(this.puntuacionTotal));
        this.lbPublicacionesPositivas.setText(String.valueOf(publicacionesPositivas));
        this.lbPublicacionesNegativas.setText(String.valueOf(publicacionesNegativas));

    }
    private  void volver(){
        MainController.activate("MiembrosDenunciados", "Reporte de miembros denunciados", MainController.Sizes.MID);
    }

    public void clicAtras(ActionEvent actionEvent) {
        volver();
    }

    public void clicProhibirMiembro(ActionEvent actionEvent) {

        if (MainController.alert(Alert.AlertType.CONFIRMATION,
                "Por favor confirme", "¿Desea expulsar al a este miembroOfercompas?")) {
            try {
                HashMap respuesta = MiembroOfercompas.prohibir(miembroDetalleDenuncias.getIdMiembro());
                if (200 == (int) respuesta.get("status")) {
                    MainController.alert(Alert.AlertType.INFORMATION, "Miembro expulsado", "Miembro expulsado " +
                            "correctamente");
                    volver();

                } else {
                    MainController.alert(Alert.AlertType.ERROR, "Error conexion", "Error al conectar con el" +
                            "servidor");
                }
            } catch (IOException e) {
                MainController.alert(Alert.AlertType.ERROR, "Error conexion", "Error al conectar con el" +
                        "servidor");
            }

        }


    }
}
