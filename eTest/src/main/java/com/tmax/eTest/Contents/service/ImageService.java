package com.tmax.eTest.Contents.service;

import com.tmax.eTest.Contents.exception.ContentsException;
import com.tmax.eTest.Contents.exception.ErrorCode;

import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ImageService {

  public String getThumbnail(String url) {

    String thumbnail = "";
    Connection conn = Jsoup.connect(url)
        .userAgent("Mozilla");
    // .header("User-Agent", "Mozilla/5.0");
    try {
      Document document = conn.get();
      thumbnail = document.select("meta[property=og:image]").first().attr("content");
      log.info("Thumbnail URL = " + thumbnail);
    } catch (HttpStatusException e) {
      log.error("Request URL Error: " + e.getMessage());
      throw new ContentsException(ErrorCode.OPENGRAPH_ERROR, "Request URL = " + url);
    } catch (Exception e) {
      log.error("Parsing Error: " + e.getMessage());
      throw new ContentsException(ErrorCode.OPENGRAPH_ERROR, "Parsing Error");
    }

    return thumbnail;
  }
}
