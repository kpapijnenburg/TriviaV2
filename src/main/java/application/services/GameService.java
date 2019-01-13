package application.services;

import api.interfaces.IGameService;
import application.model.MultiPlayerGame;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class GameService implements IGameService {
    private String baseUrl = "http://localhost:8090/game";

    @Override
    public void saveSinglePlayer(int score, int userId) {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(baseUrl + "/savesingleplayer?");

        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("score", Integer.toString(score)));
        params.add(new BasicNameValuePair("userId", Integer.toString(userId)));

        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params));

            CloseableHttpResponse response = client.execute(httpPost);
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveMultiPlayer(int playerAId, int playerBId, int playerAScore, int playerBScore, int winnerId) {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(baseUrl + "/savemultiplayer?");

        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("playerAId", Integer.toString(playerAId )));
        params.add(new BasicNameValuePair("playerBId", Integer.toString(playerBId )));
        params.add(new BasicNameValuePair("playerAScore", Integer.toString(playerAScore)));
        params.add(new BasicNameValuePair("playerBScore", Integer.toString(playerBScore)));
        params.add(new BasicNameValuePair("winnerId", Integer.toString(winnerId)));

        try{
            httpPost.setEntity(new UrlEncodedFormEntity(params));
            client.execute(httpPost);
            client.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
