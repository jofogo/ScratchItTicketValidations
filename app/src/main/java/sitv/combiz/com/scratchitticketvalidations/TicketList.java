package sitv.combiz.com.scratchitticketvalidations;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class TicketList implements Parcelable{
    private ArrayList<String> listTicketCodes;
    private ArrayList<Integer> listTicketsSelected;


    public TicketList(ArrayList<String> listTicketCodes, ArrayList<Integer> listTicketsSelected) {
        this.listTicketCodes = listTicketCodes;
        this.listTicketsSelected = listTicketsSelected;
    }

    public void setTicketCodeList (ArrayList<String>listTicketCodes) {
        this.listTicketCodes = listTicketCodes;

    }

    public ArrayList<String> getTicketCodeList () {
        return listTicketCodes;
    }

    public void setTicketsSelected (ArrayList<Integer> listTicketsSelected) {
        this.listTicketsSelected = listTicketsSelected;
    }

    public ArrayList<Integer> getListTicketsSelected() {
        return listTicketsSelected;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(this.listTicketCodes);
        dest.writeValue(this.listTicketsSelected);

    }

    public TicketList (Parcel parcel) {
   //     listTicketCodes = parcel.readArrayList(getTicketCodeList());

//        listTicketsSelected = parcel.
    }



    public static final Parcelable.Creator<TicketList> CREATOR = new Parcelable.Creator<TicketList>() {
        @Override
        public TicketList createFromParcel(Parcel source) {
            return null;
        }

        @Override
        public TicketList[] newArray(int size) {
            return new TicketList[0];
        }
    };


    public int describeContents() {
        return hashCode();
    }
}
