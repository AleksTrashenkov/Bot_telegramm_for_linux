package ru.mybot.bot;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class Servise {
    private static String limits;
    private static String valuesLimits;

    public static String getLimits() throws IOException {

        URL urlLim = new URL("https://api-developer.tech.yandex.net/projects/your_cab_number/services/pogoda/limits");
        HttpURLConnection conLim = (HttpURLConnection) urlLim.openConnection();
        conLim.setRequestMethod("GET");
        conLim.setRequestProperty("X-Auth-Key", "your_api_key");
        try (final BufferedReader in = new BufferedReader(new InputStreamReader(conLim.getInputStream()))) {
            String inputLine;
            final StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            String jsonLim = content.toString();

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(jsonLim);
            JSONArray limitsArray = new JSONArray();
            JSONObject limits1 = (JSONObject) jsonObject.get("limits");
            limitsArray.add(limits1);
            for (int i = 0; i < limitsArray.size(); i++) {
                JSONObject pogoda_hits_daily = (JSONObject) limits1.get("pogoda_hits_daily");
                int idLimit = pogoda_hits_daily.toString().indexOf("limit");
                limits = pogoda_hits_daily.toString().substring(idLimit + 7).split(",")[0];
                int idValue = pogoda_hits_daily.toString().indexOf("value");
                valuesLimits = pogoda_hits_daily.toString().substring(idValue + 7).split("}")[0];
            }
        } catch (final Exception ex) {
            return "Извините, что-то пошло не так.";
        }
        return "Запрошено: " + valuesLimits + " из " + limits;
    }
    public static String getLimitsKinopoisk () throws IOException {
            URL url = new URL("https://kinopoiskapiunofficial.tech/api/v1/api_keys/0f4fb058-7a53-46f0-9f02-2f414c130f8f");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("X-API-KEY", "your_api_key");
            con.setRequestProperty("Content-Type", "application/json");
            String jsonLim;
            try (final BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                jsonLim = in.readLine();
            }
            int idValue = jsonLim.indexOf("dailyQuota\":{\"value");
            int idUsed = jsonLim.indexOf("500,\"used");
            int idAccountStatus = jsonLim.indexOf("accountType");
            String value = jsonLim.substring(idValue + 21).split(",\"")[0];
            String used = jsonLim.substring(idUsed + 11).split("}")[0];
            String accountStatus = jsonLim.substring(idAccountStatus + 14).split("\"}")[0];
            String accountStatusRes;
            if (accountStatus.equals("FREE")) {
                accountStatusRes = "Активен";
            } else {
                accountStatusRes = "Не активен";
            }
            return "Лимиты для сервиса кинопоиска: "+"\n" +
                    "Лимит: "+value + "\n" +
                    "Использовано: "+used + "\n" +
                    "Статус: "+accountStatusRes;
    }
}
