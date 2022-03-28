
# Don't Wreck My House Planning Document

## Target User
The application user is an accommodation administrator. They pair guests to hosts to make reservations.

## Helpful Terminology
* Guest
- A customer. Someone who wants to book a place to stay.
- Guest data is provided via a zip download.

* Host
- The accommodation provider.
- Someone who has a property to rent per night. Host data is provided.

* Location
- A rental property. In Don't Wreck My House, Location and Host are combined.
- The application enforces a limit on one Location per Host, so we can think of a Host and Location as a single thing.

* Reservation
- One or more days where a Guest has exclusive access to a Location (or Host).
- Reservation data is provided.

* Administrator
- The application user.
- Guests and Hosts don't book their own Reservations. The Administrator does it for them.

## Top Level Goals
1. [ ] The administrator may view existing reservations for a host.
2. [ ] The administrator may create a reservation for a guest with a host.
3. [ ] The administrator may edit existing reservations.
4. [ ] The administrator may cancel a future reservation.

### Requirements
* View Reservations for Host - Display all reservations for a host.
- The user may enter a value that uniquely identifies a host or they can search for a host and pick one out of a list.
- If the host is not found, display a message.
- If the host has no reservations, display a message.
- Show all reservations for that host.
- Show useful information for each reservation: the guest, dates, totals, etc.
- Sort reservations in a meaningful way.

* Make a Reservation - Books accommodations for a guest at a host.
- The user may enter a value that uniquely identifies a guest or they can search for a guest and pick one out of a list.
- The user may enter a value that uniquely identifies a host or they can search for a host and pick one out of a list.
- Show all future reservations for that host so the administrator can choose available dates.
- Enter a start and end date for the reservation.
- Calculate the total, display a summary, and ask the user to confirm. The reservation total is based on the host's standard rate and weekend rate. For each day in the reservation, determine if it is a weekday (Sunday, Monday, Tuesday, Wednesday, or Thursday) or a weekend (Friday or Saturday). If it's a weekday, the standard rate applies. If it's a weekend, the weekend rate applies.
- On confirmation, save the reservation.

* Edit a Reservation - Edits an existing reservation.
- Find a reservation.
- Start and end date can be edited. No other data can be edited.
- Recalculate the total, display a summary, and ask the user to confirm.
  
* Cancel a Reservation - Cancel a future reservation.

### Validation

* Making a reservation
  1. Guest, host, and start and end dates are required.
  2. The guest and host must already exist in the "database". Guests and hosts cannot be created.
  3. The start date must come before the end date.
  4. The reservation may never overlap existing reservation dates.
  
* Finding a reservation.
  1. Only future reservations are shown. 
  2. On success, display a message.

* Cancelling
  1. You cannot cancel a reservation that's in the past.

### Technical Requirements
1. Must be a Maven project.
2. Spring dependency injection configured with either XML or annotations.
3. All financial math must use BigDecimal.
4. Dates must be LocalDate, never strings.
5. All file data must be represented in models in the application.
6. Reservation identifiers are unique per host, not unique across the entire application.
   - Effectively, the combination of a reservation identifier and a host identifier is required.

### Deliverables
1. [ ] A schedule of concrete tasks. Tasks should contain time estimates and should never be more than 4 hours.
2. [ ] Class diagram (informal is okay)
3. [ ] Sequence diagrams or flow charts (optional, but encouraged)
4. [ ] A Java Maven project that contains everything needed to run without error
5. [ ] Test suite


### Stretch Goals
1. Make reservation identifiers unique across the entire application.
2. Allow the user to create, edit, and delete guests.
3. Allow the user to create, edit, and delete hosts.
4. Display reservations for a guest.
5. Display all reservations for a state, city, or zip code.
6. Implement another set of repositories that store data in JSON format.
   - In this approach, you don't want multiple files for reservations.
   - Consider how you might migrate existing data to the JSON format safely.