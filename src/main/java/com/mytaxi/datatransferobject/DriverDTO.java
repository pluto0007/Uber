package com.mytaxi.datatransferobject;

import java.util.Set;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mytaxi.controller.mapper.CarMapper;
import com.mytaxi.domainobject.CarDO;
import com.mytaxi.domainobject.Role;
import com.mytaxi.domainvalue.GeoCoordinate;
import com.mytaxi.domainvalue.OnlineStatus;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DriverDTO extends ServiceResponse
{

    private static final long serialVersionUID = 5736901252078693187L;

    @JsonIgnore
    private Long id;

    @NotNull(message = "Username can not be null!")
    private String username;

    @NotNull(message = "Password can not be null!")
    private String password;

    private GeoCoordinate coordinate;

    private CarDTO carDTO;

    private OnlineStatus onlineStatus;

    private Set<Role> roles;


    private DriverDTO()
    {}


    private DriverDTO(
        Long id, String username, String password, GeoCoordinate coordinate, CarDTO carDTO, OnlineStatus onlineStatus, int responseCode, String responseMessage, Set<Role> roles)
    {
        this.id = id;
        this.username = username;
        this.password = password;
        this.coordinate = coordinate;
        this.carDTO = carDTO;
        this.onlineStatus = onlineStatus;
        this.setResponseCode(responseCode);
        this.setResponseMessage(responseMessage);
        this.roles = roles;

    }


    public static DriverDTOBuilder newBuilder()
    {
        return new DriverDTOBuilder();
    }


    @JsonProperty
    public Long getId()
    {
        return id;
    }


    public String getUsername()
    {
        return username;
    }


    public String getPassword()
    {
        return password;
    }


    public GeoCoordinate getCoordinate()
    {
        return coordinate;
    }


    public CarDTO getCarDTO()
    {
        return carDTO;
    }


    public OnlineStatus getOnlineStatus()
    {
        return onlineStatus;
    }


    public Set<Role> getRoles()
    {
        return roles;
    }

    public static class DriverDTOBuilder extends ServiceResponse
    {
        private Long id;
        private String username;
        private String password;
        private GeoCoordinate coordinate;
        private CarDTO carDTO;
        private OnlineStatus onlineStatus;
        private Set<Role> roles;


        public void setRoles(Set<Role> roles)
        {
            this.roles = roles;
        }


        public DriverDTOBuilder setId(Long id)
        {
            this.id = id;
            return this;
        }


        public DriverDTOBuilder setUsername(String username)
        {
            this.username = username;
            return this;
        }


        public DriverDTOBuilder setPassword(String password)
        {
            this.password = password;
            return this;
        }


        public DriverDTOBuilder setCoordinate(GeoCoordinate coordinate)
        {
            this.coordinate = coordinate;
            return this;
        }


        public DriverDTOBuilder setCarDO(CarDO carDO)
        {
            this.carDTO = CarMapper.makeCarDTO(carDO);
            return this;
        }


        public DriverDTOBuilder setOnlineStatus(OnlineStatus onlineStatus)
        {
            this.onlineStatus = onlineStatus;
            return this;
        }


        public DriverDTO createDriverDTO()
        {
            return new DriverDTO(id, username, password, coordinate, carDTO, onlineStatus, this.getResponseCode(), this.getResponseMessage(), roles);
        }

    }
}
