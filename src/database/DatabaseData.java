package database;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import interfaces.RegistrationInterface;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DatabaseData {
	private final OkHttpClient httpClient = new OkHttpClient();
	private RegistrationInterface registrationInterface;
	
        public void submit(Map<String, String> data) {
        	try {
        	String json = new ObjectMapper().writeValueAsString(data);
            // form parameters
            RequestBody formBody = new FormBody.Builder()
                    .add("data", json)
                    .build();

            Request request = new Request.Builder()
                    .url("http://qrcode-authentication.iq-joy.com/afcqrcode/updateData.php")
                    .addHeader("User-Agent", "OkHttp Bot")
                    .post(formBody)
                    .build();

            try (Response response = httpClient.newCall(request).execute()) {	//with resource - the item in the bracket must be a resource

                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                // Get response body
                System.out.println(response.body().string());
            } catch (IOException e) {
				e.printStackTrace();
				registrationInterface.isSubmitted(false);
			}

        	}catch(IOException h) {
        		h.printStackTrace();
        		registrationInterface.isSubmitted(false);
        	}
        	registrationInterface.isSubmitted(true);
	}
	
	
	public static void main(String[] args) {
		Map<String, String> data = new HashMap<>();
		data.put("ID", "127817");
		data.put("FirstName", "Fauziya");
		data.put("LastName", "Ali-Muhammad");
		data.put("Email", "fauziya.alimohammed@arise.tv");
		data.put("Company", "Arise Global Media Ltd.");
		data.put("JobTitle", "Chief of Staff");
		data.put("MobileNumber", "+2348046583383");
		data.put("Industry", "Media");
		data.put("OtherIndustry", "");
		data.put("AttendingInPerson", "In Person");
		data.put("PassType", "Organiser");
		data.put("Country", "Nigeria");
		
		new DatabaseData().submit(data);
	}

	public void setRegistrationInterface(RegistrationInterface registrationInterface) {
		this.registrationInterface = registrationInterface;
		
	}
}
