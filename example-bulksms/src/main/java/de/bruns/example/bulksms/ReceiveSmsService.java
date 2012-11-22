package de.bruns.example.bulksms;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class ReceiveSmsService {

	private String messageId = "0";

	public void receive() {
		try {
			String data = "";
			data += "username=" + URLEncoder.encode(Constants.BULKSMS_USERNAME, Constants.SMS_ENCODING);
			data += "&password=" + URLEncoder.encode(Constants.BULKSMS_PASSWORD, Constants.SMS_ENCODING);
			data += "&last_retrieved_id=" + URLEncoder.encode(messageId, Constants.SMS_ENCODING);

			URL url = new URL("http://bulksms.de:5567/eapi/reception/get_inbox/1/1.1");
			URLConnection conn = url.openConnection();
			conn.setDoOutput(true);
			OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
			wr.write(data);
			wr.flush();

			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = rd.readLine()) != null) {
				System.out.println(line);
				String[] lineContent = line.split("\\|");
				if (lineContent.length == 7) {
					String msgId = lineContent[0];
					String sender = lineContent[1];
					String message = lineContent[2];
					System.out.println(msgId + ": " + sender + " -> " + message);

					messageId = msgId;
				}
			}
			wr.close();
			rd.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		ReceiveSmsService receiveSmsService = new ReceiveSmsService();
		while (true) {
			receiveSmsService.receive();
			Thread.sleep(1000);
		}
	}

}
