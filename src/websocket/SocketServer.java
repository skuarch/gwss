package websocket;

import java.util.ArrayList;
import java.util.List;

import org.glassfish.grizzly.http.HttpRequestPacket;
import org.glassfish.grizzly.websockets.DataFrame;
import org.glassfish.grizzly.websockets.WebSocket;
import org.glassfish.grizzly.websockets.WebSocketApplication;

public class SocketServer extends WebSocketApplication {

    private final List<WebSocket> sockets = new ArrayList<WebSocket>();

    @Override
    public boolean isApplicationRequest(HttpRequestPacket request) {
        System.out.println(request.toString());
        return true;
    }

    @Override
    public void onConnect(WebSocket socket) {
        sockets.add(socket);
    }

    @Override
    public void onClose(WebSocket socket, DataFrame frame) {
        System.out.println(socket.toString());
        sockets.remove(socket);
    }

    @Override
    public void onMessage(WebSocket socket, String text) {
        System.out.println(text);
        for (WebSocket s : sockets) {
            s.send(text);
        }
    }
    
    public List<WebSocket> getSockets() {
        return sockets;
    }
}