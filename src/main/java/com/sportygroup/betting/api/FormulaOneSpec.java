package com.sportygroup.betting.api;

import com.sportygroup.betting.domain.FormulaOneEvents;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.security.InvalidParameterException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriComponentsBuilder;

public interface FormulaOneSpec {

  @SuppressWarnings("java:S1075")
  String BASE_PATH = "/api/v1/bets/formulaone";

  @Operation(
      summary = "Get driver odds for events",
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
      @RequestParam(value = "event_type", required = false) String eventType,
      @RequestParam(value = "country_code", required = false) String countryCode,
      @RequestParam(value = "year", required = false) Integer year);

  @Operation(
      summary = "Place bet for driver on event",
      description = "Requests the placement of a bet for racing driver on a given event.",
      operationId = "placeBet",
      tags = "formula-one"
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "201",
          description = "Successfully created",
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
          responseCode = "412",
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
  ResponseEntity<PlacedBetResponse> placeBet(@RequestBody PlaceBetRequest request, UriComponentsBuilder uriComponentsBuilder);

  ResponseEntity<Void> finishEvent(@PathVariable long eventId, @RequestBody FormulaOneEventResultRequest request);
}
