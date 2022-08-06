package project_prog_b2_byloos_lietar.client.views;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import project_prog_b2_byloos_lietar.shared.models.Avions;
import project_prog_b2_byloos_lietar.shared.models.Hotels;
import project_prog_b2_byloos_lietar.shared.models.Ville;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DefineVoyages_view implements EtapeAvion.Listener,EtapeHotel.Listener{

    private Listener listener;
    @FXML
    private TextField name_box;
    @FXML
    private VBox list_etape;
    @FXML
    DatePicker datePicker;
    @FXML
    Button btn_ville_dep;
    @FXML
    Label label_resume_total;
    Ville ville_depart;
    VBox edited_etape;

    public void setListener(Listener listener) {this.listener = listener;}

    public void Setup_Top_Bar_Info(Object[] info){
        name_box.setText((String) info[0]);
        if(info[1] != null){
            ville_depart = (Ville) info[1];
            Update_Destination(ville_depart.toString());
        }
        datePicker.setValue((LocalDate) info[2]);
    }
    public void SetupBottom_Resume(String string){
        label_resume_total.setText(string);
    }
    private void Update_Destination(String villetostring){
        if(villetostring != null){
            btn_ville_dep.setText(villetostring);
        }else {
            btn_ville_dep.setText("Choisir");
        }
    }
    public void Setup_List(List<Object> list_event){
        if(list_event != null) {
            list_etape.getChildren().clear();
            for (Object var : list_event) {
                if(var instanceof Hotels){Setup_Hotel((Hotels) var);}
                else {Setup_Avion((Avions) var);}
            }
        }
    }

    @FXML
    private void Add_Hotel(){
        Hotels hotels = new Hotels();
        Setup_Hotel(hotels);
        Ask_Update_Define();
    }
    @FXML
    private void Add_Avion(){
        Avions avions = new Avions();
        Setup_Avion(avions);
        Ask_Update_Define();
    }
    public void AddVilleToEtape(Ville ville){
        if(edited_etape == null){
            ville_depart = ville;
            Update_Destination(ville.toString());
        }
        else {
            FXMLLoader loader = (FXMLLoader) edited_etape.getUserData();
            EtapeAvion etapeAvion = loader.getController();
            etapeAvion.Set_Ville(ville);
            edited_etape = null;
        }
        listener.Close_Choice();
    }
    private void Setup_Avion(Avions avion){
        FXMLLoader loader = new FXMLLoader(EtapeAvion.class.getResource("/project_prog_b2_byloos_lietar/client/etape_avion.fxml"));
        Add_Loader(loader);
        EtapeAvion etapeAvion = loader.getController();
        etapeAvion.setListener(this);
        etapeAvion.Setup_Info(avion);

    }
    private void Setup_Hotel(Hotels hotel){
        FXMLLoader loader = new FXMLLoader(EtapeHotel.class.getResource("/project_prog_b2_byloos_lietar/client/etape_hotel.fxml"));
        Add_Loader(loader);
        EtapeHotel etapeHotel = loader.getController();
        etapeHotel.setListener(this);
        etapeHotel.Setup_Info(hotel);
    }

    private void Add_Loader(FXMLLoader loader){
        try {
            VBox pane = loader.load();
            list_etape.getChildren().add(pane);
            pane.setUserData(loader);
        } catch (IOException e) {
        }
    }

    public List<Object> getList_etape(){
        List<Object> list_etape_return = new ArrayList<>();
        for (Node var: list_etape.getChildren()) {
            if(var.getId().equals("root_hotel")){
                FXMLLoader loader = (FXMLLoader) var.getUserData();
                EtapeHotel etapeHotel = loader.getController();
                list_etape_return.add(etapeHotel);
            }
            else{
                FXMLLoader loader = (FXMLLoader) var.getUserData();
                EtapeAvion etapeAvion = loader.getController();
                list_etape_return.add(etapeAvion);
            }
        }
        return list_etape_return;
    }

    @FXML
    public void Open_Choice() throws IOException {
        listener.launch_choice();
    }

    public Object[] getVoyageInfo(){
        Object[] envoie_info = new Object[5];
        envoie_info[0] = name_box.getText();
        envoie_info[1] = ville_depart;
        envoie_info[2] = datePicker.getValue();
        return envoie_info;
    }

    public void GetVBox_FromDestinationClicked(VBox vBox) throws IOException {
        edited_etape = vBox;
        listener.launch_choice();
    }

    public void Ask_Update_Define(){
        listener.Ask_Update();
    }

    public interface Listener {
        void launch_choice() throws IOException;
        void Close_Choice();
        void Ask_Update();
    }
}
