package paket;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Scanner;

public class MainExec {

	@SuppressWarnings({ "unused", "resource" }) //for scanners
	public static void main(String[] args) {
		
		//executeCommand("chmod +x libs/yowsup-master/yowsup-cli");
		//	executeCommand("gedit libs/yowsup-master/yowsup-cli.config");		    
		   // fstream = new FileInputStream("libs/yowsup-master/test.txt");
		
        Scanner cc_scanner = new Scanner(System.in); 
        System.out.printf("Enter country code: ");
		String cc_in = cc_scanner.next();// 
		
        
        Scanner phone_scanner = new Scanner(System.in); 
        System.out.printf("Enter phone number with country code: ");
		String phone_in = cc_scanner.next();// 
		
        configWriter(phone_in, cc_in); //Config write!
		executeCommand("chmod +x libs/yowsup-master/yowsup-cli"); // set chmod
	
	    Scanner ask_scanner = new Scanner(System.in); 
        System.out.println("Select verification type:");
        System.out.println("1-SMS \n2-Voice Call");
		int ask_ans = ask_scanner.nextInt();// 
		if (ask_ans==1){
			System.out.println("Sending SMS verification...");
			executeCommand("libs/yowsup-master/yowsup-cli --requestcode sms --config libs/yowsup-master/yowsup-cli.configs");} // run sms verif.

		else if (ask_ans==2){
			System.out.println("Sending Voice Call verification...");
			executeCommand("libs/yowsup-master/yowsup-cli --requestcode voice --config libs/yowsup-master/yowsup-cli.configs");} // run voice call verif.
		
        

	    Scanner vcode_scanner = new Scanner(System.in); 
	    System.out.printf("Enter verification code: ");
		String vcode_in = vcode_scanner.next();
		executeCommand("libs/yowsup-master/yowsup-cli --register "+vcode_in+" --config libs/yowsup-master/yowsup-cli.configs");
		Scanner ok_scanner = new Scanner(System.in); 
		    System.out.println("All is done!\n Open config file with: gedit? (y/n)");
			String ok_in = ok_scanner.next();
			if (ok_in.equals("y")){
				System.out.println("Opening config file...");
				executeCommand("gedit libs/yowsup-master/yowsup-cli.configs");
			}
	

		}


        
	

	
	public static void configWriter(String phone_no, String cc_no) {
		try {
	    BufferedReader reader;
    	reader = new BufferedReader(new FileReader("libs/yowsup-master/base/yowsup-cli-base.config"));
	    PrintWriter writer = new PrintWriter("libs/yowsup-master/yowsup-cli.configs");  
	    String line = null;  
	    String matchRegex_cc = "cc=";
	    String replacement_cc= "cc="+cc_no;  
	    String matchRegex_phone = "phone=";
	    String replacement_phone = "phone="+phone_no;
	    while ((line = reader.readLine()) != null){  
	      line = line.replaceAll(matchRegex_cc, replacement_cc);  
	      line = line.replaceAll(matchRegex_phone, replacement_phone);  
	      writer.println(line);  
	    }  
	    reader.close();  
	    writer.close();  
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	
	
	public static void executeCommand(String bashCommand){
		MainExec obj = new MainExec();
		String output = obj.executeCommandBase(bashCommand);		 
		System.out.println(output);
	}
	private String executeCommandBase(String command) {
		StringBuffer output = new StringBuffer(); 
		Process p;
		try {
			p = Runtime.getRuntime().exec(command);
			p.waitFor();
			BufferedReader reader = 
                            new BufferedReader(new InputStreamReader(p.getInputStream())); 
                        String line = "";			
			while ((line = reader.readLine())!= null) {
				output.append(line + "\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return output.toString(); 
	}
}
