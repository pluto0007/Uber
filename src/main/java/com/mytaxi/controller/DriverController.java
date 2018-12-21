package com.mytaxi.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mytaxi.controller.mapper.DriverMapper;
import com.mytaxi.criteria.Criteria;
import com.mytaxi.criteria.CriteriaBuilder;
import com.mytaxi.datatransferobject.CriteriaDTO;
import com.mytaxi.datatransferobject.DriverDTO;
import com.mytaxi.domainobject.DriverDO;
import com.mytaxi.domainvalue.OnlineStatus;
import com.mytaxi.exception.CarAlreadyInUseException;
import com.mytaxi.exception.ConstraintsViolationException;
import com.mytaxi.exception.EntityNotFoundException;
import com.mytaxi.exception.IncorrectStatusException;
import com.mytaxi.exception.InvalidCriteriaValueException;
import com.mytaxi.service.driver.DriverService;

/**
 * All operations with a driver will be routed by this controller.
 * <p/>
 */
@RestController
@RequestMapping("v1/drivers")
public class DriverController
{

    private final DriverService driverService;


    @Autowired
    public DriverController(final DriverService driverService)
    {
        this.driverService = driverService;
    }


    @GetMapping("/{driverId}")
    @PreAuthorize("hasRole('ADMIN')")
    public @ResponseBody DriverDTO getDriver(@PathVariable long driverId) throws EntityNotFoundException
    {
        return DriverMapper.makeDriverDTO(driverService.find(driverId));
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public @ResponseBody DriverDTO createDriver(@Valid @RequestBody DriverDTO driverDTO) throws ConstraintsViolationException
    {
        DriverDO driverDO = DriverMapper.makeDriverDO(driverDTO);
        return DriverMapper.makeDriverDTO(driverService.create(driverDO));
    }


    @DeleteMapping("/{driverId}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteDriver(@PathVariable long driverId) throws EntityNotFoundException
    {
        driverService.delete(driverId);
    }


    @PutMapping("/{driverId}")
    @PreAuthorize("hasRole('ADMIN')")
    public void updateLocation(
        @PathVariable long driverId, @RequestParam double longitude, @RequestParam double latitude)
        throws EntityNotFoundException
    {
        driverService.updateLocation(driverId, longitude, latitude);
    }


    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public @ResponseBody List<DriverDTO> findDrivers(@RequestParam OnlineStatus onlineStatus)
    {
        return DriverMapper.makeDriverDTOList(driverService.find(onlineStatus),false);
    }


    @PutMapping("selectCar/{driverId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public @ResponseBody DriverDTO selectCar(@PathVariable long driverId, @RequestParam Long carId) throws EntityNotFoundException, IncorrectStatusException, CarAlreadyInUseException
    {
        return DriverMapper.makeDriverDTO(driverService.selectCar(driverId, carId));
    }
    
    
    @PutMapping("deSelectCar/{driverId}")
    public DriverDTO deSelectCar(@PathVariable long driverId) throws EntityNotFoundException, IncorrectStatusException, CarAlreadyInUseException
    {
        return DriverMapper.makeDriverDTO(driverService.deSelectCar(driverId));
    }
    
/*    @PostMapping("search")
    public List<DriverDTO> findDriversByCriteria(@RequestBody Map<String, String> params) throws EntityNotFoundException{
        return DriverMapper.makeDriverDTOList(driverService.findDriversByFilterCriteria(params));
    }*/
    
    @PostMapping("search")
    @PreAuthorize("hasRole('ADMIN')")
    public @ResponseBody List<DriverDTO> findDriversByCriteria(@Valid @RequestBody CriteriaDTO criteriaDTO) throws EntityNotFoundException, InvalidCriteriaValueException{
        Criteria criteria = CriteriaBuilder.build(criteriaDTO);
        List<DriverDO> driverList = driverService.findByCriteria(criteria);
        return DriverMapper.makeDriverDTOList(driverList,true);
    }
}
