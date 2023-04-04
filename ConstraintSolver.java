package main.java;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;




public class ConstraintSolver {
   

    private Domain dom;
    private List<Constraint> constraintSet;
    List<Domain> domains;
    List<Variable> variableSet;
    
    
  

    public ConstraintSolver(ConstraintSolver prob){
        this.dom = prob.dom;
        this.constraintSet = prob.constraintSet;
        this.variableSet = prob.variableSet;
        this.domains = prob.domains;
    }



    public Variable getVarWithSmallestDom(){
        int minDomLength =10;
        Variable smallestDom = null;
        for(Variable Var:variableSet){
            if(Var.d.vals.length<minDomLength&&Var.d.vals.length>1){
                
                minDomLength=Var.d.vals.length;
                smallestDom=Var;
            }
        }
        return smallestDom;
    }





    public ConstraintSolver() {
        this.variableSet = new ArrayList<Variable>();
        this.constraintSet = new ArrayList<Constraint>();
        this.domains = new ArrayList<Domain>();
    }



    public String toString() {
        //print variable
        for(int i = 0; i < variableSet.size(); i++){
            System.out.println(variableSet.get(i));
        }
        System.out.println();
        for(int i=0;i<constraintSet.size();i++){
            System.out.println(constraintSet.get(i));
        }
        
        return "";
    }

    



     private void parse(String fileName) {
        try {
            File inputFile = new File(fileName);
            Scanner scanner = new Scanner(inputFile);
           
            while (scanner.hasNextLine()) {
                String currentLine = scanner.nextLine();
                
                if(currentLine.startsWith("Domain-")) {
                    //this is our domain - i.e. a datastructure that contains values and can be updated, played with etc.
                    String s = currentLine.replace("Domain-","");
                    String[] array = s.split(","); 
                    int[] vals = new int[array.length];
                    for(int i = 0; i < array.length; i++) {
                        vals[i] = Integer.parseInt(array[i]);
                    }
                    dom = new Domain(vals);
                    this.domains.add(dom);

                } else if (currentLine.startsWith("Var-")) {
                    
                    String s = currentLine.replace("Var-","");
                    Variable var = new Variable(s, dom); 
                    variableSet.add(var);
                } else if (currentLine.startsWith("Cons-")) {
                    String s = currentLine.replace("Cons-","");
                    if(s.startsWith("diff")){
                        s = s.replace("diff","");
                        s = s.replace("(","");
                        s = s.replace(")", "");
                        String[] varNames = s.split(",");
                        
                        
                        Variable a = findVar(varNames[0]);
                        Variable b = findVar(varNames[1]);
                        Constraint cons = new ConstraintDifferenceVarVar(a,b);
                        constraintSet.add(cons);
                    }
                    else if(s.contains("+")){
                        
                            boolean abs = false;
                            if(s.contains("abs")){
                                abs = true;
                            }
                            s = s.replace("+","");
                            s = s.replace("abs(","");
                            s = s.replace(")","");
                            s = s.replace("=", "");
                            String [] varNames = s.split("\\s+");
                           
                            
                            Variable a = findVar(varNames[0]);
                            Variable b = findVar(varNames[1]);
                            Integer c = Integer.parseInt(varNames[2]);
                            Constraint cons = new ConstraintEqualityVarPlusCons(a,b,c,abs);
                            constraintSet.add(cons);

                        }else{


                            if(Character.isDigit(s.charAt(s.length()-1))){
                                s = s.replace("=", "");
                                String[] varNames = s.split("\\s+");
                               
                                
                                Variable a = findVar(varNames[0]);
                                Integer b = Integer.parseInt(varNames[1]);
                                Constraint cons = new ConstraintEqualityVarCons(a, b);
                                constraintSet.add(cons);
                            }else{
                                
                                String[] varNames = s.split(" = ");
                               
                                
                                Variable a = findVar(varNames[0]);
                                Variable b = findVar(varNames[1]);
                                Constraint cons = new ConstraintEqualityVarVar(a, b); 
                                constraintSet.add(cons);
                                
                                
                            }
                        }
                        }
                    }
                    
                

            

            scanner.close();
            
        } catch (FileNotFoundException e) {
            System.out.println("Error.");
            e.printStackTrace();
        }
    }

  


   

   
            
    
    

   
   
   
  

   
    

