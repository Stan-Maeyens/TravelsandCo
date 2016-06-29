package com.maeyens.stan.travelo.security;

/**
 * Created by Thomas De Praetere on 29-Jun-16.
 */

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.maeyens.stan.travelsandco.data.security.PasswordSecurity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 *
 * @author Thomas De Praetere
 */
public class PasswordSecurityTest {

    private char[] password1 = "abc123".toCharArray();
    private char[] password2 = "abc1234".toCharArray();

    private byte[] attempt1;
    private byte[] attempt2;
    private byte[] attempt3;

    private PasswordSecurity ps = new PasswordSecurity();

    public PasswordSecurityTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of hash method, of class PasswordSecurity.
     */
    @Test
    public void testHash() {
        attempt1 = ps.hash(password1);
        attempt2 = ps.hash(password1);
        attempt3 = ps.hash(password2);

        assertEquals(attempt1.length, attempt2.length);
        assertEquals(attempt2.length, attempt3.length);
        assertEquals(attempt1.length, attempt3.length);

        boolean same = true;
        for (int i = 0; i < attempt1.length; i++) {
            if (attempt1[i] != attempt2[i]) {
                same = false;
                break;
            }
        }

        if (same) {
            fail();
        }
        same = true;
        for (int i = 0; i < attempt2.length; i++) {
            if (attempt2[i] != attempt3[i]) {
                same = false;
                break;
            }
        }

        if (same) {
            fail();
        }
        same = true;
        for (int i = 0; i < attempt1.length; i++) {
            if (attempt1[i] != attempt3[i]) {
                same = false;
                break;
            }
        }

        if (same) {
            fail();
        }
        same = true;
    }

    /**
     * Test of authenticate method, of class PasswordSecurity.
     */
    @Test
    public void testAuthenticate() {
        attempt1 = ps.hash(password1);
        attempt2 = ps.hash(password1);
        attempt3 = ps.hash(password2);

        assertTrue(ps.authenticate(password1, attempt1));
        assertTrue(ps.authenticate(password1, attempt2));
        assertTrue(ps.authenticate(password2, attempt3));

        assertFalse(ps.authenticate(password1, attempt3));
        assertFalse(ps.authenticate(password2, attempt1));
        assertFalse(ps.authenticate(password2, attempt2));
    }

}

