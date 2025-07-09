##

### Main operations


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