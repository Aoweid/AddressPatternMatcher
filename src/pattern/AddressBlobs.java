package pattern;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class AddressBlobs {
	private ArrayList<String> words = new ArrayList<>();
	private ArrayList<String> addresses = new ArrayList<>();
	private File inputFile;

	public AddressBlobs(File txtFile) {
		inputFile = txtFile;
		readFile();
		findAddresses();
	}

	public void readFile() {
		try {
			Scanner sc = new Scanner(inputFile);
			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				String[] lineSplit = line.split(" ");
				for (String content : lineSplit) {
					words.add(content);
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void findAddresses() {
		for (int i = 0; i < words.size(); i++) {
			String word = words.get(i);
			Pattern pattern1 = Pattern.compile("[0-9]*");
			if (ifZipcode(word)) {
				String address = word;
				if (ifStateWord(words.get(i - 1))) {
					address = words.get(i - 1) + " " + address;
					if (i >= 10) {
						for (int j = 2; j < 11; j++) {
							String content = words.get(i - j);
							if (ifAddressWord(content)) {
								address = words.get(i - j) + " " + address;
							} 
							else if (pattern1.matcher(content).matches()) {
								if (ifRoomNumber(i - j)) {
									address = words.get(i - j) + " " + address;
								} else if (ifPOBOXNumber(i - j)) {
									address = words.get(i - j - 2) + " " + words.get(i - j - 1) + " " + words.get(i - j)
											+ " " + address;
									break;
								} else {
									address = words.get(i - j) + " " + address;
									break;
								}

							} 
							else if (content.contains(":")) {
								break;
							}
							else {
								break;
							}
						}
					}
					else {
						for (int j = 2; j <= i; j++) {
							String content = words.get(j);
							if (ifAddressWord(content)) {
								address = words.get(i - j) + " " + address;
							} 
							else if (pattern1.matcher(content).matches()) {
								if (ifRoomNumber(i - j)) {
									address = words.get(i - j) + " " + address;
								} else if (ifPOBOXNumber(i - j)) {
									address = words.get(i - j - 2) + " " + words.get(i - j - 1) + " " + words.get(i - j)
											+ " " + address;
									break;
								} else {
									address = words.get(i - j) + " " + address;
									break;
								}

							} 
							else if (content.contains(":")) {
								break;
							}
							else {
								break;
							}
						}
					}
					addresses.add(address);
				}
			}
		}
	}

	public boolean ifZipcode(String word) {
		boolean is = false;
		Pattern pattern = Pattern.compile("[[0-9]-—]*");
		if (pattern.matcher(word).matches()) {
			Pattern pattern1 = Pattern.compile("[0-9]*");
			if (word.length() == 10) {
				String beginStr = word.substring(0, 5);
				String middleChar = word.substring(5, 6);
				String endStr = word.substring(6, 10);
				if (pattern1.matcher(beginStr).matches() && pattern1.matcher(endStr).matches()) {
					if (middleChar.equals("-") || middleChar.equals("—")) {
						is = true;
					}
				}
			}
			if (word.length() == 5) {
				if (pattern1.matcher(word).matches()) {
					is = true;
				}
			}
		}
		return is;
	}

	public boolean ifPOBOXNumber(int index) {
		if (index < 2)
			return false;
		if (words.get(index - 1).equals("BOX") || words.get(index - 1).equals("Box")
				|| words.get(index - 1).equals("B0X") || words.get(index - 1).equals("B0x")) {
			if (words.get(index - 2).equals("PO") || words.get(index - 2).equals("P0")
					|| words.get(index - 2).equals("po") || words.get(index - 2).equals("p0")
					|| words.get(index - 2).equals("P.O") || words.get(index - 2).equals("P.O.")
					|| words.get(index - 2).equals("P.0") || words.get(index - 2).equals("P.0.")
					|| words.get(index - 2).equals("p.o") || words.get(index - 2).equals("p.o.")) {
				return true;
			}
		}
		return false;
	}

	public boolean ifStateWord(String word) {
		if (word.length() != 2)
			return false;
		Pattern statePattern1 = Pattern.compile("[a-z]*");
		Pattern statePattern2 = Pattern.compile("[A-Z]*");
		if (statePattern1.matcher(word).matches() || statePattern2.matcher(word).matches() || word.equals("0H")) {
			return true;
		}
		return false;

	}

	public boolean ifRoomNumber(int index) {
		if (index == 0)
			return false;
		if (words.get(index - 1).equals("UNIT") || words.get(index - 1).equals("Unit")
				|| words.get(index - 1).equals("Room") || words.get(index - 1).equals("ROOM")
				|| words.get(index - 1).equals("Suite") || words.get(index - 1).equals("SUITE")) {
			return true;
		}
		return false;
	}

	public boolean ifAddressWord(String word) {
		if (word.equals(""))
			return false;
		
		boolean is = false;
		Pattern pattern1 = Pattern.compile("[A-Z]*");
		Pattern pattern2 = Pattern.compile("[a-z]*");
		Pattern pattern3 = Pattern.compile("[0-9]*");
		if(word.length()==1){
			if (pattern1.matcher(word).matches()||pattern2.matcher(word).matches()) {
				return true;
			}
		}
		if(word.contains(",")){
			if(!word.substring(word.length()-1).equals(",")){
				return false;
			}
			else{
				if (pattern1.matcher(word.substring(0,word.length()-1)).matches()) {
					return true;
				}
				if (pattern1.matcher(word.substring(0, 1)).matches() && pattern2.matcher(word.substring(1,word.length()-1)).matches()) {
					return true;
				}
			}
		}
		
		if (pattern1.matcher(word).matches()) {
			return true;
		}
		if (pattern1.matcher(word.substring(0, 1)).matches() && pattern2.matcher(word.substring(1)).matches()) {
			return true;
		}
		if (word.length() >= 3) {
			if (pattern3.matcher(word.substring(0, word.length() - 2)).matches()) {
				if (word.substring(word.length() - 2, word.length()).equals("th")
						|| word.substring(word.length() - 2, word.length()).equals("rd")
						|| word.substring(word.length() - 2, word.length()).equals("nd")
						|| word.substring(word.length() - 2, word.length()).equals("st")
						|| word.substring(word.length() - 2, word.length()).equals("TH")
						|| word.substring(word.length() - 2, word.length()).equals("RD")
						|| word.substring(word.length() - 2, word.length()).equals("ND")
						|| word.substring(word.length() - 2, word.length()).equals("ST")) {
					return true;
				}
			}
		}
		return false;
	}

	public ArrayList<String> getWords() {
		return words;
	}

	public ArrayList<String> getAdresses() {
		return addresses;
	}
}
