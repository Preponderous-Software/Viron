# Copyright (c) 2024 Preponderous Software
# MIT License

import requests
from src.main.python.preponderous.viron.models.environment import Environment

class EnvironmentService:
    def __init__(self, base_url):
        self.base_url = base_url

    def get_all_environments(self) -> list[Environment]:
        response = requests.get(f"{self.base_url}/environment/get-all-environments")
        response.raise_for_status()
        return [Environment(**env) for env in response.json()]

    def get_environment_by_id(self, environment_id: int) -> Environment:
        response = requests.get(f"{self.base_url}/environment/get-environment-by-id/{environment_id}")
        response.raise_for_status()
        return Environment(**response.json())

    def get_environment_of_entity(self, entity_id: int) -> Environment:
        response = requests.get(f"{self.base_url}/environment/get-environment-of-entity/{entity_id}")
        response.raise_for_status()
        return Environment(**response.json())

    def create_environment(self, name: str, num_grids: int, grid_size: int) -> Environment:
        response = requests.post(f"{self.base_url}/environment/create-environment/{name}/{num_grids}/{grid_size}")
        response.raise_for_status()
        return Environment(**response.json())

    def delete_environment(self, environment_id: int) -> bool:
        response = requests.delete(f"{self.base_url}/environment/delete-environment/{environment_id}")
        response.raise_for_status()
        return response.json()

    def set_environment_name(self, environment_id: int, name: str) -> bool:
        response = requests.put(f"{self.base_url}/environment/set-environment-name/{environment_id}/{name}")
        response.raise_for_status()
        return response.json()