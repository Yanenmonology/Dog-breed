package dogapi;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.*;

/**
 * BreedFetcher implementation that relies on the dog.ceo API.
 * Note that all failures get reported as BreedNotFoundException
 * exceptions to align with the requirements of the BreedFetcher interface.
 */

public class DogApiBreedFetcher implements BreedFetcher {
    private static final OkHttpClient client = new OkHttpClient();
    private static final String API_URL = "https://dog.ceo/api/breed";
    private static final String SUCCESS_CODE = "success";

    /**
     * Fetch the list of sub breeds for the given breed from the dog.ceo API.
     * @param breed the breed to fetch sub breeds for
     * @return list of sub breeds for the given breed
     * @throws BreedNotFoundException if the breed does not exist (or if the API call fails for any reason)
     */
    @Override
    public List<String> getSubBreeds(String breed) {
        // TODO Task 1: Complete this method based on its provided documentation
        //      and the documentation for the dog.ceo API. You may find it helpful
        //      to refer to the examples of using OkHttpClient from the last lab,
        //      as well as the code for parsing JSON responses.

        final Request request = new Request.Builder()
                .url(String.format("%s/%s/list", API_URL,breed))
                .method("GET", null)
                .build();

        ArrayList<String> SubBreedList = new ArrayList<>();
        try{
            final Response response = client.newCall(request).execute();
            final JSONObject responseBody = new JSONObject(response.body().string());
        //    if(responseBody.getInt("code") == 404){
        //        throw BreedNotFoundException();
        //    }
            if (responseBody.getString("status").equals(SUCCESS_CODE)) {
                final JSONArray subBreeds = responseBody.getJSONArray("message");
                System.out.println(subBreeds.length());
                for (int i = 0; i < subBreeds.length(); i++) {
                    SubBreedList.add(subBreeds.getString(i));
                }

            }

        }
        catch (IOException | JSONException | BreedNotFoundException e){
            new RuntimeException(e);
        }
        return SubBreedList;
    }
}