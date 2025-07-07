##

### Main operations


1. View List of Formula 1 Events
   A. The User queries the list of F1 events which can:
    i. Be filtered by:
       1. Session Type 
       2. Year
       3. Country

   B. The System gets the F1 events from the open-source API here and:
    i. Returns the relevant data to the User. The events are called Sessions
    inside that API.
        ii. It also returns the Driver Market of each event:
            1. The full name of the Driver.
            2. The ID Number of the Driver.
            3. The odds when placing a bet for this driver to win the F1
          event.
        a. For simplicity, value can only be `2`, `3` or `4`.
        b. Always return a random integer between these 3 values.