package com.fixcomputer.controller;


import com.fixcomputer.domain.entity.MapCoordinate;
import com.fixcomputer.service.StoreAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/map")
@RestController
public class StoreAddress {

    @Autowired
    StoreAddressService storeAddressService;

    @GetMapping()
    public List<MapCoordinate> getAddress(){
        List<MapCoordinate> mapCoordinates = storeAddressService.selectAllCoordinate();
        return mapCoordinates;

    }

    @PostMapping()
    public boolean insertAddress(@RequestBody MapCoordinate coordinate){
        boolean coordinateInsert = storeAddressService.createCoordinate(coordinate);
        return coordinateInsert;

    }

    @DeleteMapping("/{id}")
    public boolean deleteAddress(@PathVariable int id){
        boolean b = storeAddressService.deleteCoordiante(id);
        return b;

    }



}
