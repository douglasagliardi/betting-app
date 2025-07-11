package com.sportygroup.betting.api;

import com.sportygroup.betting.domain.FormulaOneEvents;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
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
              schema = @Schema(implementation = FormulaOneEvents.class)
          )
      ),
      @ApiResponse(
          responseCode = "400",
          description = "Invalid parameters",
          content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
              schema = @Schema(implementation = ProblemDetail.class)
          )
      ),
      @ApiResponse(
          responseCode = "500",
          description = "Internal server error",
          content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
              schema = @Schema(implementation = ProblemDetail.class)
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
          content = @Content,
          headers = {
              @Header(
                  name = "Location",
                  description = "Resource location after customer has placed a bet successfully",
                  example = "/api/v1/accounts/wallets/100/bookings/5000")
          }
      ),
      @ApiResponse(
          responseCode = "400",
          description = "Invalid parameters",
          content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
              schema = @Schema(implementation = ProblemDetail.class)
          )
      ),
      @ApiResponse(
          responseCode = "412",
          description = "Invalid parameters",
          content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
              schema = @Schema(implementation = ProblemDetail.class)
          )
      ),
      @ApiResponse(
          responseCode = "500",
          description = "Internal server error",
          content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
              schema = @Schema(implementation = ProblemDetail.class)
          )
      )}
  )
  ResponseEntity<PlacedBetResponse> placeBet(@RequestBody PlaceBetRequest request, UriComponentsBuilder uriComponentsBuilder);

  @Operation(
      summary = "Place bet for driver on event",
      description = "Requests the placement of a bet for racing driver on a given event.",
      operationId = "placeBet",
      tags = "formula-one"
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "202",
          description = "Accepted",
          content = @Content
      ),
      @ApiResponse(
          responseCode = "400",
          description = "Invalid parameters",
          content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
              schema = @Schema(implementation = ProblemDetail.class)
          )
      ),
      @ApiResponse(
          responseCode = "500",
          description = "Internal server error",
          content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
              schema = @Schema(implementation = ProblemDetail.class)
          )
      )}
  )
  ResponseEntity<Void> finishEvent(@RequestBody FormulaOneEventResultRequest request);
}
