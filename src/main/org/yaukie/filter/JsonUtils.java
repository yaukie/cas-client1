package org.yaukie.filter;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils
{
  public static String convertToString(Object obj) throws IOException
  {
    if (obj == null) return null;

    ObjectMapper om = new ObjectMapper();
    return om.writeValueAsString(obj);
  }

  public static <T> T readToObject(String str, Class<T> cla)  throws IOException
  {
    if (str == null) return null;

    ObjectMapper om = new ObjectMapper();
    return om.readValue(str, cla);
  } 
}