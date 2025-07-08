package com.sportygroup.betting.api;

import com.sportygroup.betting.infrastructure.externalapi.formulaone.FormulaOneEvents;
import com.sportygroup.betting.infrastructure.externalapi.formulaone.FormulaOneParams;
import com.sportygroup.betting.infrastructure.externalapi.formulaone.FormulaOneService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/api/v1/bets/formulaone")
public class FormulaOneResource {

  private final FormulaOneService formulaOneService;

  public FormulaOneResource(final FormulaOneService formulaOneService) {
    this.formulaOneService = formulaOneService;
  }

  @GetMapping
  public ResponseEntity<FormulaOneEvents> getEvents(
      @RequestParam(value = "event_type", required = false, defaultValue = "Race") final String eventType,
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
}
