package com.taxi.backend.service;

import com.taxi.backend.entities.Location;

public interface ILocationService {
    public Location save(Location location);
    public Location findLocationById(int id);
}
