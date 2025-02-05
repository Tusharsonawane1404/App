package edu.stevens.cs522.chatclient.activities;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.JsonWriter;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Date;

import edu.stevens.cs522.base.Datagram;
import edu.stevens.cs522.base.DatagramConnectionFactory;
import edu.stevens.cs522.base.IDatagramConnection;
import edu.stevens.cs522.base.DateUtils;
import edu.stevens.cs522.chatclient.R;
import edu.stevens.cs522.chatclient.location.CurrentLocation;

public class ChatClientActivity extends Activity implements OnClickListener {

	final static private String TAG = ChatClientActivity.class.getCanonicalName();

	public final static String SENDER_NAME = "name";
	public final static String CHATROOM = "room";
	public final static String MESSAGE_TEXT = "text";
	public final static String TIMESTAMP = "timestamp";
	public final static String LATITUDE = "latitude";
	public final static String LONGITUDE = "longitude";

	private IDatagramConnection clientConnection;

	private EditText destinationAddr;
	private EditText chatName;
	private EditText messageText;
	private Button sendButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chat_client);

		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);

		destinationAddr = findViewById(R.id.destination_addr);
		chatName = findViewById(R.id.chat_name);
		messageText = findViewById(R.id.message_text);
		sendButton = findViewById(R.id.send_button);
		sendButton.setOnClickListener(this);

		try {
			int port = getResources().getInteger(R.integer.app_port);
			DatagramConnectionFactory factory = new DatagramConnectionFactory();
			clientConnection = factory.getUdpConnection(port);
		} catch (IOException e) {
			Log.e(TAG, "Cannot open client connection", e);
			throw new IllegalStateException("Cannot open client connection", e);
		}
	}

	@Override
	public void onClick(View v) {
		try {
			String destAddr = destinationAddr.getText().toString();
			String clientName = chatName.getText().toString();
			String text = messageText.getText().toString();
			String chatRoom = getString(R.string.default_chatroom);
			Date timestamp = DateUtils.now();
			CurrentLocation location = CurrentLocation.getLocation(this);

			if (destAddr.isEmpty() || clientName.isEmpty() || text.isEmpty()) {
				return;
			}

			StringWriter output = new StringWriter();
			JsonWriter wr = new JsonWriter(output);
			wr.beginObject();
			wr.name(SENDER_NAME).value(clientName);
			wr.name(CHATROOM).value(chatRoom);
			wr.name(MESSAGE_TEXT).value(text);
			wr.name(TIMESTAMP).value(timestamp.getTime());
			wr.name(LATITUDE).value(location.getLatitude());
			wr.name(LONGITUDE).value(location.getLongitude());
			wr.endObject();

			String content = output.toString();

			Log.d(TAG, "Sending message: " + content);

			Datagram sendPacket = new Datagram();
			sendPacket.setAddress(destAddr);
			sendPacket.setData(content);

			clientConnection.send(this, sendPacket);
			Log.d(TAG, "Sent packet!"+content);

		} catch (IOException e) {
			Log.e(TAG, "IO exception", e);
			throw new IllegalStateException("IO exception", e);
		}

		messageText.setText("");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (clientConnection != null) {
			clientConnection.close();
		}
	}

}
