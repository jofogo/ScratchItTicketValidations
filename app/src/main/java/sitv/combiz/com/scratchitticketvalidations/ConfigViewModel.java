package sitv.combiz.com.scratchitticketvalidations;

import android.arch.lifecycle.ViewModel;

import java.net.MalformedURLException;
import java.net.URL;

public class ConfigViewModel extends ViewModel {

    private static String LicenseKey;
    private static String IMEI = "";
    private static URL ServerURL;
    private static String Session_ID;

    private static Boolean isPermitted=false;
    private static Boolean isLoggedIn=false;

    private static String version = "";
    private static String company = "";


    public String getServerURL() {
        return ServerURL.toString();
    }

    public void setServerURL(String URL) {
        try {
            ServerURL = new URL(URL);
        } catch (MalformedURLException murle) {
            ServerURL = null;
            murle.printStackTrace();

        }
    }

    public Boolean getIsPermitted() {
        return isPermitted;
    }

    public void setIsPermitted(Boolean isPermitted) {
        ConfigViewModel.isPermitted = isPermitted;
    }

    public Boolean getIsLoggedIn() {
        return isLoggedIn;
    }

    public void setIsLoggedIn(Boolean isLoggedIn) {
        ConfigViewModel.isLoggedIn = isLoggedIn;
    }

    public String getLicenseKey() {
        return LicenseKey;
    }

    public void setLicenseKey(String licenseKey) {
        LicenseKey = licenseKey;
    }

    public String getIMEI() {
        return IMEI;
    }

    public void setIMEI(String IMEI) {
        ConfigViewModel.IMEI = IMEI;
    }

    public String getSession_ID() {
        return Session_ID;
    }

    public void setSession_ID(String session_ID) {
        Session_ID = session_ID;
    }

    public void setVersion(String version) {
        ConfigViewModel.version = version;
    }

    public void setCompany(String company) {
        ConfigViewModel.company = company;
    }

    public String getVersion() {
        return version;
    }

    public String getCompany() {
        return company;
    }

    public String getAboutMessage() {
        try {
            return "v " + getVersion() + "\n" +
                    "IMEI: " + getIMEI() + "\n" +
                    getCompany();

        } catch (NullPointerException npe) {
            return "Error!";
        }
    }






}
