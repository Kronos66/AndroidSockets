package common.socketFactory;




import java.net.URI;
import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class SocketFactory {

    private Socket socket;

    public Socket getSocket(String url) {
        URI uri = null;
        try {
            uri = new URI(url);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        socket = IO.socket(uri);
        if (null != socket) {
            return socket;
        } else {
            return null;
        }
    }

    public void connect() {
        socket.connect();
    }

    public void emit(String event, String message) {
        socket.emit(event, message);
    }

    public void on(String event, Emitter.Listener listener) {
        socket.on(event, listener);
    }

}
