package helper;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class SendEmail {

	private SendEmail() {}
	
    public static boolean isEmailAtivado() {
    	return ManipuladorEmail.getIsAtivo();
    }
    
	public static void enviarEmail(String subject, String message) {
		if (isEmailAtivado()) {
			try {
				final String url = ManipuladorEmail.getUrl();
				final String key = ManipuladorEmail.getKey();
		        final String fromEmail = ManipuladorEmail.getFromEmail();
		        final String fromName = ManipuladorEmail.getFromName();
		        final String toEmail = ManipuladorEmail.getToEmail();
		        final String toName = ManipuladorEmail.getToName();
		        
		        String body = "{\"personalizations\":[{"
		        		+ "\"to\":[{"
		        		+ "\"email\":\"" + toEmail + "\"," 
		        		+ "\"name\":\""+ toName + "\"}],"
		        		+ "\"subject\":\"" + subject + "\"}],"
		        		+ "\"content\":[{\"type\":\"text/html\","
		        		+ "\"value\":\"" + message + "\"}],"
		        		+ "\"from\":{"
		        		+ "\"email\":\"" + fromEmail + "\","
		        		+ "\"name\":\"" + fromName +"\"}}";

		        MediaType mediaType = MediaType.get("application/json");
		        RequestBody requestBody = RequestBody.create(body, mediaType);
		        
		        OkHttpClient client = new OkHttpClient();

		        Request request = new Request.Builder()
		                .url(url)
		                .addHeader("Authorization", "Bearer " + key)
		                .addHeader("Content-Type", "application/json")
		                .post(requestBody)
		                .build();

		        Call call = client.newCall(request);
		        call.execute();
		        System.out.println("Email enviado para: " + toEmail);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
}
	
