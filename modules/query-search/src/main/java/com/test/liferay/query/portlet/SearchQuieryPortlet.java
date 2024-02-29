package com.test.liferay.query.portlet;

import com.test.liferay.query.constants.SearchQuieryPortletKeys;

import java.io.IOException;
import java.util.List;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;

import com.liferay.portal.kernel.util.ParamUtil;


import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

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
		"javax.portlet.display-name=SearchQuiery",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + SearchQuieryPortletKeys.SEARCHQUIERY,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class SearchQuieryPortlet extends MVCPortlet {
	
	
    public void render(RenderRequest renderRequest, RenderResponse renderResponse)
            throws IOException, PortletException  {
        SearchContext searchContext = new SearchContext();
        searchContext.setCompanyId(20096L);
        searchContext.setStart(0);
        searchContext.setEnd(10); // Number of results to retrieve
        searchContext.setKeywords(ParamUtil.getString(renderRequest, "keywords"));
        // Execute the search
        Indexer indexer = IndexerRegistryUtil.getIndexer("test");
      
        try {
        	
            Hits hits = indexer.search(searchContext);
            // Process and display the search results
            List<Document> documents = hits.toList();
            System.out.println(documents);
            for (Document document : documents) {
                String title = document.get(Field.TITLE);
                String description = document.get(Field.DESCRIPTION);
                
                
                // Display the title and description in your portlet
                // Example: renderResponse.getWriter().write("<h2>" + title + "</h2>");
            }
        } catch (SearchException e) {
            e.printStackTrace();
            // Handle search exception
        }
    }
}