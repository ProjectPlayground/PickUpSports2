package pickupsports2.ridgewell.pickupsports2.utilities;

import android.util.Log;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import ridgewell.pickupsports2.common.Event;
import ridgewell.pickupsports2.common.User;

/**
 * Created by cameronridgewell on 2/10/15.
 */
public class ServerRequest {
    //http://ps2-wintra.rhcloud.com for server
    //http://192.168.56.1:8080 for genymotion
    //http://10.0.2.2:8080 for android emulator
    private final String ip_address = "http://ps2-wintra.rhcloud.com";
    private final RequestLibrary svc = new RestAdapter.Builder()
            .setEndpoint(ip_address).build()
            .create(RequestLibrary.class);

    public ServerRequest(){};

    public User getUser(final String username) {
        try {
            Callable<User> callable = new Callable<User>() {
                @Override
                public User call() {
                    return svc.getUser(username);
                }
            };
            ExecutorService exec = Executors.newFixedThreadPool(3);
            return exec.submit(callable).get();
        } catch (ExecutionException e) {
            Log.e("Interrupted Exception", e.getMessage());
            return null;
        } catch (InterruptedException e) {
            Log.e("Execution Exception", e.getMessage());
            return null;
        }
    }

    public void addUser(final User user) {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                svc.addUser(user, new Callback<User>() {
                    @Override
                    public void success(User user, Response response) {
                        Log.v("Retrofit Success", "User response");
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.e("Retrofit Error", "addUser Failed");
                    }
                });
            }
        };
        Thread t = new Thread(r);
        t.start();
    }

    public Event getEvent(final String event_name) {
        try {
            Callable<Event> callable = new Callable<Event>() {
                @Override
                public Event call() {
                    return svc.getEvent(event_name);
                }
            };
            ExecutorService exec = Executors.newFixedThreadPool(3 );
            return exec.submit(callable).get();
        } catch (ExecutionException e) {
            Log.e("Interrupted Exception", e.getMessage());
            return null;
        } catch (InterruptedException e) {
            Log.e("Execution Exception", e.getMessage());
            return null;
        }
    }

    public List<Event> getAllEvents() {
        try {
            Callable<List<Event>> callable = new Callable<List<Event>>() {
                @Override
                public List<Event> call() throws Exception {
                    return svc.getAllEvents();
                }
            };
            ExecutorService exec = Executors.newFixedThreadPool(3);
            return exec.submit(callable).get();
        } catch (ExecutionException e) {
            Log.e("Interrupted Exception", e.getMessage());
            return null;
        } catch (InterruptedException e) {
            Log.e("Execution Exception", e.getMessage());
            return null;
        }
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
                        Log.e("addEvent Failed", error.getMessage());
                    }
                });
            }
        };
        Thread t = new Thread(r);
        t.start();
    }

    public void deleteEvent(final Event event) {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                svc.deleteEvent(event.getName(), new Callback<Event>() {
                    @Override
                    public void success(Event event, Response response) {
                        Log.v("Retrofit Success", "Event response");
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.e("Retrofit Error", "deleteEvent Failed");
                    }
                });
            }
        };
        Thread t = new Thread(r);
        t.start();
    }

}
