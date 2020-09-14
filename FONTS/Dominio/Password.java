package Dominio;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

public class Password {
    public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchProviderException {
        String password = "password";
        String securePassword = hashPassword(password);
        System.out.println(securePassword);
        if (checkPassword(password, securePassword)) System.out.println("Correct");
        else System.out.println("Incorrect");
        password = "password";
        securePassword = hashPassword(password);
        System.out.println(securePassword);
        if (checkPassword("password", securePassword)) System.out.println("Correct");
        else System.out.println("Incorrect");
    }
     
    public static String hashPassword(String password) {
        String hash = null;
        try {
            //MD5 hashing algorithm
            MessageDigest md = MessageDigest.getInstance("MD5");
            
            //Generate salt
            SecureRandom sr = SecureRandom.getInstance("SHA1PRNG", "SUN");
            byte[] salt = new byte[16];
            sr.nextBytes(salt);
            
            //Convert bytes to hex
            StringBuilder sbSalt = new StringBuilder();
            for (int i = 0; i < salt.length; ++i){
                String hex = Integer.toString((salt[i] & 0xff) + 0x100, 16).substring(1);
                sbSalt.append(hex);
            }
            String saltS = sbSalt.toString();

            //Add salt to hash
            md.update(salt);
            //Hash password 
            byte[] bytes = md.digest(password.getBytes());

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length ; ++i) {
                String hex = Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1);
                sb.append(hex);
            }

            //Get complete hashed password in hex format
            hash = sb.toString();
            hash = saltS + hash;
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
        return hash;
    }

    public static boolean checkPassword(final String password, final String hashed){
        String hash = null;
        try {
            //MD5 hashing algorithm
            MessageDigest md = MessageDigest.getInstance("MD5");
            
            //Get salt
            byte[] salt = new byte[16];
            for (int i = 0; i < 16; ++i){
                int val = Integer.parseInt(hashed.subSequence(i*2, (i+1)*2).toString(),16);
                if (val > 127) val -= 256;
                salt[i] = (byte)val;
            }

            //Add salt to hash
            md.update(salt);
            //Hash password 
            byte[] bytes = md.digest(password.getBytes());
            //Convert bytes to hex
            StringBuilder sb = new StringBuilder();
            for(int i = 0; i < bytes.length ; ++i) {
                String hex = Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1);
                sb.append(hex);
            }
            //Get complete hashed password in hex format
            hash = sb.toString();
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
        return hashed.subSequence(32, 64).toString().equals(hash);
    }
}