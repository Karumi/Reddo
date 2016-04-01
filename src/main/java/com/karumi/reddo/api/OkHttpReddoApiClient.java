package com.karumi.reddo.api;

import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class OkHttpReddoApiClient implements ReddoApiClient {

  private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

  private final String ip;
  private final int port;
  private final OkHttpClient httpClient;

  public OkHttpReddoApiClient(String ip, int port) {
    this.ip = ip;
    this.port = port;
    this.httpClient = new OkHttpClient();
  }

  @Override public void enqueueImage(int fps, Base64Image base64image) throws IOException {
    String json = generateRequestBody(fps, base64image);
    RequestBody body = RequestBody.create(JSON, json);
    Request request = new Request.Builder().url(ip + ":" + port).post(body).build();
    httpClient.newCall(request).execute();
  }

  private String generateRequestBody(int fps, Base64Image base64image) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("{ \"fps\" : " + fps + ",");
    stringBuilder.append("\"frame\" : { ");
    stringBuilder.append("\"width\" : " + base64image.getWidth() + ",");
    stringBuilder.append("\"height\" : " + base64image.getHeight() + ",");
    stringBuilder.append("\"pixels\" : \"" + base64image.getContent() + "\" } }");
    return stringBuilder.toString();
  }
}
