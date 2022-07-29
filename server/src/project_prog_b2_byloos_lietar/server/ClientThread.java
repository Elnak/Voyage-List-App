package project_prog_b2_byloos_lietar.server;

import project_prog_b2_byloos_lietar.shared.models.*;
import project_prog_b2_byloos_lietar.shared.networks.ObjectSocket;

import java.io.IOException;
import java.net.SocketException;

public class ClientThread extends Thread {

    private final ObjectSocket objectSocket;
    private Server server;

    public ClientThread(Server server, ObjectSocket objectSocket) {
        this.objectSocket = objectSocket;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            while (true) {
                try{
                Object message = objectSocket.read();
                if (message instanceof AddVoyages) {
                    server.Add_Voyages(((AddVoyages) message).getVoyages());
                    server.Block_Interraction();
                } else if (message instanceof DeleteVoyages) {
                    server.Delete_Voyages(((DeleteVoyages) message).getPosition());
                } else if (message instanceof EditVoyages) {
                   server.Edit_Voyages(((EditVoyages) message).getVoyages(),((EditVoyages) message).getPosition());
                } else {
                    objectSocket.write(server.getListVoyages());
                }
                }catch (SocketException e){
                    objectSocket.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void write(Object object) throws IOException {
        objectSocket.write(object);
    }
}
