package solver.http;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

public class ApiClient {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static final HttpClient client = HttpClient.newHttpClient();

    public <T> Optional<T> get(String url, Class<T> tClass) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> res = client.send(request, HttpResponse.BodyHandlers.ofString());
        return Optional.of(objectMapper.readValue(res.body(), tClass));
    }

    public <Input, Output> Optional<Output> post(String url, Input inputData, Class<Output> outputClass) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(inputData)))
                .build();

        HttpResponse<String> res = client.send(request, HttpResponse.BodyHandlers.ofString());
        return Optional.of(objectMapper.readValue(res.body(), outputClass));

    }
}
