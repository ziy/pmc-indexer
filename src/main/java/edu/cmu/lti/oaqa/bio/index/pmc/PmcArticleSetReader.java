package edu.cmu.lti.oaqa.bio.index.pmc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;

import com.google.gson.Gson;

public class PmcArticleSetReader implements Iterator<PmcArticle> {

  private static Gson gson = new Gson();

  private BufferedReader br;

  public PmcArticleSetReader(InputStream inputStream) throws IOException {
    br = new BufferedReader(new InputStreamReader(inputStream));
  }

  private String line;

  @Override
  public boolean hasNext() {
    do {
      try {
        line = br.readLine();
      } catch (IOException e) {
        return false;
      }
      if (null == line) {
        return false;
      }
      if (line.startsWith("{") && line.endsWith("}")) {
        return true;
      }
      if (line.startsWith("{") && line.endsWith("},")) {
        line = line.substring(0, line.length() - 1);
        return true;
      }
    } while (true);
  }

  @Override
  public PmcArticle next() {
    return gson.fromJson(line, PmcArticle.class);
  }

  @Override
  public void remove() {
    // TODO Auto-generated method stub

  }

}
