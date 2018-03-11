# StartingPlayersByPosition
Web API to retrieve starting baseball players split by position in an Excel Worksheet

The players are ranked in the Worksheet based on the Ranking Data obtained from [Fantasy Baseball Rankings](https://fantasybaseballnerd.com). The Depth Chart data is from [Sports Radar API](https://developer.sportradar.com)

Uses: Spring Boot

- Client initiates GET request to PlayersByPosition
- Calls External APIs to retrieve player information:
  One API has League Depth Chart Info, One Player Ranking Info
- Transforms responses from JSON/XML Payloads
- Writes POJO's to staging tables for both services
- Creates actual Player table off of staging table data by using a Trie data structure to search in the following hierarchy:
   TEAM -> LAST_NAME -> FIRST_NAME (IF NO MATCH ON FIRST_NAME, 
                                    THEN JERSEY_NUMBER
                                    IF NO MATCH ON JERSEY_NUMBER
                                    THEN POSITION)
   The first/last names are converted and stored in an ASCII format for the backend matching and displayed
   in the front end format to the user (as the Depth Chart service sent the names) in the worksheet.           
- Queries database and splits data by position
- Writes data to excel worksheet
- Sends response back to client with worksheet, that the browser will download
