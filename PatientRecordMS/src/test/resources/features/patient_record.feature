Feature: Patient Record management
  As a user
  I want to manage patient records
  So that I can retrieve and update patient records in the system

  Scenario: Retrieve a patient record
    Given a patient with id "john123"
    When I retrieve the patient record with id "john123"
    Then I should get the patient record of "john123"

  Scenario: Update a patient record
    Given a patient with id "john123"
    When I update the patient record with id "john123"
    Then the patient record with id "john123" should be updated