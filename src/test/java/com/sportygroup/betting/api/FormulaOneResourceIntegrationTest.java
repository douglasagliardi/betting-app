package com.sportygroup.betting.api;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.sportygroup.betting.BettingApplication;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.junit.jupiter.Testcontainers;

@AutoConfigureMockMvc
@AutoConfigureWireMock(port = 0)
@SpringBootTest(
    classes = BettingApplication.class,
    webEnvironment = WebEnvironment.RANDOM_PORT
)
@Testcontainers
@DisplayName("Integration tests - Formula 1 - betting system")
class FormulaOneResourceIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @DisplayName("User can list all upcoming events - no params provided should return all (maybe it should be 'paginated'...)")
  @Test
  void userCanListAllUpcomingEvents() throws Exception {

    WireMock.stubFor(WireMock.get(WireMock.urlPathEqualTo("/v1/sessions"))
        .withQueryParam("session_type", WireMock.equalTo("Race"))
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
            //1st driver
            jsonPath("$.events.[0].event_id").value(99),
            jsonPath("$.events.[*].drivers.[*]", hasSize(2)),
            jsonPath("$.events.[0].drivers[0].id").value(1),
            jsonPath("$.events.[0].drivers[0].display_name").value("Max Verstappen"),
            jsonPath("$.events.[0].drivers[0].odd").isNumber(),
            //2nd driver
            jsonPath("$.events.[0].drivers[1].id").value(2),
            jsonPath("$.events.[0].drivers[1].display_name").value("Logan Sargeant"),
            jsonPath("$.events.[0].drivers[1].odd").isNumber()
        );
  }
}