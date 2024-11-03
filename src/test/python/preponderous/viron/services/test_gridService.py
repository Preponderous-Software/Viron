import pytest
from unittest.mock import patch, Mock
from src.main.python.preponderous.viron.services.gridService import GridService
from src.main.python.preponderous.viron.models.grid import Grid

def test_gridService_init():
    base_url = "http://localhost:9999"
    service = GridService(base_url)
    assert service.base_url == base_url

@patch('requests.get')
def test_get_all_grids(mock_get):
    mock_response = Mock()
    mock_response.json.return_value = [
        {'grid_id': 1, 'rows': 10, 'columns': 20},
        {'grid_id': 2, 'rows': 15, 'columns': 25}
    ]
    mock_response.raise_for_status = Mock()
    mock_get.return_value = mock_response

    service = GridService("http://localhost:9999")
    grids = service.get_all_grids()

    assert len(grids) == 2
    assert grids[0].get_grid_id() == 1
    assert grids[0].get_rows() == 10
    assert grids[0].get_columns() == 20
    assert grids[1].get_grid_id() == 2
    assert grids[1].get_rows() == 15
    assert grids[1].get_columns() == 25
    mock_get.assert_called_with("http://localhost:9999/grid/get-all-grids")

@patch('requests.get')
def test_get_grid_by_id(mock_get):
    mock_response = Mock()
    mock_response.json.return_value = {'grid_id': 1, 'rows': 10, 'columns': 20}
    mock_response.raise_for_status = Mock()
    mock_get.return_value = mock_response

    service = GridService("http://localhost:9999")
    grid = service.get_grid_by_id(1)

    assert grid.get_grid_id() == 1
    assert grid.get_rows() == 10
    assert grid.get_columns() == 20
    mock_get.assert_called_with("http://localhost:9999/grid/get-grid-by-id/1")

@patch('requests.get')
def test_get_grids_in_environment(mock_get):
    mock_response = Mock()
    mock_response.json.return_value = [
        {'grid_id': 1, 'rows': 10, 'columns': 20},
        {'grid_id': 2, 'rows': 15, 'columns': 25}
    ]
    mock_response.raise_for_status = Mock()
    mock_get.return_value = mock_response

    service = GridService("http://localhost:9999")
    grids = service.get_grids_in_environment(1)

    assert len(grids) == 2
    assert grids[0].get_grid_id() == 1
    assert grids[0].get_rows() == 10
    assert grids[0].get_columns() == 20
    assert grids[1].get_grid_id() == 2
    assert grids[1].get_rows() == 15
    assert grids[1].get_columns() == 25
    mock_get.assert_called_with("http://localhost:9999/grid/get-grids-in-environment/1")

@patch('requests.get')
def test_get_grid_of_entity(mock_get):
    mock_response = Mock()
    mock_response.json.return_value = {'grid_id': 1, 'rows': 10, 'columns': 20}
    mock_response.raise_for_status = Mock()
    mock_get.return_value = mock_response

    service = GridService("http://localhost:9999")
    grid = service.get_grid_of_entity(1)

    assert grid.get_grid_id() == 1
    assert grid.get_rows() == 10
    assert grid.get_columns() == 20
    mock_get.assert_called_with("http://localhost:9999/grid/get-grid-of-entity/1")