package com.mytaxi.service.driver;

import java.util.List;

import com.mytaxi.criteria.Criteria;
import com.mytaxi.domainobject.DriverDO;
import com.mytaxi.domainvalue.OnlineStatus;
import com.mytaxi.exception.CarAlreadyInUseException;
import com.mytaxi.exception.ConstraintsViolationException;
import com.mytaxi.exception.EntityNotFoundException;
import com.mytaxi.exception.IncorrectStatusException;

public interface DriverService
{

    DriverDO find(Long driverId) throws EntityNotFoundException;


    DriverDO create(DriverDO driverDO) throws ConstraintsViolationException;


    void delete(Long driverId) throws EntityNotFoundException;


    void updateLocation(long driverId, double longitude, double latitude) throws EntityNotFoundException;


    List<DriverDO> find(OnlineStatus onlineStatus);


    DriverDO selectCar(Long driverId, Long carId) throws EntityNotFoundException, IncorrectStatusException, CarAlreadyInUseException;


    DriverDO deSelectCar(Long driverId) throws EntityNotFoundException;


    //    List<DriverDO> findDriversByFilterCriteria(Map<String, String> params) throws EntityNotFoundException;

    List<DriverDO> findByCriteria(Criteria criteria);

}
