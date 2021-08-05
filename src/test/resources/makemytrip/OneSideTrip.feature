Feature: One Side Make my trip
  User books a one side flight

  Background:
    Given User is on MakeMyTrip landing page
    And "ONEWAY" option is selected

 Scenario Outline: User searches for a city which has flight destination and verifies it
   Given "ONEWAY" option is selected
   When user clicks on from section
   And user enters <city>
   Then user finds <city> in the Select list
   And verifies DEPARTURE date to be the next day
   And DEPARTURE also shows the day of departure

   Examples:
     | city     |
     | Chennai  |
     | Kochi    |

  Scenario Outline: From field updates with valid details when user selects a city
     Given "ONEWAY" option is selected
     When User enters <sourceCity>
     Then User verifies <sourceCity> Airport appearance in dropdown list
     When User clicks on TO destination field and enters <destinationCity>
     Then User verifies <destinationCity> to be present in the drop down
     When user clicks on Departure field
     Then user verifies departure date
     When user selects a return date
     Then user verifies the date and day shows, is as user selected
     Examples:
       | sourceCity | destinationCity|
       | Chennai    | New york       |
       | Kochi      | Chicago       |

  Scenario: user types gibberish in From field user gets
     suggestions according to the first letter of the gibberish text
     Given "ONEWAY" option is selected
     When User types "asdfasdlasdn" text in From field
     Then user verifies selection list to have airports names starting with  "A"

  Scenario Outline: User selects number of travellers and verifies the same
    Given "ONEWAY" option is selected
    When user clicks on Travellers and class field
    And User selects 2 adults
    And selects 1 child(2y-12y)
    And selects 1 infant below 2 yrs
    And selects <class> class and submits
    Then user verifies 4 travellers in the Travellers
    And verifies class to be <class>

    Examples:
    | class |
    | Economy |
    | Premium Economy |
    | Business |
    | First Class |

    Examples:
    | userReqLoc |actualReqLoc | airportCode | airportName |
    | Goa        | Goa         | GOI         | Dabolim Goa International Airport India |
