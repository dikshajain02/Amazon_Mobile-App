/**
 * Created by Diksha on 11/30/2016.
 */
public class User {

    String strName;
    String strEmail;
    String strPwd;
    String bisValid;

    public User(String strName, String strEmail, String strPwd, String bisValid) {
        this.strName = strName;
        this.strEmail = strEmail;
        this.strPwd = strPwd;
        this.bisValid = bisValid;
    }

    public String getStrName() {
        return strName;
    }

    public void setStrName(String strName) {
        this.strName = strName;
    }

    public String getStrEmail() {
        return strEmail;
    }

    public void setStrEmail(String strEmail) {
        this.strEmail = strEmail;
    }

    public String getStrPwd() {
        return strPwd;
    }

    public void setStrPwd(String strPwd) {
        this.strPwd = strPwd;
    }

    public String getBisValid() {
        return bisValid;
    }

    public void setBisValid(String bisValid) {
        this.bisValid = bisValid;
    }
}
