package controllers.helpers;

import com.fasterxml.jackson.databind.JsonNode;
import play.libs.Json;
import play.mvc.Http;

/**
 * Trait-like interface for using with controllers.
 */
public interface JsonParsable {

    /**
     * Tries to parse the http request as JSON and deserialize it to object.
     *
     * @param request     http request
     * @param requestType desired type
     * @return object of needed type
     */
    default <T> T parse(Http.Request request, Class<T> requestType) {
        JsonNode jsonNode = request.body().asJson();
        if (jsonNode == null) {
            throw new IllegalArgumentException("JSON body expected");
        }

        return Json.fromJson(jsonNode, requestType);
    }
}
