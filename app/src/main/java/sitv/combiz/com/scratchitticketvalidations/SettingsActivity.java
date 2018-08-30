package sitv.combiz.com.scratchitticketvalidations;

import android.arch.lifecycle.ViewModelProviders;
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

    EditText txtServer;
    EditText txtLicense;
    Button btnSave;
    ConfigViewModel mvConfig;

    private String un;
    private String pw;

    public void SaveSettings(View view) {
        String URLServer = txtServer.getText().toString();
        try {
            URL url = new URL(URLServer);
            Toast.makeText(this, "Settings saved!", Toast.LENGTH_SHORT).show();
            finish();
        } catch (MalformedURLException murle) {
            Toast.makeText(this, getResources().getString(R.string.settings_error_invalidURL), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        mvConfig = ViewModelProviders.of(this).get(ConfigViewModel.class);

        txtServer = (EditText) findViewById(R.id.txtServer);
        txtLicense = (EditText) findViewById(R.id.txtLicense);
        btnSave = (Button) findViewById(R.id.btnSave);

        txtServer.setText(mvConfig.getServerURL());
        txtLicense.setText(mvConfig.getLicenseKey());

        Intent login = getIntent();
        un = login.getStringExtra("username");
        pw = login.getStringExtra("password");
    }

    @Override
    public void finish() {
        mvConfig.setServerURL(txtServer.getText().toString());
        Intent data = new Intent();
        data.putExtra("username", un);
        data.putExtra("password", pw);
        setResult(RESULT_OK, data);
        super.finish();
    }
}
