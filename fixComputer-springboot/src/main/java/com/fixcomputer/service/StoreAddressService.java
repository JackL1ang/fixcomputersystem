package com.fixcomputer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fixcomputer.domain.entity.MapCoordinate;

import java.util.List;

public interface StoreAddressService extends IService<MapCoordinate> {

    List<MapCoordinate> selectAllCoordinate();

    boolean createCoordinate(MapCoordinate mapCoordinate);

    boolean updateCoordinate(MapCoordinate mapCoordinate);

    boolean deleteCoordiante(int id);

}
