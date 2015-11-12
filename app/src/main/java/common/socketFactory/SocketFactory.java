package common.socketFactory;


import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;
import com.github.nkzawa.socketio.client.IO;


import java.net.URI;
import java.net.URISyntaxException;

public class SocketFactory {

    private Socket socket;

    public Socket getSocket(String url) {
        URI uri = null;
        try {
            uri = new URI(url);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        try {
            socket = IO.socket(uri);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
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
