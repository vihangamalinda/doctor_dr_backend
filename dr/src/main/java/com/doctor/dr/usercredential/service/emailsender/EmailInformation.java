package com.doctor.dr.usercredential.service.emailsender;

public class EmailInformation {
    private final String toEmail;
    private final String username;
    private final String password;
    private final boolean isAccountRegistration;


    public EmailInformation(String toEmail, String username, String password, boolean isAccountRegistration) {
        this.toEmail = toEmail;
        this.username = username;
        this.password = password;
        this.isAccountRegistration = isAccountRegistration;
    }

    public String getToEmail() {
        return toEmail;
    }

    public String getSubject() {
        if (this.isAccountRegistration) {
            return "Your has been Registered Successfully on Doctor DR Application";
        }
        return "Your Account Credentials has successfully updated on Doctor DR Application";
    }

    public String getEmailBody() {
        return "\n Your account has successfully registered on Doctor DR application. Following are the User credentials: \n" +
                "Username: " + username + "\n" +
                "Password: " + password + "\n";

    }
}
