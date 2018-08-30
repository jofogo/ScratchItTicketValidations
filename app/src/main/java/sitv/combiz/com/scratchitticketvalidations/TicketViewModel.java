package sitv.combiz.com.scratchitticketvalidations;

import android.arch.lifecycle.ViewModel;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class TicketViewModel extends ViewModel {


    private static final int ticketMinLength = 22;
    private static String ticketCount;

    private static ArrayList<String> ticketCodes = new ArrayList<String>();
    private static ArrayList<Integer> ticketValues = new ArrayList<Integer>();
    private static ArrayList<Boolean> ticketSelected = new ArrayList<Boolean>();


    public Boolean hasTicketCode(String ticketCode) {
        try {
            return ticketCodes.contains(ticketCode);
        } catch (NullPointerException npe) {
            return false;
        }
    }

    public ArrayList<String> getTicketCodesList() {
        return ticketCodes;
    }

    public ArrayList<Integer> getTicketValuesList() {
        return ticketValues;
    }

    public ArrayList<Boolean> getTicketSelectedList() {
        return ticketSelected;
    }

    public Boolean getTicketSelected(int index) {
        try {
            return ticketSelected.get(index);
        } catch (ArrayIndexOutOfBoundsException aioobe) {
            return false;
        }
    }

    public String getTicketCount() {
        return ticketCount;
    }

    public String getTicketCode(int index) {
        try {
            return ticketCodes.get(index);
        } catch (ArrayIndexOutOfBoundsException aioobe) {
            return "";
        }
    }

    public Integer getTicketValue(int index) {
        try {
            return ticketValues.get(index);
        } catch (ArrayIndexOutOfBoundsException aioobe) {
            return -1;
        }
    }

    public static int getTicketMinLength() {
        return ticketMinLength;
    }

    public static int getTicketMaxLength() {
        return ticketMinLength + 5;
    }

    public void setTicketCount(int ticketCount) {
        TicketViewModel.ticketCount = String.format(Locale.getDefault(), "%03d", ticketCount);
    }

    public void addTicketValue(int val) {
        ticketValues.add(val);
    }

    public void addNewTicket(String ticket) {
        try {
            addTicketCode(formatTicket(ticket));
            addTicketSelected(false);
            setTicketCount(ticketCodes.size());
        } catch (NullPointerException npe) {
            npe.printStackTrace();
        }
    }

    public String formatTicket(String ticket) {
        if (ticket.length() == getTicketMinLength()) {
            return getTicketFormatted(ticket);
        } else {
            return getTicketUnformatted(ticket);
        }
    }

    public void addDemoTicket(String ticket, Boolean isSelected) {
        addTicketCode(formatTicket(ticket));
        addTicketSelected(isSelected);
        setTicketCount(ticketCodes.size());
    }

    public String getTicketFormatted(String ticket) {
        return String.format("%s-%s-%s %s %s-%s",
                ticket.substring(0, 3),
                ticket.substring(3,9),
                ticket.substring(9,12),
                ticket.substring(12,18),
                ticket.substring(18,20),
                ticket.substring(20,22));
    }

    public String getTicketUnformatted(String ticket) {
        return ticket.replaceAll("\\D", "");
    }

    private void addTicketCode(String ticket) {
        try {
            ticketCodes.add(ticket);
        } catch (NullPointerException npe) {
            npe.printStackTrace();
        }
    }

    private void addTicketSelected(Boolean isSelected) {
        ticketSelected.add(isSelected);
    }

    public void selectTicket(int index) {
        try {
            ticketSelected.set(index, true);
        } catch (ArrayIndexOutOfBoundsException aioobe) {
            aioobe.printStackTrace();
        }
    }

    public void deselectTicket(int index) {
        try {
            ticketSelected.set(index, false);
        } catch (ArrayIndexOutOfBoundsException aioobe) {
            aioobe.printStackTrace();
        }
    }

    public void deselectAllTickets() {
        try {
            for (int ctr = 0; ctr < getTicketTotal(); ctr++) {
                deselectTicket(ctr);
            }
        } catch (NullPointerException npe) {
            npe.printStackTrace();
        }
    }

    public void selectAllTickets() {
        try {
            for (int ctr = 0; ctr < getTicketTotal(); ctr++) {
                selectTicket(ctr);
            }
        } catch (NullPointerException npe) {
            npe.printStackTrace();
        }
    }

    public int getSelectedTotal() {
        try {
            int selected = 0;
            for (int ctr = 0; ctr < getTicketTotal(); ctr++) {
                if (getTicketSelected(ctr)) {
                    selected++;
                }
            }
            return selected;
        } catch (NullPointerException npe) {
            npe.printStackTrace();
            return 0;
        }
    }

    public void deleteTicket(int index) {
        try {
            ticketCodes.remove(index);
            ticketSelected.remove(index);
        } catch (ArrayIndexOutOfBoundsException aioobe) {
            aioobe.printStackTrace();
        }
    }

    public void clearTickets() {
        ticketCodes.clear();
        ticketSelected.clear();
        setTicketCount(ticketCodes.size());
    }

    public int getTicketTotal() {
        setTicketCount(ticketCodes.size());
        return ticketCodes.size();
    }





}
