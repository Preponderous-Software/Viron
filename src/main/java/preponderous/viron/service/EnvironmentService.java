package preponderous.viron.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import preponderous.viron.models.Environment;

import java.util.List;
import java.util.Arrays;

@Service
public class EnvironmentService {
    private RestTemplateBuilder restTemplateBuilder;
    private String baseUrl = "http://localhost:9999/environment"; // TODO: pull from config

    @Autowired
    public EnvironmentService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplateBuilder = restTemplateBuilder;
    }

    public List<Environment> getAllEnvironments() {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<Environment[]> response = restTemplate.exchange(baseUrl + "/get-all-environments", HttpMethod.GET, null, Environment[].class);
        return Arrays.asList(response.getBody());
    }

    public Environment getEnvironmentById(int id) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<Environment> response = restTemplate.exchange(baseUrl + "/get-environment-by-id/" + id, HttpMethod.GET, null, Environment.class);
        return response.getBody();
    }

    public Environment getEnvironmentOfEntity(int entityId) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<Environment> response = restTemplate.exchange(baseUrl + "/get-environment-of-entity/" + entityId, HttpMethod.GET, null, Environment.class);
        return response.getBody();
    }
    
    // testing
    public static void main(String[] args) {
        EnvironmentService environmentService = new EnvironmentService(new RestTemplateBuilder());
        List<Environment> environments = environmentService.getAllEnvironments();
        for (Environment environment : environments) {
            System.out.println(environment);
        }
    }
}
