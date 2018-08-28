package sitv.combiz.com.scratchitticketvalidations;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;

public class SettingsActivity extends AppCompatActivity {
    private String server;
    private String license;
    EditText txtServer;
    EditText txtLicense;
    Button btnSave;

    private void getIntents() {
        Intent login = getIntent();

        server = login.getStringExtra("URLServer");
        license = login.getStringExtra("LicenseKey");
    }

    public void SaveSettings(View view) {
        String URLServer = txtServer.getText().toString();
        try {
            URL url = new URL(URLServer);
            finish();
        } catch (MalformedURLException murle) {
            Toast.makeText(this, getResources().getString(R.string.settings_error_invalidURL), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        txtServer = (EditText) findViewById(R.id.txtServer);
        txtLicense = (EditText) findViewById(R.id.txtLicense);
        btnSave = (Button) findViewById(R.id.btnSave);

        getIntents();

        txtServer.setText(server);
        txtLicense.setText(license);
    }

    @Override
    public void finish() {
        Intent data = new Intent();
        data.putExtra("URLServer", txtServer.getText().toString());
        data.putExtra("LicenseKey", txtLicense.getText().toString());
        setResult(RESULT_OK, data);
        super.finish();
    }
}
