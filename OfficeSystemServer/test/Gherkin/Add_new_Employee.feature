#----------------------------------
# Empty Cucumber .feature file
#----------------------------------
    
Feature:   registered
    As a HR (Manager), I can add a new employee.
    Scenario Outline: Simple registered
        Given a HR put a new employee in the system
        When the employee account "<Account>"  is entered
        And put the emplpoyee "<EmployeeName>"
        And the password "<Password>"  is typing
        And confirm password "<Repassword>"
        When choose development "<Development>"
        And Select "<Position>" if there is a corresponding "<Postion>" option under department
        And  choose the "<Status>"
        Then Select the entry time, "<From>" what day "<To>" what day

        Examples: Loginin
        |Account     |EmployeeName     |Password     |Repassword     |DepartMent     |Position    |Status    |From      |To         |result                   |
        |123         |Bo               |admin        |admin          |Managerment    |HR          |1         |2020/3/13 |2020/3/20  |Current interface        |
        |123         |Bo               |admin        |admin          |Department     |null        |1         |2020/3/13 |2020/3/20  |Jump to the errorPage    |
        |123         |Bo               |admin        |adm            |Managerment    |HR          |1         |2020/3/13 |2020/3/20  |Jump to the errorPage    |
        |123         |Bo               |admin        |admin          |Managerment    |HR          |1         |2020/3/13 |2020/3/20  |Jump to the errorPage    |
   
