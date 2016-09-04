package org.races.util;

import java.util.HashMap;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class Encryptor {
    private static String alphabet = "abcdefghijklmnopqrstuvwxyz";
	public String encryptText(String eDatas) throws InterruptedException {
		// Create a HashMap
		// A hash map takes keys and values, which are both Characters in this
		// case.
		HashMap<Character, Character> alphaMap = new HashMap<Character, Character>();
		int shift;
		// Get the text from the app and store it in a String variable.
		String textNum = "1";
		//String eDatas = "SASHI";
		// Check to see if a "Shift Factor" value was entered.
		// If there wasn't, set shift to zero,
		// Otherwise parse the input value to an integer so we can use it.
		if (!textNum.equals("")) {
			shift = Integer.parseInt(textNum) % 26;
		} else {
			shift = 0;
		}
		// Map every letter of the alphabet to another letter in the alphabet,
		// shifted by x places.
		for (int i = 0; i < alphabet.length(); i++) {
			alphaMap.put(alphabet.charAt(i), alphabet.charAt((i + shift) % 26));
		}
		// Get input text and put it all to lower-case so it's easy to convert
		String inputText = eDatas.toLowerCase();
		String outputText = "";
		// Go to each letter and switch it with it's shifted counterpart
		for (int j = 0; j < inputText.length(); j++) {
			outputText = outputText.concat(alphaMap.get(inputText.charAt(j))
					.toString());
		}
		// Output the encrypted text
		return outputText;
	}

	public String decryptText(String dats) throws InterruptedException {
		HashMap<Character, Character> alphaMap = new HashMap<Character, Character>();
		int shift;
		String textNum = "1";
		if (!textNum.equals("")) {
			shift = Integer.parseInt(textNum) % 26;
		} else {
			shift = 0;
		}
		for (int i = 0; i < alphabet.length(); i++) {
			alphaMap.put(alphabet.charAt((i + shift) % 26), alphabet.charAt(i));
		}
		String inputText = dats.toLowerCase();
		String outputText = "";
		for (int j = 0; j < inputText.length(); j++) {
			outputText = outputText.concat(alphaMap.get(inputText.charAt(j))
					.toString());
		}
		return outputText;
	}
	 public static void main(String[] args) throws InterruptedException {
		Encryptor ee = new Encryptor();
		String edata = ee.encryptText("salesTeam");
		System.out.println(edata);
	}
}
