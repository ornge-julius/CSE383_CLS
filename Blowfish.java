package blowfish;
/*
 * Scott Campbell
 * CSA467
 * Fall 2015
 * Assignment: None
 * Description: Blowfish class - used to encrypt data using the blowfish algorithm.
 * 
 */

import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.*;
import java.io.*;
import java.math.BigInteger;

/**
 * 
 * @author scott
 *
 * Class to encrpt/decrypt data using the blowfish algorithm
 * 
 */
public class Blowfish {
	SecretKeySpec key = null;		//stores symmetric key
	String algorithm = "Blowfish";	//use the blowfish algorithm

	/*
	 * Constructor
	 * Loads the key from given file
	 */
	public Blowfish(String filename) throws IOException{
		loadKey(filename);
	}

	/*
	 * Constructor
	 * Loads the key from byte array
	 */
	public Blowfish(byte key[]) throws IOException{
		setKey(key);
	}

	//generic constructor
	public Blowfish() {
	}



	/*
	 * Encrypts STRING data
	 * 
	 */
	public byte[] encryptString(String msg) throws IOException {
		return encrypt(msg.getBytes(),msg.length());
	}

	/*
	 * encrypts byte data
	 */
	public byte[] encrypt(byte[] clear,int num) throws IOException {
		byte [] encrypted= null;
		try {
			// create a cipher object that will perform the encryption
			Cipher cipher = Cipher.getInstance(algorithm);
			cipher.init(Cipher.ENCRYPT_MODE, key);
			encrypted=	cipher.doFinal(clear,0,num);
		} catch (NoSuchAlgorithmException err1) {
			throw new IOException("No such algorithm");
		}
		catch (InvalidKeyException err2) {
			throw new IOException("invalid key");
		}
		catch (IllegalBlockSizeException err3) {
			throw new IOException("illegal block size");
		}
		catch (BadPaddingException err4) {
			throw new IOException("bad padding");
		}
		catch (NoSuchPaddingException err5) {
			throw new IOException("no such padding");
		}
		return encrypted;
	}


	/*
	 * decrypts string data
	 */
	public String decryptString(byte[] encrypted, int num) throws IOException{
		String m = null;
		m = new String(decrypt(encrypted,num));
		return m;
	}


	/*
	 * decrypts byte data
	 */
	public byte[] decrypt(byte[] encrypted, int num) throws IOException {
		byte [] clear = null;
		// create a cipher object that will perform the encryption
		try {
			Cipher cipher = Cipher.getInstance(algorithm);
			cipher.init(Cipher.DECRYPT_MODE, key);
			clear = 
				cipher.doFinal(encrypted,0,num);
		} catch (NoSuchAlgorithmException err1) {
			throw new IOException("No such algorithm");
		}
		catch (InvalidKeyException err2) {
			throw new IOException("invalid key");
		}
		catch (IllegalBlockSizeException err3) {
			throw new IOException("illegal block size");
		}
		catch (BadPaddingException err4) {
			throw new IOException("bad padding");
		}
		catch (NoSuchPaddingException err5) {
			throw new IOException("no such padding");
		}
		return clear;

	}


	/*
	 * create a random, new unique private key
	 */
	public byte[] createKey() throws IOException {
		byte raw[] = null;
		try {
			KeyGenerator kgen = KeyGenerator.getInstance(algorithm);
			SecretKey newKey= kgen.generateKey();	// generate the key
			raw = newKey.getEncoded();			// store the key in this array
		} catch (NoSuchAlgorithmException err) {
			throw new IOException("No such algoriothm");
		}
		return raw;	
	}

	public void createKey(String filename) throws IOException {
		// get the blowfish instance
		byte b[] = createKey();
		// output the key via serialize
		FileOutputStream fout = new FileOutputStream(filename);
		fout.write(b);
		fout.close();
	}

	/*
	 * loads key from file
	 */
	public void loadKey(String filename) throws IOException {
		// read in and create the key
		FileInputStream fin = new FileInputStream(filename);
		byte tmp[] = new byte[200];
		int r = fin.read(tmp);
		byte rawKey[] = new byte[r];
		System.arraycopy(tmp,0,rawKey,0,r);
		setKey(rawKey);
		fin.close();
	}

	/*
	 * Sets key to data in byte array
	 */
	public void setKey(byte keyData[]) throws IOException {
		key= new SecretKeySpec(keyData, algorithm); // convert this key to the proper format
	}

	//from http://www.java2s.com/Code/Java/Data-Type/byteArrayToHexString.htm
	public static String toHex(byte raw[]) {
		BigInteger bi = new BigInteger(1,raw);
		return String.format("%0" + (raw.length<<1) + "X",bi);
	}

	//from http://stackoverflow.com/questions/140131/convert-a-string-representation-of-a-hex-dump-to-a-byte-array-using-java
	public static byte[] fromHex(String s) {
		int len = s.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
					+ Character.digit(s.charAt(i+1), 16));
		}
		return data;
	}

}
