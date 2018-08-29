package sitv.combiz.com.scratchitticketvalidations;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.MissingResourceException;

public class LoginActivity extends AppCompatActivity {
    private URL URLServer;
    private String server = "";
    private String license = "";
    private String aboutMessage = "Check permissions: Allow phone must be enabled";
    private final int REQUEST_SETTINGS = 2;
    private final int MY_PERMISSIONS_REQUEST_NECESSARY = 1;

    EditText txtUsername;
    EditText txtPassword;
    Button btnLogin;
    Boolean userLoggedIn = false;

    HashMap<String, String> credentials = new HashMap<String, String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtUsername = (EditText) findViewById(R.id.txtUsername);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);

        checkAndRequestPermissions();
        //Inititation
        setupDemo();
        setURLServer();

        txtUsername.requestFocus();
        txtPassword.setEnabled(false);
        btnLogin.setEnabled(false);

        txtUsername.addTextChangedListener(new TextWatcher() {
                                               @Override
                                               public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                               }

                                               @Override
                                               public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                               }

                                               @Override
                                               public void afterTextChanged(Editable editable) {
                                                   if (txtUsername.getText().toString().equals("")) {
                                                       disableLogin();
                                                       txtPassword.setEnabled(false);
                                                   } else {
                                                       if (!txtPassword.getText().toString().equals("")) {
                                                           enableLogin();
                                                       } else {
                                                           disableLogin();
                                                       }
                                                       txtPassword.setEnabled(true);
                                                   }
                                               }
                                           }

        );

        txtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (txtPassword.getText().toString().equals("") || txtUsername.getText().toString().equals("")) {
                    disableLogin();
                } else {
                    enableLogin();
                }
            }
        });
    }

    private void disableLogin() {
        btnLogin.setEnabled(false);
        btnLogin.setTextColor(getResources().getColor(R.color.text_gray));
        btnLogin.setBackgroundColor(getResources().getColor(R.color.button_inactive));
    }

    private void enableLogin() {
        btnLogin.setEnabled(true);
        btnLogin.setTextColor(getResources().getColor(R.color.text_white));
        btnLogin.setBackgroundColor(getResources().getColor(R.color.scratchit_green));
    }

    private void setupDemo() {
        server = "https://combizcorp.com/license";
        license = "fb11b44a95a6cbce1b7d61294b827276fb1f1a70";
        credentialsAddUser("combiz", "demo");
        credentialsAddUser("dasc", "dasc");
    }

    //Add a unique user to the allowed user list
    private void credentialsAddUser(String user, String pass) {
        String userAddStatus = "";
        Log.i("Add User", "Attempting to add " + user + "/" + pass);
        if (user.equals("")) {
            userAddStatus += " username";
        }
        if (pass.equals("")) {
            userAddStatus += " password";
        }
        if (userAddStatus.equals("")) {
            if (credentialsCheckUsername(user)) {
                userAddStatus = "User already exists";
            } else {
                credentials.put(user.toLowerCase(), pass.toLowerCase());
                Log.i("User added", user);
            }

        }
    }

    //Check if the specified username exists
    private Boolean credentialsCheckUsername(String user) {
        Boolean userExists = false;
        user = user.toLowerCase();
        if (user.equals("")) {
            Log.i("Check User", "User is empty!");
        } else {
            if (credentials.containsKey(user)) {
                userExists = true;
                Log.i("Check User", "User found!");
            }

        }
        return userExists;
    }

    //Check the username/password input
    private Boolean credentialsCheckPassword(String user, String pass) {
        String checkCredentials = "";
        Boolean status = false;
        Log.i("Check if exists", user + "/" + pass);
        if (user.equals("") || pass.equals("")) {
            checkCredentials = "No username/password supplied!";
        } else {
            if (credentialsCheckUsername(user)) {
                if (credentials.get(user.toLowerCase()).equals(pass)) {
                    checkCredentials = "Logging in...";
                    status = true;
                } else {
                    checkCredentials = "Incorrect password!";
                }
            } else {
                checkCredentials = "Invalid username/password";
            }
        }
        Toast.makeText(this, checkCredentials, Toast.LENGTH_SHORT).show();
        return status;
    }

    //Validate login form inputs
    public void checkForm(View view) {
        String username = txtUsername.getText().toString();
        String password = txtPassword.getText().toString();
        Log.i("credentials supplied", username + "/" + password);
        if (credentialsCheckPassword(username, password)) {
            userLoggedIn = true;
            Intent activityValidation = new Intent(this, ValidationActivity.class);
            startActivity(activityValidation);
        } else {
            txtPassword.setText("");
            txtUsername.requestFocus();
            txtUsername.selectAll();
        }
        ;

    }


    private String getStringLicense() {
        return license;
    }

    private void setURLServer() {
        try {
            URLServer = new URL(server);
        } catch (MalformedURLException murle) {
            murle.printStackTrace();
        }
    }

    private String getStringURLServer() {
        return URLServer.toString();
    }

    private void setStringServer(String url) {
        server = url;
        setURLServer();
    }

    private void setStringLicense(String key) {
        license = key;
    }

    private boolean checkAndRequestPermissions() {

        int permissionCAMERA = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA);
        int permissionPHONESTATE = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_PHONE_STATE);

        List<String> listPermissionsNeeded = new ArrayList<>();
        if (permissionPHONESTATE != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (permissionCAMERA != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this,
                    listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), MY_PERMISSIONS_REQUEST_NECESSARY);
            return false;
        }

        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_NECESSARY:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    setAboutDetails();
                    //Permission Granted Successfully. Write working code here.
                } else {
                    quitApp();
                }
                break;
        }
    }

    private void quitApp() {

        finishAffinity();
    }


    //Menu launcher
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_loggedout, menu);
        return true;
    }

    @SuppressLint("MissingPermission")
    private void setAboutDetails() {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

            aboutMessage = "V " + getPackageManager().getPackageInfo(getPackageName(), 0).versionName + "\n" +
                    "IMEI: " + telephonyManager.getDeviceId() + "\n"
                    + getResources().getString(R.string.dev_name);
        } catch (PackageManager.NameNotFoundException nnfe) {
            nnfe.printStackTrace();
        }


    }

    private void showAboutMessage( ) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        TextView myMsg = new TextView(this);
        myMsg.setText(aboutMessage);
        myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
        builder.setTitle(getResources().getString(R.string.app_name))
                .setView(myMsg)
                .show();
    }

    //Menu handler
    @SuppressLint("MissingPermission")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_about:
                checkAndRequestPermissions();
                setAboutDetails();
                showAboutMessage();
                break;
            case R.id.menu_settings:
                Intent intent = new Intent (this, SettingsActivity.class);
                //intent.putExtra("ticketlist", new TicketList(ticketCodes, ticketSelected));
                intent.putExtra("URLServer", getStringURLServer());
                intent.putExtra("LicenseKey", getStringLicense());


                startActivityForResult(intent, REQUEST_SETTINGS);

                break;
            default:
                break;
        }
        return true;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQUEST_SETTINGS && resultCode == RESULT_OK) {
            setStringServer(data.getStringExtra("URLServer"));
            setStringLicense(data.getStringExtra("LicenseKey"));
        }
    }
}
