package com.example.demo.utils;

import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

public class QueryUtil {
    private static final Logger logger = Logger.getLogger(QueryUtil.class.getName());
    static String baseUrl = "https://dbpedia.org/sparql?default-graph-uri=http%3A%2F%2Fdbpedia.org&format=application%2Fsparql-results%2Bjson&CXML_redir_for_subjs=121&CXML_redir_for_hrefs=&timeout=30000&debug=on&run=+Run+Query+&query=";

    public static String encodeQuery(String query) {
        try {
            return URLEncoder.encode(query, StandardCharsets.UTF_8.name());
        } catch (Exception e) {
            logger.log(Level.WARNING, "Cannot encode to a valid url: " + query);
            return "";
        }
    }

    public static String getURLWithQuery(String query) {
        return baseUrl + encodeQuery(query);
    }

    public static String getResultFromQuery(String query) {
        try {
            String readLine;
            URL url = new URL(getURLWithQuery(query));
            HttpURLConnection conection = (HttpURLConnection) url.openConnection();
            conection.setRequestMethod("GET");
            conection.setRequestProperty("Content-Type", "application/json");
            int responseCode = conection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(conection.getInputStream()));
                StringBuilder response = new StringBuilder();
                while ((readLine = in.readLine()) != null) {
                    response.append(readLine);
                }
                JSONParser jsonParser = new JSONParser();
                JSONObject responseJson = (JSONObject) jsonParser.parse(response.toString());
                return ((JSONObject)responseJson.get("results")).getAsString("bindings");
            } else {
                logger.log(Level.WARNING, "get request failed");
            }
        } catch (Exception e) {
            logger.log(Level.WARNING, e.getMessage());
        }
        return "{}";
    }
}
