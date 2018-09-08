package com.crmmarketing.hmt.GsonModel;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DocumentList {

@SerializedName("document")
@Expose
private List<Document> document = null;

public List<Document> getDocument() {
return document;
}

public void setDocument(List<Document> document) {
this.document = document;
}

}