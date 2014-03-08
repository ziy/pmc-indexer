package edu.cmu.lti.oaqa.bio.index.pmc;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileAlreadyExistsException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import com.google.common.base.Stopwatch;

public class PmcArticleIndexer {

  private IndexWriter writer;

  public static String PMID_FIELD = "pmid";

  public static String TITLE_FIELD = "title";

  public static String SECTION_FIELD = "section";

  public static String TEXT_FIELD = "text";

  private static Stopwatch stopwatch = Stopwatch.createStarted();

  public PmcArticleIndexer(String indexPath) throws IOException {
    File indexDir = new File(indexPath);
    if (indexDir.exists() && indexDir.listFiles().length > 0) {
      throw new RuntimeException(new FileAlreadyExistsException(indexDir.getAbsolutePath()));
    } else if (!indexDir.exists()) {
      indexDir.mkdir();
    }
    Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_46);
    IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_46, analyzer);
    iwc.setOpenMode(OpenMode.CREATE);
    iwc.setRAMBufferSizeMB(2000);
    writer = new IndexWriter(FSDirectory.open(indexDir), iwc);
  }

  public void indexDocs(InputStream inputStream) throws IOException {
    PmcArticleSetReader reader = new PmcArticleSetReader(inputStream);
    int count = 0;
    while (reader.hasNext()) {
      count++;
      PmcArticle article = reader.next();
      for (int i = 0; i < article.getSections().size(); i++) {
        Document doc = new Document();
        doc.add(new IntField(PMID_FIELD, article.getPmid(), Field.Store.YES));
        String title = article.getTitle();
        if (title != null) {
          doc.add(new TextField(TITLE_FIELD, title, Field.Store.YES));
        }
        doc.add(new IntField(SECTION_FIELD, i, Field.Store.YES));
        doc.add(new TextField(TEXT_FIELD, article.getSections().get(i), Field.Store.YES));
        writer.addDocument(doc);
      }
      if (count % 10000 == 0) {
        writer.commit();
        System.out.println(stopwatch);
        System.out.print("Indexing " + count + "... ");
      }
    }
    writer.commit();
  }

  public void optimize() throws IOException {
    writer.forceMerge(1);
    writer.close();
  }

  public static void main(String[] args) throws IOException {
    PmcArticleIndexer pai = new PmcArticleIndexer(args[0]);
    File file = new File(args[1]);
    System.out.print("Indexing " + file.getName() + "... ");
    pai.indexDocs(new FileInputStream(file));
    System.out.println(stopwatch);
    System.out.print("Optimizing... ");
    stopwatch.reset();
    pai.optimize();
    System.out.println(stopwatch);
  }
}
