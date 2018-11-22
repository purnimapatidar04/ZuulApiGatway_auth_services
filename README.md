# ZuulApiGatway_auth_services

For running api gateway with spring security follow below steps-
1.First run student emp and auth servic these are indivisual services
2.Run api-gateway on server and mapped all service with their port in application.properties file
3.On apigatway i enabled security and mapped emp and student service with some user and role
for accessing any api first call login service to genrate token for specific user
 i.e.- 
    localhost:8080/Zuul-api-gat/login
     Content-Type: application/json
     
body-
     {"username":"admin","password":"admin"}
     
after this you will get token in header part 
use this token to call all other services via gateway
i.e 
   GET- localhost:8080/Zuul-api-gat/student/echoStudentName/purnima 
   Header part-
  Content-Type: application/json
   Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGhvcml0aWVzIjpbIlJPTEVfQURNSU4iLCJST0xFX1VTRVIiXSwiaWF0IjoxNTQyNzk0Nzc3LCJleHAiOjE1NDI4ODExNzd9.6f_b3TXBvOY0BVzaz1X_-ysVzOWoKU5nsOD1CGczFR0

