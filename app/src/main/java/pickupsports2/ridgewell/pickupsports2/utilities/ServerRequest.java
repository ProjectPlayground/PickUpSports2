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
import ridgewell.pickupsports2.common.Location;
import ridgewell.pickupsports2.common.Sport;
import ridgewell.pickupsports2.common.User;

/**
 * Created by cameronridgewell on 2/10/15.
 */
public class ServerRequest {
    //http://ps2-wintra.rhcloud.com for server
    //http://192.168.56.1:8080 for genymotion NOTE: sometimes http://10.0.3.2:8080
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

    public void deleteUser(final User user) {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                svc.deleteUser(user.getUsername(), new Callback<User>() {
                    @Override
                    public void success(User user, Response response) {
                        Log.v("Retrofit Success", "User response");
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.e("Retrofit Error", "deleteUser Failed");
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

    public void addEvent(final Event event) {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                svc.addEvent(event, new Callback<Event>() {
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

    public Sport getSport(final String sport) {
        try {
            Callable<Sport> callable = new Callable<Sport>() {
                @Override
                public Sport call() {
                    return svc.getSport(sport);
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

    public void addSport(final Sport sport) {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                svc.addSport(sport, new Callback<Sport>() {
                    @Override
                    public void success(Sport event, Response response) {
                        Log.v("Retrofit Success", "Sport response");
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.e("addSport Failed", error.getMessage());
                    }
                });
            }
        };
        Thread t = new Thread(r);
        t.start();
    }

    public void deleteSport(final Sport sport) {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                svc.deleteSport(sport.getSportName(), new Callback<Sport>() {
                    @Override
                    public void success(Sport sport, Response response) {
                        Log.v("Retrofit Success", "Sport response");
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.e("Retrofit Error", "deleteSport Failed");
                    }
                });
            }
        };
        Thread t = new Thread(r);
        t.start();
    }

    public Location getLocation(final String location) {
        try {
            Callable<Location> callable = new Callable<Location>() {
                @Override
                public Location call() {
                    return svc.getLocation(location);
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

    public void addLocation(final Location location) {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                svc.addLocation(location, new Callback<Location>() {
                    @Override
                    public void success(Location location, Response response) {
                        Log.v("Retrofit Success", "Location response");
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.e("addLocation Failed", error.getMessage());
                    }
                });
            }
        };
        Thread t = new Thread(r);
        t.start();
    }

    public void deleteLocation(final Location location) {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                svc.deleteLocation(location.getLocation(), new Callback<Location>() {
                    @Override
                    public void success(Location location, Response response) {
                        Log.v("Retrofit Success", "Location response");
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.e("Retrofit Error", "deleteLocation Failed");
                    }
                });
            }
        };
        Thread t = new Thread(r);
        t.start();
    }
}
