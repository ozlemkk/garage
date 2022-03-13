
To park a vehicle in the garage, send a POST request to the endpoint `http://localhost:8080/garage/park`  
with a JSON request body like `{
"type": "Car",
"plate": "34-SO-1988",
"color": "Black"
}`

When leave a vehicle, send a DELETE request to the endpoint `http://localhost:8080/garage/leave`  
with a request param `ticketId`, which starts from 1.

To check the status of the garage, send a GET request to the endpoint `http://localhost:8080/garage/status`

You can import and use the postman collection file: `/garage.postman_collection.json`