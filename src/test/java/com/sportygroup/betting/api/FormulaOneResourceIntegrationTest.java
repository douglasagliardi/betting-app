package com.sportygroup.betting.api;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.sportygroup.betting.AbstractIntegrationIT;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

@DisplayName("Integration tests - Formula 1 - betting system")
class FormulaOneResourceIntegrationTest extends AbstractIntegrationIT {

  @Autowired
  private MockMvc mockMvc;

  @Nested
  final class ViewEventsTest {

    @DisplayName("User should list all 'upcoming' events")
    @Test
    void userCanListAllUpcomingEvents() throws Exception {

      WireMock.stubFor(WireMock.get(WireMock.urlPathEqualTo("/v1/sessions"))
          .withHeader(HttpHeaders.ACCEPT, WireMock.equalTo(MediaType.APPLICATION_JSON_VALUE))
          .willReturn(WireMock.aResponse()
              .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
              .withBodyFile("openf1-api/sessions/get-all-sessions-no-filter-success-200.json")));

      WireMock.stubFor(WireMock.get(WireMock.urlPathEqualTo("/v1/drivers"))
          .withQueryParam("session_key", WireMock.equalTo("99"))
          .withHeader(HttpHeaders.ACCEPT, WireMock.equalTo(MediaType.APPLICATION_JSON_VALUE))
          .willReturn(WireMock.aResponse()
              .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
              .withBodyFile("openf1-api/drivers/get-drivers-for-race-id-99-success-200.json")));

      mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/bets/formulaone")
              .accept(MediaType.APPLICATION_JSON_VALUE))
          .andExpectAll(
              status().isOk(),
              jsonPath("$.events.[*]", hasSize(1)),
              //circuit details
              jsonPath("$.events.[0].id").value(99),
              jsonPath("$.events.[0].circuit_name").value("Sakhir"),
              jsonPath("$.events.[0].location.country").value("Bahrain"),
              jsonPath("$.events.[0].location.country_code").value("BRN"),
              //1st driver
              jsonPath("$.events.[*].drivers.[*]", hasSize(2)),
              jsonPath("$.events.[0].drivers[0].id").value(1),
              jsonPath("$.events.[0].drivers[0].display_name").value("Max Verstappen"),
              jsonPath("$.events.[0].drivers[0].odd").isNumber(),
              //2nd driver
              jsonPath("$.events.[0].drivers[1].id").value(2),
              jsonPath("$.events.[0].drivers[1].display_name").value("Logan Sargeant"),
              jsonPath("$.events.[0].drivers[1].odd").isNumber()
          )
          .andDo(MockMvcResultHandlers.print());

