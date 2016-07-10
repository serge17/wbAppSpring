package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.example.model.Data;
import com.example.repository.DataRepositoryMongo;

@SpringBootApplication
@EnableTransactionManagement
@EnableCaching
public class Application implements CommandLineRunner {
	JSONArray jsonArray;
	@Autowired
	private DataRepositoryMongo repository;

	public static void main(String[] args) throws Exception {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public CacheManager cacheManager() {
		ConcurrentMapCacheManager cacheManager = new ConcurrentMapCacheManager("data");
		return cacheManager;
	}

	@Override
	public void run(String... arg0) throws Exception {

		repository.deleteAll();
		populate();
		JSONArray arr = jsonArray.getJSONArray(1);
		for (int i = 0; i < arr.length(); i++) {
			JSONObject jo = arr.getJSONObject(i);
			if (!jo.get("value").toString().equals("null")) {
				Data data = new Data();
				data.setCountryId(jo.getJSONObject("country").getString("id"));
				data.setCountryValue(jo.getJSONObject("country").getString("value"));
				data.setDate(jo.getInt("date"));
				data.setDecimal(jo.getInt("decimal"));
				data.setIndicatorId(jo.getJSONObject("indicator").getString("id"));
				data.setIndicatorValue(jo.getJSONObject("indicator").getString("value"));
				data.setValue(Double.valueOf(jo.get("value").toString()));
				data.setId(data.getCountryId() + data.getDate());
				repository.save(data);
			}
		}
	}

	private void populate() {

		try {
			URL worldBank = new URL("http://api.worldbank.org/country/all/indicator/SP.POP.GROW?per_page=15000&format=json");
			URLConnection yc = worldBank.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
			String inputLine;
			StringBuilder sb = new StringBuilder();
			while ((inputLine = in.readLine()) != null)
				sb.append(inputLine);
				sb.append(System.lineSeparator());
			in.close();
			String everything = sb.toString();
			jsonArray = new JSONArray(everything);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
