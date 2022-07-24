package project_prog_b2_byloos_lietar.client.views;

import javafx.scene.control.Button;

public class CustomButton extends Button {
    int position;
    public CustomButton(String name, int position){
        super(name);
        this.position = position;
    }

    public int getPosition() {
        return position;
    }
}
