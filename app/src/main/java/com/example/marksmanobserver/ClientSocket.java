package com.example.marksmanobserver;

import org.example.marksmangame.net.protocol.Req;
import org.example.marksmangame.net.protocol.Resp;
import org.example.marksmangame.net.protocol.TypeMsg;
import com.google.gson.Gson;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientSocket {
    private Socket socket;
    private DataOutputStream dos;
    private DataInputStream dis;
    private final Gson gson = new Gson();
    private final Model model = BModel.getModel();

    public void connect() {
        new Thread(() -> {
            try {
                // 10.0.2.2 - это localhost для эмулятора Android
                socket = new Socket("10.0.2.2", 3124);
                dos = new DataOutputStream(socket.getOutputStream());
                dis = new DataInputStream(socket.getInputStream());

                sendReq(new Req(TypeMsg.CONNECT_SPECTATOR));

                while (!socket.isClosed()) {
                    String json = dis.readUTF();
                    Resp resp = gson.fromJson(json, Resp.class);

                    if (resp.getLeaderboard() != null) {
                        model.updateLeaderboard(resp.getLeaderboard());
                    } else if (resp.getSnapshot() != null) {
                        model.updateSnapshot(resp.getSnapshot());
                    }
                }
            } catch (IOException e) { e.printStackTrace(); }
        }).start();
    }

    public void sendReq(Req req) {
        new Thread(() -> {
            try {
                if (dos != null) {
                    dos.writeUTF(gson.toJson(req));
                    dos.flush();
                }
            } catch (IOException e) { e.printStackTrace(); }
        }).start();
    }
}
