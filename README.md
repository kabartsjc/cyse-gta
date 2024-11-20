# cyse-gta
The system aims to manage the GTA process, including the feedback evaluation.

#cyse-api

change application.yaml (insert the correct ip)

edu.gmu.cyse.gta.rest.ApplicationController.java
	public static String BASE_DIR = "/home/kabart/gta_files";


  
server:
    address:10.151.206.174

  cors:
    allowed-origins: http://localhost, http://10.151.206.174


#cyse-ui

change the package.json
      "start": "HOST=192.168.1.165 PORT=80 react-scripts start",


change GtaApi.js

  replace the address of the request
    const netaddr ="http://10.151.206.174:8080"

