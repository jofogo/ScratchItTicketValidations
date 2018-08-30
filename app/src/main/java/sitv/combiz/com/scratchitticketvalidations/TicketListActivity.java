package sitv.combiz.com.scratchitticketvalidations;

import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;
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

    TableLayout tblTickets;
    CheckBox hCb;

    TicketViewModel mvTicket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_list);
        mvTicket = ViewModelProviders.of(this).get(TicketViewModel.class);

        tblTickets = (TableLayout) findViewById(R.id.tblTickets);
        //logTickets();
        //addDemoTickets();
        //addTicketTicked();
        addTableHeader();
        populateTable();
    }

    private void addDemoTickets() {
        mvTicket.addDemoTicket("1234567890123456789011", false);
        mvTicket.addDemoTicket("1234567890123456789022", false);
        mvTicket.addDemoTicket("1234567890123456789033", false);
        mvTicket.addDemoTicket("1234567890123456789044", true);
        mvTicket.addDemoTicket("1234567890123456789055", false);
        mvTicket.addDemoTicket("1234567890123456789066", true);
        mvTicket.addDemoTicket("1234567890123456789077", false);
        mvTicket.addDemoTicket("1234567890123456789088", true);
        mvTicket.addDemoTicket("1234567890123456789099", false);
        mvTicket.addDemoTicket("1234567890123456789000", false);
        mvTicket.addDemoTicket("1234567890123456789111", true);
        mvTicket.addDemoTicket("1234567890123456789122", true);
        mvTicket.addDemoTicket("1234567890123456789133", false);
        mvTicket.addDemoTicket("1234567890123456789144", false);
        mvTicket.addDemoTicket("1234567890123456789155", false);

    }


    public void toggleAllTickets(View view) {
        if (!hCb.isSelected()) {
            selectAllTickets();
        } else {
            deselectAllTickets();
        }
        populateTable();
    }
    private void selectAllTickets() {
        mvTicket.selectAllTickets();
    }
    private void deselectAllTickets() {
        mvTicket.deselectAllTickets();
    }

    private int getTableTicketRows() { return tblTickets.getChildCount() - 1;}



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

         hCb = new CheckBox( this);
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


        for (int ctr = 0; ctr < mvTicket.getTicketTotal(); ctr++) {
            TableRow tr = new TableRow(this);
            TextView c1 = new TextView(this);
            c1.setText(String.format(Locale.ENGLISH, "%03d", (ctr+1)));
            c1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            TextView c2 = new TextView(this);
            c2.setText(mvTicket.getTicketCode(ctr));
            c2.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            CheckBox cb = new CheckBox(this);
            cb.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            if (!mvTicket.getTicketSelected(ctr)) {
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
                removeTickets();
            }
        });
        tblTickets.addView(trFooter);


    }


    private void removeTickets() {
        String message = "";
        int totalSelected = mvTicket.getSelectedTotal();
        if (hCb.isChecked()) {
            message = "Remove ALL tickets?";

        } else {
            if (totalSelected > 0) {
                message = "Remove " + totalSelected + " tickets?";
            }
        }

        if (totalSelected > 0) {
            new AlertDialog.Builder(this)
                    .setTitle("Logout")
                    .setMessage(message)
                    .setPositiveButton(
                            "Yes",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    if (hCb.isChecked()) {
                                        mvTicket.clearTickets();
                                    }
                                    finish();
                                }
                            }
                    )
                    .setNegativeButton(
                            "No",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    finish();
                                }
                            }
                    ).show();
        } else {
            finish();
        }
    }
}
