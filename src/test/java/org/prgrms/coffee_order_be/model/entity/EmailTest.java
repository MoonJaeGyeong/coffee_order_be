package org.prgrms.coffee_order_be.model.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailTest {
    @Test
    public void testInvalidEmail(){
        assertThrows(IllegalArgumentException.class, () ->{
            var email = new Email("acccc");
        });
    }

    @Test
    public void testValidEmail() {
        var email = new Email("mjk8087@naver.com");
        assertTrue(email.getEmail().equals("mjk8087@naver.com"));
    }

    @Test
    public void testEqEmail() {
        var email = new Email("mjk8087@naver.com");
        var email2 = new Email("mjk8087@naver.com");
        assertEquals(email, email2);
    }

}