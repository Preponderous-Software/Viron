package preponderous.viron.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import preponderous.viron.models.Entity;

import java.util.List;
import java.util.Arrays;

@Service
public class EntityService {
    private RestTemplateBuilder restTemplateBuilder;
    private String baseUrl = "http://localhost:9999/entity"; // TODO: pull from config

    @Autowired
    public EntityService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplateBuilder = restTemplateBuilder;
    }

    public List<Entity> getAllEntities() {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<Entity[]> response = restTemplate.exchange(baseUrl + "/get-all-entities", HttpMethod.GET, null, Entity[].class);
        return Arrays.asList(response.getBody());
    }

    public Entity getEntityById(int id) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<Entity> response = restTemplate.exchange(baseUrl + "/get-entity-by-id/" + id, HttpMethod.GET, null, Entity.class);
        return response.getBody();
    }

    public List<Entity> getEntitiesInEnvironment(int environmentId) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<Entity[]> response = restTemplate.exchange(baseUrl + "/get-entities-in-environment/" + environmentId, HttpMethod.GET, null, Entity[].class);
        return Arrays.asList(response.getBody());
    }

    public List<Entity> getEntitiesInGrid(int gridId) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<Entity[]> response = restTemplate.exchange(baseUrl + "/get-entities-in-grid/" + gridId, HttpMethod.GET, null, Entity[].class);
        return Arrays.asList(response.getBody());
    }

    public List<Entity> getEntitiesInLocation(int locationId) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<Entity[]> response = restTemplate.exchange(baseUrl + "/get-entities-in-location/" + locationId, HttpMethod.GET, null, Entity[].class);
        return Arrays.asList(response.getBody());
    }

    // testing
    public static void main(String[] args) {
        EntityService entityService = new EntityService(new RestTemplateBuilder());
        List<Entity> entities = entityService.getEntitiesInEnvironment(1);
        for (Entity entity : entities) {
            System.out.println(entity);
        }
    }
}
