import pytest
from unittest.mock import patch, Mock
from src.main.python.preponderous.viron.services.locationService import LocationService
from src.main.python.preponderous.viron.models.location import Location

def test_locationService_init():
    base_url = "http://localhost:9999"
    service = LocationService(base_url)
    assert service.base_url == base_url

@patch('requests.get')
def test_get_all_locations(mock_get):
    mock_response = Mock()
    mock_response.json.return_value = [
        {'location_id': 1, 'x': 10, 'y': 20},
        {'location_id': 2, 'x': 15, 'y': 25}
    ]
    mock_response.raise_for_status = Mock()
    mock_get.return_value = mock_response

    service = LocationService("http://localhost:9999")
    locations = service.get_all_locations()

    assert len(locations) == 2
    assert locations[0].get_location_id() == 1
    assert locations[0].get_x() == 10
    assert locations[0].get_y() == 20
    assert locations[1].get_location_id() == 2
    assert locations[1].get_x() == 15
    assert locations[1].get_y() == 25
    mock_get.assert_called_with("http://localhost:9999/location/get-all-locations")

@patch('requests.get')
def test_get_location_by_id(mock_get):
    mock_response = Mock()
    mock_response.json.return_value = {'location_id': 1, 'x': 10, 'y': 20}
    mock_response.raise_for_status = Mock()
    mock_get.return_value = mock_response

    service = LocationService("http://localhost:9999")
    location = service.get_location_by_id(1)

    assert location.get_location_id() == 1
    assert location.get_x() == 10
    assert location.get_y() == 20
    mock_get.assert_called_with("http://localhost:9999/location/get-location-by-id/1")

@patch('requests.get')
def test_get_locations_in_environment(mock_get):
    mock_response = Mock()
    mock_response.json.return_value = [
        {'location_id': 1, 'x': 10, 'y': 20},
        {'location_id': 2, 'x': 15, 'y': 25}
    ]
    mock_response.raise_for_status = Mock()
    mock_get.return_value = mock_response

    service = LocationService("http://localhost:9999")
    locations = service.get_locations_in_environment(1)

    assert len(locations) == 2
    assert locations[0].get_location_id() == 1
    assert locations[0].get_x() == 10
    assert locations[0].get_y() == 20
    assert locations[1].get_location_id() == 2
    assert locations[1].get_x() == 15
    assert locations[1].get_y() == 25
    mock_get.assert_called_with("http://localhost:9999/location/get-locations-in-environment/1")

@patch('requests.get')
def test_get_locations_in_grid(mock_get):
    mock_response = Mock()
    mock_response.json.return_value = [
        {'location_id': 1, 'x': 10, 'y': 20},
        {'location_id': 2, 'x': 15, 'y': 25}
    ]
    mock_response.raise_for_status = Mock()
    mock_get.return_value = mock_response

    service = LocationService("http://localhost:9999")
    locations = service.get_locations_in_grid(1)

    assert len(locations) == 2
    assert locations[0].get_location_id() == 1
    assert locations[0].get_x() == 10
    assert locations[0].get_y() == 20
    assert locations[1].get_location_id() == 2
    assert locations[1].get_x() == 15
    assert locations[1].get_y() == 25
    mock_get.assert_called_with("http://localhost:9999/location/get-locations-in-grid/1")

@patch('requests.get')
def test_get_location_of_entity(mock_get):
    mock_response = Mock()
    mock_response.json.return_value = {'location_id': 1, 'x': 10, 'y': 20}
    mock_response.raise_for_status = Mock()
    mock_get.return_value = mock_response

    service = LocationService("http://localhost:9999")
    location = service.get_location_of_entity(1)

    assert location.get_location_id() == 1
    assert location.get_x() == 10
    assert location.get_y() == 20
    mock_get.assert_called_with("http://localhost:9999/location/get-location-of-entity/1")

@patch('requests.post')
def test_add_entity_to_location(mock_post):
    mock_response = Mock()
    mock_response.json.return_value = True
    mock_response.raise_for_status = Mock()
    mock_post.return_value = mock_response

    service = LocationService("http://localhost:9999")
    result = service.add_entity_to_location(1, 1)

    assert result is True
    mock_post.assert_called_with("http://localhost:9999/location/add-entity-to-location/1/1")

@patch('requests.post')
def test_remove_entity_from_location(mock_post):
    mock_response = Mock()
    mock_response.json.return_value = True
    mock_response.raise_for_status = Mock()
    mock_post.return_value = mock_response

    service = LocationService("http://localhost:9999")
    result = service.remove_entity_from_location(1, 1)

    assert result is True
    mock_post.assert_called_with("http://localhost:9999/location/remove-entity-from-location/1/1")

@patch('requests.post')
def test_remove_entity_from_current_location(mock_post):
    mock_response = Mock()
    mock_response.json.return_value = True
    mock_response.raise_for_status = Mock()
    mock_post.return_value = mock_response

    service = LocationService("http://localhost:9999")
    result = service.remove_entity_from_current_location(1)

    assert result is True
    mock_post.assert_called_with("http://localhost:9999/location/remove-entity-from-current-location/1")