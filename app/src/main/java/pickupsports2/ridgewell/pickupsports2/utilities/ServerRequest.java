package pickupsports2.ridgewell.pickupsports2.utilities;

import android.util.Log;

import java.security.InvalidParameterException;
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
import ridgewell.pickupsports2.common.LocationProperties;
import ridgewell.pickupsports2.common.Sport;
import ridgewell.pickupsports2.common.User;
import org.joda.time.DateTime;

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

    private static ExecutorService exec = Executors.newFixedThreadPool(1);

    private static ServerRequest instance = null;

    protected ServerRequest(){};

    public static ServerRequest getInstance() {
        if (instance == null) {
            return new ServerRequest();
        } else {
            return instance;
        }
    }

    public User getUser(final String id) {
        try {
            Callable<User> callable = new Callable<User>() {
                @Override
                public User call() {
                    return svc.getUser(id, "ps2");
                }
            };
            Log.v("In","GetUser");
            return exec.submit(callable).get();
        } catch (ExecutionException e) {
            Log.e("Interrupted Exception", e.getMessage());
            return null;
        } catch (InterruptedException e) {
            Log.e("Execution Exception", e.getMessage());
            return null;
        }
    }

    public User isExistingUser(final String id, final String id_type) {
        if (!id_type.equals("fb") && !id_type.equals("ps2")) {
            throw new InvalidParameterException("Parameter 'id_type' is not valid");
        }
        try {
            Callable<User> callable = new Callable<User>() {
                @Override
                public User call() {
                    return svc.getUser(id, id_type);
                }
            };
            return exec.submit(callable).get();
        } catch (ExecutionException e) {
            Log.e("Interrupted Exception", e.getMessage());
            return null;
        } catch (InterruptedException e) {
            Log.e("Execution Exception", e.getMessage());
            return null;
        }
    }

    public void attendEvent(final Event event, final User user) {
        Callable c = new Callable() {
            @Override
            public String call() {
                svc.attendEvent(event.get_id(), user.get_id(), new Callback<String>() {
                    @Override
                    public void success(String string, Response response) {
                        Log.v("Retrofit Success", "attendEvent response");
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.e("Retrofit Error", "attendEvent Failed");
                    }
                });
                return "";
            }
        };
        try {
            exec.submit(c).get();
        } catch (ExecutionException e) {
            Log.e("Interrupted Exception", e.getMessage());
        } catch (InterruptedException e) {
            Log.e("Execution Exception", e.getMessage());
        }
    }

    public void leaveEvent(final Event event, final User user) {
        Callable c = new Callable() {
            @Override
            public String call() {
                svc.leaveEvent(event.get_id(), user.get_id(), new Callback<String>() {
                    @Override
                    public void success(String string, Response response) {
                        Log.v("Retrofit Success", "leaveEvent response");
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.e("Retrofit Error", "leaveEvent Failed");
                        Log.e("Error",error.getMessage());
                    }
                });
                return "";
            }
        };
        try {
            exec.submit(c).get();
        } catch (ExecutionException e) {
            Log.e("Interrupted Exception", e.getMessage());
        } catch (InterruptedException e) {
            Log.e("Execution Exception", e.getMessage());
        }
    }

    public void editEvent(final Event event) {
        Callable c = new Callable() {
            @Override
            public String call() {
                Log.v("In","EditEvent");
                svc.editEvent(event, new Callback<Event>() {
                    @Override
                    public void success(Event event, Response response) {
                        Log.v("Retrofit Success", "Event response");
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.e("Retrofit Error", "editEvent Failed");
                    }
                });
                return "";
            }
        };
        try {
            exec.submit(c).get();
        } catch (ExecutionException e) {
            Log.e("Interrupted Exception", e.getMessage());
        } catch (InterruptedException e) {
            Log.e("Execution Exception", e.getMessage());
        }
    }

    public void addUser(final User user) {
        Callable c = new Callable() {
            @Override
            public String call() {
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
                return "";
            }
        };
        try {
            exec.submit(c).get();
        } catch (ExecutionException e) {
            Log.e("Interrupted Exception", e.getMessage());
        } catch (InterruptedException e) {
            Log.e("Execution Exception", e.getMessage());
        }
    }

    public void editUser(final User user) {
        Callable c = new Callable() {
            @Override
            public String call() {
                Log.v("In","EditUser");
                svc.editUser(user, new Callback<User>() {
                    @Override
                    public void success(User user, Response response) {
                        Log.v("Retrofit Success", "User response");
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.e("Retrofit Error", "editUser Failed");
                    }
                });
                return "";
            }
        };
        try {
            exec.submit(c).get();
        } catch (ExecutionException e) {
            Log.e("Interrupted Exception", e.getMessage());
        } catch (InterruptedException e) {
            Log.e("Execution Exception", e.getMessage());
        }
    }

    public void deleteUser(final User user) {
        Callable c = new Callable() {
            @Override
            public String call() {
                svc.deleteUser(user.get_id(), new Callback<User>() {
                    @Override
                    public void success(User user, Response response) {
                        Log.v("Retrofit Success", "User response");
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.e("Retrofit Error", "deleteUser Failed");
                    }
                });
                return "";
            }
        };
        try {
            exec.submit(c).get();
        } catch (ExecutionException e) {
            Log.e("Interrupted Exception", e.getMessage());
        } catch (InterruptedException e) {
            Log.e("Execution Exception", e.getMessage());
        }
    }

    public Event getEvent(final String id) {
        try {
            Callable<Event> callable = new Callable<Event>() {
                @Override
                public Event call() {
                    return svc.getEvent(id);
                }
            };
            return exec.submit(callable).get();
        } catch (ExecutionException e) {
            Log.e("Interrupted Exception", e.getMessage());
            return null;
        } catch (InterruptedException e) {
            Log.e("Execution Exception", e.getMessage());
            return null;
        }
    }

    public Event getEventFromName(final String event_name) {
        try {
            Callable<Event> callable = new Callable<Event>() {
                @Override
                public Event call() {
                    return svc.getEventFromName(event_name);
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

    public List<Event> getEventsInDateRange(final DateTime date1, final DateTime date2) {
        try {
            Callable<List<Event>> callable = new Callable<List<Event>>() {
                @Override
                public List<Event> call() throws Exception {
                    return svc.getEventsInDateRange(date1.getMillis(), date2.getMillis());
                }
            };
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
        Callable c = new Callable() {
            @Override
            public String call() {
                svc.addEvent(event, new Callback<Event>() {
                    @Override
                    public void success(Event event, Response response) {
                        Log.v("Retrofit Success", "Event response");
                        Log.v("Response",response.toString());
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.e("addEvent Failed", error.getMessage());
                    }
                });
                return "";
            }
        };
        try {
            exec.submit(c).get();
        } catch (ExecutionException e) {
            Log.e("Interrupted Exception", e.getMessage());
        } catch (InterruptedException e) {
            Log.e("Execution Exception", e.getMessage());
        }
    }

    public void deleteEvent(final Event event) {
        Callable c = new Callable() {
            @Override
            public String call() {
                svc.deleteEvent(event.get_id(), new Callback<Event>() {
                    @Override
                    public void success(Event event, Response response) {
                        Log.v("Retrofit Success", "Event response");
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.e("Retrofit Error", "deleteEvent Failed");
                    }
                });
                return "";
            }
        };
        try {
            exec.submit(c).get();
        } catch (ExecutionException e) {
            Log.e("Interrupted Exception", e.getMessage());
        } catch (InterruptedException e) {
            Log.e("Execution Exception", e.getMessage());
        }
    }

    public Sport getSport(final String sport) {
        try {
            Callable<Sport> callable = new Callable<Sport>() {
                @Override
                public Sport call() {
                    return svc.getSport(sport);
                }
            };
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
        Callable c = new Callable() {
            @Override
            public String call() {
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
                return "";
            }
        };
        try {
            exec.submit(c).get();
        } catch (ExecutionException e) {
            Log.e("Interrupted Exception", e.getMessage());
        } catch (InterruptedException e) {
            Log.e("Execution Exception", e.getMessage());
        }
    }

    public void deleteSport(final Sport sport) {
        Callable c = new Callable() {
            @Override
            public String call() {
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
                return "";
            }
        };
        try {
            exec.submit(c).get();
        } catch (ExecutionException e) {
            Log.e("Interrupted Exception", e.getMessage());
        } catch (InterruptedException e) {
            Log.e("Execution Exception", e.getMessage());
        }
    }

    public LocationProperties getLocation(final String location) {
        try {
            Callable<LocationProperties> callable = new Callable<LocationProperties>() {
                @Override
                public LocationProperties call() {
                    return svc.getLocation(location);
                }
            };
            return exec.submit(callable).get();
        } catch (ExecutionException e) {
            Log.e("Interrupted Exception", e.getMessage());
            return null;
        } catch (InterruptedException e) {
            Log.e("Execution Exception", e.getMessage());
            return null;
        }
    }

    public void addLocation(final LocationProperties locationProperties) {
        Callable c = new Callable() {
            @Override
            public String call() {
                svc.addLocation(locationProperties, new Callback<LocationProperties>() {
                    @Override
                    public void success(LocationProperties location, Response response) {
                        Log.v("Retrofit Success", "Location response");
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.e("addLocation Failed", error.getMessage());
                    }
                });
                return "";
            }
        };
        try {
            exec.submit(c).get();
        } catch (ExecutionException e) {
            Log.e("Interrupted Exception", e.getMessage());
        } catch (InterruptedException e) {
            Log.e("Execution Exception", e.getMessage());
        }
    }

    public void deleteLocation(final LocationProperties locationProperties) {
        Callable c = new Callable() {
            @Override
            public String call() {
                svc.deleteLocation(locationProperties.toString(), new Callback<LocationProperties>() {
                    @Override
                    public void success(LocationProperties location, Response response) {
                        Log.v("Retrofit Success", "Location response");
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.e("Retrofit Error", "deleteLocation Failed");
                    }
                });
                return "";
            }
        };
        try {
            exec.submit(c).get();
        } catch (ExecutionException e) {
            Log.e("Interrupted Exception", e.getMessage());
        } catch (InterruptedException e) {
            Log.e("Execution Exception", e.getMessage());
        }
    }
}
