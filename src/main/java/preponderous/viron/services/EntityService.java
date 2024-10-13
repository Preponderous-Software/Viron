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
import preponderous.viron.models.Entity;

@Service
public class EntityService {
    private final RestTemplateBuilder restTemplateBuilder;
    private final ServiceConfig serviceConfig;

    private final String baseUrl;

    @Autowired
    public EntityService(RestTemplateBuilder restTemplateBuilder, ServiceConfig serviceConfig) {
        this.restTemplateBuilder = restTemplateBuilder;
        this.serviceConfig = serviceConfig;

        this.baseUrl = this.serviceConfig.getVironHost() + ":" + serviceConfig.getVironPort() + "/entity";
    }

    public List<Entity> getAllEntities() {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<Entity[]> response = restTemplate.exchange(baseUrl + "/get-all-entities", HttpMethod.GET, null, Entity[].class);
        if (response.getStatusCode().isError()) {
            throw new RuntimeException("Error getting entities");
        }
        return Arrays.asList(response.getBody());
    }

    public Entity getEntityById(int id) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<Entity> response = restTemplate.exchange(baseUrl + "/get-entity-by-id/" + id, HttpMethod.GET, null, Entity.class);
        if (response.getStatusCode().isError()) {
            throw new RuntimeException("Error getting entity by id");
        }
        return response.getBody();
    }

    public List<Entity> getEntitiesInEnvironment(int environmentId) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<Entity[]> response = restTemplate.exchange(baseUrl + "/get-entities-in-environment/" + environmentId, HttpMethod.GET, null, Entity[].class);
        if (response.getStatusCode().isError()) {
            throw new RuntimeException("Error getting entities in environment");
        }
        return Arrays.asList(response.getBody());
    }

    public List<Entity> getEntitiesInGrid(int gridId) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<Entity[]> response = restTemplate.exchange(baseUrl + "/get-entities-in-grid/" + gridId, HttpMethod.GET, null, Entity[].class);
        if (response.getStatusCode().isError()) {
            throw new RuntimeException("Error getting entities in grid");
        }
        return Arrays.asList(response.getBody());
    }

    public List<Entity> getEntitiesInLocation(int locationId) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<Entity[]> response = restTemplate.exchange(baseUrl + "/get-entities-in-location/" + locationId, HttpMethod.GET, null, Entity[].class);
        if (response.getStatusCode().isError()) {
            throw new RuntimeException("Error getting entities in location");
        }
        return Arrays.asList(response.getBody());
    }

    public List<Entity> getEntitiesNotInAnyLocation() {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<Entity[]> response = restTemplate.exchange(baseUrl + "/get-entities-not-in-any-location", HttpMethod.GET, null, Entity[].class);
        if (response.getStatusCode().isError()) {
            throw new RuntimeException("Error getting entities not in any location");
        }
        return Arrays.asList(response.getBody());
    }

    public Entity createEntity(String name) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<Entity> response = restTemplate.exchange(baseUrl + "/create-entity/" + name, HttpMethod.POST, null, Entity.class);
        if (response.getStatusCode().isError()) {
            throw new RuntimeException("Error creating entity");
        }
        return response.getBody();
    }

    public boolean deleteEntity(int id) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<Boolean> response = restTemplate.exchange(baseUrl + "/delete-entity/" + id, HttpMethod.DELETE, null, Boolean.class);
        if (response.getStatusCode().isError()) {
            throw new RuntimeException("Error deleting entity");
        }
        return response.getBody();
    }

    public boolean setEntityName(int id, String name) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<Boolean> response = restTemplate.exchange(baseUrl + "/set-entity-name/" + id + "/" + name, HttpMethod.PUT, null, Boolean.class);
        if (response.getStatusCode().isError()) {
            throw new RuntimeException("Error setting entity name");
        }
        return response.getBody();
    }

}
