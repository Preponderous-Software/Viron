# Copyright (c) 2024 Preponderous Software
# MIT License

import requests

from src.main.python.preponderous.viron.models.entity import Entity


class EntityService:
    def __init__(self, base_url):
        self.base_url = base_url

    def getAllEntities(self) -> list[Entity]:
        response = requests.get(f"{self.base_url}/entity/get-all-entities")
        response.raise_for_status()
        return [Entity(**entity) for entity in response.json()]

    def getEntityById(self, entity_id) -> Entity:
        response = requests.get(f"{self.base_url}/entity/get-entity-by-id/{entity_id}")
        response.raise_for_status()
        return Entity(**response.json())

    def getEntitiesInEnvironment(self, environment_id) -> list[Entity]:
        response = requests.get(f"{self.base_url}/entity/get-entities-in-environment/{environment_id}")
        response.raise_for_status()
        return [Entity(**entity) for entity in response.json()]

    def getEntitiesInGrid(self, grid_id) -> list[Entity]:
        response = requests.get(f"{self.base_url}/entity/get-entities-in-grid/{grid_id}")
        response.raise_for_status()
        return [Entity(**entity) for entity in response.json()]

    def getEntitiesInLocation(self, location_id) -> list[Entity]:
        response = requests.get(f"{self.base_url}/entity/get-entities-in-location/{location_id}")
        response.raise_for_status()
        return [Entity(**entity) for entity in response.json()]

    def getEntitiesNotInAnyLocation(self) -> list[Entity]:
        response = requests.get(f"{self.base_url}/entity/get-entities-not-in-any-location")
        response.raise_for_status()
        return [Entity(**entity) for entity in response.json()]

    def createEntity(self, name) -> Entity:
        response = requests.post(f"{self.base_url}/entity/create-entity/{name}")
        response.raise_for_status()
        return Entity(**response.json())

    def deleteEntity(self, entity_id) -> dict:
        response = requests.delete(f"{self.base_url}/entity/delete-entity/{entity_id}")
        response.raise_for_status()
        return response.json()

    def setEntityName(self, entity_id, name) -> dict:
        response = requests.put(f"{self.base_url}/entity/set-entity-name/{entity_id}/{name}")
        response.raise_for_status()
        return response.json()
