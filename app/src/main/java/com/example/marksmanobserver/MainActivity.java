package com.example.marksmanobserver;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import org.example.marksmangame.net.protocol.GameSnapshot;
import org.example.marksmangame.net.protocol.PlayerState;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView tvStatus;
    private ArrayAdapter<String> adapter;
    private final List<String> listData = new ArrayList<>();

    private final Model model = BModel.getModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvStatus = findViewById(R.id.tvStatus);
        ListView listViewPlayers = findViewById(R.id.listViewPlayers);
        Button btnLeaderboard = findViewById(R.id.btnLeaderboard);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        listViewPlayers.setAdapter(adapter);

        btnLeaderboard.setOnClickListener(v ->
                startActivity(new Intent(this, LeaderboardActivity.class))
        );

        model.addObserver(this::updateUI);

        ClientSocket clientSocket = new ClientSocket();
        clientSocket.connect();
    }

    private void updateUI() {
        GameSnapshot snapshot = model.getSnapshot();
        if (snapshot == null) return;

        runOnUiThread(() -> {
            tvStatus.setText("Статус: " + snapshot.getStatusMessage());
            listData.clear();

            // Выводим имя, выстрелы и score (как попадания)
            for (PlayerState player : snapshot.getPlayers()) {
                listData.add(player.getName() + " | " + player.getShots() + " | " + player.getScore());
            }
            adapter.notifyDataSetChanged();
        });
    }
}