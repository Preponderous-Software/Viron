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
import preponderous.viron.models.Grid;

@Service
public class GridService {
    private final RestTemplateBuilder restTemplateBuilder;
    private final ServiceConfig serviceConfig;

    private final String baseUrl;
    
    @Autowired
    public GridService(RestTemplateBuilder restTemplateBuilder, ServiceConfig serviceConfig) {
        this.restTemplateBuilder = restTemplateBuilder;
        this.serviceConfig = serviceConfig;

        this.baseUrl = this.serviceConfig.getVironHost() + ":" + serviceConfig.getVironPort() + "/grid";
    }

    public List<Grid> getAllGrids() {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<Grid[]> response = restTemplate.exchange(baseUrl + "/get-all-grids", HttpMethod.GET, null, Grid[].class);
        if (response.getStatusCode().isError()) {
            throw new RuntimeException("Error getting grids");
        }
        return Arrays.asList(response.getBody());
    }

    public Grid getGridById(int id) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<Grid> response = restTemplate.exchange(baseUrl + "/get-grid-by-id/" + id, HttpMethod.GET, null, Grid.class);
        if (response.getStatusCode().isError()) {
            throw new RuntimeException("Error getting grid by id");
        }
        return response.getBody();
    }

    public List<Grid> getGridsInEnvironment(int environmentId) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<Grid[]> response = restTemplate.exchange(baseUrl + "/get-grids-in-environment/" + environmentId, HttpMethod.GET, null, Grid[].class);
        if (response.getStatusCode().isError()) {
            throw new RuntimeException("Error getting grids in environment");
        }
        return Arrays.asList(response.getBody());
    }

    public Grid getGridOfEntity(int entityId) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<Grid> response = restTemplate.exchange(baseUrl + "/get-grid-of-entity/" + entityId, HttpMethod.GET, null, Grid.class);
        if (response.getStatusCode().isError()) {
            throw new RuntimeException("Error getting grid of entity");
        }
        return response.getBody();
    }

    // testing
    public static void main(String[] args) {
        ServiceConfig serviceConfig = new ServiceConfig();
        serviceConfig.setVironHost("http://localhost");
        serviceConfig.setVironPort(8080);
        GridService gridService = new GridService(new RestTemplateBuilder(), serviceConfig);
        List<Grid> grids = gridService.getGridsInEnvironment(1);
        for (Grid grid : grids) {
            System.out.println(grid);
        }
    }
}
