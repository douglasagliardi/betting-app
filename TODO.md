

### Items to develop

- create layer to fetch data from OpenF1 for needed operations
- create mapping between OpenF1 and Sporty Group data-model (modelling)
- MAYBE: create an asynchronous job to periodically fetch the data from OpenF1 API OR query the data and provide them via Wiremock for offline usage.
- create endpoint to receive a betting from a given customer
  - book the amount from user balance
  - MAYBE: propagate business event to a broker - e.g., analytics (not a requirement but would be nice to get insights from user-behaviour)
- create endpoint (or topic/queue) to receive live events, including one to complete a given event
  - create logic to evaluate bookings (aka bets) and, for each bet, we shall adjust the balance accordingly.
    - MAYBE: one business event (aka event/message) for each individual customer, as processing "millions" of transactions is not a scalable solution.
- MAYBE: create endpoint for customer to check their profile, including their balance, upcoming events matching customer activities, etc.