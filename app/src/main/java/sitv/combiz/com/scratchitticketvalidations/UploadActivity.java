package sitv.combiz.com.scratchitticketvalidations;

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
    ArrayList<String> ticketList = new ArrayList<String>();
    ArrayList<Integer> ticketValue =  new ArrayList<Integer>();
    TableLayout tblTickets;


    private Integer getTicketTotalCount() {
        return ticketList.size();
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


        for (int ctr = 0; ctr < getTicketTotalCount(); ctr++) {
            TableRow tr = new TableRow(this);
            TextView c1 = new TextView(this);
            c1.setText(String.format(Locale.ENGLISH, "%03d", (ctr+1)));
            c1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            TextView c2 = new TextView(this);
            c2.setText(ticketList.get(ctr));
            c2.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            TextView c3 = new TextView(this);
            c3.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            double test = ticketValue.get(ctr) / 1000;



            if (test < 1) {
                DecimalFormat df = new DecimalFormat("#.00");

                c3.setText(df.format(ticketValue.get(ctr)));

            } else {
                c3.setText(String.format("%.0f K",test).toString());
            }

            if (ticketValue.get(ctr) < 0) {
                c3.setText("CLAIMED");
                c3.setTextColor(getResources().getColor(R.color.text_red));
            } else if (ticketValue.get(ctr) == 0) {
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

    private void getTicketList () {
        Intent intent = getIntent();
//        TicketList tl = intent.getParcelableExtra("ticketlist");
//        ticketList = tl.getTicketCodeList();
//        ticketSelected = tl.getListTicketsSelected();
        ticketList = intent.getStringArrayListExtra("ticketCodeList");
    }


    private void addDemoTickets() {
        ticketList.add("123-456789-012 345678 90-11");
        ticketList.add("123-456789-012 345678 90-22");
        ticketList.add("123-456789-012 345678 90-33");
        ticketList.add("123-456789-012 345678 90-44");
        ticketList.add("123-456789-012 345678 90-55");
        ticketList.add("123-456789-012 345678 90-66");
        ticketList.add("123-456789-012 345678 90-77");
        ticketList.add("123-456789-012 345678 90-88");
        ticketList.add("123-456789-012 345678 90-99");
        ticketList.add("123-456789-012 345678 90-01");
        ticketList.add("123-456789-012 345678 90-02");
        ticketList.add("123-456789-012 345678 90-03");
        ticketList.add("123-456789-012 345678 90-04");
        ticketList.add("123-456789-012 345678 90-05");


    }

    private void addTicketValues() {
        ticketValue.add(-1);
        ticketValue.add(0);
        ticketValue.add(0);
        ticketValue.add(20);
        ticketValue.add(0);
        ticketValue.add(50);
        ticketValue.add(0);
        ticketValue.add(-1);
        ticketValue.add(200000);
        ticketValue.add(-1);
        ticketValue.add(0);
        ticketValue.add(200);
        ticketValue.add(0);
        ticketValue.add(10);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        tblTickets = (TableLayout) findViewById(R.id.tblTickets);
        getTicketList();
        //addDemoTickets();
        addTicketValues();
        addTableHeader();
        populateTable();
        addTableFooter();
    }
}
