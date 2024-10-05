package preponderous.viron.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import preponderous.viron.models.Grid;

import java.util.List;
import java.util.Arrays;

@Service
public class GridService {
    private RestTemplateBuilder restTemplateBuilder;
    private String baseUrl = "http://localhost:9999/grid"; // TODO: pull from config
    
    @Autowired
    public GridService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplateBuilder = restTemplateBuilder;
    }

    public List<Grid> getAllGrids() {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<Grid[]> response = restTemplate.exchange(baseUrl + "/get-all-grids", HttpMethod.GET, null, Grid[].class);
        return Arrays.asList(response.getBody());
    }

    public Grid getGridById(int id) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<Grid> response = restTemplate.exchange(baseUrl + "/get-grid-by-id/" + id, HttpMethod.GET, null, Grid.class);
        return response.getBody();
    }

    public List<Grid> getGridsInEnvironment(int environmentId) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<Grid[]> response = restTemplate.exchange(baseUrl + "/get-grids-in-environment/" + environmentId, HttpMethod.GET, null, Grid[].class);
        return Arrays.asList(response.getBody());
    }

    // testing
    public static void main(String[] args) {
        GridService gridService = new GridService(new RestTemplateBuilder());
        List<Grid> grids = gridService.getGridsInEnvironment(1);
        for (Grid grid : grids) {
            System.out.println(grid);
        }
    }
}
