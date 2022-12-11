package Interfaces.Administrador;

import Interfaces.Main;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import modelos.Area;
import modelos.Elemento;

import javax.persistence.TypedQuery;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AreasController implements Initializable {

    private List<String> elementosTree = new ArrayList<>();

    private ObservableList<Elemento> elementosArea;

    @FXML
    private Button btn_back;

    @FXML
    private Button btn_addSubArea;

    @FXML
    private TreeView<String> cod_treeView;

    @FXML
    private Button btn_addArea;

    @FXML
    private Button btn_delArea;

    @FXML
    private Label txt_cantElementos;

    @FXML
    private Label txt_dniRec;

    @FXML
    private Label txt_idArea;

    @FXML
    private Label txt_nombreArea;

    @FXML
    private Button btn_eliminarSeleccion;

    @FXML
    private ComboBox<Integer> cbox_areasEliminar;

    @FXML
    void addAreaClicked(ActionEvent event) throws IOException {
        Main m = new Main();
        try {
            m.changeSceneOnParent("src/Interfaces/Administrador/AgregarAreaEspecializacion.fxml", "Nueva Area");
            this.elementosTree.clear();
            this.actualizarTreeView();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void addSubAreaButtonClicked(ActionEvent event) {
        Main m = new Main();
        try {
            Main.agregandoSubArea = true;
            m.changeSceneOnParent("src/Interfaces/Administrador/AgregarAreaEspecializacion.fxml", "Nueva Sub Area");
            Main.agregandoSubArea = false;
            this.elementosTree.clear();
            this.actualizarTreeView();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void backButtonClicked(ActionEvent event) {
        Main m = new Main();
        try {
            m.changeScene("src/Interfaces/Administrador/MenuPrincipalRoot.fxml", "Menu Gamma");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void eliminarAreaClicked(ActionEvent event) {
        this.cbox_areasEliminar.setVisible(true);
        this.btn_eliminarSeleccion.setVisible(true);
    }

    @FXML
    void delSeleccionClicked(ActionEvent event) {

        if (!Main.manager.getTransaction().isActive())
            Main.manager.getTransaction().begin();

        Area a =  Main.manager.find(Area.class,this.cbox_areasEliminar.getSelectionModel().getSelectedItem());

        if(!a.getComponentes().isEmpty())
            a.setComponente(null);

        Area ar = (Area)Main.manager.createQuery("SELECT a FROM Area a JOIN a.componentes c where c = :idArea").setParameter("idArea",a).getSingleResult();
        ar.eliminarComponente(a);
        System.out.println(ar.getId());
        Main.manager.remove(a);
        Main.manager.merge(ar);

        Main.manager.getTransaction().commit();

        this.cbox_areasEliminar.setVisible(false);
        this.btn_eliminarSeleccion.setVisible(false);
        this.elementosTree.clear();
        this.actualizarTreeView();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.actualizarTreeView();
        this.cod_treeView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if(newSelection!=null && !newSelection.getValue().equals("Areas")){
                Main.areaSeleccionada = (Area) Main.manager.createQuery("SELECT a FROM Area a join Elemento e on e.id = a.id where e.nombre like : nameArea").setParameter("nameArea",newSelection.getValue()).getSingleResult();
                this.actualizarElementosArea();
            }
            else{
                if(newSelection.getValue().equals("Areas")){
                    Main.areaSeleccionada = null;
                    this.actualizarElementosArea();
                }
            }
        });
    }

    public void actualizarTreeView(){
        List<Elemento> listaElems= (ArrayList<Elemento>)Main.manager.createQuery("FROM Elemento ").getResultList();
        System.out.println(listaElems);
        Area areaGral =(Area) Main.manager.createQuery("SELECT a FROM Area a join Elemento e on e.id = a.id where e.nombre like : nameArea").setParameter("nameArea","Area").getSingleResult();
        TreeItem<String> rootItem = new TreeItem<>(areaGral.getNombre());
        this.generarTreeItems(listaElems,rootItem);
        this.cod_treeView.setRoot(rootItem);

        List<Area> listaArea= (ArrayList<Area>)Main.manager.createQuery("FROM Area ").getResultList();
        ObservableList<Integer> idAreas;
        idAreas = FXCollections.observableArrayList();
        for(Area ar:listaArea) {
            idAreas.add(ar.getId());
        }
        this.cbox_areasEliminar.setItems(idAreas);
    }

    public void generarTreeItems(List<Elemento> listElem,TreeItem<String> rootItem){
        for(Elemento e :listElem){
            if(!this.elementosTree.contains(e.getNombre()) && !e.getNombre().equals("Area")) {
                Area areaCons = (Area) Main.manager.createQuery("SELECT a FROM Area a join Elemento e on e.id = a.id where e.nombre like : nameArea ").setParameter("nameArea",e.getNombre()).getSingleResult();
                if(areaCons != null){
                    System.out.println("Estoy en un area");
                    TreeItem<String> childrenElement = new TreeItem<>(areaCons.getNombre());
                    if(!areaCons.getComponentes().isEmpty()){
                        List<Elemento> childrenElements = areaCons.getComponentes();
                        generarTreeItems(childrenElements,childrenElement);
                        rootItem.getChildren().add(childrenElement);
                        this.elementosTree.add(areaCons.getNombre());
                    }
                    else{
                        rootItem.getChildren().add(childrenElement);
                        this.elementosTree.add(areaCons.getNombre());
                    }
                }
                else{
                    System.out.println("Estoy en un consultorio");
                }
            }
        }
    }

    public void actualizarElementosArea(){
        Area a = Main.areaSeleccionada;
        if(a!=null) {
            List<Elemento> listaElementos = a.getComponentes();
            this.txt_nombreArea.setText(a.getNombre());
            String idArea = Integer.toString(a.getId());
            this.txt_idArea.setText(idArea);

            String dniRec;
            if(a.getRecepcionista() != null)
                dniRec = Integer.toString(a.getRecepcionista().getDni());
            else
                dniRec = "Sin recepcionista";
            this.txt_dniRec.setText(dniRec);

            String cantComp = Integer.toString(a.getComponentes().size());
            this.txt_cantElementos.setText(cantComp);
            this.elementosArea = FXCollections.observableArrayList(listaElementos);
        }
        else{
            this.txt_idArea.setText("");
            this.txt_nombreArea.setText("");
            this.txt_dniRec.setText("");
            this.txt_cantElementos.setText("");
        }
    }
}


