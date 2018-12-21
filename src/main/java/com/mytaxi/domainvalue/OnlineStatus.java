package com.mytaxi.domainvalue;

import java.util.Arrays;

public enum OnlineStatus
{

    ONLINE("ONLINE"), OFFLINE("OFFLINE");
    private OnlineStatus()
    {}

    private String value;


    private OnlineStatus(String value)
    {
        this.value = value;
    }


    public static OnlineStatus fromValue(String value)
    {
        for (OnlineStatus category : values())
        {
            if (category.value.equalsIgnoreCase(value))
            {
                return category;
            }
        }
        throw new IllegalArgumentException(
            "Unknown enum type " + value + ", Allowed values are " + Arrays.toString(values()));
    }

}
