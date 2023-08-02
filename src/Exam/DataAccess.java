package Exam;
import java.util.ArrayList;
import java.util.List;
import java.util.*;
import java.io.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import Exam.DataModel.ListData;

import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
public class DataAccess {
	public static List<ListData> l = new ArrayList<>();

	 public DataAccess() {
	        HttpClient client = HttpClient.newHttpClient();
	        String url = "https://samples.openweathermap.org/data/2.5/forecast/hourly?q=London,us&appid=b6907d289e10d714a6e88b30761fae22";
	        HttpRequest request = HttpRequest.newBuilder()
	                .uri(URI.create(url))
	                .GET()
	                .build();

	        CompletableFuture<HttpResponse<String>> response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());

	        try {
	            HttpResponse<String> result = response.get();
	            String da = result.body();
	            Gson gson = new Gson();
	            Type dataType = new TypeToken<DataModel>(){}.getType();
	            DataModel deserializedData = gson.fromJson(da, dataType);

	            l.addAll(deserializedData.list);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	    public List<DataRepresentation.WindsWithDate> GetWinds(String date) {
	        List<DataRepresentation.WindsWithDate> lw = new ArrayList<>();
	        ListData[] l;
			for (ListData data : l) {
	            String db_txt = data.dt_txt;
	            String onlydate = db_txt.substring(0, 10);
	            if (onlydate.equals(date)) {
	                DataModel.Wind w = data.wind;
	                DataRepresentation.WindsWithDate dp = new DataReprestentation.WindsWithDate(db_txt, w);
	                lw.add(dp);
	            }
	        }
	        return lw;
	    }

	    @SuppressWarnings("null")
		public List<DataRepresentation.MainWithDate> GetData(String date) {
	        List<DataRepresentation.MainWithDate> mw = new ArrayList<>();
	        ListData[] l = null;
			for (ListData data : l) {
	            String db_txt = data.dt_txt;
	            String onlydate = db_txt.substring(0, 10);

	            if (onlydate.equals(date)) {
	                DataModel.Main m = data.main;
	                DataRepresentation.MainWithDate datemain = new DataRepresentation.MainWithDate(m, db_txt);
	                mw.add(datemain);
	            }
	        }
	        return mw;
	    }
	}


