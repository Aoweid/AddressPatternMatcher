package pattern;

import java.util.regex.Pattern;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Pattern pattern = Pattern.compile("[a-zA-Z]*");
		String str = "AS";
		System.out.println(pattern.matcher(str).matches());

	}

}
