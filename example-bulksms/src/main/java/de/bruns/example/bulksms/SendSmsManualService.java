package de.bruns.example.bulksms;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class SendSmsManualService {

	public void sendSms(String receiver, String message) throws Exception {

		String data = "";
		data += "username=" + URLEncoder.encode(Constants.BULKSMS_USERNAME, Constants.SMS_ENCODING);
		data += "&password=" + URLEncoder.encode(Constants.BULKSMS_PASSWORD, Constants.SMS_ENCODING);
		data += "&message=" + URLEncoder.encode(message, Constants.SMS_ENCODING);
		data += "&want_report=1";
		data += "&msisdn=" + receiver;
		data += "&repliable=1";
		System.out.println(data);

		URL url = new URL("http://bulksms.de:5567/eapi/submission/send_sms/2/2.0");
		URLConnection conn = url.openConnection();
		conn.setDoOutput(true);
		OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
		wr.write(data);
		wr.flush();

		// Get the response
		BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String line;
		while ((line = rd.readLine()) != null) {
			// Print the response output...
			System.out.println(line);
		}
		wr.close();
		rd.close();
	}

	public static void main(String[] args) throws Exception {
		if (args.length != 2) {
			System.out.println("Bitte Telefonnummer und Nachricht angeben.");
			return;
		}

		String receiver = args[0];
		String message = args[1];

		System.out.println("Versende Nachricht mit SendSmsManualService: " + receiver + " => " + message);
		new SendSmsManualService().sendSms(receiver, message);
	}

}
