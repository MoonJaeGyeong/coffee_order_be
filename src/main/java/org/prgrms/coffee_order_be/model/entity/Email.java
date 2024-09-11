package org.prgrms.coffee_order_be.model.entity;

import lombok.Getter;
import org.springframework.util.Assert;

import java.util.Objects;
import java.util.regex.Pattern;

@Getter
public class Email {

    private final String email;

    public Email(String email){
        Assert.notNull(email, "address should not be null");
        Assert.isTrue(email.length() >= 4 && email.length() <= 50, "address length must be between 4 and 50 characters.");
        Assert.isTrue(checkAddress(email), "Invalid email address");
        this.email = email;
    }

    private static boolean checkAddress(String address){
        return Pattern.matches("\\b[\\w\\.-]+@[\\w\\.-]+\\.\\w{2,4}\\b", address);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email email = (Email) o;
        return Objects.equals(email, email.email);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(email);
    }

    @Override
    public String toString() {
        return "Email{" +
                "address='" + email + '\'' +
                '}';
    }
}
