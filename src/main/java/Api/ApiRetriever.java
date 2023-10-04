package Api;

import Api.Model.Card;
import Api.Model.Data;
import com.google.gson.Gson;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static java.lang.Math.round;


public class ApiRetriever {

    /**
     * Sends an HTTP GET request to a specified API endpoint with the given query parameters.
     *
     * @param param The query parameters to be appended to the API endpoint.
     * @return An HttpResponse containing the response from the API.
     * @throws URISyntaxException If there is an issue with the URI construction.
     * @throws IOException      If an I/O error occurs during the HTTP request.
     * @throws InterruptedException If the HTTP request is interrupted.
     */
    public HttpResponse<String> getHttpFromApi(String param) throws URISyntaxException, IOException, InterruptedException {
        // Define the base URL of the API endpoint
        String baseUrl = "https://db.ygoprodeck.com/api/v7/cardinfo.php?";

        // Build an HTTP GET request with the specified URI
        HttpRequest getRequest = HttpRequest.newBuilder()
                .uri(new URI(baseUrl + param))
                .GET()
                .build();

        // Create an HTTP client
        HttpClient httpClient = HttpClient.newHttpClient();


        //TODO: remove unnecessary printing the response time
        
        // Measure the time taken for the HTTP request and response
        long startTime = System.nanoTime();
        HttpResponse<String> getResponse = httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());
        long elapsedTime = System.nanoTime() - startTime;

        // Print the total elapsed time for the HTTP request/response
        System.out.println("Total elapsed http request/response time: " + round(elapsedTime / 1000000.0) + "ms");

        // Check the HTTP response status code
        int statusCode = getResponse.statusCode();
        if (statusCode != 200) {
            // Handle the case where an error occurred and print the error code and response body
            System.out.println("An error has occurred, please read the error code below to figure out what happened. \n"+
            getResponse.body());
            return getResponse;
        }

