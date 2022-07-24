package project_prog_b2_byloos_lietar.shared.networks;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ObjectSocket implements AutoCloseable{
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public ObjectSocket(Socket socket) throws IOException {
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
    }

    public void write(Object object) throws IOException {
        out.reset();
        out.writeObject(object);
        out.flush();
    }

    public <T> T read() throws IOException, ClassNotFoundException {
        return (T) in.readObject();
    }

    @Override
    public void close() throws Exception {
        out.close();
        in.close();
    }
}
