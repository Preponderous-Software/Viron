# Copyright (c) 2024 Preponderous Software
# MIT License

from src.main.python.preponderous.viron.services.entityService import EntityService
from unittest.mock import patch, Mock
from src.main.python.preponderous.viron.models.entity import Entity

def test_entityService_init():
    base_url = "http://localhost:9999"
    service = EntityService(base_url)
    assert service.base_url == base_url


@patch('requests.get')
def test_getAllEntities(mock_get):
    mock_response = Mock()
    mock_response.json.return_value = [{'entityId': 1, 'name': 'Entity1', 'creationDate': '2024-01-01'}, {'entityId': 2, 'name': 'Entity2', 'creationDate': '2024-01-01'}]
    mock_response.raise_for_status = Mock()
    mock_get.return_value = mock_response

    service = EntityService("http://localhost:9999")
    entities = service.getAllEntities()

    assert len(entities) == 2
    assert entities[0].entityId == 1
    assert entities[0].name == 'Entity1'
    assert entities[1].entityId == 2
    assert entities[1].name == 'Entity2'
    mock_get.assert_called_with("http://localhost:9999/entity/get-all-entities")


@patch('requests.get')
def test_getEntityById(mock_get):
    mock_response = Mock()
    mock_response.json.return_value = {'entityId': 1, 'name': 'Entity1', 'creationDate': '2024-01-01'}
    mock_response.raise_for_status = Mock()
    mock_get.return_value = mock_response

    service = EntityService("http://localhost:9999")
    entity = service.getEntityById(1)

    assert entity.entityId == 1
    assert entity.name == 'Entity1'
    mock_get.assert_called_with("http://localhost:9999/entity/get-entity-by-id/1")


@patch('requests.get')
def test_getEntitiesInEnvironment(mock_get):
    mock_response = Mock()
    mock_response.json.return_value = [{'entityId': 1, 'name': 'Entity1', 'creationDate': '2024-01-01'}, {'entityId': 2, 'name': 'Entity2', 'creationDate': '2024-01-01'}]
    mock_response.raise_for_status = Mock()
    mock_get.return_value = mock_response

    service = EntityService("http://localhost:9999")
    entities = service.getEntitiesInEnvironment(1)

    assert len(entities) == 2
    assert entities[0].entityId == 1
    assert entities[0].name == 'Entity1'
    assert entities[1].entityId == 2
    assert entities[1].name == 'Entity2'
    mock_get.assert_called_with("http://localhost:9999/entity/get-entities-in-environment/1")


@patch('requests.get')
def test_getEntitiesInGrid(mock_get):
    mock_response = Mock()
    mock_response.json.return_value = [{'entityId': 1, 'name': 'Entity1', 'creationDate': '2024-01-01'}, {'entityId': 2, 'name': 'Entity2', 'creationDate': '2024-01-01'}]
    mock_response.raise_for_status = Mock()
    mock_get.return_value = mock_response

    service = EntityService("http://localhost:9999")
    entities = service.getEntitiesInGrid(1)

    assert len(entities) == 2
    assert entities[0].entityId == 1
    assert entities[0].name == 'Entity1'
    assert entities[1].entityId == 2
    assert entities[1].name == 'Entity2'
    mock_get.assert_called_with("http://localhost:9999/entity/get-entities-in-grid/1")


@patch('requests.get')
def test_getEntitiesInLocation(mock_get):
    mock_response = Mock()
    mock_response.json.return_value = [{'entityId': 1, 'name': 'Entity1', 'creationDate': '2024-01-01'}, {'entityId': 2, 'name': 'Entity2', 'creationDate': '2024-01-01'}]
    mock_response.raise_for_status = Mock()
    mock_get.return_value = mock_response

    service = EntityService("http://localhost:9999")
    entities = service.getEntitiesInLocation(1)

    assert len(entities) == 2
    assert entities[0].entityId == 1
    assert entities[0].name == 'Entity1'
    assert entities[1].entityId == 2
    assert entities[1].name == 'Entity2'
    mock_get.assert_called_with("http://localhost:9999/entity/get-entities-in-location/1")


@patch('requests.get')
def test_getEntitiesNotInAnyLocation(mock_get):
    mock_response = Mock()
    mock_response.json.return_value = [{'entityId': 1, 'name': 'Entity1', 'creationDate': '2024-01-01'}, {'entityId': 2, 'name': 'Entity2', 'creationDate': '2024-01-01'}]
    mock_response.raise_for_status = Mock()
    mock_get.return_value = mock_response

    service = EntityService("http://localhost:9999")
    entities = service.getEntitiesNotInAnyLocation()

    assert len(entities) == 2
    assert entities[0].entityId == 1
    assert entities[0].name == 'Entity1'
    assert entities[1].entityId == 2
    assert entities[1].name == 'Entity2'
    mock_get.assert_called_with("http://localhost:9999/entity/get-entities-not-in-any-location")


@patch('requests.post')
def test_createEntity(mock_post):
    mock_response = Mock()
    mock_response.json.return_value = {'entityId': 1, 'name': 'Entity1', 'creationDate': '2024-01-01'}
    mock_response.raise_for_status = Mock()
    mock_post.return_value = mock_response

    service = EntityService("http://localhost:9999")
    entity = service.createEntity('Entity1')

    assert entity.entityId == 1
    assert entity.name == 'Entity1'
    mock_post.assert_called_with("http://localhost:9999/entity/create-entity/Entity1")


@patch('requests.delete')
def test_deleteEntity(mock_delete):
    mock_response = Mock()
    mock_response.json.return_value = {'status': 'success'}
    mock_response.raise_for_status = Mock()
    mock_delete.return_value = mock_response

    service = EntityService("http://localhost:9999")
    response = service.deleteEntity(1)

    assert response['status'] == 'success'
    mock_delete.assert_called_with("http://localhost:9999/entity/delete-entity/1")


@patch('requests.put')
def test_setEntityName(mock_put):
    mock_response = Mock()
    mock_response.json.return_value = {'status': 'success'}
    mock_response.raise_for_status = Mock()
    mock_put.return_value = mock_response

    service = EntityService("http://localhost:9999")
    response = service.setEntityName(1, 'NewName')

    assert response['status'] == 'success'
    mock_put.assert_called_with("http://localhost:9999/entity/set-entity-name/1/NewName")


@patch('requests.get')
def test_getAllEntities_empty(mock_get):
    mock_response = Mock()
    mock_response.json.return_value = []
    mock_response.raise_for_status = Mock()
    mock_get.return_value = mock_response
    
    service = EntityService("http://localhost:9999")
    entities = service.getAllEntities()
    
    assert len(entities) == 0
    mock_get.assert_called_with("http://localhost:9999/entity/get-all-entities")

