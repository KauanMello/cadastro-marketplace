package helper;

import java.io.IOException;

import okhttp3.Credentials;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class SendSms {
	
	public static boolean isSmsAtivado() {
		return ManipuladorSms.getIsAtivo();
	}
	
	public static void enviarSms(String mensagem) {
		if (isSmsAtivado()) {
			OkHttpClient client = new OkHttpClient();
			
			String number = ManipuladorSms.getToNumber();
			String from = ManipuladorSms.getFromNumber();
			String url = ManipuladorSms.getUrl();
			String accountSid = ManipuladorSms.getAccountSsid();
			String authToken = ManipuladorSms.getAuthToken();
			
			String credentials = Credentials.basic(accountSid, authToken);
			String tipo = "application/x-www-form-urlencoded";
			
			MediaType mediaType = MediaType.parse(tipo);
			RequestBody requestBody = RequestBody.create(
				  "To=" + number
				+ "&From=" + from
				+ "&Body=" + mensagem
				, mediaType); 
			
			Request request = new Request.Builder()
				.url(url)
				.post(requestBody)
				.addHeader("Authorization", credentials)
				.addHeader("Content-Type", tipo)
				.build();
			
			try {
				client.newCall(request).execute();
				System.out.println("mensagem enviada para: " + number);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
