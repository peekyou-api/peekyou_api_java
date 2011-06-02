package peekyou_api;
import java.net.*;
import java.util.*;
import java.util.regex.*;



public class peekyou_api {

	private String api_key;
	private int frequency=5;//default frequency in seconds

	/**
	 * Sets the private key.
	 *
	 * @param string key
	 * @return none
	 * 									
	 */
	public void set_key(String key){
		this.api_key=key;
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
	 * Gets url information from peekyou_api(For example http://www.peekyou.com/[username]
	 * 
	 * @param string url
	 * @param string type (json,xml)
	 * @return string representing json,or xml
	 * 									
	 */
	public String get_url(String url,String type){
		type=type.toLowerCase().trim();
		
		if(type!="json" && type!="xml")
			return "Invalid type!!\n";
	
		url="http://api.peekyou.com/analytics.php?key="+this.api_key+"&url="+url+"&output="+type+"";	
        String result;
        
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
	 * For testing purposes used to print out current key
	 * 
	 * 
	 * @return api_key
	 * 									
	 */
	public String echo_key(){

		return this.api_key;
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
