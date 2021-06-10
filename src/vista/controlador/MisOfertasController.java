package vista.controlador;

import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;

public class MisOfertasController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    /*private List<Oferta> ofertas = new ArrayList();
    private int pagina = 1;
    private MiembroOfercompas miembroOfercompas;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        inicializarMiembro();
    }

    private void llenarOfertas(){
        ofertas.addAll(this.cargarOfertas(pagina, this.miembroOfercompas.getIdMiembro()));
    }
    public List<Oferta> cargarOfertas(int pagina, int idPublicador) {
        List<Oferta> ofertas = new ArrayList<>();
        Oferta oferta = new Oferta();
        Oferta[] ofertasArray = new modelo.Oferta[0];
        try {
            ofertasArray = oferta.obtenerOfertasPorPublicador(pagina, idPublicador);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        for (int i = 0; i < ofertasArray.length; i++) {
            ofertas.add(ofertasArray[i]);
            ofertasArray[i].toString();
        }
        return ofertas;
    }
    private void inicializarMiembro(){
        this.miembroOfercompas = (MiembroOfercompas) MainController.get("miembroLogeado");
    }*/
}
