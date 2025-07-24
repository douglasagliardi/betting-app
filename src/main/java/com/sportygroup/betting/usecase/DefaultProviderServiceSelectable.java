package com.sportygroup.betting.usecase;

import com.sportygroup.betting.infrastructure.database.ProviderDatasource;
import com.sportygroup.betting.infrastructure.database.ProviderDatasourceRepository;
import com.sportygroup.betting.infrastructure.database.SportyType;
import com.sportygroup.betting.infrastructure.externalapi.formulaone.client.FormulaOneApiService;
import java.util.Collection;
import org.springframework.stereotype.Component;

@Component
public class DefaultProviderServiceSelectable implements ProviderServiceSelectable {

  private final ProviderDatasourceRepository repository;
  private final Collection<FormulaOneApiService> formulaOneApiCandidates;

  public DefaultProviderServiceSelectable(final ProviderDatasourceRepository repository,
      final Collection<FormulaOneApiService> formulaOneApiCandidates) {
    this.repository = repository;
    this.formulaOneApiCandidates = formulaOneApiCandidates;
  }

  @Override
  public FormulaOneApiService getApiService() {
    final var provider = repository.findByType(SportyType.FORMULA_ONE)
        .map(ProviderDatasource::getProvider)
        .orElse("fallback");
    return formulaOneApiCandidates.stream()
        .filter(e -> e.getProvider().equalsIgnoreCase(provider))
        .findFirst()
        .orElseGet(this::getFallback);
  }

  private FormulaOneApiService getFallback() {
    return formulaOneApiCandidates.stream().filter(FormulaOneApiService::isFallback).findFirst().orElseThrow();
  }
}