        // Return the HTTP response
        return getResponse;
    }

    /**
     * Converts API response into an Arraylist of Cards using Gson.
     *
     * @param response takes API response from getHttpFromApi().
     * @return ArrayList of Cards.
     */
    public ArrayList<Card> convertJsonToCard(HttpResponse<String> response) {
        Gson gson = new Gson();
        Data data = gson.fromJson(response.body(), Data.class);
        return new ArrayList<Card>(data.getData());
    }

    /**
     * Retrieves a list of cards based on specified search criteria.
     *
     * @param name      The name of the card to search for (nullable).
     * @param type      The type of the card (nullable).
     * @param atk       The attack points of the card (nullable).
     * @param def       The defense points of the card (nullable).
     * @param level     The level of the card (nullable).
     * @param race      The race of the card (nullable).
     * @param attribute The attribute of the card (nullable).
     * @param link      The link value of the card (nullable).
     * @param linkMarker The link marker of the card (nullable).
     * @param scale     The scale value of the card (nullable).
     * @param cardSet   The card set information (nullable).
     * @param archetype The archetype of the card (nullable).
     * @throws InterruptedException   If the operation is interrupted.
     * @throws IOException            If an I/O error occurs.
     * @throws URISyntaxException     If a URI syntax error occurs.
     */
    public List<Card> getCards(
            @Nullable String name,
            @Nullable String type,
            @Nullable String atk,
            @Nullable String def,
            @Nullable String level,
            @Nullable String race,
            @Nullable String attribute,
            @Nullable Integer link,
            @Nullable String linkMarker,
            @Nullable Integer scale,
            @Nullable String cardSet,
            @Nullable String archetype
    ) throws InterruptedException, IOException, URISyntaxException {
        StringBuilder parameters = new StringBuilder("");

        appendQueryParam(parameters, "fname", name);
        appendQueryParam(parameters, "type", type);
        appendQueryParamWithComparison(parameters, "atk", atk);
        appendQueryParamWithComparison(parameters, "def", def);
        appendQueryParamWithComparison(parameters, "level", level);
        appendQueryParam(parameters, "race", race);
        appendQueryParam(parameters, "attribute", attribute);
        appendQueryParam(parameters, "link", Optional.ofNullable(link).map(Object::toString).orElse(null));
        appendQueryParam(parameters, "linkmarker", linkMarker);
        appendQueryParam(parameters, "scale", Optional.ofNullable(scale).map(Object::toString).orElse(null));
        appendQueryParam(parameters, "cardset", cardSet);
        appendQueryParam(parameters, "archetype", archetype);

        // Remove the trailing "&"
        if (parameters.length() > 1) {
            parameters.deleteCharAt(parameters.length() - 1);
        }
        HttpResponse<String> response = getHttpFromApi(parameters.toString());
        List<Card> cards = convertJsonToCard(response);
        return cards;

    }
    /**
     * Appends a query parameter to the StringBuilder if the parameter value is not null.
     *
     * @param parameters  The StringBuilder to which the parameter is appended.
     * @param paramName   The name of the parameter.
     * @param paramValue  The value of the parameter (nullable).
     * @throws UnsupportedEncodingException If URL encoding fails.
     */
    private void appendQueryParam(StringBuilder parameters, String paramName, String paramValue) throws UnsupportedEncodingException {
        if (paramValue != null) {
            parameters.append(paramName)
                    .append("=")
                    .append(URLEncoder.encode(paramValue, StandardCharsets.UTF_8))
                    .append("&");
        }
    }

    /**
     * Appends a query parameter with a comparison prefix to the StringBuilder if the parameter value is not null.
     *
     * @param parameters  The StringBuilder to which the parameter is appended.
     * @param paramName   The name of the parameter.
     * @param paramValue  The value of the parameter (nullable).
     * @throws UnsupportedEncodingException If URL encoding fails.
     */
    private void appendQueryParamWithComparison(StringBuilder parameters, String paramName, String paramValue) throws UnsupportedEncodingException {
        if (paramValue != null) {
            if (paramValue.startsWith(">")) {
                parameters.append(paramName)
                        .append("=")
                        .append("gt")
                        .append(URLEncoder.encode(paramValue.substring(1), StandardCharsets.UTF_8))
                        .append("&");
            } else if (paramValue.startsWith("<")) {
                parameters.append(paramName)
                        .append("=")
                        .append("lt")
                        .append(URLEncoder.encode(paramValue.substring(1), StandardCharsets.UTF_8))
                        .append("&");
            }else if (paramValue.startsWith(">=")) {
                    parameters.append(paramName)
                            .append("=")
                            .append("gte")
                            .append(URLEncoder.encode(paramValue.substring(1), StandardCharsets.UTF_8))
                            .append("&");
                } else if (paramValue.startsWith("<=")) {
                    parameters.append(paramName)
                            .append("=")
                            .append("lte")
                            .append(URLEncoder.encode(paramValue.substring(1), StandardCharsets.UTF_8))
                            .append("&");
            } else {
                appendQueryParam(parameters, paramName, paramValue);
            }
        }
    }




    /**
     * Prints some information about the cards internally.
     *
     * @param cards ArrayList of Cards.
     */
    public void print(List<Card> cards) {
        //TODO remove all this test code
        System.out.println("\n" + "Mulder card searcher" + "\n");
        if (cards.size() == 1) {
            System.out.println(cards.size() + " card has been found.");
        } else {
            System.out.println(cards.size() + " cards have been found.");
        }
        for (Card card : cards) {
            System.out.println(
                    card
            );
        }
    }

    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
        //TODO: remove all these test code
        String name = "dark magician";
        String type = null;
        String atk = ">2500";
        String def = null;
        String level = "7";
        String race = null;
        String attribute = "dark";
        Integer link = null;
        String linkMarker = null;
        Integer scale = null;
        String cardSet = null;
        String archetype = null;

        ApiRetriever instance = new ApiRetriever();  // create instance of the class
        instance.getCards(name,type,atk,def,level,race,attribute,link,linkMarker,scale,cardSet,archetype);


    }
}

