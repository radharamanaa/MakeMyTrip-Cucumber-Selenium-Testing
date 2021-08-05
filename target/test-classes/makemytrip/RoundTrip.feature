Feature: Round Trip on MakeMy Trip
  Background:
    Given User is on MakeMyTrip landing page
    And Round Trip option is selected

  Scenario: User verifies delhi and bangalore as default FROM and TO cities for first time indian user
    Given User is on landing page
    When user is from India
    Then user verifies FROM city to be "Delhi"
    And TO city to be "Bangalore"

  Scenario: User verifies previously selected FROM and TO cities when he visits again
    Given User visits landing page
    When user selects FROM and TO city
    And user selects DEPARTURE and RETURN dates
    And navigates to any other webpage
    And navigates back to makemytrip landing page again
    Then user verifies previously selected FROM and TO cities
    And user verifies previously selected DEPARTURE and RETURN dates

  Scenario Outline:  Verify workflow of a round trip flight
    Given User visits landing page
    When user enters <fromCity> in FROM field
    And selects <suggestedFromCity> from dropdown options
    And user enters <toCity> in TO field
    And selects <suggestedToCity> from dropdown options
    And user selects tomorrows date as DEPARTURE date
    And selects day after tomorrows date as RETURN date
    Then user verifies <suggestedFromCity> in FROM field
    And user verifies <suggestedToCity> in TO field
    And user verifies tomorrows date in DEPARTURE date
    And user verifies day after tomorrows date in RETURN field

    Examples:
      | fromCity | suggestedFromCity | toCity | suggestedToCity |
      | Goa      | Goa               | Miami  | Miami           |

#    Scenario: User cannot select RETURN date which is before DEPARTURE date
#      Given User visits landing page
#      When user selects a DEPARTURE date
#      Then user cannot select a RETURN date prior to DEPARTURE date
