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
        try:
            response = requests.get(self.base_url)
            response.raise_for_status()
            return [Grid(**grid) for grid in response.json()]
        except Exception as e:
            logger.error(f"Error getting all grids: {str(e)}")
            raise Exception("Failed to fetch all grids") from e

    def get_grid_by_id(self, grid_id: int) -> Optional[Grid]:
        try:
            response = requests.get(f"{self.base_url}/{grid_id}")
            response.raise_for_status()
            return Grid(**response.json())
        except Exception as e:
            logger.error(f"Error getting grid by id {grid_id}: {str(e)}")
            raise Exception(f"Failed to fetch grid by id: {grid_id}") from e

    def get_grids_in_environment(self, environment_id: int) -> List[Grid]:
        try:
            response = requests.get(f"{self.base_url}/environment/{environment_id}")
            response.raise_for_status()
            return [Grid(**grid) for grid in response.json()]
        except Exception as e:
            logger.error(f"Error getting grids in environment {environment_id}: {str(e)}")
            raise Exception(f"Failed to fetch grids in environment: {environment_id}") from e

    def get_grid_of_entity(self, entity_id: int) -> Optional[Grid]:
        response = requests.get(f"{self.base_url}/entity/{entity_id}")
        response.raise_for_status()
        return Grid(**response.json())