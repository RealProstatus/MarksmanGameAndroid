package com.example.marksmanobserver;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

import org.example.marksmangame.net.protocol.LeaderboardEntry;
import org.example.marksmangame.net.protocol.Req;
import org.example.marksmangame.net.protocol.TypeMsg;

import java.util.ArrayList;
import java.util.List;

public class LeaderboardActivity extends AppCompatActivity {
    private ArrayAdapter<String> adapter;
    private final List<String> listData = new ArrayList<>();
    private final Model model = BModel.getModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        ListView listViewLeaderboard = findViewById(R.id.listViewLeaderboard);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        listViewLeaderboard.setAdapter(adapter);

        model.addObserver(this::updateUI);

        // Отправляем запрос
        ClientSocket socket = BSocket.getSocket();
        socket.sendReq(new Req(TypeMsg.LEADERBOARD_SPECTATOR_REQUEST));
    }

    private void updateUI() {
        List<LeaderboardEntry> leaderboard = model.getLeaderboard();
        if (leaderboard == null) return;

        runOnUiThread(() -> {
            listData.clear();
            for (LeaderboardEntry entry : leaderboard) {
                listData.add(entry.getName() + " | " + entry.getWins());
            }
            adapter.notifyDataSetChanged();
        });
    }
}