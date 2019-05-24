package org.hp.qc.web.restapi.docexamples.docexamples;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.hp.qc.web.restapi.docexamples.docexamples.infrastructure.Assert;
import org.hp.qc.web.restapi.docexamples.docexamples.infrastructure.Constants;
import org.hp.qc.web.restapi.docexamples.docexamples.infrastructure.Entities;
import org.hp.qc.web.restapi.docexamples.docexamples.infrastructure.EntityMarshallingUtils;
import org.hp.qc.web.restapi.docexamples.docexamples.infrastructure.Response;
import org.hp.qc.web.restapi.docexamples.docexamples.infrastructure.RestConnector;
import org.xml.sax.SAXException;

import com.mongodb.DB;
import com.mongodb.MongoClient;

public class GetDefects {
	public static void main(String[] args) throws Exception {
		new GetDefects().readExample(
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
		
		Map<String, String> requestHeaders = new HashMap<String, String>();
		requestHeaders.put("Content-Type", "application/xml");
		requestHeaders.put("Accept", "application/xml");

		String defectsUrl = serverUrl + "/rest/domains/" + Constants.DOMAIN + "/projects/" + Constants.PROJECT + "/defects" ;
		Response response = con.httpGet(defectsUrl, null,  requestHeaders);
	//	Response response = con.httpGet("http://ctsintbmvhpalm:8080/qcbin/rest/domains/DEFAULT/projects/Training/customization/entities/defect/fields", null,  requestHeaders);
		

		Exception failure = response.getFailure();
		if (failure != null) {
			throw failure;
		}
		String responseString = response.toString();
		
		toXmlDocument(responseString, "defects");
		
		//System.out.println("---responseString--"+responseString);
		Entities entities =
				EntityMarshallingUtils.marshal(Entities.class, responseString);
		//System.out.println("---getTotalResults--"+entities.getTotalResults());
		//System.out.println("---entity-size-"+entities.getEntity().size());
		
		ArrayList<HashMap<String, List<String>>> list = new ArrayList<HashMap<String, List<String>>>();
		
		List<org.hp.qc.web.restapi.docexamples.docexamples.infrastructure.Entities.Entity> entityList = entities.getEntity();
		for (org.hp.qc.web.restapi.docexamples.docexamples.infrastructure.Entities.Entity entity : entityList) {
			//System.out.println("-------type--------------"+entity.getType());
			//System.out.println("-------.getFields().getField().size()--------------"+entity.getFields().getField().size());
			if(entity.getType().equalsIgnoreCase("defect")) {
				List<org.hp.qc.web.restapi.docexamples.docexamples.infrastructure.Entities.Entity.Fields.Field>  fields = entity.getFields().getField();
				HashMap<String, List<String>> fieldDetails = new HashMap<String, List<String>>();
				for(org.hp.qc.web.restapi.docexamples.docexamples.infrastructure.Entities.Entity.Fields.Field field : fields) {
					fieldDetails.put(field.getName(), field.getValue());
				}
				list.add(fieldDetails);
			}
			
		}
		System.out.println("--------------list------------"+list.size());
		
	//	MongoClient mongoClient = new MongoClient("localhost", 27017); 
	//	DB database = mongoClient.getDB("myMongoDb");
	//	mongoClient.getDatabaseNames().forEach(System.out::println);
	//	Mongo mongo = new Mongo("localhost", 27017);
		
		
		//now show that you can do something with that object
	/*	List<Field> fields = entity.getFields().getField();
		
	System.out.println("---entity.getType()--"+entity.getType());*/
	//System.out.println("---fields.size()--"+fields.size());
	//System.out.println("---fields.indexOf(1)--"+fields.indexOf(1));
		
		/*Connection connection = null;
        try
        {
          // create a database connection
          connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
          Statement statement = connection.createStatement();
          statement.setQueryTimeout(30);  // set timeout to 30 sec.

          statement.executeUpdate("drop table if exists person");
          statement.executeUpdate("create table person (id integer, name string)");
          statement.executeUpdate("insert into person values(1, 'leo')");
          statement.executeUpdate("insert into person values(2, 'yui')");
          ResultSet rs = statement.executeQuery("select * from person");
          while(rs.next())
          {
            // read the result set
            System.out.println("name = " + rs.getString("name"));
            System.out.println("id = " + rs.getInt("id"));
          }
        }
        catch(SQLException e)
        {
          // if the error message is "out of memory",
          // it probably means no database file is found
          System.err.println(e.getMessage());
        }
        finally
        {
          try
          {
            if(connection != null)
              connection.close();
          }
          catch(SQLException e)
          {
            // connection close failed.
            System.err.println(e.getMessage());
          }
        }*/
	
		login.logout();
}
}