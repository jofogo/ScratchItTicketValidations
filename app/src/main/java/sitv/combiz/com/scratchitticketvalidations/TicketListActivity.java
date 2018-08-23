package sitv.combiz.com.scratchitticketvalidations;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class TicketListActivity extends AppCompatActivity {
    ArrayList<String> ticketList = new ArrayList<String>();
    ArrayList<Integer> ticketTicked =  new ArrayList<Integer>();

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
        ticketTicked.add(0);
        ticketTicked.add(0);
        ticketTicked.add(0);
        ticketTicked.add(1);
        ticketTicked.add(0);
        ticketTicked.add(0);
        ticketTicked.add(1);
        ticketTicked.add(0);
        ticketTicked.add(1);
    }

    private void selectAllTickets() {

    }

    private void populateTable() {
        int ticketTotal = ticketList.size();
        String ticked = "no";
        for (int ctr = 0; ctr < ticketTotal; ctr++) {
            if (ticketTicked.get(ctr).intValue() == 0) {
                ticked = "no";
            } else {
                ticked = "yes";
            }
            Log.i("Row" + (ctr+1), ticketList.get(ctr) + " - " + ticked);
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_list);
        addDemoTickets();
        addTicketTicked();
        populateTable();
    }
}
