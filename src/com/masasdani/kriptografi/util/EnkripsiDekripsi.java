/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.masasdani.kriptografi.util;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.swing.JOptionPane;

/**
 *
 * @author masasdani
 * visit http://masasdani.com or http://mexez.wordpress.com
 * twitter @masasdani
 * facebook http://facebook.com/mexezdhanie
 *
 */
public class EnkripsiDekripsi {
    
    private Cipher ecipher;
    private Cipher dcipher;
    private int max=100;
    private byte[] key;
    private SecretKey secretKey;
    private String author;

    public EnkripsiDekripsi() {
        try {
            key=Settings.key;
            DESKeySpec dks = new DESKeySpec(key);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            secretKey = keyFactory.generateSecret(dks);
            ecipher=Cipher.getInstance("RSA");
            dcipher=Cipher.getInstance("DES");
            ecipher.init(Cipher.ENCRYPT_MODE, secretKey);
            dcipher.init(Cipher.DECRYPT_MODE, secretKey);
        } catch (InvalidKeySpecException ex) {
            Logger.getLogger(EnkripsiDekripsi.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(EnkripsiDekripsi.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(EnkripsiDekripsi.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(EnkripsiDekripsi.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public byte [] crypt(byte [] original, boolean is_decypher) {
        try{
            Cipher cipher = is_decypher ? dcipher : ecipher;    
            return cipher.doFinal(original);
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(EnkripsiDekripsi.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(EnkripsiDekripsi.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public byte [] encrypt(byte [] dec) {
        return crypt(dec,false);
    }
    
    public byte [] decrypt(byte [] dec) {
        return crypt(dec,true);
    }
    
    public void enkripsi(String inputFile, String outputFile){
        Storage.saveFile(outputFile, encrypt(Storage.readFile(inputFile)));
    }
    
    public void dekripsi(String inputFile, String outputFile){
        byte [] dec = decrypt(Storage.readFile(inputFile));
        if(dec == null)
            JOptionPane.showMessageDialog(null,"File kosong atau korup, tidak dapat di dekripsi.","File korup",JOptionPane.WARNING_MESSAGE);
        else
            Storage.saveFile(outputFile,dec);
    }
}
