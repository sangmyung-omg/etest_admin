package com.tmax.eTest.Contents.util;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component(value = "CommonUtils")
public class CommonUtils {

  private SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

  public boolean stringNullCheck(String str) {
    return StringUtils.isBlank(str);
  }

  public boolean objectNullcheck(Object obj) {
    return ObjectUtils.isEmpty(obj);
  }

  public long zeroIfNull(Long num) {
    return Optional.ofNullable(num).orElse(0L);
  }

  public int zeroIfNull(Integer num) {
    return Optional.ofNullable(num).orElse(0);
  }

  public String dateToStr(Date date) {
    return objectNullcheck(date) ? "" : sdf.format(date);
  }

  public Date strToDate(String str) {
    Date date = null;
    try {
      date = new Date(sdf.parse(str).getTime());
    } catch (ParseException e) {
      log.info(str + ": Date Format Error ");
    }
    return date;
  }
}
