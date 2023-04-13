package edu.duke.ece651.team8.shared;

public class LoginMessage {
    private String account;
    private String password;

    public LoginMessage() {
        this.account = null;
        this.password = null;
    }

    public String[] getMessage() {
        String[] ans = new String[2];
        ans[0] = account;
        ans[1] = password;
        return ans;
    }

    public synchronized void setMessage(String userName, String password) {
        this.account = userName;
        this.password = password;
        notify();
    }
}
