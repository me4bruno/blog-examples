package de.bruns.example.bulksms;

import org.smslib.OutboundMessage;
import org.smslib.http.BulkSmsHTTPGateway;
import org.smslib.http.BulkSmsHTTPGateway.Regions;

public class SendSmsLibService {
	
	private BulkSmsHTTPGateway bulkSmsHTTPGateway;

	public SendSmsLibService() {
		bulkSmsHTTPGateway = new BulkSmsHTTPGateway("1", Constants.BULKSMS_USERNAME, Constants.BULKSMS_PASSWORD, Regions.GERMANY);
	}
	
	public void sendSms(String receiver, String message) throws Exception {
		bulkSmsHTTPGateway.sendMessage(new OutboundMessage(receiver, message));
	}
	
	public static void main(String[] args) throws Exception {
		if (args.length != 2) {
			System.out.println("Bitte Telefonnummer und Nachricht angeben.");
			return;
		}
		
		String receiver = args[0];
		String message = args[1];
		
		System.out.println("Versende Nachricht mit SendSmsLibService: " + receiver + " => " + message);
		new SendSmsLibService().sendSms(receiver, message);
	}
}
