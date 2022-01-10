package com.tmax.eTest.Contents.service;

import com.tmax.eTest.Contents.exception.ContentsException;
import com.tmax.eTest.Contents.exception.ErrorCode;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ImageService {

  public String getThumbnail(String url) {

    String thumbnail = "";
    Connection conn = Jsoup.connect(url);
    // .header("User-Agent" , "Mozilla/5.0");
    try {
      Document document = conn.get();
      thumbnail = document.select("meta[property=og:image]").attr("content");
      log.info("Url = " + thumbnail);
    } catch (Exception e) {
      log.error("Open Graph Error");
      throw new ContentsException(ErrorCode.OPENGRAPH_ERROR, "url doesn't contain og:image!");
    }

    return thumbnail;
  }
}
