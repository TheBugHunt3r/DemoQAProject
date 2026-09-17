package ui.dto;

import org.testng.annotations.DataProvider;

public class NavigationData {

    @DataProvider(name = "navigationData")
    public Object[][] navigationData() {
        return new Object[][]{
                {"/simpleLink", "https://demoqa.com/"},
                {"/dynamicLink", "https://demoqa.com/"}
        };
    }

    @DataProvider(name = "httpStatusResponses")
    public Object[][] httpStatusResponses() {
        return new Object[][]{
                createResponse("Created", 201),
                createResponse("No Content", 204),
                createResponse("Moved", 301),
                createResponse("Bad Request", 400),
                createResponse("Unauthorized", 401),
                createResponse("Forbidden", 403),
                createResponse("Not Found", 404)
        };
    }

    private Object[] createResponse(String statusText, int statusCode) {
        String message = String.format("Link has responded with status %d and status text %s",
                statusCode, statusText);
        return new Object[]{statusText, message};
    }
}
