package project_prog_b2_byloos_lietar.client.controllers;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import project_prog_b2_byloos_lietar.client.networks.CommunicationThread;
import project_prog_b2_byloos_lietar.client.views.DefineVoyages_view;
import project_prog_b2_byloos_lietar.client.views.Voyages_view;
import project_prog_b2_byloos_lietar.shared.models.*;
import project_prog_b2_byloos_lietar.shared.networks.ObjectSocket;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;


public class ClientApplication extends Application implements DefineVoyages_view.Listener, Voyages_view.Listener {

    Voyages_view voyages_view;
    DefineVoyages_view defineVoyages_view;
    Stage voyages_view_stage;
    Stage defineVoyages_view_stage;
    ObjectSocket objectSocket;
    ListVoyages listVoyages = null;
    boolean started = false;
    int voyage_edited = -1;

    @Override
    public void start(Stage stage) throws IOException {
        while (!started){
            try {
                this.objectSocket = new ObjectSocket(new Socket("127.0.0.1", 1298));
                voyages_view = showFMXL(stage, Voyages_view.class.getResource("/project_prog_b2_byloos_lietar/client/client-app.fxml"));
                voyages_view.setListener(this);
                stage.setTitle("Voyages");
                voyages_view_stage = stage;
                started = true;
            } catch (Exception e) {
                System.out.println("Impossible de se connecter au serveur\nAvez-vous bien demarré le serveur ?");
            }
        }
        Setup_Connection();
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
        defineVoyages_view.Add_Message();
        defineVoyages_view_stage.setTitle("Definir son voyage");
        defineVoyages_view_stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent e) {
                try {
                    if(voyage_edited >= 0){
                        Voyages voyage_temporaire = listVoyages.getVoyagesList().get(voyage_edited);
                        voyage_temporaire.setEdited(false);
                        voyage_temporaire.setNom_voyages(defineVoyages_view.getName_box());
                        objectSocket.write(new EditVoyages(voyage_temporaire,voyage_edited));
                        voyage_edited = -1;
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                voyages_view_stage.show();
            }
        });
    }


    public void Update_List(ListVoyages listVoyages){
        this.listVoyages = listVoyages;
        voyages_view.Show_ListVoyages(listVoyages);
    }

    public void Change_Position(int position){
        if(position < voyage_edited){
            voyage_edited = voyage_edited-1;
        }
    }

    public static void main(String[] args) {
        launch();
    }

}