package com.taxi.backend.service.impl;

import com.taxi.backend.entities.Location;
import com.taxi.backend.entities.User;
import com.taxi.backend.repository.LocationRepository;
import com.taxi.backend.service.ILocationService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LocationService implements ILocationService {
    private final LocationRepository locationRepository;

    @Override
    public Location save(Location location) {
        return locationRepository.save(location);
    }

    @Override
    public Location findLocationById(int id) {
        Optional<Location> found = locationRepository.findById(id);
        if (found.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return found.get();
    }

}
