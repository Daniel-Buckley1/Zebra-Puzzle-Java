package main.java;

public class ConstraintEqualityVarPlusCons extends Constraint {
    Variable v1, v2;
    int constant;
    Boolean abs;
    public ConstraintEqualityVarPlusCons(Variable v1, Variable v2, int cons, Boolean abs){
        this.v1 = v1;
        this.v2 = v2;
        this.constant = cons;
        this.abs = abs;
    }
    public String toString() {
        System.out.println(v1);
        System.out.println(v2);
        return "";
    }
    protected boolean isSatisfied() {
        for(int val1:v1.d.vals){
            for(int val2:v2.d.vals){
                if(val1 == val2 + constant){
                    return true;
                }
            }
        }
        return false;
        
    }

   
    
    protected void reduce() {

        //from d1
        for(int i = 0; i < v1.d.vals.length; i++) {
            Boolean found = false;


            if(abs==false) {
                for(int j = 0; j < v2.d.vals.length; j++) { 
                    if(v1.d.vals[i] == v2.d.vals[j] +constant)
                        found = true;
                }
                if (found==false) {
                    v1.d.deleteAtIdx(i); 
                }
            } else {
                for(int j = 0; j < v2.d.vals.length; j++) { 
                    if(Math.abs(v1.d.vals[i] - v2.d.vals[j]) ==constant)
                        found = true;
                }
                if (found==false) {
                    v1.d.deleteAtIdx(i); 
                }
            }
        }
    
        //from d2
        for(int i = 0; i < v2.d.vals.length; i++) {
            Boolean found = false;
            if(abs==false) {
                for(int j = 0; j < v1.d.vals.length; j++) { 
                    if(v2.d.vals[i] == v1.d.vals[j] -constant)
                        found= true;
                }
                if (found==false) {
                    v2.d.deleteAtIdx(i);
                }
            } else {
                for(int j = 0; j < v1.d.vals.length; j++) { 
                    if(Math.abs(v1.d.vals[j] - v2.d.vals[i]) ==constant)
                        found=true;
                }
                if (found==false) {
                    v2.d.deleteAtIdx(i);
                }
            }
        }
    }
    
     

   
}

