/**
 * Created by Diksha on 11/30/2016.
 */
public class Credential {
    String strEmail;
    String strPassword;
    String bisValid;

    public Credential(String strEmail, String strPassword, String bisValid) {
        this.strEmail = strEmail;
        this.strPassword = strPassword;
        this.bisValid = bisValid;
    }

    public String getStrEmail() {
        return strEmail;
    }

    public String getStrPassword() {
        return strPassword;
    }

    public String getBisValid() {
        return bisValid;
    }
}
