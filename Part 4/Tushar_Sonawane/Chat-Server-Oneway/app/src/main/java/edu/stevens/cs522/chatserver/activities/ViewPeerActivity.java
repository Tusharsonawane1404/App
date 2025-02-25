package edu.stevens.cs522.chatserver.activities;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import edu.stevens.cs522.chatserver.R;
import edu.stevens.cs522.chatserver.databases.ChatDatabase;
import edu.stevens.cs522.chatserver.entities.Message;
import edu.stevens.cs522.chatserver.entities.Peer;
import edu.stevens.cs522.chatserver.ui.SimpleArrayAdapter;

/**
 * Created by dduggan.
 */

public class ViewPeerActivity extends FragmentActivity {

    public static final String PEER_KEY = "peer";

    private ChatDatabase chatDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_peer);

        Peer peer = getIntent().getParcelableExtra(PEER_KEY);
        if (peer == null) {
            throw new IllegalArgumentException("Expected peer as intent extra");
        }

        // TODO Set the fields of the UI
		TextView nameTextView = findViewById(R.id.view_user_name);
        TextView addressTextView = findViewById(R.id.view_location);
        TextView timestampTextView = findViewById(R.id.view_timestamp);
        nameTextView.setText(String.format(getString(R.string.view_user_name), peer.name));
        String location = String.format(getString(R.string.view_location), peer.latitude, peer.longitude);
        addressTextView.setText(location);
		String timestamp = String.format(getString(R.string.view_timestamp), formatTimestamp(peer.timestamp));
        timestampTextView.setText(timestamp);

        SimpleArrayAdapter<Message> messagesAdapter = new SimpleArrayAdapter<>(this);
        ListView messagesList = findViewById(R.id.message_list);
        messagesList.setAdapter(messagesAdapter);

        chatDatabase = ChatDatabase.getInstance(getApplicationContext());

        /*
         * TODO query the database asynchronously for the messages just for this peer.
         */
        LiveData<List<Message>> messagesLiveData = chatDatabase.messageDao().fetchMessagesFromPeer(peer.name);
        messagesLiveData.observe(this, messages ->   {
                messagesAdapter.setElements(messages);
                messagesAdapter.notifyDataSetChanged();

        });
    }

    private static String formatTimestamp(Date timestamp) {
        LocalDateTime dateTime = timestamp.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return dateTime.format(formatter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        chatDatabase = null;
    }

}
