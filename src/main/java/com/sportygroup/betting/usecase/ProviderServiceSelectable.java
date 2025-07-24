package com.sportygroup.betting.usecase;

import com.sportygroup.betting.infrastructure.externalapi.formulaone.client.FormulaOneApiService;

public interface ProviderServiceSelectable {

  FormulaOneApiService getApiService();
}
