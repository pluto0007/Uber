package com.mytaxi.dataaccessobject;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.mytaxi.domainobject.CarDO;
import com.mytaxi.domainobject.DriverDO;
import com.mytaxi.domainvalue.OnlineStatus;

/**
 * Database Access Object for driver table.
 * <p/>
 */
public interface DriverRepository extends CrudRepository<DriverDO, Long>
{

    List<DriverDO> findByOnlineStatus(OnlineStatus onlineStatus);


    @Query("SELECT D FROM driver D ,car C  WHERE C.rating = :#{#carDto.rating} OR C.license_plate = :#{#carDto.license_plate} OR C.seat_count = :#{#carDto.seat_count} OR C.convertible = :#{#carDto.convertible} OR C.engine_type = :#{#carDto.engine_type} OR D.username = :#{#driverDto.username} OR D.onlineStatus = :#{#driverDto.onlineStatus}")
    List<Object[]> findDriverByFilterCriteria(@Param("carDto") final CarDO carDto, @Param("driverDto") final DriverDO driverDto);


    DriverDO findByUsername(String username);
}
