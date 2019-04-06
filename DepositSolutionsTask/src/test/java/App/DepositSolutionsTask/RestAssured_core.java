package App.DepositSolutionsTask;

import io.restassured.RestAssured;

public class RestAssured_core {
	private static String baseURIVar = "http://85.93.17.135:9000";
	private static String basePathVar = "/";
    
	// Sets the baseURI and basePath for the RestAssured tests
    public RestAssured_core() {
    	RestAssured.baseURI = baseURIVar;
    	RestAssured.basePath = basePathVar;
    }
    
    // Get the base URI
    public String getBaseUri(){
    	return baseURIVar;
    }
}