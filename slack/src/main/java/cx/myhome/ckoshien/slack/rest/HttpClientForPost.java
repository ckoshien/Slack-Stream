package cx.myhome.ckoshien.slack.rest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class HttpClientForPost {

	@Value("${filepath}")
	private String filepath;
	@Value("${slack.token}")
	private String token;

	private String URL = "https://slack.com/api/files.upload";
	private Logger logger=LoggerFactory.getLogger(HttpClientForPost.class);

	public String post(File file) {
		CloseableHttpClient client = HttpClientBuilder.create()
		        .build();
		HttpEntity requestEntity=null;
		try {
			requestEntity = MultipartEntityBuilder.create()
					.addTextBody("token", token)
					.addTextBody("channels", "CAG778918")
					.addTextBody("filetype", "jpg")
					//.addBinaryBody(String name, InputStream stream, ContentType contentType, String filename)
			        .addBinaryBody("file", new FileInputStream(file),ContentType.IMAGE_JPEG,"graph")
			        .build();
		} catch (FileNotFoundException e1) {
			// TODO 自動生成された catch ブロック
			e1.printStackTrace();
		}
		HttpPost post = new HttpPost(URL);
		String result = null;
		try {
			//post.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
			post.setEntity(requestEntity);
			HttpResponse response = client.execute(post);
			logger.info("Status: {}", response.getStatusLine());
			result = collectResponse(response);
			System.out.println(result.toString());
		} catch (IOException e) {
			logger.error("While posting", e);
		}
		return result;
	}

	private String collectResponse(HttpResponse response) {
		StringBuilder result = new StringBuilder();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			for (String line; (line = br.readLine()) != null; ) {
				result.append(line);
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result.toString();
	}
}