package com.doctor.dr.internalsystemconfiguration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@ConfigurationProperties("internal-system")
public class InternalSystemConfiguration {
    private String internalEmail;
    private String internalEmailPassword;
    InternalSystemConfiguration(String  internalEmail, String internalEmailPassword){
        this.internalEmail = internalEmail;
        this.internalEmailPassword = internalEmailPassword;
    }

    public String getInternalEmail() {
        return internalEmail;
    }

    public void setInternalEmail(String internalEmail) {
        this.internalEmail = internalEmail;
    }

    public String getInternalEmailPassword() {
        return internalEmailPassword;
    }

    public void setInternalEmailPassword(String internalEmailPassword) {
        this.internalEmailPassword = internalEmailPassword;
    }
}
