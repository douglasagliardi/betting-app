package com.sportygroup.betting.api;

import com.sportygroup.betting.domain.FormulaOneEvents;
import com.sportygroup.betting.domain.FormulaOneParams;
import com.sportygroup.betting.usecase.FormulaOneService;
import java.net.URI;
import java.security.SecureRandom;
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
@RequestMapping(
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE,
    value = FormulaOneSpec.BASE_PATH
)
public class FormulaOneResource implements FormulaOneSpec {

  private final FormulaOneService formulaOneService;

  public FormulaOneResource(final FormulaOneService formulaOneService) {
    this.formulaOneService = formulaOneService;
  }

  @GetMapping
  @Override
  public ResponseEntity<FormulaOneEvents> getEvents(
      @RequestParam(value = "event_type", required = false) final String eventType,
      @RequestParam(value = "country_code", required = false) final String countryCode,
      @RequestParam(value = "year", required = false) final Integer year) {
    final var paramsBuilder = new FormulaOneParams.Builder();
    return ResponseEntity.ok(formulaOneService.getEvents(paramsBuilder
        .withEventType(eventType)
        .withCountryCode(countryCode)
        .withYear(year)
        .build())
    );
  }

  @PostMapping
  public ResponseEntity<PlacedBetResponse> placeBet(@RequestBody final PlaceBetRequest request,
      final UriComponentsBuilder uriComponentsBuilder) {
    final var betResponse = new PlacedBetResponse(300); //this should come from service layer...
    final URI location = uriComponentsBuilder.path("/api/v1/accounts/wallets/{walletId}/bookings/{betId}")
        .buildAndExpand(request.walletId(), betResponse.betId()).toUri();
    return ResponseEntity.created(location).body(betResponse);
  }
}
