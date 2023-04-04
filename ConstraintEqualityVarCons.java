package main.java;

public class ConstraintEqualityVarCons extends Constraint {
    Variable v1;
    int number;
   
    public ConstraintEqualityVarCons(Variable v1,int number){
        this.v1=v1;
        this.number=number;
    }

    public String toString(){
        System.out.println(v1.name+" = "+number);
        return "";

        
    }
        protected boolean isSatisfied(){
            int[]array=v1.d.vals;
            
            for(int i=0;i<array.length;i++){
                if(array[i]==number){
                    return true;
                }
            }
            return false;

        }

        protected void reduce(){
            int[]newDomain=new int[1];
            newDomain[0]=number;
            v1.d.vals=newDomain;
        }


       
    }



