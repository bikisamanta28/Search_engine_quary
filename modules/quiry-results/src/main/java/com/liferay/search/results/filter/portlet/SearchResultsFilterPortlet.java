package com.liferay.search.results.filter.portlet;
import com.liferay.search.results.filter.constants.SearchResultsFilterPortletKeys;
import java.io.IOException;
import java.io.PrintWriter;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.ProcessAction;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import com.liferay.blogs.constants.BlogsConstants;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.social.BlogsActivityKeys;
import com.liferay.journal.constants.JournalArticleConstants;
import com.liferay.journal.constants.JournalFolderConstants;
import com.liferay.journal.model.JournalArticle;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.hits.SearchHit;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.query.BooleanQuery;
import com.liferay.portal.search.query.MatchQuery;
import com.liferay.portal.search.query.MoreLikeThisQuery;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.query.TermQuery;
import com.liferay.portal.search.query.TermsQuery;
import com.liferay.portal.search.searcher.SearchRequest;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.searcher.Searcher;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.Component;
/**
 * @author me
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=SearchResultsFilter",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + SearchResultsFilterPortletKeys.SEARCHRESULTSFILTER,
		"mvc.command.name=/user/description",
		"javax.portlet.resource-bundle=content.Language",
		
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)

public class SearchResultsFilterPortlet extends MVCPortlet {
	
	
	List<String> results = new ArrayList();
	
	
	@Override
	public void render(RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException {
	
		
		renderRequest.setAttribute("titleList", results);
		super.render(renderRequest, renderResponse);
		results.clear();
		
	}
	
	@Override
	public void serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)throws IOException, PortletException {
         System.out.println("3");
	    JSONArray jsonArray = JSONFactoryUtil.createJSONArray();
	    
	    for (String title : results) {
	    	 System.out.println("ajax call"+results);
	        jsonArray.put(title);
	    }
	    PrintWriter writer = resourceResponse.getWriter();
	    writer.println(jsonArray.toString());
	    System.out.println("ajax :"+jsonArray.toString());
	    System.out.println("ajax call");
	    results.clear();
	   
	}
	
	
	@ProcessAction(name = "searchResult")
	public void searchResult(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
		
		String keywords = ParamUtil.getString(actionRequest, "Search");
		Long companyId = 20096L;
		
		MatchQuery titleQuery = queries.match(Field.getLocalizedName(LocaleUtil.US, Field.TITLE), keywords);
	
			
			MatchQuery matchQuery = queries.match("localized_title", keywords);
			
//	rootFolderQuery.addValues(String.valueOf(JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID));
			//MoreLikeThisQuery likeThisQuery = queries.moreLikeThis(keywords);
			//rootFolderQuery.addValues(String.valueOf(BlogsConstants.SERVICE_NAME));
			BooleanQuery booleanQuery = queries.booleanQuery();
			booleanQuery.addMustQueryClauses(matchQuery);
			SearchRequestBuilder searchRequestBuilder =searchRequestBuilderFactory.builder();
			// Uncomment this line below if you aren't setting "keywords"
			// on the SearchContext
				//	searchRequestBuilder.emptySearchEnabled(true);
			searchRequestBuilder.withSearchContext(
				searchContext -> {
					searchContext.setCompanyId(companyId);					
					searchContext.setKeywords(keywords);
				
				});
			
			SearchRequest searchRequest = searchRequestBuilder.query(
				booleanQuery
			).build();
			SearchResponse searchResponse = searcher.search(searchRequest);
			SearchHits searchHits = searchResponse.getSearchHits();
			
			List<SearchHit> searchHitsList = searchHits.getSearchHits();
			
//	System.out.println("ggggggggggggggggg" +searchHitsList);
			
			List<String> resultsList = new ArrayList<>(searchHitsList.size());
			searchHitsList.forEach(
				searchHit -> {
					float hitScore = searchHit.getScore();
					Document doc = searchHit.getDocument();
//			System.out.println(doc);
					
					Map<String, com.liferay.portal.search.document.Field> fieldMap = doc.getFields();
					Set<String> keySet = fieldMap.keySet();
					
	System.out.println("this is keyset "+keySet);
//					for(String fld : keySet ) {
//						
//						System.out.println("this is keyset "+fld);
//						//System.out.println(fieldMap.get(fld));
//					}
//		System.out.println("thisssssssssssssssssssssssssssssssssssss "+fieldMap.get("content_en_US"));
				
				com.liferay.portal.search.document.Field title=fieldMap.get("localized_title");
				
				results.add(title.toString());
			    
			    // Your existing code...
				
				 
//				System.out.println(doc.getClass());
					String uid = doc.getString(Field.UID);
//					System.out.println(
//						StringBundler.concat(
//							"Document ", uid, " had a score of ", hitScore));
				resultsList.add(uid);
				});
//			System.out.println(StringPool.EIGHT_STARS);
			System.out.println(resultsList.size());
			System.out.println(keywords);
		
			/*
			 *  // Uncomment to inspect the Request and Response Strings
	         * System.out.println( "Request String:\n" + searchResponse.getRequestString() +
			 * "\n" + StringPool.EIGHT_STARS);
			 * System.out.println( "Response String:\n" +
			 * searchResponse.getResponseString() + "\n" + StringPool.EIGHT_STARS);
			 */
			
	}
	
	@Reference
	protected Queries queries;
	@Reference
	protected Searcher searcher;
	@Reference
	protected SearchRequestBuilderFactory searchRequestBuilderFactory;
}