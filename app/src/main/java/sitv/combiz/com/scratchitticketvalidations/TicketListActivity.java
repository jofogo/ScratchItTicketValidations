package sitv.combiz.com.scratchitticketvalidations;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class TicketListActivity extends AppCompatActivity{
    ArrayList<String> ticketList = new ArrayList<String>();
    ArrayList<Integer> ticketSelected =  new ArrayList<Integer>();

    TableLayout tblTickets;

    private void addDemoTickets() {
        ticketList.add("1234567890123456789011");
        ticketList.add("1234567890123456789022");
        ticketList.add("1234567890123456789033");
        ticketList.add("1234567890123456789044");
        ticketList.add("1234567890123456789055");
        ticketList.add("1234567890123456789066");
        ticketList.add("1234567890123456789077");
        ticketList.add("1234567890123456789088");
        ticketList.add("1234567890123456789099");
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
    }


    public void toggleAllTickets(View view) {
        if (true) {
            selectAllTickets();
        } else {
            deselectAllTickets();
        }
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

    private void removeTicketFromList(String ticket) {
        if (!ticket.equals("") && ticketList.contains(ticket)) {
            int idxTicket = ticketList.indexOf(ticket);
            ticketList.remove(idxTicket);
            ticketSelected.remove(idxTicket);
        }
    }



    private void populateTable() {
        String ticked = "no";
        for (int ctr = 0; ctr < getTicketTotalCount(); ctr++) {
            if (ticketSelected.get(ctr) == 0) {
                ticked = "no";
            } else {
                ticked = "yes";
            }
            Log.i("Row" + (ctr+1), ticketList.get(ctr) + " - " + ticked);
        }
    }

    private void addTableHeader() {
   /*     TableRow trHead = new TableRow(this);
        trHead.setId();
        trHead.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT));
        trHead.setWeightSum(1);
        //Column 1
        TextView lblCounter = new TextView(this);
        lblCounter.setId(20);
        lblCounter.setText(getResources().getText(R.string.ticketlist_lblcounter));
        lblCounter.set*/

    }

    private void addTableRow(String ticket, int ticked) {

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
        logTickets();
        //addDemoTickets();
        //addTicketTicked();
        //populateTable();
    }
}
