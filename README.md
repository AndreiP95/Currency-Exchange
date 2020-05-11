# Currency-Exchange
An app to view Fx Data against your preffered currency


Currency-Exchange is an app that provides currency data. It is splitted into three screens, a dashboard, a history screen with graphs and a settings screen.


# Dashboard Screen
  -> Dashboard screen contains a list with all the currencies against a base currency which is default set as EUR. It is refreshed every 3,5 or 15 seconds depending on the users preference with the default as 3. Also, it has a pull to refresh system if the user wants to refresh faster or if he/she does not have internet and it takes time until the app is re-updated.
  -> It also has two entries to go to the other screens on the Application top bar, a clock for the History data and a Settings icon for the Settings 
  
  
 # History Screen
   History screen contains three graphs with BGN, RON and USD currencies against EUR, taken for the last 10 days. It shows only the data that was changed during the last 10 days, omitting the days when the market was closed or the weekends.
    
 # Settings Screen
 Settings screen consists in two dropdown menus that can select a refresh time and a base currency. It also has a button to save the data and show it into the dashboard. 
