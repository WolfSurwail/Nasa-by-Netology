import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        String naseUrl = "https://api.nasa.gov/planetary/apod?api_key=HAVR54ffdiqe2gNO9md5goRzlLMIWzgAPg7KoS6f";

        ObjectMapper objectMapper = new ObjectMapper();

        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet request = new HttpGet(naseUrl);

        CloseableHttpResponse response = client.execute(request);

        NasaAnswer nasaAnswer = objectMapper.readValue(response.getEntity().getContent(), NasaAnswer.class);

        CloseableHttpResponse image = client.execute(new HttpGet(nasaAnswer.url));

        String[] answerSplitted = nasaAnswer.url.split("/");

        FileOutputStream fileOutputStream = new FileOutputStream(answerSplitted[answerSplitted.length - 1]);
        image.getEntity().writeTo(fileOutputStream);
    }

}
