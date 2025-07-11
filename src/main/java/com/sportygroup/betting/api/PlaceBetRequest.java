package com.sportygroup.betting.api;

import com.sportygroup.betting.api.schema.FormulaOneAmountSchema;
import com.sportygroup.betting.api.schema.FormulaOneCustomerWalletIdSchema;
import com.sportygroup.betting.api.schema.FormulaOneDriverIdSchema;
import com.sportygroup.betting.api.schema.FormulaOneDriverOddSchema;
import com.sportygroup.betting.api.schema.FormulaOneEventIdSchema;
import io.swagger.v3.oas.annotations.media.Schema;

public record PlaceBetRequest(@Schema(implementation = FormulaOneEventIdSchema.class) long eventId,
                              @Schema(implementation = FormulaOneCustomerWalletIdSchema.class) long walletId,
                              @Schema(implementation = FormulaOneDriverIdSchema.class) int driverId,
                              @Schema(implementation = FormulaOneAmountSchema.class) long amount,
                              @Schema(implementation = FormulaOneDriverOddSchema.class) int odd) {}
