# test_environmentService.py

from src.main.python.preponderous.viron.services.environmentService import EnvironmentService
from unittest.mock import patch, Mock
from src.main.python.preponderous.viron.models.environment import Environment

def test_environmentService_init():
    base_url = "http://localhost:9999"
    service = EnvironmentService(base_url)
    assert service.base_url == base_url


@patch('requests.get')
def test_get_all_environments(mock_get):
    mock_response = Mock()
    mock_response.json.return_value = [{'environmentId': 1, 'name': 'Environment1', 'creationDate': '2024-01-01'}, {'environmentId': 2, 'name': 'Environment2', 'creationDate': '2024-01-01'}]
    mock_response.raise_for_status = Mock()
    mock_get.return_value = mock_response

    service = EnvironmentService("http://localhost:9999")
    environments = service.get_all_environments()

    assert len(environments) == 2
    assert environments[0].environmentId == 1
    assert environments[0].name == 'Environment1'
    assert environments[1].environmentId == 2
    assert environments[1].name == 'Environment2'
    mock_get.assert_called_with("http://localhost:9999/environment/get-all-environments")


@patch('requests.get')
def test_get_environment_by_id(mock_get):
    mock_response = Mock()
    mock_response.json.return_value = {'environmentId': 1, 'name': 'Environment1', 'creationDate': '2024-01-01'}
    mock_response.raise_for_status = Mock()
    mock_get.return_value = mock_response

    service = EnvironmentService("http://localhost:9999")
    environment = service.get_environment_by_id(1)

    assert environment.environmentId == 1
    assert environment.name == 'Environment1'
    mock_get.assert_called_with("http://localhost:9999/environment/get-environment-by-id/1")


@patch('requests.get')
def test_get_environment_of_entity(mock_get):
    mock_response = Mock()
    mock_response.json.return_value = {'environmentId': 1, 'name': 'Environment1', 'creationDate': '2024-01-01'}
    mock_response.raise_for_status = Mock()
    mock_get.return_value = mock_response

    service = EnvironmentService("http://localhost:9999")
    environment = service.get_environment_of_entity(1)

    assert environment.environmentId == 1
    assert environment.name == 'Environment1'
    mock_get.assert_called_with("http://localhost:9999/environment/get-environment-of-entity/1")


@patch('requests.post')
def test_create_environment(mock_post):
    mock_response = Mock()
    mock_response.json.return_value = {'environmentId': 1, 'name': 'Environment1', 'creationDate': '2024-01-01'}
    mock_response.raise_for_status = Mock()
    mock_post.return_value = mock_response

    service = EnvironmentService("http://localhost:9999")
    environment = service.create_environment('Environment1', 10, 10)

    assert environment.environmentId == 1
    assert environment.name == 'Environment1'
    mock_post.assert_called_with("http://localhost:9999/environment/create-environment/Environment1/10/10")


@patch('requests.delete')
def test_delete_environment(mock_delete):
    mock_response = Mock()
    mock_response.json.return_value = {'status': 'success'}
    mock_response.raise_for_status = Mock()
    mock_delete.return_value = mock_response

    service = EnvironmentService("http://localhost:9999")
    response = service.delete_environment(1)

    assert response['status'] == 'success'
    mock_delete.assert_called_with("http://localhost:9999/environment/delete-environment/1")


@patch('requests.put')
def test_set_environment_name(mock_put):
    mock_response = Mock()
    mock_response.json.return_value = {'status': 'success'}
    mock_response.raise_for_status = Mock()
    mock_put.return_value = mock_response

    service = EnvironmentService("http://localhost:9999")
    response = service.set_environment_name(1, 'NewName')

    assert response['status'] == 'success'
    mock_put.assert_called_with("http://localhost:9999/environment/set-environment-name/1/NewName")


@patch('requests.get')
def test_get_all_environments_empty(mock_get):
    mock_response = Mock()
    mock_response.json.return_value = []
    mock_response.raise_for_status = Mock()
    mock_get.return_value = mock_response
    
    service = EnvironmentService("http://localhost:9999")
    environments = service.get_all_environments()
    
    assert len(environments) == 0
    mock_get.assert_called_with("http://localhost:9999/environment/get-all-environments")