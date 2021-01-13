#----------------------------------
# Empty Cucumber .feature file
#----------------------------------
    

#    


Feature: Loginin
    As a HR(Manager), Ican login in the OfficeSystem.
    Scenario Outline: Simple loginin
            Given a HR Enter a Account and Enter the password in the system.
            When the employee account "<account>" is entered
            And the password "<password>" is typing
            Then the system return "<result>"

            Examples: Loginin
            |Account   |password   |result                             |
            |D00198309 |admin      |jump to blankpage                  |
            |D00190032 |1111       |display error And need to re-enter |
