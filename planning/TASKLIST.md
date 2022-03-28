
### TASK LIST
1. [ ] Create List of Features and necessary Methods.
   1. [ ] VIEW RESERVATIONS
      1. Data Layer
         1. [ ] readFile - deserialize() data file and populate array -> findAllReservations()
      2. Domain Layer
         1. [ ] findReservationsByHost - sort findAllReservations() by provided host
         2. [ ] Validate - (Host Not Found), (Host Has No Reservations)
      3. Ui Layer
         1. [ ] displayReservations() - Format and display sorted reservations and all useful info attached to the reservations
         2. [ ] chooseHost() - User input for choosing host
   2. [ ] MAKE A RESERVATION
      1. Data Layer
         1. [ ] readFile -> findAllHosts(), findAllReservations(), findAllGuests 
         2. [ ] saveReservation() - serialize() reservation and write to data file
      2. Domain Layer
         1. [ ] findGuest() - return specific Guest from findAllGuests() with unique identifier
         2. [ ] findHost() - return specific Host from findALlHosts() with unique identifier
         3. [ ] findReservationsByHost() - sort findAllReservations() by provided host
         4. [ ] saveReservation() - returns saved reservation from ui to data
         5. [ ] validate - (Guest is required), (Host is required), (Valid stay dates are required), (Guest doesn't exist), (Host doesn't exist),
         6. [ ] validate continued - (Start date must be after the end date), (Start date must be in the future), (Reservation dates cannot overlap existing reservations)
      3. Ui Layer
         1. [ ] displayAvailableReservations() - sort findReservationsByHost to only show reservations in the future
         2. [ ] chooseHost() -  User input for choosing host
         3. [ ] chooseGuest() - User input for choosing guest
         4. [ ] getDates() - User input for start and end dates of reservation
         5. [ ] confirmation() - display total amount, host, guest, reservations dates and ask for confirmation
         6. [ ] displayResult() - Confirming success, or informing of errors
   3. [ ] EDIT A RESERVATION
      1. Data Layer
         1. [ ] readFile -> findAllReservations()
         2. [ ] saveReservation()
      2. Domain Layer
         1. [ ] findReservationsByGuest() - sort findAllReservations by provided guest
         2. [ ] findReservationsByHost() - sort findAllReservations by provided Host
         3. [ ] updateReservation() - Alter dates of an existing reservation
         4. [ ] saveReservation()
      3. Ui Layer
         1. [ ] chooseGuest() - User input for choosing guest
         2. [ ] chooseHost() - User input for choosing Host
         3. [ ] displayReservations()
         4. [ ] chooseReservation() - Select reservation
         5. [ ] getDates() - User input for start and end dates of reservation
         6. [ ] confirmation()
         7. [ ] displayResult()
   4. [ ] CANCEL A RESERVATION
      1. Data Layer
         1. [ ] readFile -> findAllReservations()
         2. [ ] deleteReservation()
      2. Domain Layer
         1. [ ] validate - (Can only delete future reservations)
         2. [ ] deleteReservation() - pass validated reservation to file repository
         3. [ ] findReservationByGuest()
         4. [ ] findReservationByHost()
      3. Ui
         1. [ ] chooseHost()
         2. [ ] chooseGuest()
         3. [ ] displayReservations()
         4. [ ] chooseReservations()
         5. [ ] confirmation()
         6. [ ] displayResult()