package Api;

import Api.Model.Card;
import Api.Model.Data;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.round;


public class ApiRetriever {

    /**
     * getHttpFromApi takes user's parameters and makes an API call, checks if it's 200(OK) and returns the response.
     *
     * @param param parameters supplied by the user through the discord bot.
     * @return API response
     * @throws URISyntaxException
     * @throws IOException
     * @throws InterruptedException
     */
    public HttpResponse<String> getHttpFromApi(String param) throws URISyntaxException, IOException, InterruptedException {
        String baseUrl = "https://db.ygoprodeck.com/api/v7/cardinfo.php";
        HttpRequest getRequest = HttpRequest.newBuilder()
                .uri(new URI(baseUrl + param))
                .GET()
                .build();

        HttpClient httpClient = HttpClient.newHttpClient();
        //timer, cause, why the fk not
        long startTime = System.nanoTime();
        HttpResponse<String> getResponse = httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());
        long elapsedTime = System.nanoTime() - startTime;
        System.out.println("Total elapsed http request/response time: " + round(elapsedTime / 1000000.0) + "ms");
        //Basic check if status code 200 (OK) was received, if not, exit.
        int statusCode = getResponse.statusCode();
        if (statusCode != 200) {
            System.out.println("An error has occurred, please read the error code below to figure out what happened. Program stopped");
            System.out.println(getResponse.body());
            return getResponse;
        }
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

    public void getCards(String param) throws InterruptedException, IOException, URISyntaxException {
        HttpResponse<String> response = getHttpFromApi(param);
        List<Card> cards = convertJsonToCard(response);
        print(cards);
    }

    /**
     * Prints some information about the cards internally.
     *
     * @param cards ArrayList of Cards.
     */
    public void print(List<Card> cards) {
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

        // "Api.Bot" gets params as input (String), calls Api.ApiRetriever and completes baseUrl into a response, handles
        //response and gives json format back, Api.Bot uses different class to turn json into a Discord JDA embed.

        //need to make sure there are enough possible params to be used (check list of possible params)
        // TODO: add all possible endpoints from the API site to the discord bot.

        String param = "?" + "atk=" + "2500" + "&" + "def=" + "2500";
        String param2 = "?" + "fname=A-assault%20core";
        String param3 = "?" + "fname=odd-eyes";
        String param4 = "?" + "fname=judgement";
        String param5 = "?" + "atk=3000&def=900&attribute=dark";

        //use baseUrl + whatever the is looking for DONE
        //String response = fetch(baseUrl+param); DONE
        //get data from response DONE
        //data to objects DONE
        //list of objects DONE
        //TODO: spaces in requested name to %20
        //handle data   DONE

        //TODO: put data back to user through discord bot
        //TODO: autocomplete, if user is done typing for 0.5 seconds, send api call to update autocomplete results

        ApiRetriever instance = new ApiRetriever();  // create instance of the class
        instance.getCards(param5);


    }
}

