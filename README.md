#CAPD Places Service

REST/JSON service which provides an endpoint for retrieving addresses based on a postcode lookup. This service acts as a wrapper around the OS Places API endpoint ( http://www.ordnancesurvey.co.uk/business-and-government/products/os-places/index.html ).

###Configuration

 - dummy: [true|false] -> Provides a dummy implementation for postcodes: SW113DR, SW114DR, SW115DR and SW116DR, RG17LB, RG17PT
 - url -> OS Places API 
 - key -> API key (this will need to be provided before the application can access OS places)
 - timeout -> timeout to access the OS Places ( milliseconds )

###Running the service

 1. In a terminal execute `./sbt clean compile` to compile the app
 2. In a terminal execute `./sbt clean test` to run the app’s unit tests
 3. In a terminal execute `./go` to start the app

