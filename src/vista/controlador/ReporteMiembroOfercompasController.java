package vista.controlador;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import modelo.MiembroDetalleDenuncias;
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

    private MiembroDetalleDenuncias miembroDetalleDenuncias;
    private int numeroOfertasDenunciadas;
    private  int numeroOfertasSinDenuncias;
    private  int numueroOfertasTotales;
    private  int puntuacionTotal;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarMiembro();
        cargarGraficoPastel();
        cargarGraficoBarras();
    }

    private void cargarMiembro(){
        this.miembroDetalleDenuncias = (MiembroDetalleDenuncias) MainController.get("miembroDetalleDenuncias");
        labelNickname.setText(miembroDetalleDenuncias.getNickname());
        try {
            HashMap reporte = this.miembroDetalleDenuncias.obtenerReporte();

            if ((int)reporte.get("status") == 200) {
                HashMap payLoad = (HashMap) reporte.get("json");

                Double numeroDenuncias = (Double) payLoad.get("numeroDenuncias");
                Double numeroOfertas = (Double) payLoad.get("numeroTotalPublicaciones");
                Double puntuacionTotalDb = (Double) payLoad.get("puntuacionTotal");

                this.numeroOfertasDenunciadas = numeroDenuncias.intValue();
                this.numueroOfertasTotales = numeroOfertas.intValue();
                this.puntuacionTotal = puntuacionTotalDb.intValue();
                this.numeroOfertasSinDenuncias = this.numueroOfertasTotales - this.numeroOfertasDenunciadas;
                System.out.println(puntuacionTotal);

            }
            System.out.println(reporte.toString());
        } catch (IOException e) {
            MainController.alert(Alert.AlertType.ERROR, "Error de conexión", "Error al conectar con el " +
                    "servidor");
        }
    }
    private  void cargarGraficoPastel(){
        ObservableList<PieChart.Data> lista = FXCollections.observableArrayList
            (new PieChart.Data("Total de publicaciones denunciadas", this.numeroOfertasDenunciadas),
            new PieChart.Data("Total de publicaciones NO denunciadas", this.numeroOfertasSinDenuncias));
        graficoPastel.setData(lista);
        graficoPastel.setTitle("Reporte de publicaciones denunciadas");
    }
    private  void cargarGraficoBarras(){
        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Puntuacion Total");
        series1.getData().add(new XYChart.Data("PuntuacionTotal", -1));

        XYChart.Series series2 = new XYChart.Series();
        series1.setName("Puntuacion Total");
        series1.getData().add(new XYChart.Data("Total de publicaciones realizadas",numueroOfertasTotales));

        graficaDeBarras.getData().addAll(series1, series2);


    }





}
