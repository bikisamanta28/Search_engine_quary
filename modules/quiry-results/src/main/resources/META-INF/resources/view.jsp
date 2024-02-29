<%@page import="javax.portlet.RenderRequest"%>
<%@ include file="/init.jsp" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>

<portlet:actionURL name="searchResult" var="searchResultURL"/>
<%List<String> title= new ArrayList();
title= (List<String>)renderRequest.getAttribute("titleList");
System.out.println("titleList :"+ title);
%>
<!--<aui:form action="<%//=searchResultURL %>" name="searchForm" >
<aui:input name="Search" placeholder="Search..."></aui:input>
<button class="btn btn-light btn-unstyled"  type="submit" value="submit"><liferay-ui:icon icon="search" markupView="lexicon"/></button>
</aui:form>-->

<portlet:resourceURL id="/user/description" var="userDetailUrl"></portlet:resourceURL>
<aui:form action="<%= searchResultURL %>" name="searchForm" >
<aui:input name="Search"  placeholder="Search..."></aui:input>
<ul id="listContainer"></ul>
<h1 id ="biki"></h1>

<button class="btn btn-light btn-unstyled"  type="submit" value="submit"><liferay-ui:icon icon="search" markupView="lexicon"/></button>

</aui:form>
<script>

var result = [
	  <% for (String re : title) { %>
	  
	    {
	      title: '<%= re%>',
	     
	     
	    },
	  <% } %>
];


var resultContainerfirst = document.getElementById('resultContainerf');
resultContainerfirst.innerHTML = '';
for(var i = 0; i < 4; i++){
	var s=i+1;
	
	var itemHTML ='<h4>'+'<a herf="#">'+ result[i].title+'</a>'+'</h4>' ; // Replace with actual item data
    
	resultContainerfirst.innerHTML += itemHTML
}
var x=i;
myFunction(x);

function iterateItems() {
    var resultContainer = document.getElementById('resultContainer');
    var iterationIndex = document.getElementById('iterationIndex');
    // Get the current iteration index
    var currentIndex = parseInt(iterationIndex.value);
    console.log(currentIndex);
 // Clear previous results
    resultContainer.innerHTML = '';
 
 
    for(var i = 4; i < currentIndex + 4 && result.length; i++){
    
	var itemHTML ='<h4>'+'<a herf="#">'+ result[i].title+'</a>'+'</h4>' ;
        resultContainer.innerHTML += itemHTML
	}	
    x=i;
    
    	
    
    
    
    
    // Update the iteration index
    iterationIndex.value = i;
    
    console.log(x);
    myFunction(x);
    
    if(numberofitems==0){
    	myFunction();
    }
}
function myFunction(x){
	var resultContainer = document.getElementById('resultContaineritems');
	resultContainer.innerHTML = '';
	var itemHTML ='<p>'+"showing "+x+" items out of "+ result.length+" Entries"+'</p>';
    resultContainer.innerHTML += itemHTML
}





</script>
<aui:form>
<div id="resultContaineritems"></div>
<div id="resultContainerf"></div>
  <div id="resultContainer"></div>
  <input type="hidden" id="iterationIndex" value="4" />
  <aui:button type="button" value="Load More" onClick="iterateItems();" />
</aui:form>