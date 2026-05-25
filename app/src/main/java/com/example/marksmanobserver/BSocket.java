package com.example.marksmanobserver;

public class BSocket {
    private static ClientSocket socket;

    public static ClientSocket getSocket() {
        if (socket == null) {
            socket = new ClientSocket();
        }
        return socket;
    }
}
