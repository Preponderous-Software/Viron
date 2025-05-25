from typing import List, Optional
import logging
import requests
from dataclasses import dataclass
from src.main.python.preponderous.viron.models.location import Location

@dataclass
class ServiceConfig:
    viron_host: str
    viron_port: int

class LocationService:
    def __init__(self, service_config: ServiceConfig):
        self.logger = logging.getLogger(__name__)
        self.base_url = f"{service_config.viron_host}:{service_config.viron_port}/api/v1/locations"

    def get_all_locations(self) -> List[Location]:
        response = requests.get(self.base_url)
        response.raise_for_status()
        return response.json()

    def get_location_by_id(self, id: int) -> Location:
        response = requests.get(f"{self.base_url}/{id}")
        if response.status_code == 404:
            raise Exception(f"Location not found with id: {id}")
        response.raise_for_status()
        return response.json()

    def get_locations_in_environment(self, environment_id: int) -> List[Location]:
        response = requests.get(f"{self.base_url}/environment/{environment_id}")
        response.raise_for_status()
        return response.json()

    def get_locations_in_grid(self, grid_id: int) -> List[Location]:
        response = requests.get(f"{self.base_url}/grid/{grid_id}")
        response.raise_for_status()
        return response.json()

    def get_location_of_entity(self, entity_id: int) -> Location:
        response = requests.get(f"{self.base_url}/entity/{entity_id}")
        if response.status_code == 404:
            raise Exception(f"Location not found for entity: {entity_id}")
        response.raise_for_status()
        return response.json()

    def add_entity_to_location(self, entity_id: int, location_id: int) -> None:
        response = requests.put(f"{self.base_url}/{location_id}/entity/{entity_id}")
        if response.status_code == 404:
            raise Exception("Location or entity not found")
        response.raise_for_status()

    def remove_entity_from_location(self, entity_id: int, location_id: int) -> None:
        response = requests.delete(f"{self.base_url}/{location_id}/entity/{entity_id}")
        if response.status_code == 404:
            raise Exception("Location or entity not found")
        response.raise_for_status()

    def remove_entity_from_current_location(self, entity_id: int) -> None:
        response = requests.delete(f"{self.base_url}/entity/{entity_id}")
        if response.status_code == 404:
            raise Exception("Entity not found")
        response.raise_for_status()