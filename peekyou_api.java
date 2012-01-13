package peekyou;
import java.net.*;
import java.util.*;
import java.util.regex.*;

public class peekyou_api {

	private String api_key;
	private String app_id;
	private float api_version=3;//set default api version to use
	private int frequency=5;//default frequency in seconds

	/**
	 * Sets the api key.
	 *
	 * @param string key
	 * @return none
	 * 									
	 */
	public void set_key(String key){
		this.api_key=key;
	}
	
	/**
	 * Sets the app id.
	 *
	 * @param string key
	 * @return none
	 * 									
	 */
	public void set_app_id(String id){
		this.app_id=id;
	}

	/**
	 * Sets the frequency,
	 * Controls the rate a user would like to check peekyou api for status
	 * @param int value
	 * @return none
	 * 									
	 */
	public void set_frequency(int value){
		this.frequency=value;
	}

	/**
	  * Gets url information from peekyou social audience api for a given url(For example http://twitter/[username]
	 * 
	 * @param string url
	 * @param string type (json,xml)
	 * @return string representing json,or xml
	 * 									
	 */
	public String get_social_audience_info(String url,String type){
		type=type.toLowerCase().trim();
		String result;
		
		if(type!="json" && type!="xml")
			return "Invalid type!!\n";
	
		url="http://api.peekyou.com/analytics.php?key="+this.api_key+"&url="+url+"&output="+type+"&app_id="+this.app_id;	
        
		while((result=check_status(url,type))=="loading"){
			
			try {
				Thread.sleep(this.frequency);
			} catch (InterruptedException e) {
				System.out.println("Error trying to sleep!");
			}
		}
		
		return result;
	}
	
	/**
	  * Gets url information from peekyou social consumer api for a given url(For example http://twitter/[username]
	 * 
	 * @param string url
	 * @param string type (json,xml)
	 * @return string representing json,or xml
	 * 									
	 */
	public String get_social_consumer_info(String url,String type){
		type=type.toLowerCase().trim();
		String result;
		
		if(type!="json" && type!="xml")
			return "Invalid type!!\n";
	
		url="http://api.peekyou.com/api.php?key="+this.api_key+"&url="+url+"&apiv="+this.api_version+"&output="+type+"&app_id="+this.app_id;	
        
		while((result=check_status(url,type))=="loading"){
			
			try {
				Thread.sleep(this.frequency);
			} catch (InterruptedException e) {
				System.out.println("Error trying to sleep!");
			}
		}
		
		return result;
	}

	
	/**
	 * Checks the status return by peekyou api.
	 * 
	 * @param string url
	 * @param string type
	 * @return If status is 1 then -1 is return implying search is still active on peekyou,otherwise any other status results is returned.
	 * 									
	 */
	private String check_status(String url,String type){

		String result=get_html(url);
		Pattern pattern;
		Matcher matcher;
		String status="0";
		
		if(type=="json"){
			/*you can replace this part with your own json decoder object to get value
			of status rather than using regexp match*/
			pattern=Pattern.compile("\"status\":\"(.*?)\",");
			matcher = pattern.matcher(result);
			if(matcher.find())
			status=matcher.group(1);
			}
		else{
			/*you can replace this part with your own xml parser to get value
			of status rather than using regexp match*/
			pattern=Pattern.compile("<status>(.*?)</status>");
			matcher = pattern.matcher(result);
			if(matcher.find())
				status=matcher.group(1);
			}
		
		if(status.trim().equals(""))
			return result;
		if(result.trim().equals(""))
			return "API down. Try again later\n";
		else if(Integer.parseInt(status)==1)
		return "loading";
		else 
			return result;

	}


	/**
	 * Returns html from url link
	 * 
	 * @param string url
	 * @return string containing html from url location.
	 * 									
	 */
	private String get_html(String url){
		String html="";
		try
		{
		URL results = new URL(url);
        URLConnection res = results.openConnection();;
        res.connect();
        Scanner in = new Scanner(res.getInputStream());
        while(in.hasNextLine())
        html=html+in.nextLine();
		}
        catch(Exception e)
        {
        System.out.println(e);
        }
        
        return html;

	}


}