package sitv.combiz.com.scratchitticketvalidations;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent activityLogin = new Intent(this, SettingsActivity.class);
        startActivity(activityLogin);
    }
}
