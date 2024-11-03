# Copyright (c) 2024 Preponderous Software
# MIT License

import requests
from src.main.python.preponderous.viron.models.grid import Grid

class GridService:
    def __init__(self, base_url):
        self.base_url = base_url

    def get_all_grids(self) -> list[Grid]:
        response = requests.get(f"{self.base_url}/grid/get-all-grids")
        response.raise_for_status()
        return [Grid(**grid) for grid in response.json()]

    def get_grid_by_id(self, grid_id: int) -> Grid:
        response = requests.get(f"{self.base_url}/grid/get-grid-by-id/{grid_id}")
        response.raise_for_status()
        return Grid(**response.json())

    def get_grids_in_environment(self, environment_id: int) -> list[Grid]:
        response = requests.get(f"{self.base_url}/grid/get-grids-in-environment/{environment_id}")
        response.raise_for_status()
        return [Grid(**grid) for grid in response.json()]

    def get_grid_of_entity(self, entity_id: int) -> Grid:
        response = requests.get(f"{self.base_url}/grid/get-grid-of-entity/{entity_id}")
        response.raise_for_status()
        return Grid(**response.json())