package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping(value = "api/v1/search/")
public class SmartSearchController {

    @PostMapping("/smart_search")
    public String redirectToFlaskMicroservice(@RequestBody String query) throws IOException {
        OkHttpClient client = new OkHttpClient();


        okhttp3.RequestBody body = okhttp3.RequestBody.create(MediaType.parse("application/json; charset=utf-8")
                , new ObjectMapper().writeValueAsString(query));
        Request request = new Request.Builder()
                .url("http://localhost:5000/smart_search")
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}
