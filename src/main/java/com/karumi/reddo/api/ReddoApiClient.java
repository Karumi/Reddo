package com.karumi.reddo.api;

import java.io.IOException;

public interface ReddoApiClient {

  void enqueueImage(int fps, Base64Image base64image) throws IOException;

}
