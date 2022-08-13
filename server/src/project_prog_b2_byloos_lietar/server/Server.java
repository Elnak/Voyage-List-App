package project_prog_b2_byloos_lietar.server;

import project_prog_b2_byloos_lietar.shared.models.BlockVoyages;
import project_prog_b2_byloos_lietar.shared.models.ListVoyages;
import project_prog_b2_byloos_lietar.shared.models.Voyages;
import project_prog_b2_byloos_lietar.shared.networks.ObjectSocket;

import java.io.*;
import java.net.ServerSocket;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Server {
    final List<ClientThread> threads = new ArrayList<>();
    static Path path = Paths.get(System.getProperty("user.dir")+"/server/src/project_prog_b2_byloos_lietar/server/resources/travels.bin");
    ListVoyages listVoyages = new ListVoyages(new ArrayList<>());

    public static void main(String[] args) {
        Server server = new Server();
        server.Read_Binary();
        server.Write_Binary();
        server.go();
    }

    private void go() {
        try (ServerSocket serverSocket = new ServerSocket(1298)) {
            while(true) {
                ObjectSocket objectSocket = new ObjectSocket(serverSocket.accept());
                ClientThread thread = new ClientThread(this,objectSocket);
                thread.start();
                synchronized (threads) {
                    threads.add(thread);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void Update_Voyages(){
        synchronized (threads){
            for (ClientThread thread : threads) {
                try {
                    thread.write(listVoyages);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void Block_Interraction(){
        synchronized (threads){
            for (ClientThread thread : threads) {
                try {
                    thread.write(new BlockVoyages(true));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public void Add_Voyages(Voyages voyages){
        listVoyages.Add_Voyages(voyages);
        Write_Binary();
        Update_Voyages();
    }
    public void Delete_Voyages(int position){
        listVoyages.Delete_Voyages(position);
        Update_Voyages();
        Write_Binary();
        synchronized (threads){
            for (ClientThread thread : threads) {
                try {
                    thread.write(position);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void Edit_Voyages(Voyages voyages, int position){
        listVoyages.Edit_Voyages(voyages,position);
        Write_Binary();
        Update_Voyages();
    }

    private void Write_Binary(){
        try {
            Path tempo = path;
            FileOutputStream fileOut = new FileOutputStream(String.valueOf(tempo));
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(listVoyages);
            objectOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void Read_Binary() {
        try {
            Path tempo = path;
            FileInputStream fstream = new FileInputStream(String.valueOf(tempo));
            ObjectInputStream ostream = new ObjectInputStream(fstream);
            listVoyages = (ListVoyages) ostream.readObject();
        } catch(Exception e){
            System.out.println("Fichier manquant ?");
        }
    }

    public ListVoyages getListVoyages() {
        return listVoyages;
    }
}
