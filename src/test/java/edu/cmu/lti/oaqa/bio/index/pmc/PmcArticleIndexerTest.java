package edu.cmu.lti.oaqa.bio.index.pmc;

import java.io.IOException;

import org.junit.Test;

/**
 * Maven execution: <code>
 * mvn exec:exec -Dindex.dir=src/test/resources/index/lucene \
 *               -Ddocs.dir=src/test/resources/pmc/articles-test.json
 * </code>
 * 
 * @author Zi Yang <ziy@cs.cmu.edu>
 * 
 */
public class PmcArticleIndexerTest {

  @Test
  public void test() throws IOException {
    String[] args = { "src/test/resources/index/lucene",
        "src/test/resources/pmc/articles-test.json" };
    PmcArticleIndexer.main(args);
  }

}
