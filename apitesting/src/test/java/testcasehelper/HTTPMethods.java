package testcasehelper;

import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

/**
 * This utility has HTTP methods can be utilized to hit request
 */
public class HTTPMethods {
	private static final Logger logger = LogManager.getLogger(HTTPMethods.class);

	/**
	 * enum to hold response of request RESPONSE_CODE - HTTP Status code,
	 * RESPONSE_BODY - Response body, RESPONSE_MESSAGE - Response message e.g. "OK",
	 * "Not found" , RESPONSE_OBJECT - Whole {@link Response} object returned by
	 * request
	 */
	public enum RESPONSE {
		RESPONSE_CODE, RESPONSE_BODY, RESPONSE_MESSAGE, RESPONSE_OBJECT
	}

	/**
	 * HTTP POST method
	 * 
	 * @param strRequestBody - String : Request body
	 * @param strURL         - String : Request URL
	 * @param header         - Map<String, String> e.g.
	 *                       {Content-Type=application/json,
	 *                       Accept=application/json} If you do not wish to pass
	 *                       header then pass it as null
	 * @return {@code} HashMap<{@link RESPONSE} , Object>>}
	 */
	public HashMap<RESPONSE, Object> post(String strRequestBody, String strURL, Map<String, String> header) {
		logger.info("Request body is {}", strRequestBody);
		RequestSpecification requestSpecification = RestAssured.given();
		requestSpecification.body(strRequestBody);
		if (header != null)
			requestSpecification.headers(header);

		Response response = requestSpecification.post(strURL);
		return getResponseAttributes(response);
	}

	/**
	 * HTTP GET method
	 * 
	 * @param strURL - String : Request URL
	 * @param header - Map<String, String> e.g. {Content-Type=application/json,
	 *               Accept=application/json} If you do not wish to pass header then
	 *               pass it as null
	 * @return {@code} HashMap<{@link RESPONSE}, Object>>}
	 */
	public HashMap<RESPONSE, Object> get(String strURL, Map<String, String> header) {
		RequestSpecification requestSpecification = RestAssured.given();
		if (header != null)
			requestSpecification.headers(header);
		Response response = requestSpecification.get(strURL);
		return getResponseAttributes(response);
	}

	/**
	 * Parse the response object and store it {@code} HashMap<{@link RESPONSE} ,
	 * Object> }
	 * 
	 * @param response - {@code Response}
	 * @return {@code HashMap}<{@link RESPONSE}, Object >
	 */
	private HashMap<RESPONSE, Object> getResponseAttributes(Response response) {
		HashMap<RESPONSE, Object> map = new HashMap<RESPONSE, Object>();
		int intResponseCode = response.getStatusCode();
		String strResponseBody = response.getBody().asPrettyString();
		String strResponseStatusLine = response.getStatusLine();
		logger.info("Response code is {}. Response status line is {}. Response body is {}", intResponseCode,
				 strResponseStatusLine, strResponseBody);
		map.put(RESPONSE.RESPONSE_OBJECT, response);
		map.put(RESPONSE.RESPONSE_BODY, strResponseBody);
		map.put(RESPONSE.RESPONSE_CODE, intResponseCode);
		map.put(RESPONSE.RESPONSE_MESSAGE, strResponseStatusLine);
		logger.info("Map having response attributes is {}", map);
		return map;
	}

}
