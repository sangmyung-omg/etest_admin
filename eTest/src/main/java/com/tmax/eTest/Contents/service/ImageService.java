package com.tmax.eTest.Contents.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import com.tmax.eTest.Contents.exception.ContentsException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import io.netty.resolver.DefaultAddressResolverGroup;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

@Service
@Slf4j
public class ImageService {

  private HttpClient httpClient;
  private WebClient webClient;

  public String getThumbnail(String url) {
    log.info(url);

    // try {
    // URL inputURL = new URL(url);
    // BufferedReader br = new BufferedReader(new
    // InputStreamReader(inputURL.openStream()));
    // final String IMAGE_TAG = "og:image";
    // final String CONTENT_TAG = "content=\"";
    // // final String START_STR = "<meta property=\"og:image\" content=\"";

    // String line = "";
    // while ((line = br.readLine()) != null) {
    // if (line.contains(IMAGE_TAG)) {
    // StringBuilder sb = new StringBuilder();
    // Integer idx = line.indexOf(CONTENT_TAG) + CONTENT_TAG.length();
    // for (int i = idx; i < line.length(); i++) {
    // char t = line.charAt(i);
    // if (t != '\"')
    // sb.append(line.charAt(i));
    // else
    // return sb.toString();
    // }
    // }
    // }
    // } catch (IOException e) {
    // log.info(e.getMessage());
    // throw new ContentsException(ErrorCode.URL_ERROR, "Request URL = " + url);
    // }
    // return "";

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

    buildClient(url);
    String htmlSource = getNaverPost();
    BufferedReader br = new BufferedReader(new StringReader(htmlSource));
    final String IMAGE_TAG = "og:image";
    final String CONTENT_TAG = "content=\"";
    // final String START_STR = "<meta property=\"og:image\" content=\"";

    String line = "";
    try {
      while ((line = br.readLine()) != null) {
        if (line.contains(IMAGE_TAG)) {
          StringBuilder sb = new StringBuilder();
          Integer idx = line.indexOf(CONTENT_TAG) + CONTENT_TAG.length();
          for (int i = idx; i < line.length(); i++) {
            char t = line.charAt(i);
            if (t != '\"')
              sb.append(line.charAt(i));
            else
              return sb.toString();
          }
        }
      }
    } catch (IOException e) {
      log.info(e.getMessage());
    }
    log.info(htmlSource);
    return "";
  }

  private void buildClient(String url) {
    this.httpClient = HttpClient.create().resolver(DefaultAddressResolverGroup.INSTANCE)
        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
        .responseTimeout(Duration.ofMillis(5000))
        .doOnConnected(conn -> conn.addHandlerLast(new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS))
            .addHandlerLast(new WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS)));

    this.webClient = WebClient.builder().baseUrl(url)
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .clientConnector(new ReactorClientHttpConnector(httpClient)).build();
  }

  public String getNaverPost() {
    String response = webClient.get().retrieve()
        .onStatus(HttpStatus::is4xxClientError,
            __ -> Mono.error(new ContentsException("ERR-POST-400", "NAVER POST 400 error")))
        .onStatus(HttpStatus::is5xxServerError,
            __ -> Mono.error(new ContentsException("ERR-POST-500", "NAVER POST 500 error")))
        .bodyToMono(String.class).flux().toStream().findFirst().orElse("");

    // response.subscribe(res -> log.info("Success: Save statement!"), e -> {
    // log.error("Error: Save Statement | " + e.getMessage());
    // throw new ContentsException(ErrorCode.LRS_ERROR, "SaveStatementAPI");
    // });
    return response;
  }

}
