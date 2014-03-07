package edu.cmu.lti.oaqa.bio.index.pmc;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Set;

import org.junit.Test;

import com.google.common.collect.Sets;

public class PmcArticleSetReaderTest {

  @Test
  public void test() throws IOException {
    PmcArticleSetReader reader = new PmcArticleSetReader(getClass().getResourceAsStream(
            "/pmc/articles-test.json"));
    Set<Integer> pmids = Sets.newHashSet();
    while (reader.hasNext()) {
      PmcArticle article = reader.next();
      pmids.add(article.getPmid());
    }
    assertEquals(pmids.size(), 7);
  }

}
