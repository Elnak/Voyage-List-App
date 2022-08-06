package project_prog_b2_byloos_lietar.client.views;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import project_prog_b2_byloos_lietar.shared.models.Hotels;

public class EtapeHotel {
    @FXML
    VBox root_hotel;
    @FXML
    Spinner NbNuitSP;
    @FXML
    Spinner PrixNuitSP;
    @FXML
    Text HotelResume;
    @FXML
    Button top_title;
    Listener listener;

    public void Delete(){
        VBox parent = (VBox) root_hotel.getParent();
        parent.getChildren().remove(root_hotel);
        OnModify();
    }

    public void Setup_Info(Hotels hotel){
        SpinnerValueFactory<Integer> spinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,100,hotel.getNbr_nuit());
        NbNuitSP.setValueFactory(spinnerValueFactory);
        spinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,2000, hotel.getPrix_nuit());
        PrixNuitSP.setValueFactory(spinnerValueFactory);
        PrixNuitSP.valueProperty().addListener((obs, oldValue, newValue) -> OnModify());
        NbNuitSP.valueProperty().addListener((obs, oldValue, newValue) -> OnModify());
        Update_Info();
    }

    public void Update_Info(){
        int nbnuit = (Integer) NbNuitSP.getValue();
        float prixnuit = (Integer) PrixNuitSP.getValue();
        String resume = nbnuit + " nuit(s) à l'hotel pour " + Hotels.Calcul_Resume((int)prixnuit,nbnuit) + " euros";
        HotelResume.setText(resume);
        top_title.setText(resume);
    }

    private int getNbNuitSP() {
        return (int) NbNuitSP.getValue();
    }

    private int getPrixNuitSP() {
        return (int)PrixNuitSP.getValue();
    }

    public int[] getInfo(){
        int tempo[] = new int[2];
        tempo[0] = getNbNuitSP();
        tempo[1]= getPrixNuitSP();
        return tempo;
    }

    public void setListener(EtapeHotel.Listener listener)
    {
        this.listener = listener;
    }

    public void OnModify(){
        listener.Ask_Update_Define();
    }
    public interface Listener{
        void Ask_Update_Define();
    }
}
