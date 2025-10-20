package dogapi;

import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
/**
 * This BreedFetcher caches fetch request results to improve performance and
 * lessen the load on the underlying data source. An implementation of BreedFetcher
 * must be provided. The number of calls to the underlying fetcher are recorded.
 *
 * If a call to getSubBreeds produces a BreedNotFoundException, then it is NOT cached
 * in this implementation. The provided tests check for this behaviour.
 *
 * The cache maps the name of a breed to its list of sub breed names.
 */
public class CachingBreedFetcher implements BreedFetcher {
    // TODO Task 2: Complete this class
    private int callsMade = 0;
    private BreedFetcher Worker;
    private Map<String,ArrayList<String>> Cache = new HashMap<>();
    public CachingBreedFetcher(BreedFetcher fetcher) {
        this.Worker = Objects.requireNonNull(fetcher);
    }

    @Override
    public List<String> getSubBreeds(String breed) {
        // return statement included so that the starter code can compile and run.
        if (Cache.containsKey(breed)){
            return new ArrayList<>(Cache.get(breed));
        }
        try{
            callsMade++;
            List<String> result = Worker.getSubBreeds(breed);
            Cache.put(breed, new ArrayList<>(result));
            return new ArrayList<>(result);
        } catch (BreedNotFoundException e) {
            throw e;
        }
    }

    public int getCallsMade() {
        return callsMade;
    }
}