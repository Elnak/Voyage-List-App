package project_prog_b2_byloos_lietar.client.views;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import project_prog_b2_byloos_lietar.shared.models.Avions;
import project_prog_b2_byloos_lietar.shared.models.Ville;

import java.io.IOException;

public class EtapeAvion {
    Listener listener;
    Ville ville;
    @FXML
    VBox root_avion;
    @FXML
    Spinner TempAttenteSP;
    @FXML
    RadioButton RadBtn1;
    @FXML
    RadioButton RadBtn2;
    @FXML
    Button DestinationCB;
    @FXML
    ChoiceBox PrixPKmCB;
    @FXML
    Button top_btn;
    @FXML
    Text VoyageResumeTXT;

    ObservableList<Double> prix_km_list = FXCollections.observableArrayList(0.025,0.0507,0.0759,0.1005,0.2);

    public void setListener(EtapeAvion.Listener listener)
    {
        this.listener = listener;
    }

    public void Delete(){
        VBox parent = (VBox) root_avion.getParent();
        parent.getChildren().remove(root_avion);
        OnModify();
    }

    public void Setup_Info(Avions avion){
        PrixPKmCB.setItems(prix_km_list);
        PrixPKmCB.setValue(avion.getPrix_km());
        SpinnerValueFactory<Integer> spinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,1440, avion.getAttente());
        TempAttenteSP.setValueFactory(spinnerValueFactory);
        TempAttenteSP.valueProperty().addListener((obs, oldValue, newValue) -> OnModify());
        PrixPKmCB.valueProperty().addListener((obs, oldValue, newValue) -> OnModify());
        if(avion.getVitesse() == 700){
            RadBtn1.setSelected(true);
        } else {
            RadBtn2.setSelected(true);
        }
        Set_Ville(avion.getDestination());
        Set_Resume(avion);
    }

    public void Set_Ville(Ville ville){
        this.ville = ville;
        if(ville == null){
            DestinationCB.setText("Choisir");
        }
        else{
            DestinationCB.setText(ville.toString());
        }
    }

    private void Set_Resume(Avions avions){
        if(avions.IsFullyCompleted()){
            VoyageResumeTXT.setText(avions.getNombre_km()+"km, " + avions.getTempString() + "heures, " + avions.getPrix() + " euros");
            top_btn.setText("Voyage en avion ("+avions.getDestination() + ", " + avions.getNombre_km()+"km, " + avions.getTempString() + "heures, " + avions.getPrix() + " euros)");
        }
        else{
            VoyageResumeTXT.setText(0 + " km, "+ 0 +" heures, " +0 + " euros");
            top_btn.setText("Voyage en avion");
        }
    }

    public void Send_VBox() throws IOException {
        listener.GetVBox_FromDestinationClicked(root_avion);
    }
    public Object[] getInfo(){
        Object[] tempo = new Object[4];
        tempo[0] = ville;
        tempo[1] = TempAttenteSP.getValue();

        if(RadBtn1.isSelected()){
            tempo[2] = 700;
        }else {
            tempo[2] = 900;
        }
        tempo[3] = PrixPKmCB.getSelectionModel().getSelectedItem();
        return tempo;
    }

    public void OnModify(){
        listener.Ask_Update_Define();
    }

    public interface Listener{
        void GetVBox_FromDestinationClicked(VBox vBox) throws IOException;
        void Open_Choice() throws IOException;
        void Ask_Update_Define();
    }

}
