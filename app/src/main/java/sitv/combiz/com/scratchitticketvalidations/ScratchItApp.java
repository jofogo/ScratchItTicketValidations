package sitv.combiz.com.scratchitticketvalidations;

import android.app.Application;

public class ScratchItApp extends Application {


    public int getTicketCodeLength() {
        return getResources().getInteger(R.integer.app_barcode_length);
    }

    public int getTicketCodeFormattedLength() {
        return getResources().getInteger(R.integer.app_barcode_max);
    }

    public String formatTicketCode (String ticketCode) {
        if (ticketCode.length() == getTicketCodeLength()) {
            return String.format("%s-%s-%s %s %s-%s",
                    ticketCode.substring(0, 3),
                    ticketCode.substring(3,9),
                    ticketCode.substring(9,12),
                    ticketCode.substring(12,18),
                    ticketCode.substring(18,20),
                    ticketCode.substring(20,22));
        } else {
            return ticketCode.replaceAll("\\D", "");
        }

    }

}
