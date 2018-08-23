package sitv.combiz.com.scratchitticketvalidations;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    //Menu launcher
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_loggedout, menu);
        return true;
    }

    //Menu handler
    @SuppressLint("MissingPermission")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_about:
                TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);

                try {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    TextView myMsg = new TextView(this);
                    myMsg.setText("V " + getPackageManager().getPackageInfo(getPackageName(),0).versionName  + "\n" +
                            "IMEI: " + telephonyManager.getDeviceId() + "\n"
                            + getResources().getString(R.string.dev_name));
                    myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
                    builder.setTitle(getResources().getString(R.string.app_name))
                            .setView(myMsg)
                            .show();
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }

                break;
            case R.id.menu_settings:
                Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return true;
    }
}
