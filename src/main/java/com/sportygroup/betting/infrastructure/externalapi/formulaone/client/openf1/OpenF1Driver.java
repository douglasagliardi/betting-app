package com.sportygroup.betting.infrastructure.externalapi.formulaone.client.openf1;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

// {
//    "broadcast_name": "M VERSTAPPEN",
//    "country_code": "NED",
//    "driver_number": 1,
//    "first_name": "Max",
//    "full_name": "Max VERSTAPPEN",
//    "headshot_url": "https://www.formula1.com/content/dam/fom-website/drivers/M/MAXVER01_Max_Verstappen/maxver01.png.transform/1col/image.png",
//    "last_name": "Verstappen",
//    "meeting_key": 1219,
//    "name_acronym": "VER",
//    "session_key": 9158,
//    "team_colour": "3671C6",
//    "team_name": "Red Bull Racing"
//  }
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record OpenF1Driver(int driverNumber,
                           String firstName,
                           String lastName,
                           String countryCode,
                           String nameAcronym,
                           String headshotUrl,
                           String teamName) {}
