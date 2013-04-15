package websocket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.websockets.WebSocket;
import org.glassfish.grizzly.websockets.WebSocketAddOn;
import org.glassfish.grizzly.websockets.WebSocketEngine;

public class StartMain {

    public static void main(String[] args) throws Exception {

        BufferedReader br = null;
        String line = null;

        SocketServer socketServer = new SocketServer();

        HttpServer server = HttpServer.createSimpleServer();
        server.getListener("grizzly").registerAddOn(new WebSocketAddOn());
        WebSocketEngine.getEngine().register("/", socketServer);
        server.start();

        List<WebSocket> sockets = socketServer.getSockets();
        br = new BufferedReader(new InputStreamReader(System.in));

        while ((line = br.readLine()) != null) {
            //desde el servidor al los browsers
            for (WebSocket webSocket : sockets) {
                webSocket.send(line);
            }
        }
        
        server.stop();

    }
}