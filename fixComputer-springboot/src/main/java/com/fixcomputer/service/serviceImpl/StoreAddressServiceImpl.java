package com.fixcomputer.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fixcomputer.domain.entity.MapCoordinate;
import com.fixcomputer.mapper.MapCoordianteMapper;
import com.fixcomputer.service.StoreAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreAddressServiceImpl extends ServiceImpl<MapCoordianteMapper,MapCoordinate> implements StoreAddressService {

    @Autowired
    MapCoordianteMapper mapCoordianteMapper;


    @Override
    public List<MapCoordinate> selectAllCoordinate() {
        List<MapCoordinate> mapCoordinates = mapCoordianteMapper.selectList(new QueryWrapper<>());
        return mapCoordinates;
    }

    @Override
    public boolean createCoordinate(MapCoordinate mapCoordinate) {
        int insert = mapCoordianteMapper.insert(mapCoordinate);
        if(insert==1){
            return true;
        }else{
            return false;
        }

    }

    @Override
    public boolean updateCoordinate(MapCoordinate mapCoordinate) {
        UpdateWrapper<MapCoordinate> mapCoordinateUpdateWrapper = new UpdateWrapper<>();
        mapCoordinateUpdateWrapper.eq("id",mapCoordinate.getId());
        int update = mapCoordianteMapper.update(mapCoordinate, mapCoordinateUpdateWrapper);
        if(update==1){
            return  true;
        }else{
            return false;
        }

    }

    @Override
    public boolean deleteCoordiante(int id) {
        QueryWrapper<MapCoordinate> mapCoordinateQueryWrapper = new QueryWrapper<>();
        mapCoordinateQueryWrapper.eq("id",id);
        int delete = mapCoordianteMapper.delete(mapCoordinateQueryWrapper);
        if(delete==1){
            return true;
        }else{
            return false;
        }

    }
}
