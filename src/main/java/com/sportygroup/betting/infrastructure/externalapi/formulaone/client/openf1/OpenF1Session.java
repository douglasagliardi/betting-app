package com.sportygroup.betting.infrastructure.externalapi.formulaone.client.openf1;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

// {
//    "circuit_key": 7,
//    "circuit_short_name": "Spa-Francorchamps",
//    "country_code": "BEL",
//    "country_key": 16,
//    "country_name": "Belgium",
//    "date_end": "2023-07-29T15:35:00+00:00",
//    "date_start": "2023-07-29T15:05:00+00:00",
//    "gmt_offset": "02:00:00",
//    "location": "Spa-Francorchamps",
//    "meeting_key": 1216,
//    "session_key": 9140,
//    "session_name": "Sprint",
//    "session_type": "Race",
//    "year": 2023
//  }
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record OpenF1Session(int sessionKey,
                            String circuitShortName,
                            String location,
                            String sessionType,
                            int year,
                            String countryName,
                            String countryCode) {

}
