package Interfaces.Administrador;

import Interfaces.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import modelos.Area;
import modelos.Consultorio;
import modelos.Elemento;
import modelos.Persona;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AreasController implements Initializable {

    private String seleccion;

    private boolean eliminoConsultorio = false;

    private boolean eliminoArea = false;

    private List<Integer> elementosTree = new ArrayList<>();

    private ObservableList<Elemento> elementosArea;

    @FXML
    private Button btn_back;

    @FXML
    private Button btn_addSubArea;

    @FXML
    private TreeView<String> cod_treeView;

    @FXML
    private TableView<?> cod_detallesConsultorio;

    @FXML
    private TableColumn<?, ?> colEstudios;

    @FXML
    private TableColumn<?, ?> col_Coberturas;

    @FXML
    private Label labelArea;

    @FXML
    private Label labelCantComp;

    @FXML
    private Label labelNombreElemento;

    @FXML
    private Label labelConsultorio;

    @FXML
    private Label labelDniDoctor;

    @FXML
    private Label labelDniRecep;

    @FXML
    private Label labelGanancia;

    @FXML
    private Label labelIdArea;

    @FXML
    private Label labelIdConsultorio;

    @FXML
    private Label labelOcupado;

    @FXML
    private Label txt_Ganancia;

    @FXML
    private Label txt_Ocupado;

    @FXML
    private Label txt_cantElementos;

    @FXML
    private Label txt_dniPersona;

    @FXML
    private Label txt_idArea;

    @FXML
    private Label txt_idConsultorio;

    @FXML
    private Label txt_nombreElemento;

    @FXML
    private Button btn_eliminarSeleccion;

    @FXML
    private Button btn_addConsultorio;

    @FXML
    void addSubAreaButtonClicked(ActionEvent event) {
        Main m = new Main();
        if(Main.areaSeleccionada != null) {
            try {
                m.changeSceneOnParent("src/Interfaces/Administrador/AgregarAreaEspecializacion.fxml", "Nueva Sub Area");
                this.elementosTree.clear();
                Main.nuevoRecepcionista = null;
                this.actualizarTreeView();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else{
            m.sendAlert(Alert.AlertType.ERROR,"No selecciono Area","No se selecciono ningun area del listado.Intentelo de nuevo");
        }
    }

    @FXML
    void addConsultorioButtonClicked(ActionEvent event) {
        Main m = new Main();
        try {
            m.changeSceneOnParent("src/Interfaces/Administrador/AgregarConsultorio.fxml", "Nuevo Consultorio");
            this.elementosTree.clear();
            Main.nuevoDoctor = null;
            this.actualizarTreeView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void backButtonClicked(ActionEvent event) {
        Main m = new Main();
        try {
            if(Main.areaSeleccionada!=null) {
                this.adminEtiquetasArea();
                this.adminEtiquetasConsultorio();
            }
            m.changeScene("src/Interfaces/Administrador/MenuPrincipalRoot.fxml", "Menu Gamma");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void delSeleccionClicked(ActionEvent event) {
        if (!Main.manager.getTransaction().isActive()) {
            Main.manager.getTransaction().begin();
        }

        if(this.eliminoArea) {
            List<Area> areas = (List<Area>) Main.manager.createQuery("SELECT a FROM Area a JOIN a.componentes c where c = :idArea").setParameter("idArea", Main.areaSeleccionada).getResultList();
            if (!areas.isEmpty()) {
                Area ar = areas.get(0);
                List<Elemento> elems = Main.areaSeleccionada.obtenerElementos();
                System.out.println(elems);
                for(Elemento e :elems){
                    Main.areaSeleccionada.eliminarComponente(e);
                    Main.manager.remove(e);
                }
                Main.manager.merge(Main.areaSeleccionada);
                ar.eliminarComponente(Main.areaSeleccionada);
                Main.manager.remove(Main.areaSeleccionada);
                Main.manager.merge(ar);
                this.eliminoArea = false;
            }
        }
        else {
            if (this.eliminoConsultorio) {
                Area padreConsultorio = (Area) Main.manager.createQuery("SELECT a FROM Area a JOIN a.componentes c where c = :consultorio").setParameter("consultorio", Main.consultorioSeleccionado).getSingleResult();
                padreConsultorio.eliminarComponente(Main.consultorioSeleccionado);
                Main.manager.remove(Main.consultorioSeleccionado);
                Main.manager.merge(padreConsultorio);
                this.eliminoConsultorio = false;
            }
        }

        this.adminEtiquetasConsultorio();
        this.adminEtiquetasArea();
        this.elementosTree.clear();
        this.actualizarTreeView();
        Main.manager.getTransaction().commit();
       // this.btn_eliminarSeleccion.setVisible(false);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.actualizarTreeView();
        this.cod_treeView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if(newSelection!=null){
                List<Elemento> areas = (List<Elemento>) Main.manager.createQuery("SELECT a FROM Area a join Elemento e on e.id = a.id where e.nombre like : nameArea").setParameter("nameArea",newSelection.getValue()).getResultList();
                if(!areas.isEmpty()) {
                    this.seleccion = "Area";
                    Main.areaSeleccionada = (Area)areas.get(0);
                    this.eliminoArea = true;
                    this.eliminoConsultorio = false;
                    this.adminEtiquetasArea();
                }
                else{
                    this.seleccion = "Consultorio";
                    Main.consultorioSeleccionado = (Consultorio) Main.manager.createQuery("SELECT c FROM Consultorio c join Elemento e on e.id = c.id where e.nombre like : nameCons").setParameter("nameCons",newSelection.getValue()).getSingleResult();
                    this.eliminoConsultorio = true;
                    this.eliminoArea = false;
                    this.adminEtiquetasConsultorio();
                }
            }
        });
    }

    public void actualizarTreeView(){
        List<Elemento> listaElems= (ArrayList<Elemento>)Main.manager.createQuery("FROM Elemento ").getResultList();
        Area areaGral =(Area) Main.manager.createQuery("SELECT a FROM Area a join Elemento e on e.id = a.id where e.nombre like : nameArea").setParameter("nameArea","Area").getSingleResult();
        TreeItem<String> rootItem = new TreeItem<>(areaGral.getNombre());
        this.generarTreeItems(listaElems,rootItem);
        this.cod_treeView.setRoot(rootItem);
    }

    public void generarTreeItems(List<Elemento> listElem,TreeItem<String> rootItem){
        for(Elemento e :listElem){
            if(!this.elementosTree.contains(e.getId()) && e.getId()!=0) {
                //Me fijo si el elemento es un area
                List<Elemento> areas = (List<Elemento>) Main.manager.createQuery("SELECT a FROM Area a join Elemento e on e.id = a.id where e.nombre like : nameArea ").setParameter("nameArea",e.getNombre()).getResultList();
                //Me fijo si el elemento tiene asociacion con la raiz.
                List<Elemento> asociacionConRoot = (List<Elemento>) Main.manager.createQuery("SELECT a FROM Area a JOIN a.componentes c where c = :elemento").setParameter("elemento",e).getResultList();
                if(!areas.isEmpty()){ //Si no esta vacia, quiere decir que estoy parado sobre un area.
                    Area areaCons = (Area)areas.get(0);
                    if(!asociacionConRoot.isEmpty()) { //Si no esta vacia, quiere decir que esta area tiene asociacion con rootItem, es decir, es hija.
                        Area areaPadre = (Area)asociacionConRoot.get(0);
                        if(areaPadre.getNombre().equals(rootItem.getValue())) {
                            TreeItem<String> childrenElement = new TreeItem<>(areaCons.getNombre());
                            if (!areaCons.getComponentes().isEmpty()) {
                                List<Elemento> childrenElements = areaCons.getComponentes();
                                generarTreeItems(childrenElements, childrenElement); //Llamado recursivo para generar subAreas.
                                rootItem.getChildren().add(childrenElement);
                                this.elementosTree.add(areaCons.getId());
                            } else {
                                rootItem.getChildren().add(childrenElement);
                                this.elementosTree.add(areaCons.getId());
                            }
                        }
                    }
                }
                else{ //Es un consultorio si o si.
                    if(!asociacionConRoot.isEmpty()) {
                        Area a = (Area)asociacionConRoot.get(0);
                        if(a.getNombre().equals(rootItem.getValue())) {
                            for (Elemento elem : a.getComponentes()) {
                                if (elem.getId() == e.getId()) {
                                    Consultorio cons = Main.manager.find(Consultorio.class, e.getId());
                                    TreeItem<String> childrenElement = new TreeItem<>(cons.getNombre());
                                    rootItem.getChildren().add(childrenElement);
                                    this.elementosTree.add(cons.getId());
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void actualizarElementosArea(){
        if(this.seleccion.equals("Area")) {
            Area a = Main.areaSeleccionada;
            if (!a.getNombre().equals("Area")) {
                List<Elemento> listaElementos = a.getComponentes();
                this.txt_nombreElemento.setText(a.getNombre());
                String idArea = Integer.toString(a.getId());
                this.txt_idArea.setText(idArea);

                String dniRec;
                if (a.getRecepcionista() != null)
                    dniRec = Integer.toString(a.getRecepcionista().getDni());
                else
                    dniRec = "Sin recepcionista";
                this.txt_dniPersona.setText(dniRec);

                String cantComp = Integer.toString(a.getComponentes().size());
                this.txt_cantElementos.setText(cantComp);
                this.elementosArea = FXCollections.observableArrayList(listaElementos);
            } else { //Seteo los datos del area general.
                this.txt_idArea.setText(Integer.toString(0));
                this.txt_nombreElemento.setText("Area General");
                Persona admin = Main.manager.find(Persona.class, 1);
                if (admin != null)
                    this.txt_dniPersona.setText(Integer.toString(admin.getDni()));
                this.txt_cantElementos.setText(Integer.toString(this.elementosTree.size()));
            }
        }else {
            if (this.seleccion.equals("Consultorio")) {
                this.txt_idConsultorio.setText(Integer.toString(Main.consultorioSeleccionado.getId()));
                this.txt_nombreElemento.setText(Main.consultorioSeleccionado.getNombre());
                if (Main.consultorioSeleccionado.getDoctor() != null)
                    this.txt_dniPersona.setText(Integer.toString(Main.consultorioSeleccionado.getDoctor().getDni()));
                else
                    this.txt_dniPersona.setText("Sin doctor por el momento");
                if (Main.consultorioSeleccionado.isOcupado())
                    this.txt_Ocupado.setText("Si");
                else
                    this.txt_Ocupado.setText("No");
                this.txt_Ganancia.setText(Double.toString(Main.consultorioSeleccionado.getGananciaMensual()));
            }
        }
    }

    public void adminEtiquetasArea(){
        this.labelConsultorio.setVisible(false);
        this.labelNombreElemento.setVisible(false);
        this.txt_nombreElemento.setVisible(false);
        this.labelIdConsultorio.setVisible(false);
        this.txt_idConsultorio.setVisible(false);
        this.labelDniDoctor.setVisible(false);
        this.labelOcupado.setVisible(false);
        this.txt_Ocupado.setVisible(false);
        this.labelGanancia.setVisible(false);
        this.txt_Ganancia.setVisible(false);
        this.cod_detallesConsultorio.setVisible(false);

        this.actualizarElementosArea();

        this.btn_addSubArea.setVisible(true);
        this.btn_addConsultorio.setVisible(true);
        this.btn_eliminarSeleccion.setVisible(true);
        this.labelArea.setVisible(true);
        this.labelNombreElemento.setVisible(true);
        this.txt_nombreElemento.setVisible(true);
        this.labelIdArea.setVisible(true);
        this.txt_idArea.setVisible(true);
        this.labelDniRecep.setVisible(true);
        this.txt_dniPersona.setVisible(true);
        this.labelCantComp.setVisible(true);
        this.txt_cantElementos.setVisible(true);
    }

    public void adminEtiquetasConsultorio(){
        this.labelArea.setVisible(false);
        this.labelNombreElemento.setVisible(false);
        this.txt_nombreElemento.setVisible(false);
        this.labelIdArea.setVisible(false);
        this.txt_idArea.setVisible(false);
        this.labelDniRecep.setVisible(false);
        this.labelCantComp.setVisible(false);
        this.txt_cantElementos.setVisible(false);
        this.btn_addSubArea.setVisible(false);
        this.btn_addConsultorio.setVisible(false);

        this.actualizarElementosArea();

        this.btn_eliminarSeleccion.setVisible(true);
        this.labelConsultorio.setVisible(true);
        this.labelNombreElemento.setVisible(true);
        this.txt_nombreElemento.setVisible(true);
        this.labelIdConsultorio.setVisible(true);
        this.txt_idConsultorio.setVisible(true);
        this.labelDniDoctor.setVisible(true);
        this.txt_dniPersona.setVisible(true);
        this.labelOcupado.setVisible(true);
        this.txt_Ocupado.setVisible(true);
        this.labelGanancia.setVisible(true);
        this.txt_Ganancia.setVisible(true);
        this.cod_detallesConsultorio.setVisible(true);
    }
}


