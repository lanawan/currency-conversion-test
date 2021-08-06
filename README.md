Test Assignment

Implement 2 separate applications (services) in java.

Service #1. 
Implements a REST-API with two methods:
Method 1. Converts currency to its ruble equivalent at today's exchange rate. The currency exchange rate should be obtained from the Central Bank website http://www.cbr.ru/scripts/XML_daily.asp
Method 2. Gets the list of available currency codes.

The service does not use the database; in case of an error it returns it in the response.


Service #2
Service #1 tests in asynchronous mode

Every second, it selects a random number from 1 to 1000, a random currency received from service 1 and sends it to service #1 for conversion.

Stops after receiving 10 errors in a row or after 1000 iterations.
All requests and responses to Service #1 must be logged.



The code must be posted to any public repository.

-------------------------------------
Considering that the service #2 is just a test of the first service I did this task in the same application.
The service #2 works
If you ever want to test the service #1 so the endpoint is :
http://localhost:8092/api/currency
For method 1 use GET with this endpoint
For method 2 use this endpoint with POST and send the following JSON parameters :
{
    "charCode": "USD",
    "amount": 28
}
You can choose any charCode from the data received by GET http://localhost:8092/api/currency

If you don't want the testing service, so you can disable it by putting @Scheduled annotation in comments and recompiling
