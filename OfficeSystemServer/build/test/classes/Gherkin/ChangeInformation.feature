#----------------------------------
# Empty Cucumber .feature file
#----------------------------------
    

   
Feature:   ChangeInformation
    As a HR (Manager), I can change the employee's information
        Scenario Outline: Simple change
            Given  a HR click the "<employee>" to change the information to employee.
            When the HR choose the "<Department>" 
            And HR can select the "<position>" if this department have the "<position>"
            And HR also can change the "<status>"
            Then If HR need to change the date They can the entry time, "<From>" what day "<To>" what day

            |employee  |Department  |position  |status  |From       |To           |result                 |
            |Bo        |Managerment |Manger    |2       |2020/03/12 |2020/03/17   |jump to the index page |
            |Bo        |Development |null      |2       |2020/3/12  |2020/03/17   |jump to the error page |
            |Bo        |Managerment |Manger    |1       |2020/03/12 |2020/03/17   |jump to the index page |
            |Bo        |Managerment |Manger    |3       |2020/03/12 |2020/03/17   |jump to the index page |
            |Bo        |Managerment |Manger    |4       |2020/03/12 |2020/03/17   |jump to the index page |
            