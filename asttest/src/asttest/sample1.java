package asttest;

public class sample1 {

	private String text1 = "Hello World!", text2;  
    
    public void pr(String value) {
    	int i=3;
    	value=value+2;
    	if(i==4){
    		System.out.println("1231");
    	}
    	else{
    		System.out.println("123321");
    	}
    	value=value+i;
        System.out.println(value);  
    }  
      
    public void input(String value) {  
        text2 = value;  
    }  
	
}
