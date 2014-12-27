package com.asa.weatherapp;

import org.json.JSONException;
import com.asa.weatherapp.R;
import com.asa.weatherapp.model.Weather;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

public class SplashActivity extends Activity {

	String city;
	
	public SplashActivity() {
		city = "Kolkata,IN";
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		
		JSONWeatherTask task = new JSONWeatherTask();
		
		task.execute(new String[]{city});
	}
	
	private class JSONWeatherTask extends AsyncTask<String, Void, Weather> {
		
		@Override
		protected Weather doInBackground(String... params) {
			Weather weather = new Weather();
			String data = ( (new WeatherHttpClient()).getWeatherData(params[0]));

			try {
				weather = JSONWeatherParser.getWeather(data);
				
				weather.iconData = ( (new WeatherHttpClient()).getImage(weather.currentCondition.getIcon()));
				
			} catch (JSONException e) {				
				e.printStackTrace();
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
			
			return weather;
		
		}
		
		@Override
		protected void onPostExecute(Weather weather) {			
			super.onPostExecute(weather);
			
			Intent i = new Intent(SplashActivity.this, MainActivity.class);
			i.putExtra("WeatherObj", weather);
            startActivity(i);

            finish();				
		}
	}
}
