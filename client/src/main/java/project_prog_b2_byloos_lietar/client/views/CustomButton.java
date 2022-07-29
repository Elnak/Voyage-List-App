package project_prog_b2_byloos_lietar.client.views;

import javafx.scene.control.Button;
import project_prog_b2_byloos_lietar.shared.models.Voyages;

public class CustomButton extends Button {
    int position;
    Voyages voyages;

    public CustomButton(String name, int position,Voyages voyages){
        super(name);
        this.position = position;
        this.voyages = voyages;
    }

    public int getPosition() {
        return position;
    }

    public Voyages getVoyages() {
        return voyages;
    }
}
