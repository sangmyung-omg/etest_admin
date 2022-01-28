package com.tmax.eTest.Contents.service;

import com.tmax.eTest.Contents.exception.ContentsException;
import com.tmax.eTest.Contents.exception.ErrorCode;
import com.tmax.eTest.Contents.util.CommonUtils;

import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.UnsupportedMimeTypeException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ImageService {

  @Autowired
  private CommonUtils commonUtils;

  public String getThumbnail(String url) {
    log.info(url);

    String thumbnail = "";
    Connection conn = Jsoup.connect(url)
        // .userAgent("Mozilla")
        // .header("User-Agent", "Mozilla/5.0")
        .userAgent(
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/97.0.4692.71 Safari/537.36")
        .timeout(5000);
    try {
      Document document = conn.get();
      log.info("Get Succeded!");

      Element element = document.selectFirst("meta[property=og:image]");
      if (commonUtils.objectNullcheck(element))
        element = document.selectFirst("meta[property=og:secure_image]");
      thumbnail = element.attr("content");

      log.info("Thumbnail URL = " + thumbnail);
    } catch (java.net.MalformedURLException | HttpStatusException | UnsupportedMimeTypeException
        | java.net.SocketTimeoutException e) {
      log.error("Request URL Error: " + e.getMessage());
      throw new ContentsException(ErrorCode.OPENGRAPH_ERROR, "Request URL = " + url);
    } catch (Exception e) {
      log.error("Parsing Error: " + e.getMessage());
      throw new ContentsException(ErrorCode.OPENGRAPH_ERROR, "Parsing Error");
    }

    return thumbnail;
  }
}
