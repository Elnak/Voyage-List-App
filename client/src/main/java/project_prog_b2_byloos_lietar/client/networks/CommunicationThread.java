package project_prog_b2_byloos_lietar.client.networks;

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
        while (!isRunning){
            try {
                Object message = objectSocket.read();
                if(message instanceof ListVoyages) {
                    ListVoyages listVoyages = (ListVoyages) message;
                    listener.setListVoyages(listVoyages);
                    System.out.println("List updater");
                }
            }
            catch (Exception e){
                System.out.println("Aucun message reçu ???");
            }
        }
    }


    public void stopRunning() {
        this.isRunning = false;
    }

    public interface Listener {
        void setListVoyages(ListVoyages listVoyages) throws IOException;
    }
}
