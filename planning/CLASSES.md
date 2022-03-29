## Class Definitions

### DATA
* DataAccessException - General Exception to communicate errors with user
* ReservationFileRepository - CRUD operations for Reservations
* HostFileRepository - CRUD operations for Host
* GuestFileRepository - CRUD operations for Guest

### DOMAIN
* ReservationService - Validation of data and Ui access to CRUD operations for Reservation
* HostService - Validation of data and Ui access to CRUD operations for Host
* GuestService - Validation of data and Ui access to CRUD operations for Guest
* Result - Communication of success of validation or of CRUD operations

### MODELS
* Host 
- UUID id,
- String firstName,
- String lastName,
- String email,
- String phone,
- String address,
- String city,
- String state,
- String postal_code,
- BigDecimal standard_rate,
- BigDecimal weekend_rate
* Guest
- UUID guestId,
- String firstName,
- String lastName,
- String email,
- String phone,
- String state
* Reservation
- UUID hostId,
- int reservationId,
- LocalDate startDate,
- LocalDate endDate,
- UUID guestId,
- BigDecimal total

### UI
MenuOptions - Enum defining menu options
View - controls display to user and delivers user inputs to the controller
Controller - communicates between view and domain
ConsoleIo - Read\Write operations for console
TextIo - Defines ConsoleIo operations



File Structure:
```
.
└── mastery-week-05/
├── .idea
├── dont-wreck-my-house-data/
│   ├── production/
│   │   ├── reservations
│   │   ├── guests
│   │   └── hosts
│   └── test/
│       ├── reservations-seed
│       └── reservations-test
├── planning
└── src/
├── main/
│   ├── java/
│   │   ├── myhouse/
│   │   │   ├── data/
│   │   │   │   ├── DataAccessException.java
│   │   │   │   ├── ReservationFileRepository.java
│   │   │   │   ├── ReservationRepository.java
│   │   │   │   ├── HostFileRepository
│   │   │   │   ├── HostRepository
│   │   │   │   ├── GuestFileRepository
│   │   │   │   └── GuestRepository
│   │   │   ├── domain/
│   │   │   │   ├── ReservationService
│   │   │   │   ├── HostService
│   │   │   │   ├── GuestService
│   │   │   │   └── Result
│   │   │   ├── models/
│   │   │   │   ├── Reservation
│   │   │   │   ├── Host
│   │   │   │   └── Guest
│   │   │   └── ui/
│   │   │       ├── MenuOptions
│   │   │       ├── View
│   │   │       ├── Controller
│   │   │       ├── ConsoleIo
│   │   │       └── TextIo
│   │   └── app.java
│   └── resources
└── test/
└── java/
├── data/
│   ├── ReservationFileReposotoryTest
│   ├── HostFileRepositoryTest
│   ├── GuestFileRepositoryTest
│   ├── ReservationFileReposotoryDouble
│   ├── HostFileRepositoryDouble
│   └── GuestFileRepositoryDouble
└── domain/
├── ReservationServiceTest
├── HostServiceTest
└── GuestServiceTest
```