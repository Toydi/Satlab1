package asttest;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

public class MyASTVisitor extends ASTVisitor{
	
	List<TypeDeclaration> typeNodeList=new ArrayList<>();
	List<MethodDeclaration> methodNodeList = new ArrayList<>();
	List<FieldDeclaration> fieldNodeList=new ArrayList<>();
	

	@Override  
    public boolean visit(FieldDeclaration node) {   
          
		fieldNodeList.add(node);
        return true;  
    }  
  
    @Override  
    public boolean visit(MethodDeclaration node) {  
        methodNodeList.add(node);
        return true;  
    }  
  
    @Override  
    public boolean visit(TypeDeclaration node) {  
        typeNodeList.add(node);
        return true;	
    }
    
    public List<TypeDeclaration> getTypeNodeList(){
    	return this.typeNodeList;
    }
    public List<MethodDeclaration> getMethodNodeList(){
    	return this.methodNodeList;
    }
    public List<FieldDeclaration> getFieldNodeList(){
    	return this.fieldNodeList;
    }
}
