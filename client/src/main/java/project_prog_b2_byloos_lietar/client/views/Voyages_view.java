package project_prog_b2_byloos_lietar.client.views;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import project_prog_b2_byloos_lietar.shared.models.ListVoyages;
import project_prog_b2_byloos_lietar.shared.models.Voyages;

import java.io.IOException;

public class Voyages_view {
    @FXML
    VBox list_container;
    @FXML
    ScrollPane scrollpane_voyages;

    private Listener listener;

    @FXML
    protected void onCreationVoyage() throws IOException {
        listener.Create_Define();
    }

    public void Show_ListVoyages(ListVoyages listVoyages){
        int count = 0;
        list_container.getChildren().clear();
        for(Voyages var : listVoyages.getVoyagesList()){
            Voyages voyages = var;
            HBox hBox = new HBox();

            VBox VBox_Voyages_Gauche = new VBox();
            Label label_nom_voyage = new Label(voyages.getNom_voyages());
            label_nom_voyage.setFont(Font.font("Segoe UI",  20));
            Label label_date_ville_dep = new Label(voyages.getVille_depart() + ", "+ voyages.getDate_depart());

            HBox hBox1 = new HBox();
            Label label_cout = new Label(voyages.getCout() +" euros");
            Label label_duree = new Label(voyages.getTemps_voyages() + " heure(s)");
            Label label_km = new Label(voyages.getKm() + " km");
            Label label_villes = new Label("Villes traversée : " + voyages.getVille_traversee());

            VBox VBox_Voyages_Droite = new VBox();
            Button button_voir = new CustomButton("Voir",count,var);
            Button button_suppr = new CustomButton("Supprimer",count,var);
            button_voir.setPrefSize(80,20);
            button_suppr.setPrefSize(80,20);
            if (var.isEdited()){
                button_suppr.setDisable(true);
                button_voir.setDisable(true);
            }
            button_suppr.setOnMouseClicked(event -> {
                try {
                    Delete_Click(event);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            button_voir.setOnMouseClicked(event -> {
                try {
                    Edit_Click(event);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            VBox_Voyages_Gauche.getChildren().add(label_nom_voyage);
            VBox_Voyages_Gauche.getChildren().add(label_date_ville_dep);

            VBox_Voyages_Gauche.setPadding(new Insets(0,0,0,20));
            VBox_Voyages_Gauche.setSpacing(5);
            hBox1.getChildren().add(label_cout);
            hBox1.getChildren().add(label_duree);
            hBox1.getChildren().add(label_km);

            label_cout.setPadding(new Insets(0,100,0,0));
            label_duree.setPadding(new Insets(0,30,0,0));

            //hBox.setStyle("-fx-background-color: green;");
            VBox_Voyages_Droite.getChildren().add(button_voir);
            VBox_Voyages_Droite.getChildren().add(button_suppr);
            VBox_Voyages_Droite.setAlignment(Pos.TOP_RIGHT);
            //VBox_Voyages_Droite.setStyle("-fx-background-color: blue;");
            VBox_Voyages_Droite.setPadding(new Insets(10,20,10,20));
            VBox_Voyages_Droite.setSpacing(20);
            HBox.setHgrow(VBox_Voyages_Gauche, Priority.ALWAYS);
            hBox.setFillHeight(true);
            VBox_Voyages_Gauche.getChildren().add(hBox1);
            VBox_Voyages_Gauche.getChildren().add(label_villes);
            scrollpane_voyages.setFitToWidth(true);
            scrollpane_voyages.setFitToHeight(true);
            hBox.getChildren().addAll(VBox_Voyages_Gauche,VBox_Voyages_Droite);
            list_container.setSpacing(15);
            list_container.setPadding(new Insets(15,0,0,0));
            list_container.getChildren().add(hBox);
            count++;
        }
    }

    private void Delete_Click(MouseEvent mouseEvent) throws IOException {
        Object node = mouseEvent.getSource();
        CustomButton node_btn = (CustomButton) node;
        listener.Delete_Define(node_btn.getPosition());
    }

    private void Edit_Click(MouseEvent mouseEvent) throws IOException {
        Object node = mouseEvent.getSource();
        CustomButton node_btn = (CustomButton) node;
        listener.Edit_Define(node_btn.getVoyages());
    }

    public void setListener(Listener listener)
    {
        this.listener = listener;
    }

    public interface Listener {
        void Create_Define() throws IOException;
        void Delete_Define(int position) throws IOException;
        void Edit_Define(Voyages voyages) throws IOException;
    }
}