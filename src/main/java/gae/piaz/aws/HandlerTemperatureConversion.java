package gae.piaz.aws;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPResponse;
import io.lumigo.handlers.LumigoConfiguration;
import io.lumigo.handlers.LumigoRequestExecutor;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

// Handler value: gae.piaz.aws.HandlerTemperatureConversion
public class HandlerTemperatureConversion implements RequestHandler<APIGatewayV2HTTPEvent, APIGatewayV2HTTPResponse> {

    static {
        LumigoConfiguration.builder().build().init();
    }

    /**
     * Returns the temperature converted from Fahrenheit to Celsius.
     * <p>
     * Example URL to Call: https://<UUID>.lambda-url.<Region>.on.aws/?temperature=10
     */
    @Override
    public APIGatewayV2HTTPResponse handleRequest(APIGatewayV2HTTPEvent event, Context context) {

        Supplier<APIGatewayV2HTTPResponse> supplier = () -> {

            LambdaLogger logger = context.getLogger();

            logger.log("EVENT TYPE: " + event.getClass().toString());

            Map<String, String> param = event.getQueryStringParameters();
            logger.log("QueryParams:" + param.toString());

            String temp = param.get("temperature");
            logger.log("Converting Fahrenheit: " + temp);

            double fahrenheit = Double.parseDouble(temp);
            double celsius = fahrenheit - 32;
            celsius = (int) (celsius / 1.8);

            APIGatewayV2HTTPResponse response = new APIGatewayV2HTTPResponse();
            response.setIsBase64Encoded(false);
            response.setStatusCode(200);
            Map<String, String> headers = new HashMap<>();
            headers.put("Content-Type", "application/json");
            response.setHeaders(headers);
            response.setBody("{ temperature: " + celsius + "}");
            return response;

        };

        return LumigoRequestExecutor.execute(event, context, supplier);
    }

}