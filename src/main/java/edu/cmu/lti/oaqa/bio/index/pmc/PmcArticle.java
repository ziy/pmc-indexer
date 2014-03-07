package edu.cmu.lti.oaqa.bio.index.pmc;

import java.util.List;

public class PmcArticle {

  private int pmid;
  
  private String title;

  private List<String> sections;

  public PmcArticle(int pmid, String title, List<String> sections) {
    super();
    this.pmid = pmid;
    this.title = title;
    this.sections = sections;
  }

  @Override
  public String toString() {
    return String.valueOf(pmid);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + pmid;
    result = prime * result + ((sections == null) ? 0 : sections.hashCode());
    result = prime * result + ((title == null) ? 0 : title.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    PmcArticle other = (PmcArticle) obj;
    if (pmid != other.pmid)
      return false;
    if (sections == null) {
      if (other.sections != null)
        return false;
    } else if (!sections.equals(other.sections))
      return false;
    if (title == null) {
      if (other.title != null)
        return false;
    } else if (!title.equals(other.title))
      return false;
    return true;
  }

  public int getPmid() {
    return pmid;
  }

  public void setPmid(int pmid) {
    this.pmid = pmid;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public List<String> getSections() {
    return sections;
  }

  public void setSections(List<String> sections) {
    this.sections = sections;
  }

}
