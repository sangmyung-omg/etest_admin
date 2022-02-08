package com.tmax.eTest.Contents.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import com.tmax.eTest.Contents.exception.ContentsException;
import com.tmax.eTest.Contents.exception.ErrorCode;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ImageService {

  public String getThumbnail(String url) {
    log.info(url);

    try {
      URL inputURL = new URL(url);
      BufferedReader br = new BufferedReader(new InputStreamReader(inputURL.openStream()));
      final String END_STR = "\">";
      final String START_STR = "<meta property=\"og:image\" content=\"";

      String line = "";
      while ((line = br.readLine()) != null) {
        if (line.contains(START_STR))
          return line.trim().replace(START_STR, "").replace(END_STR, "");
      }
    } catch (IOException e) {
      log.info(e.getMessage());
      throw new ContentsException(ErrorCode.URL_ERROR, "Request URL = " + url);
    }
    return "";

    // String thumbnail = "";
    // Connection conn = Jsoup.connect(url)
    // // .userAgent("Mozilla")
    // // .header("User-Agent", "Mozilla/5.0")
    // .userAgent(
    // "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like
    // Gecko) Chrome/97.0.4692.71 Safari/537.36")
    // .timeout(5000);
    // try {
    // Document document = conn.get();
    // log.info("Get Succeded!");

    // Element element = document.selectFirst("meta[property=og:image]");
    // if (commonUtils.objectNullcheck(element))
    // element = document.selectFirst("meta[property=og:secure_image]");
    // thumbnail = element.attr("content");

    // log.info("Thumbnail URL = " + thumbnail);
    // } catch (java.net.MalformedURLException | HttpStatusException |
    // UnsupportedMimeTypeException
    // | java.net.SocketTimeoutException e) {
    // log.error("Request URL Error: " + e.getMessage());
    // throw new ContentsException(ErrorCode.OPENGRAPH_ERROR, "Request URL = " +
    // url);
    // } catch (Exception e) {
    // log.error("Parsing Error: " + e.getMessage());
    // throw new ContentsException(ErrorCode.OPENGRAPH_ERROR, "Parsing Error");
    // }

    // return thumbnail;
  }
}
