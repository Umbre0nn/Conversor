import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CurrencyConverter {
    private String apiKey;

    public CurrencyConverter(String apiKey) {
        this.apiKey = apiKey;
    }

    public double convertCurrency(String baseCurrency, String targetCurrency, double amountToConvert) throws IOException {
        String url_str = "https://v6.exchangerate-api.com/v6/" + apiKey + "/pair/" + baseCurrency + "/" + targetCurrency + "/" + amountToConvert;

        URL url = new URL(url_str);
        HttpURLConnection request = (HttpURLConnection) url.openConnection();
        request.connect();

        int responseCode = request.getResponseCode();
        if (responseCode != 200) {
            throw new IOException("Error en la solicitud HTTP: " + responseCode);
        }

        JsonParser jp = new JsonParser();
        JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
        JsonObject jsonobj = root.getAsJsonObject();

        String result = jsonobj.get("result").getAsString();
        if (!result.equals("success")) {
            throw new IOException("Error al convertir las divisas.");
        }

        return jsonobj.get("conversion_result").getAsDouble();
    }
}
