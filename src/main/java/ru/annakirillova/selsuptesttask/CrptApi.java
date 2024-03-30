import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class CrptApi {

    private static final String URL = "https://ismp.crpt.ru/api/v3/lk/documents/create";
    private static final int STATUS_CREATED = 201;
    private static final Gson gson = new GsonBuilder().create();

    private final Semaphore semaphore;
    private final long restrictionPeriodMillis;

    public CrptApi(TimeUnit timeUnit, int requestLimit) {
        this.semaphore = new Semaphore(requestLimit);
        this.restrictionPeriodMillis = timeUnit.toMillis(1);
    }

    public static void main(String[] args) {
        String signature = "sign";
        Document document = new Document();
        CrptApi crptApi = new CrptApi(TimeUnit.MINUTES, 5);

        for (int i = 0; i < 1000; i++) {
            new Thread(() -> crptApi.sendDocument(document, signature)).start();
        }
    }

    public void sendDocument(Document document, String signature) {
        String json = gson.toJson(document);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL))
                .header("Content-Type", "application/json")
                .header("Signature", signature)
                .POST(BodyPublishers.ofString(json))
                .build();

        try (HttpClient httpClient = HttpClient.newHttpClient()) {
            semaphore.acquire();
            long timeStart = System.currentTimeMillis();

            HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());

            if (response.statusCode() == STATUS_CREATED) {
                System.out.println("Object created! Response code: " + response.statusCode());
            } else {
                System.out.println("Object wasn't created! Response code: " + response.statusCode());
                System.out.println("Response body: " + response.body());
            }

            long timeEnd = System.currentTimeMillis();
            long sleepTime = Math.max(restrictionPeriodMillis - (timeEnd - timeStart), 0);
            Thread.sleep(sleepTime);
            semaphore.release();
        } catch (Exception e) {
            System.out.println("Error happened upon request!");
            e.printStackTrace();
        }
    }
}
