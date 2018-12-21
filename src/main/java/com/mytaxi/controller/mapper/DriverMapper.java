package com.mytaxi.controller.mapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.mytaxi.datatransferobject.DriverDTO;
import com.mytaxi.domainobject.DriverDO;
import com.mytaxi.domainvalue.GeoCoordinate;

public class DriverMapper
{
    public static DriverDO makeDriverDO(DriverDTO driverDTO)
    {
        return new DriverDO(driverDTO.getUsername(), driverDTO.getPassword(), driverDTO.getOnlineStatus(),driverDTO.getRoles());
    }


    public static DriverDTO makeDriverDTO(DriverDO driverDO)
    {
        DriverDTO.DriverDTOBuilder driverDTOBuilder =
            DriverDTO
                .newBuilder()
                .setId(driverDO.getId())
                .setPassword(driverDO.getPassword())
                .setUsername(driverDO.getUsername());

        GeoCoordinate coordinate = driverDO.getCoordinate();
        if (coordinate != null)
        {
            driverDTOBuilder.setCoordinate(coordinate);
        }

        if (driverDO.getCar() != null)
        {
            driverDTOBuilder.setCarDO(driverDO.getCar());
        }
        driverDTOBuilder.setResponseMessage("SUCCESS");
        driverDTOBuilder.setResponseCode(0);
        return driverDTOBuilder.createDriverDTO();
    }


    public static DriverDTO makeSearchDriverDTO(DriverDO driverDO)
    {
        DriverDTO.DriverDTOBuilder driverDTOBuilder =
            DriverDTO
                .newBuilder()
                .setId(driverDO.getId())
                .setUsername(driverDO.getUsername());

        GeoCoordinate coordinate = driverDO.getCoordinate();
        if (coordinate != null)
        {
            driverDTOBuilder.setCoordinate(coordinate);
        }

        if (driverDO.getCar() != null)
        {
            driverDTOBuilder.setCarDO(driverDO.getCar());
        }
        driverDTOBuilder.setResponseMessage("SUCCESS");
        driverDTOBuilder.setResponseCode(0);
        return driverDTOBuilder.createDriverDTO();
    }


    public static List<DriverDTO> makeDriverDTOList(Collection<DriverDO> drivers, boolean searchResult)
    {
        if (!searchResult)
        {
            return drivers
                .stream()
                .map(DriverMapper::makeDriverDTO)
                .collect(Collectors.toList());
        }
        else
        {

            return drivers
                .stream()
                .map(DriverMapper::makeSearchDriverDTO)
                .collect(Collectors.toList());

        }
    }


/*    public static DriverDO convertParamsToDto(Map<String, String> searchParams)
    {
        String status = MapUtils.getString(searchParams, "status");
        OnlineStatus onlineStatus;
        if (status != null)
        {
            onlineStatus = OnlineStatus.valueOf(status);
        }
        else
        {
            onlineStatus = null;
        }

        DriverDO driverDO = new DriverDO(MapUtils.getString(searchParams, "username"), null, onlineStatus);
        return driverDO;
                DriverDTO.DriverDTOBuilder driverDTOBuilder =
            DriverDTO.newBuilder().setUsername(MapUtils.getString(searchParams, "username")).setOnlineStatus(OnlineStatus.valueOf(MapUtils.getString(searchParams, "status")));
        
        return driverDTOBuilder.createDriverDTO();
         }
*/
}
