/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.maeyens.stan.travelsandco.data.security;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * This class can be used to secure passwords.
 *
 * @author Thomas De Praetere
 */
public class PasswordSecurity {

    /**
     * A secure random number generator
     */
    private static final SecureRandom random = new SecureRandom();
    /**
     * Which algorithm to use, it is specific for salt-password hashing.
     */
    private static final String ALGORITHM = "PBKDF2WithHmacSHA1";
    /**
     * The amount of iterations the PBKDF2 algorithm must undertake to prevent
     * brute-force generating all salt-hash combinations.
     */
    private static final int AMOUNT_OF_ITERATIONS = 10000;
    /**
     * The length of a salt in bytes.
     */
    private static final int SALT_SIZE = 32;
    /**
     * The length of a hash in bytes.
     */
    private static final int HASH_SIZE = 512;

    /**
     * Generates a hash for an algorithm. This generates a random salt for a
     * password and used it to hash the password.
     *
     * @param password The password to be hashed.
     * @return An array filled with the random salt followed by the hashed
     * password.
     */
    public byte[] hash(char[] password) {
        byte[] salt = new byte[SALT_SIZE];
        random.nextBytes(salt);
        byte[] hash = encrypt(password, salt);

        byte[] full_encoding = new byte[salt.length + hash.length];
        System.arraycopy(salt, 0, full_encoding, 0, salt.length);
        System.arraycopy(hash, 0, full_encoding, salt.length, hash.length);
        return full_encoding;
    }

    /**
     * Checks whether a given password is the same as the encrypted version.
     *
     * @param password A password.
     * @param encrypted The encrypted version of a password to test whether it
     * is the same.
     * @return True if the password belongs to the encrypted version.
     */
    public boolean authenticate(char[] password, byte[] encrypted) {
        byte[] salt = new byte[SALT_SIZE];
        byte[] hash = new byte[encrypted.length-SALT_SIZE];
        System.arraycopy(encrypted, 0, salt, 0, SALT_SIZE);
        System.arraycopy(encrypted, SALT_SIZE, hash, 0, hash.length);
        byte[] check = encrypt(password, salt);
        for (int i = 0; i < check.length; i++) {
            if (check[i] != hash[i]) {
                return false;
            }
        }
        return true;
    }

    /**
     * This method encrypts a password together with a salt.
     *
     * @param password The password.
     * @param salt The salt.
     * @return The encrypted password.
     */
    private byte[] encrypt(char[] password, byte[] salt) {
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance(ALGORITHM);
            KeySpec spec = new PBEKeySpec(password, salt, AMOUNT_OF_ITERATIONS, HASH_SIZE);
            SecretKeyFactory f = SecretKeyFactory.getInstance(ALGORITHM);
            byte[] hash = f.generateSecret(spec).getEncoded();
            return hash;
        } catch (InvalidKeySpecException | NoSuchAlgorithmException ex) {
            Logger.getLogger(PasswordSecurity.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
