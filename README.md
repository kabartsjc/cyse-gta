# cyse-gta
The system aims to manage the GTA process, including the feedback evaluation.

#cyse-api

change application.yaml (insert the correct ip)
  
server:
    address:10.151.206.174

  cors:
    allowed-origins: http://localhost, http://10.151.206.174


#cyse-ui

change GtaApi.js

replace the address of the request
    const netaddr ="http://10.151.206.174:8080"

