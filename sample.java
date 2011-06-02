// Sample of code using peekyou_api
package peekyou_api;


public class sample {
    public static void main(String[] args) {
        
        peekyou_api test=new peekyou_api();
        test.set_key("YOUR API KEY GOES HERE");
       //second parameter for get_url can be xml,json
       System.out.print(test.get_url("http://twitter.com/michaelhussey","xml"));
       

    }
}
