package pattern;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList<File> files = new ArrayList<>();
//		String inputPath = "/Users/daw/Documents/workspace/AddressPattern/data/input";
		String inputPath = "/Users/daw/Documents/workspace/AddressPattern/data/TagedAddresses";
		GetFiles gf = new GetFiles(inputPath, "addresses.txt");
		files = gf.getFilesArr();
		for (File file : files) {
			System.out.println(file.getName());
			PlainText pt = new PlainText(file);
			ArrayList<String> words = pt.getWords();
			ArrayList<String> addresses = pt.getAdresses();
			String outputFileName = file.getName().replace("_addresses.txt", "_AddressBlobsMatching.txt");
			String outputFolder = "data/PatternMatching/"+file.getName().replace("_addresses.txt", "");
			File folder = new File(outputFolder);
			folder.mkdirs();
			File outputFile = new File(outputFolder+"/"+outputFileName);
			try {
				outputFile.createNewFile();
				FileWriter writer = new FileWriter(outputFile);
				for(String address:addresses){
					writer.write(address + "\n");
				}
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
//			for(String word:words){
//				System.out.println(word);
//			}
//			for(String address:addresses){
//				System.out.println(address);
//			}
		}
	}

}
