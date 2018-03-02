# PlayersByPosition
Web API to retrieve starting baseball players split by position in an Excel Worksheet

Uses: Spring Boot

-client initiates GET request to PlayersByPosition
-Calls External APIs to retrieve player information
-Transforms responses from JSON/XML Payloads
-Writes POJO's to embedded H2 database
-Queries H2 database and splits data by position
-Writes data to excel worksheet
-Sends response back to client with worksheet, that the browser will download
