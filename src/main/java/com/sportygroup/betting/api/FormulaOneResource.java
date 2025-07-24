package com.sportygroup.betting.api;

import com.sportygroup.betting.domain.FormulaOneEvents;
import com.sportygroup.betting.domain.FormulaOneRaceQuery;
import com.sportygroup.betting.usecase.BetBookingService;
import com.sportygroup.betting.usecase.FormulaOneService;
import java.net.URI;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
@RequestMapping(value = FormulaOneSpec.BASE_PATH)
public class FormulaOneResource implements FormulaOneSpec {

  private final FormulaOneService formulaOneService;
  private final BetBookingService betBookingService;

  public FormulaOneResource(final FormulaOneService formulaOneService, final BetBookingService betBookingService) {
    this.formulaOneService = formulaOneService;
    this.betBookingService = betBookingService;
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  @Override
  public ResponseEntity<FormulaOneEvents> getEvents(
      @RequestParam(value = "event_type", required = false) final String eventType,
      @RequestParam(value = "country_code", required = false) final String countryCode,
      @RequestParam(value = "year", required = false) final Integer year) {
    final var paramsBuilder = new FormulaOneRaceQuery.Builder();
    return ResponseEntity.ok(formulaOneService.getDriverOddsForEvents(paramsBuilder
        .withEventType(eventType)
        .withCountryCode(countryCode)
        .withYear(year)
        .build())
    );
  }

  @Override
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<PlacedBetResponse> placeBet(@RequestBody final PlaceBetRequest request,
      final UriComponentsBuilder uriComponentsBuilder) {
    final var orderPlaced = betBookingService.create(request);
    final URI location = uriComponentsBuilder.path("/api/v1/accounts/wallets/{walletId}/bookings/{betId}")
        .buildAndExpand(request.walletId(), orderPlaced.betId()).toUri();
    return ResponseEntity.created(location).build();
  }

  @Override
  @PostMapping(value = "/events", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Void> finishEvent(@RequestBody final FormulaOneEventResultRequest request) {
    betBookingService.completeRace(request);
    return ResponseEntity.accepted().build();
  }
}
