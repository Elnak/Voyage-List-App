package project_prog_b2_byloos_lietar.client.views;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import project_prog_b2_byloos_lietar.shared.models.Ville;

import java.util.List;

public class Choice_view {
    Listener listener;
    @FXML
    ListView<Ville> ListView_Ville;
    @FXML
    Button btn_selection;

    public void Setup(){
        Set_ListView_Double_Click();
    }

    public void setListener(Choice_view.Listener listener) {this.listener = listener;}

    private void Set_ListView_Double_Click(){
        ListView_Ville.setOnMouseClicked(click -> {
            if (click.getClickCount() == 2) {
                listener.Send_Ville_Selected(ListView_Ville.getSelectionModel().getSelectedItem());
            }
        });
    }

    @FXML
    private void Button_Choice_Clicked(){
        if(ListView_Ville.getSelectionModel().getSelectedItem() != null){
            listener.Send_Ville_Selected(ListView_Ville.getSelectionModel().getSelectedItem());
        }

    }
    public void Set_MapInfo_Data(List<Ville> villeList){
        ObservableList<Ville> observableList = FXCollections.observableList(villeList);
        ListView_Ville.setItems(observableList);
    }

    public interface Listener{ void Send_Ville_Selected(Ville ville);}
}
