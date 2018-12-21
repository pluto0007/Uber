package com.mytaxi.service.driver;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mytaxi.criteria.Criteria;
import com.mytaxi.dataaccessobject.DriverRepository;
import com.mytaxi.domainobject.CarDO;
import com.mytaxi.domainobject.DriverDO;
import com.mytaxi.domainobject.Role;
import com.mytaxi.domainvalue.GeoCoordinate;
import com.mytaxi.domainvalue.OnlineStatus;
import com.mytaxi.exception.CarAlreadyInUseException;
import com.mytaxi.exception.ConstraintsViolationException;
import com.mytaxi.exception.EntityNotFoundException;
import com.mytaxi.exception.IncorrectStatusException;
import com.mytaxi.service.car.CarService;

/**
 * Service to encapsulate the link between DAO and controller and to have business logic for some driver specific things.
 * <p/>
 */
@Service
public class DefaultDriverService implements DriverService
{

    private static final Logger LOG = LoggerFactory.getLogger(DefaultDriverService.class);

    private final DriverRepository driverRepository;
    private final CarService carService;
    @Autowired
    private BCryptPasswordEncoder bcryptEncoder;


    public DefaultDriverService(final DriverRepository driverRepository, final CarService carService)
    {
        this.driverRepository = driverRepository;
        this.carService = carService;
    }


    /**
     * Selects a driver by id.
     *
     * @param driverId
     * @return found driver
     * @throws EntityNotFoundException if no driver with the given id was found.
     */
    @Override
    public DriverDO find(Long driverId) throws EntityNotFoundException
    {
        return findDriverChecked(driverId);
    }


    /**
     * Creates a new driver.
     *
     * @param driverDO
     * @return
     * @throws ConstraintsViolationException if a driver already exists with the given username, ... .
     */
    @Override
    public DriverDO create(DriverDO driverDO) throws ConstraintsViolationException
    {
        DriverDO driver;
        try
        {
            Set<Role> roles = new HashSet<>();
            Role role = new Role();
            role.setDescription("User role");
            role.setName("USER");
            //role.setId(5);
            roles.add(role);
            driverDO.setRoles(roles);
            driver = driverRepository.save(driverDO);
        }
        catch (DataIntegrityViolationException e)
        {
            LOG.warn("ConstraintsViolationException while creating a driver: {}", driverDO, e);
            throw new ConstraintsViolationException(e.getMessage());
        }
        return driver;
    }


    /**
     * Deletes an existing driver by id.
     *
     * @param driverId
     * @throws EntityNotFoundException if no driver with the given id was found.
     */
    @Override
    @Transactional
    public void delete(Long driverId) throws EntityNotFoundException
    {
        DriverDO driverDO = findDriverChecked(driverId);

        Optional<CarDO> driverCar = Optional.ofNullable(driverDO.getCar());
        driverCar.ifPresent(c -> {
            c.setSelected(false);
            carService.save(c);
        });
        driverDO.setDeleted(true);
        driverRepository.delete(driverDO);
    }


    /**
     * Update the location for a driver.
     *
     * @param driverId
     * @param longitude
     * @param latitude
     * @throws EntityNotFoundException
     */
    @Override
    @Transactional
    public void updateLocation(long driverId, double longitude, double latitude) throws EntityNotFoundException
    {
        DriverDO driverDO = findDriverChecked(driverId);
        driverDO.setCoordinate(new GeoCoordinate(latitude, longitude));
    }


    /**
     * Find all drivers by online state.
     *
     * @param onlineStatus
     */
    @Override
    public List<DriverDO> find(OnlineStatus onlineStatus)
    {
        return driverRepository.findByOnlineStatus(onlineStatus);
    }


    private DriverDO findDriverChecked(Long driverId) throws EntityNotFoundException
    {
        return driverRepository
            .findById(driverId)
            .orElseThrow(() -> new EntityNotFoundException("Could not find entity with id: " + driverId));
    }


    @Override
    @Transactional
    public DriverDO selectCar(Long driverId, Long carId) throws EntityNotFoundException, IncorrectStatusException, CarAlreadyInUseException
    {
        DriverDO driverDO = findDriverChecked(driverId);

        // To check if the driver is online or not
        if (driverDO.getOnlineStatus().equals(OnlineStatus.OFFLINE))
        {
            throw new IncorrectStatusException("Driver with OFFLINE status cannot select a car");
        }
        CarDO carDO = carService.find(carId);

        // to check if the car is already being taken or not
        if (carDO.isSelected())
        {
            throw new CarAlreadyInUseException("Car is already in use by other driver");
        }

        carDO.setSelected(true);
        carService.save(carDO);

        // setting current car of the driver to false as we are going to assign a new car to the driver
        Optional<CarDO> car = Optional.ofNullable(driverDO.getCar());
        car.ifPresent(c -> {
            c.setSelected(false);
            carService.save(c);
        });

        driverDO.setCar(carDO);

        driverRepository.save(driverDO);

        return driverDO;

    }


    @Override
    @Transactional
    public DriverDO deSelectCar(Long driverId) throws EntityNotFoundException
    {
        DriverDO driverDO = findDriverChecked(driverId);

        Optional<CarDO> driverCar = Optional.ofNullable(driverDO.getCar());
        driverCar.ifPresent(c -> {
            c.setSelected(false);
            carService.save(c);
        });
        driverDO.setCar(null);
        driverRepository.save(driverDO);
        return driverDO;

    }


    /*    @Override
    public List<DriverDO> findDriversByFilterCriteria(Map<String, String> params) throws EntityNotFoundException
    {
        List<DriverDO> driverDataList = new ArrayList<>();
        try
        {
            CarDO carFilter = CarMapper.convertParamsToDto(params);
            DriverDO driverFilter = DriverMapper.convertParamsToDto(params);
    
            List<Object[]> drivers = driverRepository.findDriverByFilterCriteria(carFilter, driverFilter);
            Set<DriverDO> notDuplicatedElements = new HashSet<>();
    
            for (Object obj : drivers)
            {
                driverDataList.add((DriverDO) obj);
            }
            notDuplicatedElements.addAll(driverDataList);
            driverDataList.clear();
            driverDataList.addAll(notDuplicatedElements);
        }
        catch (Exception e)
        {
            throw new EntityNotFoundException("Driver entity not found ");
        }
    
        return driverDataList;
    }
    */

    @Override
    public List<DriverDO> findByCriteria(Criteria criteria)
    {
        List<DriverDO> drivers = new ArrayList<>();
        driverRepository.findAll().forEach(drivers::add);

        if (null == criteria)
        {
            return drivers;
        }

        return criteria.meetCriteria(drivers);
    }

    /*    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        DriverDO driver = driverRepository.findByUsername(username);
        if (driver == null)
        {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(driver.getUsername(), driver.getPassword(), getAuthority(driver));
    }
    
    private Set<SimpleGrantedAuthority> getAuthority(DriverDO driver) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        driver.getRoles().forEach(role -> {
            //authorities.add(new SimpleGrantedAuthority(role.getName()));
            authorities.add(new SimpleGrantedAuthority("ROLE_" + ((Logger) role).getName()));
        });
        return authorities;
        //return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }*/
}
