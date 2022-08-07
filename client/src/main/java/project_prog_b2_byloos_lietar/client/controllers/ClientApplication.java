package project_prog_b2_byloos_lietar.client.controllers;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import project_prog_b2_byloos_lietar.client.networks.CommunicationThread;
import project_prog_b2_byloos_lietar.client.views.*;
import project_prog_b2_byloos_lietar.shared.models.*;
import project_prog_b2_byloos_lietar.shared.networks.ObjectSocket;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class ClientApplication extends Application implements DefineVoyages_view.Listener, Voyages_view.Listener,Choice_view.Listener {

    Voyages_view voyages_view;
    DefineVoyages_view defineVoyages_view;
    Choice_view choice_view;
    Stage voyages_view_stage;
    Stage defineVoyages_view_stage;
    Stage choice_view_stage;
    ObjectSocket objectSocket;
    ListVoyages listVoyages = null;
    List<Ville> villeList = new ArrayList<>();
    boolean started = false;
    boolean define_iswaiting = false;
    int voyage_edited = -1;
    Voyages voyage_tempo_local;

    @Override
    public void start(Stage stage) throws IOException {
        while (!started){
            try {
                this.objectSocket = new ObjectSocket(new Socket("127.0.0.1", 1298));
                voyages_view = showFMXL(stage, Voyages_view.class.getResource("/project_prog_b2_byloos_lietar/client/client-app.fxml"));
                voyages_view.setListener(this);
                stage.setTitle("Voyages");
                voyages_view_stage = stage;
                voyages_view_stage.setMinHeight(200);
                voyages_view_stage.setMinWidth(550);
                started = true;
            } catch (Exception e) {
                System.out.println("Impossible de se connecter au serveur\nAvez-vous bien demarré le serveur ?");
            }
        }
        Setup_Connection();
        Setup_MapInfo();
    }
    private void Setup_MapInfo(){
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/client/src/main/resources/project_prog_b2_byloos_lietar/client/mapInfo.txt"));
            String line = reader.readLine();
            int compteur_ligne = 0;
            int compteur_info_ville = 0;
            String[] ville_info_tempo = new String[4];
            while (line != null) {
                line = reader.readLine();
                if(compteur_ligne%11 == 0 || compteur_ligne%11 == 1 || compteur_ligne%11 == 2 || compteur_ligne%11 == 3){
                    ville_info_tempo[compteur_info_ville] = line;
                    compteur_info_ville++;
                }
                if(compteur_info_ville == 4){
                    villeList.add(new Ville(ville_info_tempo));
                    ville_info_tempo = new String[4];
                    compteur_info_ville = 0;
                }
                compteur_ligne++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void Setup_Connection() throws IOException {
        objectSocket.write(new Serial());
        CommunicationThread thread = new CommunicationThread(objectSocket, new CommunicationThread.Listener() {
            @Override
            public void setListVoyages(ListVoyages listVoyages) throws IOException {
                Platform.runLater(() -> Update_List(listVoyages));
            }
            @Override
            public void updateEditedPosition(int position){
                Platform.runLater(() -> Change_Position(position));
            }
        });
        thread.setDaemon(true);
        thread.start();
    }
    private <T> T showFMXL(Stage stage, URL url) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(url);
        Scene scene = new Scene(fxmlLoader.load(),800,900);
        T controller = fxmlLoader.getController();
        stage.setScene(scene);
        stage.show();
        return controller;
    }
    public void Create_Define() throws IOException {
        objectSocket.write(new AddVoyages(new Voyages()));
        voyage_edited = listVoyages.getVoyagesList().size();
        Show_Define();
    }
    public void Delete_Define(int position) throws IOException {
        objectSocket.write(new DeleteVoyages(position));
    }
    public void Edit_Define(Voyages voyages) throws IOException {
        voyage_edited = listVoyages.getVoyagesList().indexOf(voyages);
        listVoyages.getVoyagesList().get(voyage_edited).setEdited(true);
        objectSocket.write(new EditVoyages((listVoyages.getVoyagesList().get(voyage_edited)),voyage_edited));
        Show_Define();
    }
    private void Show_Define() throws IOException {
        voyages_view_stage.close();
        defineVoyages_view_stage = new Stage();
        defineVoyages_view = showFMXL(defineVoyages_view_stage, DefineVoyages_view.class.getResource("/project_prog_b2_byloos_lietar/client/define-voyage.fxml"));
        defineVoyages_view.setListener(this);
        defineVoyages_view_stage.setTitle("Definir son voyage");
        defineVoyages_view_stage.setMinHeight(310);
        defineVoyages_view_stage.setMinWidth(600);
        define_iswaiting = true;
        defineVoyages_view_stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent e) {
                try {
                    if(voyage_edited >= 0){
                        Define_Save();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                voyages_view_stage.show();
            }
        });
    }
    public void Define_Save_Local(){
        voyage_tempo_local = Get_Voyage_Saved();
        defineVoyages_view.Setup_List(voyage_tempo_local.getListe_evenement_voyage());
        defineVoyages_view.Setup_Top_Bar_Info(voyage_tempo_local.getTopBarInfo());
        defineVoyages_view.SetupBottom_Resume(voyage_tempo_local.getBottomBar());
    }
    private Voyages Get_Voyage_Saved(){
        Object[] list_info_voyage = defineVoyages_view.getVoyageInfo();
        String Ville_Traversee = list_info_voyage[1] + ", ";
        list_info_voyage[4] = ControllerViewToClass(defineVoyages_view.getList_etape());
        list_info_voyage[3] =  GetVilleTrav( (List<Object>) list_info_voyage[4],Ville_Traversee);
        Voyages voyages_afteredit = new Voyages(list_info_voyage);
        return voyages_afteredit;
    }
    public void Define_Save() throws IOException {
        objectSocket.write(new EditVoyages(Get_Voyage_Saved(),voyage_edited));
        voyage_edited = -1;
    }
    public void Send_Ville_Selected(Ville ville){
        defineVoyages_view.AddVilleToEtape(ville);
    }
    private String GetVilleTrav(List<Object> list_etape_edited,String Ville_Traversee){
        for (Object var : list_etape_edited) {
            if(var instanceof Avions){
                Avions avions = (Avions) var;
                if(avions.getDestination() != null){
                    Ville_Traversee = Ville_Traversee + avions.getDestination().toString() + " ,";
                }
            }
        }
        return Ville_Traversee;
    }
    private List<Object> ControllerViewToClass(List<Object> list_view){
        List<Object> list_etape_edited = new ArrayList<>();

        for (Object var : list_view) {
            if(var instanceof EtapeHotel){
                Hotels hotels = new Hotels(((EtapeHotel) var).getInfo());
                list_etape_edited.add(hotels);
            }
            else{
                Avions avions = new Avions(((EtapeAvion) var).getInfo());
                list_etape_edited.add(avions);
            }
        }
        return list_etape_edited;
    }
    public void launch_choice() throws IOException {
        choice_view_stage = new Stage();
        choice_view_stage.initModality(Modality.APPLICATION_MODAL);
        choice_view = showFMXL(choice_view_stage, Choice_view.class.getResource("/project_prog_b2_byloos_lietar/client/choice_ville.fxml"));
        choice_view_stage.setWidth(400);
        choice_view_stage.setHeight(300);
        choice_view.Set_MapInfo_Data(villeList);
        choice_view.Setup();
        choice_view.setListener(this);
    }
    public void Close_Choice(){
        choice_view_stage.close();
        Ask_Update();
    }
    public void Update_List(ListVoyages listVoyages){
        this.listVoyages = listVoyages;
        voyages_view.Show_ListVoyages(listVoyages);
        if (define_iswaiting) {
            defineVoyages_view.Setup_List(listVoyages.getVoyagesList().get(voyage_edited).getListe_evenement_voyage());
            defineVoyages_view.Setup_Top_Bar_Info(listVoyages.getVoyagesList().get(voyage_edited).getTopBarInfo());
            defineVoyages_view.SetupBottom_Resume(listVoyages.getVoyagesList().get(voyage_edited).getBottomBar());
            define_iswaiting = false;
        }
    }
    public void Change_Position(int position){
        if(position < voyage_edited){
            voyage_edited = voyage_edited-1;
        }
    }
    public void Ask_Update(){
        Define_Save_Local();
    }
    public static void main(String[] args) {
        launch();
    }

}