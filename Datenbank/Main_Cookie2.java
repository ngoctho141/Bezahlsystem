
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

public class Main_Cookie2 {
	public static void main(String[] args) {
		String aid="B8:27:EB:CB:AE:9D";
		
		
		Scanner sc = new Scanner(System.in);
		String name, pass, line, produkt;
		int type;
		try {	
			URL url = new URL ("http://pr-245.lab.if.haw-landshut.de/handy.php");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			con.setDoOutput(true);
			con.connect();
			
			System.out.println("Bitte Login eingeben:");
				name =(sc.nextLine());
			System.out.println("Bitte passwort eingeben:");
				pass =(sc.nextLine());
			
			DataOutputStream out = new DataOutputStream(con.getOutputStream());
			out.writeBytes("command=Login&name="+ name +"&password=" + pass);
			out.flush();
			out.close();
			
			con.setConnectTimeout(5000);
			con.setReadTimeout(5000);
			
			con.setInstanceFollowRedirects(false);
			
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			StringBuffer content = new StringBuffer();
			
			while((line = in.readLine()) != null) 
			{
				content.append(line);
			}

			if(content.substring(0,2).equals("-1"))
			{
				System.out.println("Falsche daten");
				return;
			}
			
			System.out.println(content);
			in.close();
			
			content.delete(0, content.length());
			
			
			//neuer teil mit cookie suchen und schreiben
			System.out.println("Liste anzeigen");
			
			//cookie suchen und value ziehen
			StringBuilder sb = new StringBuilder();
			
			List<String> cookies = con.getHeaderFields().get("Set-Cookie");
			if (cookies != null) {
			    for (String cookie : cookies) {
			        if (sb.length() > 0) {
			            sb.append("; ");
			        }

			        // only want the first part of the cookie header that has the value
			        String value = cookie.split(";")[0];
			        sb.append(value);
			    }
			}
			
			url = new URL("http://pr-245.lab.if.haw-landshut.de/handy.php");
			con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			//cookie hinzufügen
			con.setRequestProperty("Cookie", sb.toString() );
			con.setDoOutput(true);
			con.setDoInput(true);
			
			con.connect();
			
			out = new DataOutputStream(con.getOutputStream());
			out.writeBytes("command=Charge");
			out.flush();
			out.close();
			
			con.setConnectTimeout(5000);
			con.setReadTimeout(5000);
			
			con.setInstanceFollowRedirects(false);
			
			in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			content = new StringBuffer();
			
			while((line = in.readLine()) != null) {
				content.append(line);
				content.append("\n");
			}
			content.trimToSize();
			
			System.out.println(content);
			in.close();
			content.delete(0, content.length());
			
			url = new URL("http://pr-245.lab.if.haw-landshut.de/handy.php");
			con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			//cookie hinzufügen
			con.setRequestProperty("Cookie", sb.toString() );
			con.setDoOutput(true);
			con.setDoInput(true);
			
			con.connect();
			
			line = "";
			 
			//Auflisten der Produkte
			
			out = new DataOutputStream(con.getOutputStream());
			out.writeBytes("command=List");
			out.flush();
			out.close();
			
			con.setConnectTimeout(5000);
			con.setReadTimeout(5000);
			
			con.setInstanceFollowRedirects(false);
			
			in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			content = new StringBuffer();
			
			while((line = in.readLine()) != null) {
				content.append(line);
				content.append("\n");
			}
			content.trimToSize();
			
			System.out.println(content);
			in.close();
			content.delete(0, content.length());
			
			
			//Auswahl + Kauf
			
			System.out.println("Produkt auswählen 1: Latte | 2: Schwarz");
				type=(sc.nextInt());
				
			if(type == 1)
				produkt = "latte";
			else 
				produkt = "black";
				
			url = new URL("http://pr-245.lab.if.haw-landshut.de/handy.php");
			con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			//cookie hinzufügen
			con.setRequestProperty("Cookie", sb.toString() );
			con.setDoOutput(true);
			con.setDoInput(true);
			
			con.connect();
			
			line = "";
			 
			
			out = new DataOutputStream(con.getOutputStream());
			out.writeBytes("command=Buy&aid=" + aid +"&produkt=" + produkt);
			out.flush();
			out.close();
			
			con.setConnectTimeout(5000);
			con.setReadTimeout(5000);
			
			con.setInstanceFollowRedirects(false);
			
			in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			content = new StringBuffer();
			
			while((line = in.readLine()) != null) {
				content.append(line);
				content.append("\n");
			}
			content.trimToSize();
			
			System.out.println(content);
			in.close();
			content.delete(0, content.length());	
		
			
			
			url = new URL("http://pr-245.lab.if.haw-landshut.de/handy.php");
			con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			//cookie hinzufügen
			con.setRequestProperty("Cookie", sb.toString() );
			con.setDoOutput(true);
			con.setDoInput(true);
			
			con.connect();
			
			line = "";
			 
			
			out = new DataOutputStream(con.getOutputStream());
			out.writeBytes("command=End");
			out.flush();
			out.close();
			
			con.setConnectTimeout(5000);
			con.setReadTimeout(5000);
			
			con.setInstanceFollowRedirects(false);
			
			in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			content = new StringBuffer();
			
			while((line = in.readLine()) != null) {
				content.append(line);
				content.append("\n");
			}
			content.trimToSize();
			
			System.out.println(content);
			in.close();
			content.delete(0, content.length());	
			
			
			
			sc.close();
			
		} 
		
		catch (MalformedURLException e) {
			e.printStackTrace();
		}

		catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}