    public ArrayList<String> printAnswer(String fileName){
        
        parse(fileName);
        List<Variable>returnList=new ArrayList<Variable>();
        solveProblem(returnList);
        ArrayList<String>finalAnswer=new ArrayList<>();
        for(Variable var:returnList){
            String string="Sol-";
            string+=var.name+"-";
            string+=var.d.vals[0];
            finalAnswer.add(string);
        }
        
        return finalAnswer;

    }

    

 

    public static void main(String[] args) {
        ConstraintSolver problem = new ConstraintSolver();
       ArrayList<String>list= problem.printAnswer("data.txt");
       System.out.println(list);
      
       
        
           

            }




            public Variable findVar(String string){
                Variable returnVar=null;
                for(Variable var:variableSet){
                    if(var.name.equals(string)){
                        returnVar=var;
                    }
                }
                return returnVar;
            }



            public void reduce() {
                for(int i = 0; i < constraintSet.size(); i++)
                    constraintSet.get(i).reduce();
            }


            public boolean solveProblem(List<Variable>List){
                reduce();
                reduce();
                reduce();
                if(emptyDomain()){
                     return false;
                }
               if(isSolved()){
                   for(Variable var:variableSet){
                        List.add(var);
                    }
                    return true;
        
                }
                ConstraintSolver subprobelm1 = copyProblem();
               subprobelm1.splitIntoTwo(0);

               ConstraintSolver subproblem2 = copyProblem();
               subproblem2.splitIntoTwo(1);
        
               if(subprobelm1.solveProblem(List)){
                return true;
               }
               if(subproblem2.solveProblem(List)){
                return true;
               }
        
        
               return false;
        
            }


            public boolean emptyDomain(){
                for(Variable var : this.variableSet){
                    if(var.d.vals.length == 0){
                        return true;
                    }
                }
                return false;
            }


            public boolean isSolved(){
                for(Variable var : this.variableSet){
                    if(var.d.vals.length != 1){
                        return false;
                    }
                }
                
                return true;
            }
        



            public void splitIntoTwo(int idx){
                Variable smallestVar=getVarWithSmallestDom();
                Domain smallestDomain = smallestVar.d;
        
                
                
                Domain[] smallestDomSplitted = smallestDomain.split();
                 smallestDomain.vals=(smallestDomSplitted[idx].vals);
        
        
            }





            public ConstraintSolver copyProblem(){
                ConstraintSolver copy = new ConstraintSolver();
        
                for(Variable var : variableSet){
                    Domain domain = new Domain(var.d.vals);
                    Variable variable = new Variable(var.name,domain);
                    copy.variableSet.add(variable);
                }
             
                
        
        
                for (Constraint constraint : constraintSet) {
                    Constraint constraintCopied = null;
                    if (constraint instanceof ConstraintEqualityVarPlusCons) {
                        ConstraintEqualityVarPlusCons eqVarPlusCons = (ConstraintEqualityVarPlusCons) constraint;
                       
                        Variable v1 = copy.findVar(eqVarPlusCons.v1.name);
                       
                        Variable v2 = copy.findVar(eqVarPlusCons.v2.name);
        
                        constraintCopied = new ConstraintEqualityVarPlusCons(v1, v2, eqVarPlusCons.constant, eqVarPlusCons.abs);


                    } else if (constraint instanceof ConstraintEqualityVarCons) {
                        ConstraintEqualityVarCons eqVarCons = (ConstraintEqualityVarCons) constraint;
                        
                        Variable v1 = copy.findVar(eqVarCons.v1.name);
                        constraintCopied = new ConstraintEqualityVarCons(v1, eqVarCons.number);


                    } else if (constraint instanceof ConstraintDifferenceVarVar) {
                        ConstraintDifferenceVarVar diffVarVar = (ConstraintDifferenceVarVar) constraint;
                       
                        Variable v1 = copy.findVar(diffVarVar.v1.name);
                      
                        Variable v2 = copy.findVar(diffVarVar.v2.name);

                        constraintCopied = new ConstraintDifferenceVarVar(v1, v2);
                    }
                    
                    
                    else if   (constraint instanceof ConstraintEqualityVarVar) {
                          ConstraintEqualityVarVar eqVarVar = (ConstraintEqualityVarVar) constraint;
                        
                        Variable v1 = copy.findVar(eqVarVar.v1.name);
                       
                        Variable v2 = copy.findVar(eqVarVar.v2.name);

                        constraintCopied = new ConstraintEqualityVarVar(v1, v2);
                    }

                    copy.constraintSet.add(constraintCopied);
                }
        
                return copy;
            }
           
           

           
    
            






        
        


        
        

        

        

        
    }



  


  
   
        
        
        



      

    

    


   
