package com.example.httprequest;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
    public void executeHttpGet() throws Exception {
		// Creating HTTP client
  	  //  ArrayList<ArrayList<String>> points;
    	ArrayList<ArrayList<String>> points;
  		HttpClient httpClient = new DefaultHttpClient();
  		double myLat = 40.192512, myLon = -83.526306;
  		int myDist = 2;

  		// Creating HTTP Post
  		HttpPost httpPost = new HttpPost("http://redlights.herokuapp.com/in_proximity_of");

  		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(3);
  		nameValuePair.add(new BasicNameValuePair("latitude", String.valueOf(myLat)));
  		nameValuePair.add(new BasicNameValuePair("longitude", String.valueOf(myLon)));
  		nameValuePair.add(new BasicNameValuePair("distance_in_miles", String.valueOf(myDist)));
  		String body="";


  		// Url Encoding the POST parameters
  		try {
  		    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
  		}
  		catch (UnsupportedEncodingException e) {
  		    // writing error to Log
  		    e.printStackTrace();
  		}

  		// Making HTTP Request
  		try {
  		    HttpResponse response = httpClient.execute(httpPost);

  		    // writing response to log
  		    body = response.toString();

  		} catch (ClientProtocolException e) {
  		    // writing exception to log
  		    e.printStackTrace();

  		} catch (IOException e) {
  		    // writing exception to log
  		    e.printStackTrace();
  		}
  		// TODO Auto-generated method stub
  		//NOT USED String body = HttpRequest.get("http://redlights.herokuapp.com/in_proximity_of", true, "latitude", myLat, "longitude", myLon,"distance_in_miles",myDist).body();
  		//System.out.println(body);
  		//body = body.substring(69); //may need to change this index if the header changes, and perhaps define it somewhere
  		String longitude="", latitude="", city="", state="", name="", entry = "", trash = "";
  		//double lat = 0, lon=0;
  		points = new ArrayList< ArrayList <String> >();


  		Scanner scanner = new Scanner(body);
  		scanner.useDelimiter("}");
  		while (body.length()>0 && !scanner.hasNext("]") ){


  			if (scanner.hasNext()){
  				entry = scanner.next();
  			}
  			Scanner scan2 = new Scanner(entry);
  			scan2.useDelimiter("\":");

  			if (scan2.hasNext()){
  				trash = scan2.next();
  			}
  			if (scan2.hasNext()){
  				state = scan2.next();
  				state = (String) state.subSequence(1, state.indexOf("\","));
  			}

  			if (scan2.hasNext()){
  				city = scan2.next();
  				city = (String) city.subSequence(1, city.indexOf("\","));
  			}

  			if (scan2.hasNext()){
  				name = scan2.next();
  				name = (String) name.subSequence(1, name.indexOf("\","));				
  			}

  			if (scan2.hasNext()){
  				longitude = scan2.next();
  				longitude = (String) longitude.subSequence(0, longitude.indexOf(",\""));
  			}

  			if (scan2.hasNext()){
  				latitude = scan2.next();
  				latitude = (String) latitude.subSequence(0, latitude.indexOf(",\""));				
  			}

  			/*  try{
  				  lat = Double.valueOf(latitude.trim()).doubleValue();
  				  lon = Double.valueOf(longitude.trim()).doubleValue();
  			  }
  				    catch (NumberFormatException e){
  				    System.out.println("NumberFormatException: " + e.getMessage());
  			  }*/

  			ArrayList<String> vals = new ArrayList <String> ();
  			vals.add(state);
  			vals.add(city);
  			vals.add(name);
  			vals.add(latitude);
  			vals.add(longitude);
  			points.add(vals);
  			//System.out.println(state + " " + city + " " + name + " " + lon + " " + lat +"\n");
  		}

  	}
    

}
