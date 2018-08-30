package sitv.combiz.com.scratchitticketvalidations;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

public class ValidationActivity extends AppCompatActivity {
    TextInputEditText txtTicketCount;
    TextInputEditText txtTicketCode;
    ImageButton btnTorch;
    ImageButton btnAdd;
    ImageButton btnUpload;

    AudioManager audioManager;

    private Boolean torchToggled=false;
    private DecoratedBarcodeView tViewScanner;
    private String lastTicketCode = "";

    private static Boolean isLengthReached = false;

    ConfigViewModel mvConfig;
    TicketViewModel mvTicket;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validation);
        mvConfig = ViewModelProviders.of(this).get(ConfigViewModel.class);
        mvTicket = ViewModelProviders.of(this).get(TicketViewModel.class);


        //Activity elements
        txtTicketCount = (TextInputEditText) findViewById(R.id.txtTicketCount);
        txtTicketCode = (TextInputEditText) findViewById(R.id.txtTicketCode);
        btnTorch = (ImageButton)findViewById(R.id.btnTorch);
        btnAdd = (ImageButton)findViewById(R.id.btnAdd);
        btnUpload = (ImageButton)findViewById(R.id.btnUpload);

        //Default state
        btnAdd.setEnabled(false);
        txtTicketCount.requestFocus();

        //Barcode scanner
        tViewScanner = (DecoratedBarcodeView) findViewById(R.id.tViewScanner);
        Collection<BarcodeFormat> codeFormats = Arrays.asList(BarcodeFormat.PDF_417, BarcodeFormat.QR_CODE);
        //tViewScanner.getBarcodeView().setDecoderFactory(new DefaultDecoderFactory(codeFormats));
        tViewScanner.decodeContinuous(callback);

        //Set app volume
        setVolume();
        //Listen for Ticket Code field changes

        if (mvTicket.getTicketTotal()>0) {
            txtTicketCount.setText(mvTicket.getTicketCount());
            enableUpload();
        } else {
            txtTicketCount.setText(mvTicket.getTicketCount());
            disableUpload();
        }

        txtTicketCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {  }


            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {  }

            @Override
            public void afterTextChanged(Editable editable) {

                if (editable.length() == mvTicket.getTicketMinLength()) {
                    editable.replace(0, editable.length(), mvTicket.formatTicket(editable.toString()));
                    btnAdd.setEnabled(true);
                    btnAdd.setImageResource(R.drawable.ico_enabled_add);
                    isLengthReached = true;

                } else {
                    if (isLengthReached) {
                        if (mvTicket.formatTicket(editable.toString()).length() != mvTicket.getTicketMaxLength()) {
                            btnAdd.setEnabled(false);
                            btnAdd.setImageResource(R.drawable.ico_disabled_add);
                            isLengthReached = false;
                            editable.replace(0, editable.length(), mvTicket.formatTicket(editable.toString()));
                        }
                    }
                }

            }
        });


        //Listen for Ticket Count field changes
        txtTicketCount.addTextChangedListener(new TextWatcher() {


            //txtTicketCount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (txtTicketCount.getText().toString().equals("000")) {
                    //    if (txtTicketCount.getText().toString().equals("0")) {
                    disableUpload();
                } else {
                    enableUpload();

                }
            }
        });
    }

    private void enableUpload () {

        btnUpload.setEnabled(true);
        btnUpload.setImageResource(R.drawable.ico_enabled_upload);

        txtTicketCount.setEnabled(true);
    }

    private void disableUpload() {
        btnUpload.setEnabled(false);
        btnUpload.setImageResource(R.drawable.ico_disabled_upload);
        txtTicketCount.setEnabled(false);
    }


    /* Start Barcode reader methods */
    @Override
    protected void onResume() {
        super.onResume();
        txtTicketCount.setText(mvTicket.getTicketCount());
        tViewScanner.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        tViewScanner.pause();
    }

    public void pause (View view) {
        tViewScanner.pause();
    }

    public void resume (View view) {
        tViewScanner.resume();
    }

    public void triggerScan(View view) {
        tViewScanner.decodeSingle(callback);
    }

    private BarcodeCallback callback = new BarcodeCallback() {
        @Override
        public void barcodeResult(BarcodeResult result) {
            String ticket = result.getText();
            if (ticket.equals("") || ticket.equals(lastTicketCode)) {
                return;
            }
            //Toast.makeText(ValidationActivity.this, ticket, Toast.LENGTH_SHORT).show();
            ticketAddAuto(ticket);
            lastTicketCode = ticket;

        }

        @Override
        public void possibleResultPoints(List<ResultPoint> resultPoints) {

        }
    };


    public void displayTicketList(View view) {
        Intent intent = new Intent (this, TicketListActivity.class);
        //intent.putExtra("ticketlist", new TicketList(ticketCodes, ticketSelected));
        startActivity(intent);

    }


    public void uploadTicketList (View view) {
        Intent intent = new Intent (this, UploadActivity.class);
        //intent.putExtra("ticketlist", new TicketList(ticketCodes, ticketSelected));
        startActivity(intent);
    }

    //Add button pressed (manually add a ticketcode):
    //1. Check if null text is being added
    //2. call addTicket if Ticket Code text field is not null
    public void ticketAddManual(View view) {
        String ticketCode = txtTicketCode.getText().toString();
        if (ticketCode.equals("")) {
            Toast.makeText(this, "Ticket code is empty!", Toast.LENGTH_SHORT).show();
        } else {
            ticketAddAuto(mvTicket.getTicketUnformatted(ticketCode));
        }
    }

    private void ticketAddAuto(String ticketCode) {
        int ticketCodeLength = mvTicket.getTicketMinLength();
        if (ticketCode.length() != ticketCodeLength) {
            playBeep(99);
            Toast.makeText(this, "Ticket code should be " + ticketCodeLength + " digits!", Toast.LENGTH_SHORT).show();
        } else if (mvTicket.hasTicketCode(ticketCode)) {
            playBeep(1);
            Toast.makeText(this, "Ticket code was already used!", Toast.LENGTH_SHORT).show();
        } else {
            mvTicket.addNewTicket(ticketCode);
            playBeep(0);
            Toast.makeText(this, "Ticket " + ticketCode + " was added.", Toast.LENGTH_SHORT).show();
            txtTicketCount.setText(mvTicket.getTicketCount());
            txtTicketCount.requestFocus();
            txtTicketCode.setText("");
        }
    }

    //Set app volume
    private void setVolume() {
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        int upperLimitVolume = maxVolume - 3;
        if (currentVolume < upperLimitVolume) {
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, upperLimitVolume, 0);
        }
    }

    //Beep handler
    private void playBeep(int status) {
        MediaPlayer mediaPlayer = new MediaPlayer();

        if (status == 0) {
            mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.beep_ok);
        } else if (status == 1) {
            mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.beep_duplicate);
        } else {
            mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.beep_error);
        }

        try {
            mediaPlayer.start();
        } catch (Exception e) {
            mediaPlayer.release();
        }

    }


    //Torch handler
    public void toggleTorch (View view) {
        if (this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
            if (torchToggled) {
                torchOff();
            } else {
                torchOn();
            }

        } else {
            playBeep(99);
            Toast.makeText(this, "This device does not have a camera flash!", Toast.LENGTH_SHORT).show();
        }

    }

    //Turn off the phone's torch
    private void torchOff() {
        tViewScanner.setTorchOff();
        torchToggled = false;
        btnTorch.setImageResource(R.drawable.ico_disabled_torch);

    }

    //Turn on the phone's torch
    private void torchOn() {
        tViewScanner.setTorchOn();
        torchToggled = true;
        btnTorch.setImageResource(R.drawable.ico_enabled_torch);

    }

    //Initiate logout sequence when hardware Back button is pressed
    @Override
    public void onBackPressed() {
        userLogout(null);
    }

    //User logout:
    // 1. Logs out automatically if Ticket Count = 0
    // 2. Logs out after pop-up confirmation if Ticket Count > 0
    public void userLogout(View view) {
        if (mvTicket.getTicketTotal() == 0) {
            logout();
        } else {
            new AlertDialog.Builder(this)
                    .setTitle("Logout")
                    .setMessage("Are you sure you want to log out?")
                    .setPositiveButton(
                            "Yes",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    logout();
                                }
                            }
                    )
                    .setNegativeButton(
                            "No",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            }
                    ).show();
        }

    }

    //Logout sequence - Moves the Activity back to the login (MainActivity)
    private void logout() {
        mvConfig.setIsLoggedIn(false);
        super.finish();
    }


    //Menu launcher
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_loggedin, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_about:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                TextView myMsg = new TextView(this);
                myMsg.setText(mvConfig.getAboutMessage());
                myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
                builder.setTitle(getResources().getString(R.string.app_name))
                        .setView(myMsg)
                        .show();
                break;
            case R.id.menu_logout:
                userLogout(null);
                break;
            default:
                break;
        }
        return true;
    }

}
