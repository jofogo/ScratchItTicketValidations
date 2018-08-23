package sitv.combiz.com.scratchitticketvalidations;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class ValidationActivity extends AppCompatActivity {
    private DecoratedBarcodeView tViewScanner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validation);

        //Barcode scanner
        tViewScanner = (DecoratedBarcodeView) findViewById(R.id.tViewScanner);
        Collection<BarcodeFormat> codeFormats = Arrays.asList(BarcodeFormat.PDF_417, BarcodeFormat.QR_CODE);
        //tViewScanner.getBarcodeView().setDecoderFactory(new DefaultDecoderFactory(codeFormats));

        tViewScanner.decodeContinuous(callback);
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

            //tViewScanner.setStatusText(lastTicketCode);
            //txtTicketCode.setText(lastTicketCode);

        }

        @Override
        public void possibleResultPoints(List<ResultPoint> resultPoints) {

        }
    };
    /* Stop Barcode reader methods */





    //Menu launcher
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_loggedin, menu);
        return true;

    }

}
