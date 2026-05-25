package com.example.marksmanobserver;

import org.example.marksmangame.net.protocol.GameSnapshot;
import org.example.marksmangame.net.protocol.LeaderboardEntry;
import java.util.ArrayList;
import java.util.List;

public class Model {
    private GameSnapshot snapshot;
    private List<LeaderboardEntry> leaderboard;
    private final List<IEvent> observers = new ArrayList<>();

    public void addObserver(IEvent e) { observers.add(e); }
    private void notifyObservers() { for (IEvent e : observers) { e.event(); } }

    public void updateSnapshot(GameSnapshot snapshot) {
        this.snapshot = snapshot;
        notifyObservers();
    }

    public void updateLeaderboard(List<LeaderboardEntry> leaderboard) {
        this.leaderboard = leaderboard;
        notifyObservers();
    }

    public GameSnapshot getSnapshot() { return snapshot; }
    public List<LeaderboardEntry> getLeaderboard() { return leaderboard; }
}
