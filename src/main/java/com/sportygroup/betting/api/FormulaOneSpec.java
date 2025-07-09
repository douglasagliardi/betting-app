package com.sportygroup.betting.api;

import com.sportygroup.betting.infrastructure.externalapi.formulaone.FormulaOneEvents;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.security.InvalidParameterException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

public interface FormulaOneSpec {

  @SuppressWarnings("java:S1075")
  String BASE_PATH = "/api/v1/bets/formulaone";

  @Operation(
      summary = "Get events",
      description = "Requests a list of events containing the drivers and respective chances to win the race.",
      operationId = "getEvents",
      tags = "formula-one"
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Successful operation",
          content = @Content(
              mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(oneOf = {
                  String.class,
              })
          )
      ),
      @ApiResponse(
          responseCode = "400",
          description = "Invalid parameters",
          content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
              schema = @Schema(implementation = InvalidParameterException.class)
          )
      ),
      @ApiResponse(
          responseCode = "500",
          description = "Internal server error",
          content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
              schema = @Schema(implementation = String.class)
          )
      )}
  )
  ResponseEntity<FormulaOneEvents> getEvents(
      @RequestParam(value = "event_type", required = false) final String eventType,
      @RequestParam(value = "country_code", required = false) final String countryCode,
      @RequestParam(value = "year", required = false) final Integer year);
}
