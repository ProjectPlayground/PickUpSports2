package pickupsports2.ridgewell.pickupsports2.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.annotation.SuppressLint;
import android.app.AlertDialog;

import pickupsports2.ridgewell.pickupsports2.R;
import pickupsports2.ridgewell.pickupsports2.intents.IntentProtocol;

public class LocationPickerActivity extends ActionBarActivity {

	private WebView locationPickerView;
	private EditText searchText;
	private Button searchButton;

	private Float latitude = 0f;
	private Float longitude = 0f;
	private Integer zoom = 5;
	private String locationName;

	@Override
	@SuppressLint("SetJavaScriptEnabled")
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_location_picker);

		if (savedInstanceState!=null) {
			latitude = savedInstanceState.getFloat("latitude");
			longitude = savedInstanceState.getFloat("longitude");
			zoom = savedInstanceState.getInt("zoom");
			locationName = savedInstanceState.getString("locationName");
		}

        final Bundle extras = getIntent().getExtras();
        String initial_location = extras.getString("initial_location");

		// LOCATION PICKER WEBVIEW SETUP
		locationPickerView = (WebView) findViewById(R.id.locationPickerView);
		locationPickerView.setScrollContainer(false);
		locationPickerView.getSettings().setDomStorageEnabled(true);
		locationPickerView.getSettings().setJavaScriptEnabled(true);
		locationPickerView.addJavascriptInterface(new LocationPickerJSInterface(), "AndroidFunction");
		
		locationPickerView.loadUrl("file:///android_asset/locationPickerPage/index.html");
        locationPickerView.loadUrl("javascript:activityInitialize(" + latitude + "," + longitude + "," + zoom + ")");

		locationPickerView.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onProgressChanged(WebView view, int progress) {
				if (progress == 100) {
					locationPickerView.loadUrl("javascript:activityInitialize(" + latitude + "," + longitude + "," + zoom + ")");
				    Log.v("location", "lat = " + latitude + ", long = " + longitude);
                }
			}
		});
		// ^^^
		
		// EVENT HANDLER FOR PERFORMING SEARCH IN WEBVIEW
		searchText = (EditText) findViewById(R.id.searchText);
		searchButton = (Button) findViewById(R.id.searchButton);
		searchButton.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View view) {
				locationPickerView.loadUrl("javascript:if (typeof activityPerformSearch == 'function') {activityPerformSearch(\"" + searchText.getText().toString() + "\")}");
			}
		});
		// ^^^

		// EVENT HANDLER FOR ZOOM IN WEBVIEW
		Button zoomIncreaseButton = (Button) findViewById(R.id.zoomIncreaseButton);
		zoomIncreaseButton.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View view) {
				locationPickerView.loadUrl("javascript:activityPerformZoom(1)");
			}
		});
		
		Button zoomDecreaseButton = (Button) findViewById(R.id.zoomDecreaseButton);
		zoomDecreaseButton.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View view) {
				locationPickerView.loadUrl("javascript:activityPerformZoom(-1)");
			}
		});
		// ^^^

		// EVENT HANDLER FOR SAMPLE QUERY BUTTON
		Button finishButton = (Button) findViewById(R.id.finishButton);
		finishButton.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View arg0) {
                AlertDialog.Builder builder = new AlertDialog.Builder(LocationPickerActivity.this);
                builder
                        .setMessage("Set Event Location : " + locationName + "?")
                        .setTitle("Select Location")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                Log.v("setting location", "lat: " + latitude +", long: " + longitude);
                                Log.v("Zoom",zoom+"");
                                IntentProtocol.returnLocationPicker(LocationPickerActivity.this,
                                        CreateEventActivity.class, latitude, longitude, locationName);
                                finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {}
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
			}
		});
	}

	public class LocationPickerJSInterface {
		@JavascriptInterface 
		public void getValues(String latitude, String longitude, String zoom, String locationName){
			LocationPickerActivity.this.latitude = Float.parseFloat(latitude);
			LocationPickerActivity.this.longitude = Float.parseFloat(longitude);
			LocationPickerActivity.this.zoom = Integer.parseInt(zoom);
			LocationPickerActivity.this.locationName = locationName;
		}

		// to ease debugging
		public void showToast(String toast){
			Toast.makeText(LocationPickerActivity.this, toast, Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putFloat("latitude", latitude);
		outState.putFloat("longitude", longitude);
		outState.putInt("zoom", zoom);
		outState.putString("locationName", locationName);
	}
}
