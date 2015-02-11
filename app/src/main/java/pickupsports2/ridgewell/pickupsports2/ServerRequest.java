package pickupsports2.ridgewell.pickupsports2;

import android.util.Log;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import pickupsports2.ridgewell.pickupsports2.intents.IntentProtocol;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import ridgewell.pickupsports2.common.Event;

/**
 * Created by cameronridgewell on 2/10/15.
 */
public class ServerRequest {
    private final String ip_address = "http://10.0.2.2:8080";
    private final RequestLibrary svc = new RestAdapter.Builder()
            .setEndpoint(ip_address).build()
            .create(RequestLibrary.class);

    public ServerRequest(){};

    public Event getEvent(String event_name) throws ExecutionException, InterruptedException {
        final String e = event_name;
        Callable<Event> callable = new Callable<Event>() {
            @Override
            public Event call() {
                return svc.getEvent(e);
            }
        };
        ExecutorService exec = Executors.newFixedThreadPool(3 );
        return exec.submit(callable).get();
    }

    public List<Event> getAllEvents() throws ExecutionException, InterruptedException {
        Callable<List<Event>> callable = new Callable<List<Event>>() {
            @Override
            public List<Event> call() throws Exception {
                return svc.getAllEvents();
            }
        };
        ExecutorService exec = Executors.newFixedThreadPool(3);
        return exec.submit(callable).get();
    }

    public void addEvent(Event event) {
        final Event e = event;
        Runnable r = new Runnable() {
            @Override
            public void run() {
                svc.addEvent(e, new Callback<Event>() {
                    @Override
                    public void success(Event event, Response response) {
                        Log.v("Retrofit Success", "Event response");
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.e("Retrofit Error", "addEvent Failed");
                    }
                });
            }
        };
        Thread t = new Thread(r);
        t.start();
    }

}
