package edu.stevens.cs522.hello;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class ShowActivity extends Activity {

    public final static String MESSAGE_KEY = "message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        String message = getIntent().getStringExtra(MESSAGE_KEY);
        TextView textview = findViewById(R.id.message);
        String formattedMessage = getString(R.string.show_name, message);
        textview.setText(formattedMessage);
    }
}
