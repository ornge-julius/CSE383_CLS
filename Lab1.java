/*
   Campbest
   383-f15

   Program to decrypt message

 */

package lab1;

import java.io.*;
import blowfish.Blowfish;

public class Lab1 {
	public static void main(String args[]) throws IOException {
		//get file size
		File fin = new File(args[0]);
		long size = fin.length();

		//read in file
		byte msg[] = new byte[(int)size];
		FileInputStream  fis = new FileInputStream(args[0]);
		int n = fis.read(msg);
		if (n!=size) {
			System.err.println("Error reading file fully");
			System.exit(-1);
		}


		for(int i = 0; i < 256; i++){
			for(int j = 0; j < 256; j++){
				//create key
				byte key[] = new byte[] {(byte)i, (byte)j, (byte)i, (byte)j, (byte)i, (byte)j, (byte)i, (byte)j};

				//create cipher
				Blowfish cipher = new Blowfish(key);

				//try to decrypt
				byte clear[] = cipher.decrypt(msg,(int)size);
				String finalMessage = new String(clear);
				System.out.println(finalMessage);
			}
		}
	}		
}

