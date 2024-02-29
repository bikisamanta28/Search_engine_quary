<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/ddm" prefix="liferay-ddm" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ page import="com.liferay.portal.kernel.dao.search.SearchContainer" %><%@
page import="com.liferay.portal.kernel.model.User" %><%@
page import="com.liferay.portal.kernel.search.Document" %><%@
page import="com.liferay.portal.kernel.util.HashMapBuilder" %><%@
page import="com.liferay.portal.kernel.util.WebKeys" %><%@
page import="com.liferay.portal.search.web.internal.result.display.context.SearchResultSummaryDisplayContext" %><%@
page import="com.liferay.portal.search.web.internal.search.results.configuration.SearchResultsPortletInstanceConfiguration" %><%@
page import="com.liferay.portal.search.web.internal.search.results.portlet.SearchResultsPortletDisplayContext" %>
<portlet:defineObjects />

<%
SearchResultsPortletDisplayContext searchResultsPortletDisplayContext = (SearchResultsPortletDisplayContext)java.util.Objects.requireNonNull(request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT));
if (searchResultsPortletDisplayContext.isRenderNothing()) {
	return;
}
SearchResultsPortletInstanceConfiguration searchResultsPortletInstanceConfiguration = searchResultsPortletDisplayContext.getSearchResultsPortletInstanceConfiguration();
List<SearchResultSummaryDisplayContext> searchResultSummaryDisplayContexts = searchResultsPortletDisplayContext.getSearchResultSummaryDisplayContexts();
SearchContainer<Document> searchContainer = searchResultsPortletDisplayContext.getSearchContainer();
%>
<%! List<String> items = new ArrayList<String>(); %>
<div id="resultContaineritems"></div>
<div id="resultContainerf"></div>
<script>
var searchResultSummaryDisplayContexts = [
	  <% for (SearchResultSummaryDisplayContext summaryDisplayContext : searchResultSummaryDisplayContexts) { %>
	  
	    {
	      title: '<%= summaryDisplayContext.getTitle() %>',
	      date: '<%= summaryDisplayContext.getCreationDateString() %>',
	      user: '<%= summaryDisplayContext.getCreatorUserName() %>'
	     
	    },
	  <% } %>
];
var numberofitems=searchResultSummaryDisplayContexts.length;
console.log("first num ",numberofitems);
var resultContainerfirst = document.getElementById('resultContainerf');	
resultContainerfirst.innerHTML = '';	
for(var i = 0; i < 4; i++){
	var s=i+1;
	console.log(searchResultSummaryDisplayContexts[i].title);
	var itemHTML ='<h4>'+'<a herf="#">'+ searchResultSummaryDisplayContexts[i].title+'</a>'+'</h4>' +'<br>'+'<h5>'+ searchResultSummaryDisplayContexts[i].user +'<br>'+ searchResultSummaryDisplayContexts[i].date+'</h5>' + '<hr>'; // Replace with actual item data
    
	resultContainerfirst.innerHTML += itemHTML
}
var x=i;
console.log(x+"x");
myFunction(x);
function iterateItems() {
    var resultContainer = document.getElementById('resultContainer');
    var iterationIndex = document.getElementById('iterationIndex');
    // Get the current iteration index
    var currentIndex = parseInt(iterationIndex.value);
    console.log(currentIndex);
 // Clear previous results
    resultContainer.innerHTML = '';
    for(var i = 4; i < currentIndex + 4 && searchResultSummaryDisplayContexts.length; i++){
    	let s=i+1;
		console.log(searchResultSummaryDisplayContexts[i].title);
		var itemHTML ='<h4>'+'<a herf="#">'+ searchResultSummaryDisplayContexts[i].title+'</a>'+'</h4>' +'<br>'+'<h5>'+ searchResultSummaryDisplayContexts[i].user +'<br>'+ searchResultSummaryDisplayContexts[i].date+'</h5>' + '<hr>'; // Replace with actual item data
        resultContainer.innerHTML += itemHTML
	}	
    x=i;
    // Update the iteration index
    iterationIndex.value = i;
    console.log(i);
    console.log(x);
    myFunction(x);
    
    if(numberofitems==0){
    	myFunction();
    }
}
function myFunction(x){
	var resultContainer = document.getElementById('resultContaineritems');
	resultContainer.innerHTML = '';
	var itemHTML ='<p>'+"showing "+x+" items out of "+ numberofitems+" Entries"+'</p>';
    resultContainer.innerHTML += itemHTML
}
</script>
<aui:form>
  <div id="resultContainer"></div>
  <input type="hidden" id="iterationIndex" value="4" />
  <aui:button type="button" value="Load More" onClick="iterateItems();" />
</aui:form>