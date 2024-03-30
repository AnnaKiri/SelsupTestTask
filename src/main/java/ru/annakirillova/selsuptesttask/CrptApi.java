import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class CrptApi {

    private static final int STATUS_CREATED = 201;
    private static final Gson gson = new GsonBuilder().create();
    private static final HttpClient httpClient = HttpClient.newHttpClient();

    private final Semaphore semaphore;
    private final long restrictionPeriodMillis;

    public CrptApi(TimeUnit timeUnit, int durationLimit, int requestLimit) {
        if (requestLimit < 0) {
            throw new IllegalArgumentException("requestLimit must be positive");
        }

        this.semaphore = new Semaphore(requestLimit);
        this.restrictionPeriodMillis = timeUnit != null && durationLimit > 0 ? timeUnit.toMillis(durationLimit) : 0;
    }

    public static void main(String[] args) {
        CrptApi crptApi = new CrptApi(TimeUnit.MINUTES, 1, 5);
        String signature = "sign";
        Document document = crptApi.new Document();

        for (int i = 0; i < 1000; i++) {
            new Thread(() -> crptApi.sendDocument(document, signature)).start();
        }
    }

    public void sendDocument(Document document, String signature) {
        String json = gson.toJson(document);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://ismp.crpt.ru/api/v3/lk/documents/create"))
                .header("Content-Type", "application/json")
                .header("Signature", signature)
                .POST(BodyPublishers.ofString(json))
                .build();

        apiRequest(request, response -> {
            if (response.statusCode() == STATUS_CREATED) {
                System.out.println("Object created! Response code: " + response.statusCode());
            } else {
                System.out.println("Object wasn't created! Response code: " + response.statusCode());
                System.out.println("Response body: " + response.body());
            }
        });
    }

    public void apiRequest(HttpRequest request, Consumer<HttpResponse<String>> responseHandler) {
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Operation was interrupted.");
            return;
        }

        long timeStart = System.currentTimeMillis();

        try {
            HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());
            responseHandler.accept(response);
        } catch (Exception e) {
            System.out.println("Error happened upon request!");
            e.printStackTrace();
        } finally {
            long timeEnd = System.currentTimeMillis();
            long sleepTime = Math.max(restrictionPeriodMillis - (timeEnd - timeStart), 0);

            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Operation was interrupted.");
            }

            semaphore.release();
        }
    }

    class Description {
        private String participantInn;

        public Description() {
        }
    }

    class Product {
        private String certificate_document;
        private String certificate_document_date;
        private String certificate_document_number;
        private String owner_inn;
        private String producer_inn;
        private String production_date;
        private String tnved_code;
        private String uit_code;
        private String uitu_code;

        public Product() {
        }
    }

    class Document {
        private Description description;
        private String doc_id;
        private String doc_status;
        private String doc_type;
        private boolean importRequest;
        private String owner_inn;
        private String participant_inn;
        private String producer_inn;
        private String production_date;
        private String production_type;
        private List<Product> products;
        private String reg_date;
        private String reg_number;

        public Document() {
        }
    }
}
