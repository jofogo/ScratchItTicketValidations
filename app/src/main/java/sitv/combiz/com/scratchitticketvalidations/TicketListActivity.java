package sitv.combiz.com.scratchitticketvalidations;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Scroller;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Locale;

public class TicketListActivity extends AppCompatActivity{
    ArrayList<String> ticketList = new ArrayList<String>();
    ArrayList<Integer> ticketSelected =  new ArrayList<Integer>();

    TableLayout tblTickets;
    CheckBox hcb;

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
        ticketList.add("123-456789-012 345678 90-06");
        ticketList.add("123-456789-012 345678 90-07");
        ticketList.add("123-456789-012 345678 90-08");
        ticketList.add("123-456789-012 345678 90-09");
        ticketList.add("123-456789-012 345678 90-10");

    }

    private void addTicketTicked() {
        ticketSelected.add(0);
        ticketSelected.add(0);
        ticketSelected.add(0);
        ticketSelected.add(1);
        ticketSelected.add(0);
        ticketSelected.add(0);
        ticketSelected.add(1);
        ticketSelected.add(0);
        ticketSelected.add(1);
        ticketSelected.add(0);
        ticketSelected.add(0);
        ticketSelected.add(1);
        ticketSelected.add(0);
        ticketSelected.add(1);
        ticketSelected.add(0);
        ticketSelected.add(0);
        ticketSelected.add(1);
        ticketSelected.add(0);
        ticketSelected.add(1);

    }


    public void toggleAllTickets(View view) {
        if (!hcb.isSelected()) {
            selectAllTickets();
        } else {
            deselectAllTickets();
        }
        populateTable();
    }
    private void selectAllTickets() {
        ticketSelected.clear();
        for (int ctr=0; ctr < getTicketTotalCount(); ctr++) {
            ticketSelected.add(1);

        }
    }
    private void deselectAllTickets() {
        ticketSelected.clear();
        for (int ctr=0; ctr < getTicketTotalCount(); ctr++) {
            ticketSelected.add(0);
        }
    }

    private int getTicketTotalCount() {
        return ticketList.size();
    }

    private int getTableTicketRows() { return tblTickets.getChildCount() - 1;}

    private void removeTicketFromList(String ticket) {
        if (!ticket.equals("") && ticketList.contains(ticket)) {
            int idxTicket = ticketList.indexOf(ticket);
            ticketList.remove(idxTicket);
            ticketSelected.remove(idxTicket);
        }
    }




    private void addTableHeader() {
        tblTickets.setStretchAllColumns(true);
        tblTickets.bringToFront();
        tblTickets.removeAllViews();
        TableRow trHeader = new TableRow( this);
        TextView h1 = new TextView(this);
        h1.setText(getResources().getString(R.string.ticketlist_lblcounter));
        h1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        h1.setTypeface(null, Typeface.BOLD);
        h1.setGravity(Gravity.CENTER);

        TextView h2 = new TextView(this);
        h2.setText(getResources().getString(R.string.ticketlist_lblheader));
        h2.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        h2.setTypeface(null, Typeface.BOLD);
        h2.setGravity(Gravity.CENTER);

        final CheckBox hCb = new CheckBox( this);
        hCb.setGravity(Gravity.CENTER);
        hCb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hCb.isChecked()) {
                    selectAllTickets();
                } else {
                    deselectAllTickets();
                }
                populateTable();
            }
        });

        trHeader.addView(h1);
        trHeader.addView(h2);
        trHeader.addView(hCb);
        tblTickets.addView(trHeader);
    }


    private void populateTable() {
        tblTickets.removeViews(1, tblTickets.getChildCount()-1);


        for (int ctr = 0; ctr < getTicketTotalCount(); ctr++) {
            TableRow tr = new TableRow(this);
            TextView c1 = new TextView(this);
            c1.setText(String.format(Locale.ENGLISH, "%03d", (ctr+1)));
            c1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            TextView c2 = new TextView(this);
            c2.setText(ticketList.get(ctr));
            c2.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            CheckBox cb = new CheckBox(this);
            cb.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            if (ticketSelected.get(ctr)==0) {
                cb.setChecked(false);
            } else {
                cb.setChecked(true);
            }


            tr.addView(c1);
            tr.addView(c2);
            tr.addView(cb);
            tblTickets.addView(tr);
        }


        TableRow trFooter = new TableRow(this);
        TextView filler1 = new TextView(this);
        filler1.setText(" ");

        TextView filler2 = new TextView(this);
        filler2.setText(" ");

        Button btnRemove = new Button(this);
        btnRemove.setText(getResources().getString(R.string.ticketlist_btnremove));
        btnRemove.setBackgroundColor(getResources().getColor(R.color.scratchit_green));
        btnRemove.setTextColor(getResources().getColor(R.color.text_white));

        trFooter.addView(filler1);
        trFooter.addView(btnRemove);
        trFooter.addView(filler2);

        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int ctr = 1; ctr <= getTableTicketRows(); ctr++) {
                }
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
        ticketSelected = intent.getIntegerArrayListExtra("ticketsSelected");
    }

    private void logTickets() {
        for (int ctr=0; ctr < getTicketTotalCount(); ctr++) {
            Log.i("" + (ctr+1), ticketList.get(ctr) + " - " + ticketSelected.get(ctr));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_list);
        tblTickets = (TableLayout) findViewById(R.id.tblTickets);
        getTicketList();
        //logTickets();
        //addDemoTickets();
        //addTicketTicked();
        addTableHeader();
        populateTable();
    }
}
