package controllers.helpers;

import com.fasterxml.jackson.databind.JsonNode;
import play.libs.Json;
import play.mvc.Http;

public interface JsonParsable {

    default <T> T parse(Http.Request request, Class<T> requestType) {
        JsonNode jsonNode = request.body().asJson();
        if (jsonNode == null) {
            throw new IllegalArgumentException("JSON body expected");
        }

        return Json.fromJson(jsonNode, requestType);
    }
}
