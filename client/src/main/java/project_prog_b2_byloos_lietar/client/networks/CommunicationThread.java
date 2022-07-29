package project_prog_b2_byloos_lietar.client.networks;

import project_prog_b2_byloos_lietar.shared.models.BlockVoyages;
import project_prog_b2_byloos_lietar.shared.models.ListVoyages;
import project_prog_b2_byloos_lietar.shared.models.Voyages;
import project_prog_b2_byloos_lietar.shared.networks.ObjectSocket;

import java.io.IOException;

public class CommunicationThread extends Thread{
    private ObjectSocket objectSocket;
    private boolean isRunning = false;
    Listener listener;

    public CommunicationThread(ObjectSocket objectSocket,Listener listener) {
        this.objectSocket = objectSocket;
        this.listener = listener;
    }

    @Override
    public void run() {
        while (true){
            try {
                Object message = objectSocket.read();
                if(message instanceof ListVoyages) {
                    ListVoyages listVoyages = (ListVoyages) message;
                    listener.setListVoyages(listVoyages);
                }
                if(Integer.class.isInstance(message)){
                    listener.updateEditedPosition((Integer) message);
                }
            }
            catch (Exception e){
                System.out.println("Aucun message reçu ???");
            }
        }
    }

    public interface Listener {
        void setListVoyages(ListVoyages listVoyages) throws IOException;
        void updateEditedPosition(int position);
    }
}
