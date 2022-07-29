package project_prog_b2_byloos_lietar.client.views;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class DefineVoyages_view {

    private Listener listener;
    @FXML
    private TextField name_box;
    @FXML
    private VBox list_etape;

    public void setListener(Listener listener)
    {
        this.listener = listener;
    }

    public void Add_Message(){
        FXMLLoader loader = new FXMLLoader(EtapeAvion.class.getResource("/project_prog_b2_byloos_lietar/client/etape_avion.fxml"));
        try {
            AnchorPane pane = loader.load();
            list_etape.getChildren().add(pane);
        } catch (IOException e) {
        }
    }
    public interface Listener {
    }

    public String getName_box() {
        return name_box.getText();
    }
}
