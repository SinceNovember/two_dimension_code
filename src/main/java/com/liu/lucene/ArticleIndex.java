package com.liu.lucene;

import java.io.StringReader;
import java.nio.file.Paths;
import java.text.Format;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.enterprise.inject.New;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Fragmenter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.search.highlight.SimpleSpanFragmenter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import com.liu.Utils.DateUtil;
import com.liu.Utils.StringUtil;
import com.liu.entity.Article;

 /** 
 * @ClassName: ArticleIndex 
 * @author: lyd
 * @date: 2019��4��17�� ����3:07:02 
 * @describe:luncene����
 */
public class ArticleIndex {
	private Directory directory;   
	private IndexWriter getWriter() throws Exception
	{
		directory=FSDirectory.open(Paths.get("D:\\article_Index"));//����Ŀ¼ ���ش��̴洢
		SmartChineseAnalyzer analyzer=new SmartChineseAnalyzer();//���ý�����
		IndexWriterConfig config=new IndexWriterConfig(analyzer);//����IndexWriter����
		IndexWriter indexWriter=new IndexWriter(directory, config);//����IndexWriter
		return indexWriter;
	}
	public void addIndex(Article article)throws Exception//�������
	{
		IndexWriter indexWriter=getWriter();
		Document document=new Document();//������¼
		document.add(new StringField("id", String.valueOf(article.getArticleId()), Field.Store.YES));//���һ�����ڴ洢����Ӽ�¼��
		document.add(new TextField("title", article.getArticleTitle(), Field.Store.YES));
		document.add(new TextField("content",article.getHtmlContent(), Field.Store.YES));
		indexWriter.addDocument(document);//��ӵ�IndexWriter
		indexWriter.close();//�رպܹؼ�
	}
	public void deleteIndex(String articleId)throws Exception//ɾ������
	{
		IndexWriter indexWriter=getWriter();
		indexWriter.deleteDocuments(new Term("id",articleId)); //Termһ����ǣ�ɾ����¼
		indexWriter.forceMergeDeletes();//ǿ��ɾ��
		indexWriter.commit();
		indexWriter.close();
	}
	public void updateIndex(Article article)throws Exception//�޸�����
	{
		IndexWriter indexWriter=getWriter();
		Document document=new Document();
		document.add(new StringField("id", String.valueOf(article.getArticleId()), Field.Store.YES));//���һ�����ڴ洢����Ӽ�¼��
		document.add(new TextField("title", article.getArticleTitle(), Field.Store.YES));
		document.add(new TextField("content",article.getHtmlContent(), Field.Store.YES));
		indexWriter.updateDocument(new Term("id",String.valueOf(article.getArticleId())), document);
		indexWriter.close();
	}
	public List<Article> searchArticle(String q,Integer flag)throws Exception//��ѯ����
	{
		directory=FSDirectory.open(Paths.get("D:\\article_Index"));
		IndexReader indexReader=DirectoryReader.open(directory);
		IndexSearcher indexSearcher=new IndexSearcher(indexReader);//����������
		BooleanQuery.Builder booleanQuery=new BooleanQuery.Builder();//���ڶԶ���ֶν��в�ѯ
		SmartChineseAnalyzer analyzer=new SmartChineseAnalyzer();
		QueryParser parser1=new QueryParser("title", analyzer);
		Query query1=parser1.parse(q);
		QueryParser parser2=new QueryParser("content",analyzer);
		Query query2=parser2.parse(q);
		booleanQuery.add(query1,BooleanClause.Occur.SHOULD);//Ӧ�ô��ڣ���������ӵ���ѯ�б���
		booleanQuery.add(query2,BooleanClause.Occur.SHOULD);
		TopDocs hits=indexSearcher.search(booleanQuery.build(),20);//��ѯ20��
		QueryScorer queryScorer=new QueryScorer(query1);//�����ߵ�
		SimpleHTMLFormatter simpleHTMLFormatter=new SimpleHTMLFormatter("<b><font color='red'>","</font></b>");//���ڶԹؼ�����Ӹ�ʽ
		Highlighter highlighter=new Highlighter(simpleHTMLFormatter, queryScorer);//�ƶ��ֽ��и���
		Fragmenter fragmenter=new SimpleSpanFragmenter(queryScorer);
		highlighter.setTextFragmenter(fragmenter);
		List<Article> blogIndexList=new LinkedList<Article>();
		for(ScoreDoc score:hits.scoreDocs)//���б�������ѯ��������
		{
			Document document=indexSearcher.doc(score.doc);
			Article article=new Article();
			article.setArticleId(Integer.parseInt(document.get("id")));
			String title=document.get("title");
			if(title!=null)
			{
				TokenStream tokenStream=analyzer.tokenStream("title", new StringReader(title));
				String hTitle=highlighter.getBestFragment(tokenStream, title);
				if(StringUtil.isEmpty(hTitle))
				{
					article.setArticleTitle(title);
				}
				else
				{
					article.setArticleTitle(hTitle);
				}
			}
			if(flag==1)//���Ϊ1������̨�Ĳ�ѯ����
			{
			String content=document.get("content");
			String reg = "[^\u4e00-\u9fa5]";
			content = content.replaceAll(reg, "");
			if(content!=null)
			{

				TokenStream tokenStream=analyzer.tokenStream("content", new StringReader(content));
				String hContent=highlighter.getBestFragment(tokenStream, content);
				if(StringUtil.isEmpty(hContent))
				{

						article.setHtmlContent(hContent);
					
				}
				else
				{
					article.setHtmlContent(hContent);
				}
			}
			}
			blogIndexList.add(article);
		}
		
			return blogIndexList;
	}
}
