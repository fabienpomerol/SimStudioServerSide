/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author babusseuil
 */
public class EncryptMD5 {
    public EncryptMD5()
    {
    }
    
    public String encryptMD5(String s)
    {
        byte[] defaultBytes = s.getBytes();
        try {
            MessageDigest algorithm = MessageDigest.getInstance("MD5");
            algorithm.reset();
            algorithm.update(defaultBytes);
            byte messageDigest[] = algorithm.digest();

            StringBuilder hexString = new StringBuilder();
            for (int i = 0; i < messageDigest.length; i++) {
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            }
            s = hexString.toString() + "";
        } catch (NoSuchAlgorithmException nsae) {
        }
        return s;
    }
}
