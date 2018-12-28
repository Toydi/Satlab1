package asttest;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

public class ASTUtil {

	
	public static CompilationUnit getCompilationUnit(String javaFilePath){  
        byte[] input = null;  
        try {  
            BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(javaFilePath));  
            input = new byte[bufferedInputStream.available()];  
                bufferedInputStream.read(input);  
                bufferedInputStream.close();  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
  
          
    ASTParser astParser = ASTParser.newParser(AST.JLS8);  
        astParser.setSource(new String(input).toCharArray());  
        astParser.setKind(ASTParser.K_COMPILATION_UNIT);  
  
        CompilationUnit result = (CompilationUnit) (astParser.createAST(null));  
        return result;  
    }  
	
	 public static String stringMax(String ss1, String ss2) {  
	        String sameString = null; 
	        String s1=ss1,s2=ss2;
	        if (s1.length() < s2.length()) {  
	            String temp = s1;  
	            s1 = s2;  
	            s2 = temp;  
	        }   
	  
	        if ( s1.contains(s2)) {  
	            return s2;  
	        } else {  
	            boolean b1 = false;  
	            for (int i = 0; i < s2.length(); i++) {  
	                for (int j = 0; j <= i; j++) {  
	                    String str = s2.substring(j, s2.length() - i + j);  
	                    //System.out.println("第" + i + "次比较：" + str);  
	                    if (s1.contains(str)) {  
	                        sameString = str;  
	                        b1 = true;  
	                        break;  
	                    }  
	  
	                }  
	                if (s2.substring(0, s2.length() - i).length() == 2) {  
	                   // System.out.println("没有相同的子字符串");  
	                    b1 = true;  
	                    break;  
	  
	                }  
	                if (b1 == true)  
	                    break;  
	            }  
	        }  
	        return sameString;  
	    }  
	
	public static double compareType(List<TypeDeclaration> type1,List<TypeDeclaration> type2){
		double result=0;
		double count=0;
		for(int i=0;i<type1.size();i++){
			String longString="";
			String name1=type1.get(i).getName().toString();
			String name2="";
			for(int j=0;j<type2.size();j++){
				String tempname2=type2.get(j).getName().toString();
				String sub=stringMax(name1, tempname2);
				if(sub==null) continue;
				else{
					if(longString.length()<sub.length()){
						longString=sub;
						name2=tempname2;
					}
				}
			}
			count++;
			result+=(2*(double)longString.length()/((double)name1.length()+(double)name2.length()));
		}
//		System.out.println("type1 num+++++++++++++++"+type1.size());
//		System.out.println("type2 num+++++++++++++++"+type2.size());
//		System.out.println("typeDeclaration result+++++++++++"+result+count);
		return result/count;
	}
	public static double compareField(List<FieldDeclaration> field1,List<FieldDeclaration> field2){
		double result=0;
		double count=0;
		FieldDeclaration f1=field1.get(0);
		FieldDeclaration f2=field2.get(0);
		List f1List=f1.fragments();
		List f2List=f2.fragments();
		for(int i=0;i<f1List.size();i++){
			double temp=0.0;
			VariableDeclarationFragment v = (VariableDeclarationFragment)f1List.get(i);
			String longString = "";
			String initialString1="";
			String initialString2="";
			String name1=v.getName().toString();
			String name2="";
			Expression initial=v.getInitializer();
			for(int j=0;j<f2List.size();j++){
				VariableDeclarationFragment v2=(VariableDeclarationFragment)f2List.get(j);
				String tempname2=v2.getName().toString();
				Expression initial2=v2.getInitializer();
				String sub=stringMax(name1, tempname2);
				if(sub==null) continue;
				else{
					if(longString.length()<sub.length()){
						longString=sub;
						name2=tempname2;
						if(initial!=null&&initial2!=null){
							initialString1=initial.toString();
							initialString2=initial2.toString();
						}
					}
				}
			}
			if(initialString1!=""&&initialString2!=""){
				if(initialString1.equals(initialString2))
					temp+=1;
				count+=2;
			}
			else{
				count+=1;
			}
			
			temp+=(2*(double)longString.length()/((double)name1.length()+(double)name2.length()));
			result+=temp;
		}
//		System.out.println("fieldDeclaration result+++++++++++"+result+count);
		return result/count;
	}
	public static double compareMethod(List<MethodDeclaration> method1,List<MethodDeclaration> method2){
		double result=0;
		for(int i=0;i<method1.size();i++){
			double temp=0;
			String longString="";
			String name2="";
			MethodDeclaration temp2=null;
			MethodDeclaration m1=method1.get(i);
			String name1=m1.getName().toString();
			for(int j=0;j<method2.size();j++){
				MethodDeclaration m2=method2.get(j);
				String tempname2=m2.getName().toString();
//				System.out.println("**********************"+name1+tempname2);
				String sub=stringMax(name1, tempname2);
				if(sub==null) continue;
				else{
					if(longString.length()<sub.length()){
						longString=sub;
						name2=tempname2;
						temp2=m2;
					}
				}
			}
			if(longString==""){
				continue;
			}
			else{
				temp+=(2*(double)longString.length()/((double)name1.length()+(double)name2.length()));
				if(m1.getReturnType2().toString().equals((temp2.getReturnType2().toString()))){
					temp+=1;
				}
				if(isSame(m1.parameters(), temp2.parameters())) 
					temp++;
				if(isSame(m1.modifiers(), temp2.modifiers())) 
					temp++;
				temp=temp/4.0;
			
//				System.out.println(m1.getReturnType2());
//				System.out.println(m1.getReturnType2());
//				System.out.println(m1.parameters());
//				System.out.println(temp2.parameters());
//				System.out.println(m1.modifiers());
//				System.out.println(temp2.modifiers());
			}
			result+=temp;
		}
//		System.out.println("method1 num+++++++++++++++"+method1.size());
//		System.out.println("method2 num+++++++++++++++"+method2.size());
//		System.out.println("method decalarition +++++++"+result+"size"+method1.size());
		return result/(double)method1.size();
	}
	public static boolean isSame(List l1,List l2){
		boolean result=false;
		int i=0;
		List<String> s1=new ArrayList<String>();
		List<String> s2=new ArrayList<String>();
		for(i=0;i<l1.size();i++){
			s1.add(l1.get(i).toString());
		}
		for(i=0;i<l2.size();i++){
			s2.add(l2.get(i).toString());
		}
		for(i=0;i<s1.size();i++){
			if(s2.contains(s1.get(i))) 
				continue;
			else 
				break;
		}
		if(i==s1.size()) result=true;
		return result;
	}
	public static double getResult(String file1,String file2){
		
		double result=0;
		CompilationUnit re1=getCompilationUnit(file1);
		CompilationUnit re2=getCompilationUnit(file2);
		MyASTVisitor myASTVisitor1=new MyASTVisitor();
		re1.accept(myASTVisitor1);
		MyASTVisitor myASTVisitor2=new MyASTVisitor();
		re2.accept(myASTVisitor2);
		
		double result1=compareType(myASTVisitor1.getTypeNodeList(), myASTVisitor2.getTypeNodeList());
		double result2=compareField(myASTVisitor1.getFieldNodeList(),myASTVisitor2.getFieldNodeList());
		double result3=compareMethod(myASTVisitor1.getMethodNodeList(),myASTVisitor2.getMethodNodeList());
		result=result1+result2+result3;
		return result/3.0;
	}
	
	public static void main(String[] args){
		 
		 System.out.println("The similarity num is "+getResult("src/asttest/sample.java", "src/asttest/sample1.java"));

	}
	
}
