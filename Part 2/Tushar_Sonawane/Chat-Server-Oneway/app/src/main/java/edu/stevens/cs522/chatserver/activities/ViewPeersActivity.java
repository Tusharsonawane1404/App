package edu.stevens.cs522.chatserver.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

import edu.stevens.cs522.chatserver.R;
import edu.stevens.cs522.chatserver.entities.Peer;


public class ViewPeersActivity extends Activity implements AdapterView.OnItemClickListener {

    public static final String PEERS_KEY = "peers";

    //ArrayAdapter<Peer> peersAdapter;
    ArrayAdapter<String> peersAdapter;
    HashMap<String, Peer> peerMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_peers);

        ArrayList<Peer> peers = getIntent().getParcelableArrayListExtra(PEERS_KEY);
        if (peers == null || peers.isEmpty()) {
            throw new IllegalArgumentException("Missing list of peers!");
        }

        peerMap = new HashMap<>();
        for (Peer peer : peers) {
            peerMap.put(peer.name, peer);
        }

        // Create a list of peer names
        /*ArrayList<String> peerNames = new ArrayList<>();
        for (Peer peer : peers) {
            peerNames.add(peer.name);
        } */

        // TODO display the list of peers, set this activity as onClick listener
        /*ListView peerListView = findViewById(R.id.peer_list);
        peersAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, peerNames);
        peerListView.setAdapter(peersAdapter);
        peerListView.setOnItemClickListener(this);*/

        ListView peerListView = findViewById(R.id.peer_list);
        peersAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<>(peerMap.keySet()));
        peerListView.setAdapter(peersAdapter);
        peerListView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        /*
         * Clicking on a peer brings up details
         */
       /* String peer = peersAdapter.getItem(position);
        Intent intent = new Intent(this, ViewPeerActivity.class);
        intent.putExtra(ViewPeerActivity.PEER_KEY, peer);
        startActivity(intent);*/
        String peerName = peersAdapter.getItem(position);
        Peer peer = peerMap.get(peerName);
        if (peer != null) {
            Intent intent = new Intent(this, ViewPeerActivity.class);
            intent.putExtra(ViewPeerActivity.PEER_KEY, peer);
            startActivity(intent);
        }
    }
}
