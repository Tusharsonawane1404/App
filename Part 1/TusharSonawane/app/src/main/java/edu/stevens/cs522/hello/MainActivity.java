// MainActivity.java
package edu.stevens.cs522.hello;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toolbar;

public class MainActivity extends Activity implements View.OnClickListener {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button) {
            EditText textBox = (EditText) findViewById(R.id.textBox);
            String text = textBox.getText().toString();

            Intent intent = new Intent(this, ShowActivity.class);
            intent.putExtra(ShowActivity.MESSAGE_KEY, text);
            startActivity(intent);
        }
    }
}
