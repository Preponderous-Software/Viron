# Copyright (c) 2024 Preponderous Software
# MIT License

import requests
from src.main.python.preponderous.viron.models.location import Location

class LocationService:
    def __init__(self, base_url):
        self.base_url = base_url

    def get_all_locations(self) -> list[Location]:
        response = requests.get(f"{self.base_url}/location/get-all-locations")
        response.raise_for_status()
        return [Location(**location) for location in response.json()]

    def get_location_by_id(self, location_id: int) -> Location:
        response = requests.get(f"{self.base_url}/location/get-location-by-id/{location_id}")
        response.raise_for_status()
        return Location(**response.json())

    def get_locations_in_environment(self, environment_id: int) -> list[Location]:
        response = requests.get(f"{self.base_url}/location/get-locations-in-environment/{environment_id}")
        response.raise_for_status()
        return [Location(**location) for location in response.json()]

    def get_locations_in_grid(self, grid_id: int) -> list[Location]:
        response = requests.get(f"{self.base_url}/location/get-locations-in-grid/{grid_id}")
        response.raise_for_status()
        return [Location(**location) for location in response.json()]

    def get_location_of_entity(self, entity_id: int) -> Location:
        response = requests.get(f"{self.base_url}/location/get-location-of-entity/{entity_id}")
        response.raise_for_status()
        return Location(**response.json())

    def add_entity_to_location(self, entity_id: int, location_id: int) -> bool:
        response = requests.post(f"{self.base_url}/location/add-entity-to-location/{entity_id}/{location_id}")
        response.raise_for_status()
        return response.json()

    def remove_entity_from_location(self, entity_id: int, location_id: int) -> bool:
        response = requests.post(f"{self.base_url}/location/remove-entity-from-location/{entity_id}/{location_id}")
        response.raise_for_status()
        return response.json()

    def remove_entity_from_current_location(self, entity_id: int) -> bool:
        response = requests.post(f"{self.base_url}/location/remove-entity-from-current-location/{entity_id}")
        response.raise_for_status()
        return response.json()