// Copyright (c) 2024 Preponderous Software
// MIT License

package preponderous.viron.services;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import preponderous.viron.config.ServiceConfig;
import preponderous.viron.models.Environment;

@Service
public class EnvironmentService {
    private final RestTemplateBuilder restTemplateBuilder;
    private final ServiceConfig serviceConfig;
    private final String baseUrl;

    @Autowired
    public EnvironmentService(RestTemplateBuilder restTemplateBuilder, ServiceConfig serviceConfig) {
        this.restTemplateBuilder = restTemplateBuilder;

        this.serviceConfig = serviceConfig;

        this.baseUrl = this.serviceConfig.getVironHost() + ":" + serviceConfig.getVironPort() + "/environment";
    }

    public List<Environment> getAllEnvironments() {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<Environment[]> response = restTemplate.exchange(baseUrl + "/get-all-environments", HttpMethod.GET, null, Environment[].class);
        if (response.getStatusCode().isError()) {
            throw new RuntimeException("Error getting environments");
        }
        return Arrays.asList(response.getBody());
    }

    public Environment getEnvironmentById(int id) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<Environment> response = restTemplate.exchange(baseUrl + "/get-environment-by-id/" + id, HttpMethod.GET, null, Environment.class);
        if (response.getStatusCode().isError()) {
            throw new RuntimeException("Error getting environment by id");
        }
        return response.getBody();
    }

    public Environment getEnvironmentOfEntity(int entityId) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<Environment> response = restTemplate.exchange(baseUrl + "/get-environment-of-entity/" + entityId, HttpMethod.GET, null, Environment.class);
        if (response.getStatusCode().isError()) {
            throw new RuntimeException("Error getting environment of entity");
        }
        return response.getBody();
    }

    public Environment createEnvironment(String name, int numGrids, int gridSize) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<Environment> response = restTemplate.exchange(baseUrl + "/create-environment/" + name + "/" + numGrids + "/" + gridSize, HttpMethod.POST, null, Environment.class);
        if (response.getStatusCode().isError()) {
            throw new RuntimeException("Error creating environment");
        }
        return response.getBody();
    }

    public boolean deleteEnvironment(int id) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<Boolean> response = restTemplate.exchange(baseUrl + "/delete-environment/" + id, HttpMethod.DELETE, null, Boolean.class);
        if (response.getStatusCode().isError()) {
            throw new RuntimeException("Error deleting environment");
        }
        return response.getBody();
    }

    public boolean setEnvironmentName(int id, String name) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<Boolean> response = restTemplate.exchange(baseUrl + "/set-environment-name/" + id + "/" + name, HttpMethod.PUT, null, Boolean.class);
        if (response.getStatusCode().isError()) {
            throw new RuntimeException("Error setting environment name");
        }
        return response.getBody();
    }

}
