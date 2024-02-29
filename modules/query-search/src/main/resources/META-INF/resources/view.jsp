<%@ include file="/init.jsp" %>

<portlet:actionURL name="searchResult" var="searchResultURL"/>

<aui:form action="<%=searchResultURL %>" name="employeeForm" method="GET" >


 <aui:input name="searchkeyword" >
         <aui:validator name="required"/>
         <aui:validator name="search"/>
    </aui:input>
    
    <button class="btn btn-light btn-unstyled"  type="submit" value="submit">
							<liferay-ui:icon
								icon="search"
								markupView="lexicon"
							/>



</aui:form>