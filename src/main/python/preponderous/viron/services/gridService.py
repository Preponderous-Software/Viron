# Copyright (c) 2024 Preponderous Software
# MIT License

import logging
from typing import List, Optional
import requests
from requests.exceptions import HTTPError
from src.main.python.preponderous.viron.models.grid import Grid

logger = logging.getLogger(__name__)

class GridService:
    def __init__(self, base_url: str):
        self.base_url = f"{base_url}/api/v1/grids"

    def get_all_grids(self) -> List[Grid]:
        response = requests.get(self.base_url)
        response.raise_for_status()
        return [Grid(**grid) for grid in response.json()]

    def get_grid_by_id(self, grid_id: int) -> Optional[Grid]:
        response = requests.get(f"{self.base_url}/{grid_id}")
        response.raise_for_status()
        return Grid(**response.json())

    def get_grids_in_environment(self, environment_id: int) -> List[Grid]:
        response = requests.get(f"{self.base_url}/environment/{environment_id}")
        response.raise_for_status()
        return [Grid(**grid) for grid in response.json()]

    def get_grid_of_entity(self, entity_id: int) -> Optional[Grid]:
        response = requests.get(f"{self.base_url}/entity/{entity_id}")
        response.raise_for_status()
        return Grid(**response.json())