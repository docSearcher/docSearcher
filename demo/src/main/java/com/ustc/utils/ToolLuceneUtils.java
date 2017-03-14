package com.ustc.utils;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.ustc.domain.Picinfo;

public class ToolLuceneUtils {
     private static IndexWriterConfig indexWriterConfig;
     
     private static Analyzer analyzer = new IKAnalyzer();
     
     private static Directory directory;
     private static File file = new File(GloablNames.file);
     
     
     /*static{
    	 try{
    		 directory  = FSDirectory.open(file);
    		 indexWriterConfig = new IndexWriterConfig(Version.LATEST, analyzer);
    		 indexWriterConfig.setOpenMode(OpenMode.CREATE_OR_APPEND);
    	 }catch(Exception e){
    		 e.printStackTrace();
    	 }
     }*/
     
     public static Analyzer getAnalyzer(){
    	 return analyzer;
     }
     
     
	public static IndexWriter getIndexWriter() {
    	 IndexWriter indexWriter = null;
    	 try {
    		 if (!file.exists())
    				file.getParentFile().mkdirs();
			directory  = FSDirectory.open(file);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		 indexWriterConfig = new IndexWriterConfig(Version.LATEST, analyzer);
		 indexWriterConfig.setOpenMode(OpenMode.CREATE_OR_APPEND);
    	 try {
    		 ///兩個不同的indexWriter不能使用同一個indexWriterConfig
			indexWriter  = new IndexWriter(directory,indexWriterConfig);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	 return indexWriter;
     }
     
     public static IndexSearcher getIndexSerarcher(){
    	 IndexSearcher indexSearcher = null;
    	 IndexReader indexReader;
		try {
			directory  = FSDirectory.open(file);
			indexReader = DirectoryReader.open(directory);
			indexSearcher  = new IndexSearcher(indexReader);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	 return indexSearcher;
     }
     
     public static void closeIndexWriter(IndexWriter indexWriter){
    	 if(indexWriter != null){
    		 try {
				indexWriter.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	 }
     }
     
     
     
     public static Document pptInfotoDocument(Picinfo picInfo){
    	 Document document =new Document();
    	 //document.add(new StringField("fileName", picInfo.getFileName(), Store.YES));
    	 document.add(new StringField("fileUrl", picInfo.getFileUrl(), Store.YES));
    	 document.add(new TextField("fileContent", picInfo.getFileContent(), Store.YES));
    	 //document.add(new StringField("originalFileName", picInfo.getUploadfileinfo().getFileId(), Store.YES));
    	 document.add(new IntField("filePageNumber", picInfo.getFilePageNumber(), Store.YES));
    	 return document;
     }
     
     /*
      * document文档转为具体的对象
      */
     
     public static Picinfo documnetToPicInfo(Document document){
    	 Picinfo picInfo = new Picinfo();
    	 
    	 picInfo.setFileContent(document.get("fileContent"));
    	 //picInfo.setFileName(document.get("fileName"));
    	 picInfo.setFileUrl(document.get("fileUrl"));
    	 picInfo.setFilePageNumber(Integer.parseInt(document.get("filePageNumber")));
    	 return picInfo;
     }
}
