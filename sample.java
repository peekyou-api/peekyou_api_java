// Sample of code using peekyou_api
package peekyou;


public class sample{
	public static void main(String args[]){
        
        peekyou_api test=new peekyou_api();
        test.set_key("YOUR API KEY GOES HERE");
        test.set_app_id("YOUR APP ID GOES HERE");
        
        //second parameter for get_url can be xml,json
        System.out.print(test.get_social_audience_info("http://twitter.com/michaelhussey","json"));
        System.out.print(test.get_social_consumer_info("http://twitter.com/michaelhussey","json"));

    }
}