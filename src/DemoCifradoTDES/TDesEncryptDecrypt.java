/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DemoCifradoTDES;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * IMPORTANTE, SE DEBE INSTALAR LIBRERIAS JCE 8 (jce_policy-8.zip) zippara el correcto uso con claves de 32 caracteres, de lo contrario, solo soporta 16 caracteres
 * @author datasoft-edgardo
 */
public class TDesEncryptDecrypt {

    /**
     * Vector de Inicializacion: No se puede encriptar sin él. Es de 8 bytes de
     * longitud. No es una 2ª llave, por lo tanto,
     * no se trata de una dato que haya que esconder, únicamente hay que
     * considerar que hay que usar el mismo IV para encriptar/desencriptar un
     * mensaje concreto. Un error común es utilizar el mismo vector de
     * inicialización en todas las encriptaciones. Utilizar siempre un mismo IV
     * es equivalente en seguridad a no utilizar encriptación.
     */
    private static String TDesIV = "!QAZ2WSX";//8    
    /**
     * Llave de encriptacion: Esta es la principal información para
     * encriptar/desencriptar en los algoritmos simétricos. Toda la seguridad de
     * un sistema simétrico depende de dónde esté esta llave, cómo esté
     * compuesta y quién tiene acceso. Éste es un dato que debe conocerse única
     * y exclusivamente por los interlocutores de la comunicación. De otra
     * forma, la seguridad en la comunicación se vería comprometida.
     */
    private static String TDesKey = "5TGB&YHN7UJM(IK<5TGB&YHN";//24

    public TDesEncryptDecrypt() {
    }
    
    public TDesEncryptDecrypt(String TDesKey, String TDesIV) {
        this.TDesKey=TDesKey;
        this.TDesIV=TDesIV;
    }

    public byte[] Encrypt256(byte[] src) throws Exception {
        try {
            SecretKeySpec key = new SecretKeySpec(TDesKey.getBytes(), "DESede");
            IvParameterSpec iv = new IvParameterSpec(TDesIV.getBytes());

            Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key, iv);
            byte[] encoded = cipher.doFinal(src);
            return encoded;

        } catch (InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException ex) {
            throw new Exception("Encryption Exception: " + ex.toString());
        }
    }

    public byte[] Decrypt256(byte[] src) throws Exception {
        try {
            SecretKeySpec key = new SecretKeySpec(TDesKey.getBytes(), "DESede");
            IvParameterSpec iv = new IvParameterSpec(TDesIV.getBytes());

            Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key, iv);
            byte[] decoded = cipher.doFinal(src);
            //System.out.println(new String(decoded));            
            return decoded;
        } catch (InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException ex) {
            throw new Exception("Encryption Exception: " + ex.toString());
        }
    }

    public static String byteArrayToB64(byte[] src) {
        return Base64.getEncoder().encodeToString(src);
    }

    public static byte[] B64ToByteArray(String src) {
        return Base64.getDecoder().decode(src);
    }

}
