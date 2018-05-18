Usefull library to handle command line arguments. CLI args to JSON object. 

example:

>java -jar project.jar --home.numberOfRooms=3 --home.numberOfBathRooms=2

JSONObject jObject = ArgsToJson.getJsonObject(args);
System.Out.println(jObject.toString());

>{"home":{"numberOfRooms":3, "numberOfBathRooms":2}}
