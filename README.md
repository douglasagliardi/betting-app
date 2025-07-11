# Betting on F1 System

### Requirements

1. View List of Formula 1 Events
    - The User queries the list of F1 events which can be filtered by: a. Session Type, b. Year and 3. Country.
    - The System gets the F1 events from the open-source API here and:
        - Returns the relevant data to the User. The events are called Sessions inside that API. It also returns the Driver Market of each event:
            - The full name of the Driver.
            - The ID Number of the Driver.
            - The odds when placing a bet for this driver to win the F1 event.
    - For simplicity, value can only be `2`, `3` or `4`.
    - Always return a random integer between these 3 values.

2. To place a bet.
    - The User places a Single Bet for the driver he/she thinks will win the specified F1 event.
        - For simplicity, let’s say the User can place bets in any F1 event from the past so the API shared before can be used.
        - The User specifies the amount to bet in EUR.
    - The System places the bet for the User and updates the User Balance.

3. Event outcome
    - The System receives a request for a F1 Event that has been finished.
        - We get the ID of the event and the ID of the driver that won.
    - The System saves the outcome.
    - The System checks which bets have won and which ones are lost and updates their status.
    - If a given bet was won, the System calculates the prize for the User.
        - e. The System adds the won money to each User Balance.

### Conditions:

- For simplicity, the User is already registered. Consider each user has an associated `User ID` you pass as parameter.
- For simplicity, the User cannot deposit or withdraw money. They can only play with `100 EUR` given as a gift during the registration stage.
- We will add new F1 event provider API’s in the future. Ensure your solution **is not coupled** with the 3rd party `API`.

## Solution

## Missing or incomplete parts

As there are limitations around time to deliver, I'd to focus on the main requirements and tried to exemplify the approach of the solution.
Some aspects I'd like to put more effort into but could not are:

#### 3rd Party API limitations & Application Design

- Unfortunately, the external system which holds the information has strict policy over Rate Limit (max 10 requests per min), which prevents this
  bootstrap the application and run some exercises with betting on customer accounts/wallets.
    - Alternative: I've set up a `wiremock` for testing locally with **limited examples** from the live API for testing. See testing locally section
      for more details.
    - If I had time, I'd completely revisit this design. Probably a background job to sync historical data would make more sense instead of live query
      on 3rd party API.
        - Additionally, another service could provide such interface along with ant-corruption layer.

#### Code quality & application maintenance.

- increase coverage of scenarios (e.g., multiple bets for same customer, same event, different odds)
- wider validation on customer input & logic.
- additional logging
- anti-corruption layer -> implement a clear separation between the layers (incoming request DTO -> domain entity -> 3rd party API).
    - suggestion: used [MapStruct](https://mapstruct.org/) library to reduce boilerplate while transforming from one to another.

#### Production readiness

- Docker image for deployable application.
- Missing helm-configuration on project (although running `helm init` would just create boilerplate).
- Http client -> handling of exceptions, custom error decoder.
- Instrumentation & Metrics -> unable to properly instrument the service and configure actuator to expose the endpoints for health & readiness checks.
    - why? usually used by Kubernetes to control if applicable is up-and-running and ready to accept traffic to get in the load balancer.

#### Scalability

- Implement pagination while getting the bets made on a given event. Considering that we could have millions of bets (event for the same user/wallet).
    - suggestion(s):
        - partitioning of the `bet-booking` table, by attributes like 'country-code'.
        - maybe a different strategy to apply bulk-operations to update account balances.

#### Product enhancements (suggestions)

- The odds SHALL come from a different service instead of generating one event/driver.
    - This could be integrated with ML backend API or event aggregated from different sources in order to provide live odds.
- The account & wallet design was to allow customers to have different places to do/keep bets. Why? Some people are organized and I think it makes
  even sense to have a wallet per type of betting or sporty (e.g., football wallet A, f1 wallet b) so you can compare personal performance based on
  different sports.
- Propagation of business events when placing a bet. This could feed into the ML datalake to e.g., vary the odds for a given event -> trends to up or
  down depending on N-factors.
- developed a resource to get customer wallet (dashboard) for activities - it would even make easy to assert scenarios on testing.

#### Maintainability

- The incoming request about ending/completing an event **probably** could have been automated via live-api from OpenF1 instead of manually doing so.

#### Security

- No security layer on top of bets, no RBAC on API endpoints, etc.
    - suggestion: OAuth2 with customer token should suffice.

## Testing locally

### First things first

**Requirements**

- you MUST have docker install on your computer
- you MUST have the docker-daemon running.
- note: unfortunately, no time to use a more 'standard' and open source solution to bootstrap dependencies (e.g., mini-kube).

```
$ docker compose up
```

There are 3 dependencies to run this app locally.

- POSTGRESQL (v17) - to keep bets for events
- RABBITMQ (v3.7) - to asynchronous process a result of an event for all customers which did bet on it
- WIREMOCK - to mimic the OpenF1 API - due to API quotas, the live integration is quite limited without a login/credentials (10 r/p).

### Live API documentation

After the container has started, you can access the following endpoint: `http:localhost:8080/docs/api.html`

- Tips: you can try out the API using the live specification.

### Trying the API

**IMPORTANT**: I've set up some data for the execution of the application. It only serves to demonstrate the code challenge (migration v2).

-> I've created a few examples (requests + payload) under the folder `/development` -> see file `betting-api.http`.