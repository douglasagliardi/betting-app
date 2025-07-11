package com.sportygroup.betting.api.schema;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
    description = "the id of the customer's wallet. Each customer might have more than one wallet to place bets.",
    example = "150",
    name = "wallet_id",
    type = "long"
)
public @interface FormulaOneCustomerWalletIdSchema { }
