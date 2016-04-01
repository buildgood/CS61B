import java.io.*;

public class Nuke2 {
	
	public static void main(String[] arg) throws Exception{
		
		BufferedReader keyboard;
		String inputLine;
		
		keyboard = new BufferedReader(new InputStreamReader(System.in));
		inputLine = keyboard.readLine();
		
		String out;
		out = inputLine.charAt(0)+inputLine.substring(2);
		System.out.println(out);
		
	}

}
