import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

public class CrptApi {

    private static final String URL = "https://ismp.crpt.ru/api/v3/lk/documents/create";
    private static final int STATUS_CREATED = 201;

    public static void main(String[] args) {
        String signature = "sign";
        Document document = new Document();

        sendDocument(document, signature);
    }

    public static void sendDocument(Document document, String signature) {
        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(document);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL))
                .header("Content-Type", "application/json")
                .header("Signature", signature)
                .POST(BodyPublishers.ofString(json))
                .build();

        try (HttpClient httpClient = HttpClient.newHttpClient()) {
            HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());

            if (response.statusCode() == STATUS_CREATED) {
                System.out.println("Object created! Response code: " + response.statusCode());
            } else {
                System.out.println("Object wasn't created! Response code: " + response.statusCode());
                System.out.println("Response body: " + response.body());
            }

        } catch (Exception e) {
            System.out.println("Error happened upon request!");
            e.printStackTrace();
        }
    }
}
