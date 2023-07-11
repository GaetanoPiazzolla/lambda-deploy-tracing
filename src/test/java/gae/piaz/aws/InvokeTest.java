package gae.piaz.aws;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPResponse;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;

import com.amazonaws.services.lambda.runtime.Context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

class InvokeTest {
    private static final Logger logger = LoggerFactory.getLogger(InvokeTest.class);

    @Test
    void testHandlerTemperatureConversion() {
        logger.info("Invoke TEST - HandlerTemperatureConversion");
        Context context = new TestContext();
        HandlerTemperatureConversion handler = new HandlerTemperatureConversion();

        APIGatewayV2HTTPEvent event = new APIGatewayV2HTTPEvent();
        event.setQueryStringParameters(new HashMap<>());
        event.getQueryStringParameters().put("temperature", "320");
        APIGatewayV2HTTPResponse outputData = handler.handleRequest(event, context);

        assertEquals(outputData.getStatusCode(), 200);
        Gson gson = new Gson();
        WhetherData data = gson.fromJson(outputData.getBody(), WhetherData.class);

        assertEquals(160, data.temperature);
    }

    public static class WhetherData {

        public WhetherData() {
            super();
        }

        public Double temperature;

    }

}