      WireMock.verify(WireMock.getRequestedFor(WireMock.urlPathEqualTo("/v1/sessions"))
          .withHeader(HttpHeaders.ACCEPT, WireMock.equalTo(MediaType.APPLICATION_JSON_VALUE))
      );
      WireMock.verify(WireMock.getRequestedFor(WireMock.urlPathEqualTo(("/v1/drivers")))
          .withHeader(HttpHeaders.ACCEPT, WireMock.equalTo(MediaType.APPLICATION_JSON_VALUE))
          .withQueryParam("session_key", WireMock.equalTo("99"))
      );
    }
  }

  @Nested
  final class QueryEventsWithFilters {

    @DisplayName("User should be able to list upcoming events by type")
    @ParameterizedTest
    @CsvSource(value = {
        "event_type, session_type, Qualifying",
        "country_code, country_code, DE",
        "year, year, 2023"
    })
    void getEventsByParameters(final String apiParam, final String partnerApiParam, final String parameterValue) throws Exception {

      WireMock.stubFor(WireMock.get(WireMock.urlPathEqualTo("/v1/sessions"))
          .withQueryParam(partnerApiParam, WireMock.equalTo(parameterValue))
          .withHeader(HttpHeaders.ACCEPT, WireMock.equalTo(MediaType.APPLICATION_JSON_VALUE))
          .willReturn(WireMock.aResponse()
              .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
              .withBodyFile("openf1-api/sessions/get-sessions-by-type-qualifying-success-200.json")));

      WireMock.stubFor(WireMock.get(WireMock.urlPathEqualTo("/v1/drivers"))
          .withQueryParam("session_key", WireMock.equalTo("100"))
          .withHeader(HttpHeaders.ACCEPT, WireMock.equalTo(MediaType.APPLICATION_JSON_VALUE))
          .willReturn(WireMock.aResponse()
              .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
              .withBodyFile("openf1-api/drivers/get-drivers-for-race-id-100-success-200.json")));

      WireMock.stubFor(WireMock.get(WireMock.urlPathEqualTo("/v1/drivers"))
          .withQueryParam("session_key", WireMock.equalTo("110"))
          .withHeader(HttpHeaders.ACCEPT, WireMock.equalTo(MediaType.APPLICATION_JSON_VALUE))
          .willReturn(WireMock.aResponse()
              .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
              .withBodyFile("openf1-api/drivers/get-drivers-for-race-id-110-success-200.json")));

      mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/bets/formulaone")
              .param(apiParam, parameterValue)
              .accept(MediaType.APPLICATION_JSON_VALUE))
          .andExpectAll(
              status().isOk(),
              jsonPath("$.events.[*]", hasSize(2)),
              //1st circuit
              jsonPath("$.events.[0].id").value(100),
              jsonPath("$.events.[0].circuit_name").value("Melbourne"),
              jsonPath("$.events.[0].location.country").value("Australia"),
              jsonPath("$.events.[0].location.country_code").value("AUS"),
              //1st circuit-1st driver
              jsonPath("$.events.[0].drivers.[*]", hasSize(2)),
              jsonPath("$.events.[0].drivers[0].id").value(14),
              jsonPath("$.events.[0].drivers[0].display_name").value("Fernando Alonso"),
              jsonPath("$.events.[0].drivers[0].odd").isNumber(),
              //1st circuit-2nd driver
              jsonPath("$.events.[0].drivers[1].id").value(44),
              jsonPath("$.events.[0].drivers[1].display_name").value("Lewis Hamilton"),
              jsonPath("$.events.[0].drivers[1].odd").isNumber(),

              //2nd circuit
              jsonPath("$.events.[1].drivers.[*]", hasSize(2)),
              jsonPath("$.events.[1].id").value(110),
              jsonPath("$.events.[1].circuit_name").value("Miami"),
              jsonPath("$.events.[1].location.country").value("United States"),
              jsonPath("$.events.[1].location.country_code").value("USA"),
              //2nd circuit-1st driver
              jsonPath("$.events.[1].drivers[0].id").value(14),
              jsonPath("$.events.[1].drivers[0].display_name").value("Fernando Alonso"),
              jsonPath("$.events.[1].drivers[0].odd").isNumber(),
              //2nd circuit-2nd driver
              jsonPath("$.events.[1].drivers[1].id").value(55),
              jsonPath("$.events.[1].drivers[1].display_name").value("Carlos Sainz"),
              jsonPath("$.events.[1].drivers[1].odd").isNumber()
          )
          .andDo(MockMvcResultHandlers.print());

      WireMock.verify(WireMock.getRequestedFor(WireMock.urlPathEqualTo("/v1/sessions"))
          .withHeader(HttpHeaders.ACCEPT, WireMock.equalTo(MediaType.APPLICATION_JSON_VALUE))
          .withQueryParam(partnerApiParam, WireMock.equalTo(parameterValue))
      );
      //get drivers for 1st event
      WireMock.verify(WireMock.getRequestedFor(WireMock.urlPathEqualTo(("/v1/drivers")))
          .withHeader(HttpHeaders.ACCEPT, WireMock.equalTo(MediaType.APPLICATION_JSON_VALUE))
          .withQueryParam("session_key", WireMock.equalTo("100"))
      );
      //get drivers for 2nd event
      WireMock.verify(WireMock.getRequestedFor(WireMock.urlPathEqualTo(("/v1/drivers")))
          .withHeader(HttpHeaders.ACCEPT, WireMock.equalTo(MediaType.APPLICATION_JSON_VALUE))
          .withQueryParam("session_key", WireMock.equalTo("110"))
      );
    }
  }

  @DisplayName("Betting on events")
  @Nested
  final class BetOnEventTest {

    @Test
    @DisplayName("Customer can place a bet for a driver on an event")
    @Sql("/sql-script/bootstrap-wallet-for-betting.sql")
    void customerCanPlaceBetForADriverOnANewEvent() throws Exception {

      final var betPayload = """
          {
            "user_id": 1,
            "wallet_id": 10,
            "event_id": 1,
            "amount": 100,
            "odd": 2
          }
          """;
      mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/bets/formulaone")
              .content(betPayload)
              .contentType(MediaType.APPLICATION_JSON_VALUE)
              .accept(MediaType.APPLICATION_JSON_VALUE))
          .andExpectAll(
              status().isCreated(),
              header().string("Location", is("http://localhost/api/v1/accounts/wallets/10/bookings/1"))
          );
    }

    @Disabled("Desired but maybe not necessary at this point.")
    @Test
    @DisplayName("Customer placing a bet without enough funds should return error with details")
    @Sql("/sql-script/bootstrap-wallet-without-funds-for-betting.sql")
    void customerWithoutEnoughBalanceShouldNotBeAbleTo() {
      //implementation goes here...
    }
  }

  @DisplayName("Finishing events")
  @Nested
  final class FinishingEventTest {

    @Test
    @DisplayName("Operator or Agent system on finishing event should payout all winners")
    @Sql("/sql-script/bootstrap-bet-bookings.sql")
    void customerCanPlaceBetForADriverOnANewEvent() throws Exception {

      final var eventId = "50";

      final var formulaOneEventCompletedPayload = """
          {
              "driver_id": 50,
              "name": "Fernando Alonso"
          }
          """;

      mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/bets/formulaone/events/{eventId}", eventId)
              .content(formulaOneEventCompletedPayload)
              .contentType(MediaType.APPLICATION_JSON_VALUE))
          .andExpectAll(status().isAccepted());
    }
  }

  @DisplayName("Exception Handling - exception, timeout(s), invalid parameter(s), invalid operations")
  @Nested
  final class ErrorHandlingTest {

  }
}