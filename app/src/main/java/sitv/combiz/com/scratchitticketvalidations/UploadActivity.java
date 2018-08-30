package sitv.combiz.com.scratchitticketvalidations;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Locale;

public class UploadActivity extends AppCompatActivity {

    TableLayout tblTickets;
    TicketViewModel mvTicket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mvTicket = ViewModelProviders.of(this).get(TicketViewModel.class);

        setContentView(R.layout.activity_upload);
        tblTickets = (TableLayout) findViewById(R.id.tblTickets);
        //addDemoTickets();
        addDemoTicketValues();
        addTableHeader();
        populateTable();
        addTableFooter();
    }


    private void addTableHeader() {
        tblTickets.setStretchAllColumns(true);
        tblTickets.bringToFront();
        tblTickets.removeAllViews();
        TableRow trHeader = new TableRow( this);
        TextView h1 = new TextView(this);
        h1.setText(getResources().getString(R.string.upload_lblcounter));
        h1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        h1.setTypeface(null, Typeface.BOLD);
        h1.setGravity(Gravity.CENTER);

        TextView h2 = new TextView(this);
        h2.setText(getResources().getString(R.string.upload_lblheader));
        h2.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        h2.setTypeface(null, Typeface.BOLD);
        h2.setGravity(Gravity.CENTER);

        TextView h3 = new TextView(this);
        h3.setText(getResources().getString(R.string.upload_lblstatus));
        h3.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        h3.setTypeface(null, Typeface.BOLD);
        h3.setGravity(Gravity.CENTER);

        trHeader.addView(h1);
        trHeader.addView(h2);
        trHeader.addView(h3);
        tblTickets.addView(trHeader);
    }

    private void populateTable() {
   //     tblTickets.removeViews(1, tblTickets.getChildCount());


        for (int ctr = 0; ctr < mvTicket.getTicketTotal(); ctr++) {
            TableRow tr = new TableRow(this);
            TextView c1 = new TextView(this);
            c1.setText(String.format(Locale.ENGLISH, "%03d", (ctr+1)));
            c1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            TextView c2 = new TextView(this);
            c2.setText(mvTicket.getTicketCode(ctr));
            c2.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            TextView c3 = new TextView(this);
            c3.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            double test = mvTicket.getTicketValue(ctr) / 1000;



            if (test < 1) {
                DecimalFormat df = new DecimalFormat("#.00");

                c3.setText(df.format(mvTicket.getTicketValue(ctr)));

            } else {
                c3.setText(String.format("%.0f K",test).toString());
            }

            if (mvTicket.getTicketValue(ctr) < 0) {
                c3.setText("CLAIMED");
                c3.setTextColor(getResources().getColor(R.color.text_red));
            } else if (mvTicket.getTicketValue(ctr) == 0) {
                c3.setText("LOSE");
                c3.setTextColor(getResources().getColor(R.color.text_red));
            } else {
                c3.setTextColor(getResources().getColor(R.color.scratchit_green));
            }



            tr.addView(c1);
            tr.addView(c2);
            tr.addView(c3);
            tblTickets.addView(tr);
        }
    }

    private void addTableFooter() {
        TableRow trFooter = new TableRow(this);
        TextView filler1 = new TextView(this);
        filler1.setText(" ");

        TextView filler2 = new TextView(this);
        filler2.setText(" ");

        Button btnRemove = new Button(this);
        btnRemove.setText(getResources().getString(R.string.upload_btnupload));
        btnRemove.setBackgroundColor(getResources().getColor(R.color.scratchit_green));
        btnRemove.setTextColor(getResources().getColor(R.color.text_white));

        trFooter.addView(filler1);
        trFooter.addView(btnRemove);
        trFooter.addView(filler2);

        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });
        tblTickets.addView(trFooter);
    }





    private void addDemoTicketValues() {
        mvTicket.addTicketValue(-1);
        mvTicket.addTicketValue(0);
        mvTicket.addTicketValue(0);
        mvTicket.addTicketValue(20);
        mvTicket.addTicketValue(0);
        mvTicket.addTicketValue(50);
        mvTicket.addTicketValue(0);
        mvTicket.addTicketValue(-1);
        mvTicket.addTicketValue(200000);
        mvTicket.addTicketValue(-1);
        mvTicket.addTicketValue(0);
        mvTicket.addTicketValue(200);
        mvTicket.addTicketValue(0);
        mvTicket.addTicketValue(10);

    }

}
