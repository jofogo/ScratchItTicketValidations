package sitv.combiz.com.scratchitticketvalidations;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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

public class ValidationActivity extends AppCompatActivity {
    Button btnTicketCount;
    EditText txtTicketCode;
    ImageButton btnTorch;
    ImageButton btnAdd;
    ImageButton btnUpload;

    AudioManager audioManager;


    private Boolean torchToggled=false;
    private DecoratedBarcodeView tViewScanner;
    private String  lastTicketCode = "";
    private final int ticketCodeLength = 22;
    //List of ticket codes:
    private ArrayList<String> ticketCodes = new ArrayList<String>();
    //List of tickets selected in the TicketList activity
    private ArrayList<Integer> ticketSelected = new ArrayList<Integer>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validation);

        //Activity elements
        btnTicketCount = (Button)findViewById(R.id.btnTicketCount);
        txtTicketCode = (EditText)findViewById(R.id.txtTicketCode);
        btnTorch = (ImageButton)findViewById(R.id.btnTorch);
        btnAdd = (ImageButton)findViewById(R.id.btnAdd);
        btnUpload = (ImageButton)findViewById(R.id.btnUpload);

        //Barcode scanner
        tViewScanner = (DecoratedBarcodeView) findViewById(R.id.tViewScanner);
        Collection<BarcodeFormat> codeFormats = Arrays.asList(BarcodeFormat.PDF_417, BarcodeFormat.QR_CODE);
        //tViewScanner.getBarcodeView().setDecoderFactory(new DefaultDecoderFactory(codeFormats));

        tViewScanner.decodeContinuous(callback);

        //Set app volume
        setVolume();


        //Listen for Ticket Code field changes
        txtTicketCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (txtTicketCode.getText().toString().equals("") || txtTicketCode.length() < ticketCodeLength) {
                    btnAdd.setEnabled(false);
                    btnAdd.setImageResource(R.drawable.ico_disabled_add);

                } else {
                    btnAdd.setEnabled(true);
                    btnAdd.setImageResource(R.drawable.ico_enabled_add);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        //Listen for Ticket Count field changes
        btnTicketCount.addTextChangedListener(new TextWatcher() {


            //txtTicketCount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (btnTicketCount.getText().toString().equals("000")) {
                    //    if (txtTicketCount.getText().toString().equals("0")) {
                //    btnUpload.setEnabled(false);
                //    btnUpload.setImageResource(R.drawable.ico_disabled_upload);
                    btnTicketCount.setEnabled(false);

                } else {
                //    btnUpload.setEnabled(true);
                //    btnUpload.setImageResource(R.drawable.ico_enabled_upload);

                    btnTicketCount.setEnabled(true);

                }
            }
        });
    }

    /* Start Barcode reader methods */
    @Override
    protected void onResume() {
        super.onResume();
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
        intent.putExtra("ticketlist", new TicketList(ticketCodes, ticketSelected));

        intent.putStringArrayListExtra("ticketCodeList" , ticketCodes);
        intent.putIntegerArrayListExtra("ticketsSelected", ticketSelected);
        startActivity(intent);

    }


    //Formats the text for the Ticket Count button
    private void setBtnTicketCountText(int count) {
        String btnText = "";
        try {
            btnText = String.format("%03d", count);
        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
            btnText = String.format("%03d", 0);
        }
        btnTicketCount.setText(btnText);
    }

    //Add button pressed (manually add a ticketcode):
    //1. Check if null text is being added
    //2. call addTicket if Ticket Code text field is not null
    public void ticketAddManual(View view) {
        String ticketCode = txtTicketCode.getText().toString();
        if (ticketCode.equals("")) {
            Toast.makeText(this, "Ticket code is empty!", Toast.LENGTH_SHORT).show();
        } else {
            ticketAddAuto(ticketCode);
        }
    }

    private void ticketAddAuto(String ticketCode) {
        if (ticketCode.length() != ticketCodeLength) {
            playBeep(99);
            Toast.makeText(this, "Ticket code should be " +ticketCodeLength+ " digits!", Toast.LENGTH_SHORT).show();
        } else if (ticketCodes.contains(ticketCode)) {
            playBeep(1);
            Toast.makeText(this, "Ticket code was already used!", Toast.LENGTH_SHORT).show();
        } else {
            ticketCodes.add(ticketCode);
            ticketSelected.add(0);
            playBeep(0);
            Toast.makeText(this, "Ticket " + ticketCode + " was added.", Toast.LENGTH_SHORT).show();
            setBtnTicketCountText(ticketCodes.size());
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

    //Menu launcher
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_loggedin, menu);
        return true;

    }

}
