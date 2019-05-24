package org.hp.qc.web.restapi.docexamples.docexamples;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.hp.qc.web.restapi.docexamples.docexamples.infrastructure.Assert;
import org.hp.qc.web.restapi.docexamples.docexamples.infrastructure.Constants;
import org.hp.qc.web.restapi.docexamples.docexamples.infrastructure.Response;
import org.hp.qc.web.restapi.docexamples.docexamples.infrastructure.RestConnector;
import org.xml.sax.SAXException;

public class GetDetails {
	public static void main(String[] args) throws Exception {
		new GetDetails().readExample(
				"http://" + Constants.HOST + ":" +
						Constants.PORT + "/qcbin",
						Constants.DOMAIN,
						Constants.PROJECT,
						Constants.USERNAME,
						Constants.PASSWORD);
	}
	
	  private static void toXmlDocument(String str, String fileName) throws ParserConfigurationException, SAXException, IOException{
	         java.io.FileWriter fw = new java.io.FileWriter("Responses/"+fileName+ ".xml");
	         fw.write(str);
	         fw.close();
	    }
	 
	public void readExample(final String serverUrl, final String domain,
			final String project, String username, String password)
					throws Exception {

		RestConnector con =
				RestConnector.getInstance().init(
						new HashMap<String, String>(),
						serverUrl,
						domain,
						project);

		// Use the login example code to login for this test.
		// Go over this code to learn how to authenticate/login/logout
		AuthenticateLoginLogoutExample login =
				new AuthenticateLoginLogoutExample();

		boolean loginState = login.login(username, password);
		Assert.assertTrue("login failed.", loginState);

		//After login set the session
		con.getQCSession();
		
		/********** GET RELEASES *****************/
		Map<String, String> requestHeaders = new HashMap<String, String>();
		requestHeaders.put("Content-Type", "application/xml");
		requestHeaders.put("Accept", "application/xml");

		String releasesUrl = serverUrl + "/rest/domains/" + Constants.DOMAIN + "/projects/" + Constants.PROJECT + "/releases" ;
		Response response = con.httpGet(releasesUrl, null,  requestHeaders);

		Exception failure = response.getFailure();
		if (failure != null) {
			throw failure;
		}
		String responseString = response.toString();
		
		System.out.println("---responseString--"+responseString);
		
		toXmlDocument(responseString, "releases");
		
		/********** GET RELEASES ATTACHMENT****************
		 * Ref : https://community.microfocus.com/t5/Quality-Center-ALM-User/Download-an-attachment-via-REST-API/td-p/1020538
		 * */
		Map<String, String> requestHeadersA = new HashMap<String, String>();
		//requestHeadersA.put("Content-Type", "application/octet-stream");
		requestHeadersA.put("Accept", "application/octet-stream");

		String releasesAttachUrl = serverUrl + "/rest/domains/" + Constants.DOMAIN + "/projects/" + Constants.PROJECT + "/releases/1002/attachments/Requirement_attachment.docx" ;
		Response responseA = con.httpGet(releasesAttachUrl, null,  requestHeadersA);

		Exception failureA = responseA.getFailure();
		if (failureA != null) {
			throw failureA;
		}
		String responseStringA = responseA.toString();
		
		//System.out.println("---responseStringA--"+responseStringA);
	
		//toXmlDocument(responseStringA, "releases-attachments");
		
		/********** GET Tests****************
		 * Ref : https://stackoverflow.com/questions/28244802/hp-alm-adding-test-cases-with-rest-api
		 * */
		Map<String, String> requestHeadersB = new HashMap<String, String>();
		requestHeadersA.put("Content-Type", "application/xml");
		requestHeadersB.put("Accept", "application/xml");

		String testsUrl = serverUrl + "/rest/domains/" + Constants.DOMAIN + "/projects/" + Constants.PROJECT + "/tests" ;
		Response responseB = con.httpGet(testsUrl, null,  requestHeadersB);

		Exception failureB = responseB.getFailure();
		if (failureB != null) {
			throw failureB;
		}
		String responseStringB = responseB.toString();
		
		System.out.println("---responseStringB--"+responseStringB);
	
		toXmlDocument(responseStringB, "tests");
		
		/********** GET Requirements *****************/
		Map<String, String> requestHeadersC = new HashMap<String, String>();
		requestHeadersC.put("Content-Type", "application/xml");
		requestHeadersC.put("Accept", "application/xml");

		String requirementsUrl = serverUrl + "/rest/domains/" + Constants.DOMAIN + "/projects/" + Constants.PROJECT + "/requirements" ;
		Response responseC = con.httpGet(requirementsUrl, null,  requestHeadersC);

		Exception failureC = responseC.getFailure();
		if (failureC != null) {
			throw failureC;
		}
		String responseStringC = responseC.toString();
		
		System.out.println("---responseString--"+responseStringC);
		
		toXmlDocument(responseStringC, "requirements");
		
		/********** GET TestRuns **********
		 * Ref : https://stackoverflow.com/questions/39660373/how-to-add-a-test-run-to-alm-using-rest-api
		 * *******/
		Map<String, String> requestHeadersD = new HashMap<String, String>();
		requestHeadersD.put("Content-Type", "application/xml");
		requestHeadersD.put("Accept", "application/xml");

		String testRunssUrl = serverUrl + "/rest/domains/" + Constants.DOMAIN + "/projects/" + Constants.PROJECT + "/runs" ;
		Response responseD = con.httpGet(testRunssUrl, null,  requestHeadersD);

		Exception failureD = responseD.getFailure();
		if (failureD != null) {
			throw failureD;
		}
		String responseStringD = responseD.toString();
		
		System.out.println("---responseString--"+responseStringD);
		
		toXmlDocument(responseStringD, "test-runs");
		
		
		/********** GET test-instances **********
		 * *******/
		Map<String, String> requestHeadersE = new HashMap<String, String>();
		requestHeadersE.put("Content-Type", "application/xml");
		requestHeadersE.put("Accept", "application/xml");

		String testInstancesUrl = serverUrl + "/rest/domains/" + Constants.DOMAIN + "/projects/" + Constants.PROJECT + "/test-instances" ;
		Response responseE = con.httpGet(testInstancesUrl, null,  requestHeadersE);

		Exception failureE = responseE.getFailure();
		if (failureE != null) {
			throw failureE;
		}
		String responseStringE = responseE.toString();
		
		System.out.println("---responseString--"+responseStringE);
		
		toXmlDocument(responseStringE, "test-instances");
		
		/********** GET test-sets **********
		 * Ref : 
		 * https://community.microfocus.com/t5/Quality-Center-ALM-User/Using-REST-API-to-retrieve-the-latest-test-run-for-some-test/td-p/957579
		 * Test lab individual row details
		 * *******/
		Map<String, String> requestHeadersF = new HashMap<String, String>();
		requestHeadersF.put("Content-Type", "application/xml");
		requestHeadersF.put("Accept", "application/xml");

		String testSetsUrl = serverUrl + "/rest/domains/" + Constants.DOMAIN + "/projects/" + Constants.PROJECT + "/test-sets" ;
		Response responseF = con.httpGet(testSetsUrl, null,  requestHeadersF);

		Exception failureF = responseF.getFailure();
		if (failureF != null) {
			throw failureF;
		}
		String responseStringF = responseF.toString();
		
		System.out.println("---responseString--"+responseStringF);
		
		toXmlDocument(responseStringF, "test-sets");
		
		/********** GET test-set-folders **********
		 * Ref : https://community.microfocus.com/t5/Quality-Center-ALM-User/Using-REST-API-to-retrieve-the-latest-test-run-for-some-test/td-p/957579
		 * Test Lab fodler details
		 * *******/
		Map<String, String> requestHeadersG = new HashMap<String, String>();
		requestHeadersG.put("Content-Type", "application/xml");
		requestHeadersG.put("Accept", "application/xml");

		String testSetFoldersUrl = serverUrl + "/rest/domains/" + Constants.DOMAIN + "/projects/" + Constants.PROJECT + "/test-set-folders" ;
		Response responseG = con.httpGet(testSetFoldersUrl, null,  requestHeadersG);

		Exception failureG = responseG.getFailure();
		if (failureG != null) {
			throw failureG;
		}
		String responseStringG = responseG.toString();
		
		System.out.println("---responseString--"+responseStringG);
		
		toXmlDocument(responseStringG, "test-sets-folders");
		
		/********** GET cycles **********
		 * *******/
		Map<String, String> requestHeadersH = new HashMap<String, String>();
		requestHeadersG.put("Content-Type", "application/xml");
		requestHeadersG.put("Accept", "application/xml");

		String cycleUrl = serverUrl + "/rest/domains/" + Constants.DOMAIN + "/projects/" + Constants.PROJECT + "/list-items" ;
		Response responseH = con.httpGet(cycleUrl, null,  requestHeadersG);

		Exception failureH = responseH.getFailure();
		if (failureH != null) {
			throw failureH;
		}
		String responseStringH = responseH.toString();
		
		System.out.println("---responseString--"+responseStringH);
		
		toXmlDocument(responseStringH, "cycles");
		
		login.logout();
}
}