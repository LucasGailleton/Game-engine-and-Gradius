package engine.gal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import JavaProject.src.gal.ast.AST;
import JavaProject.src.gal.ast.Actions;
import JavaProject.src.gal.ast.Automaton;
import JavaProject.src.gal.ast.Behaviour;
import JavaProject.src.gal.ast.BinaryOp;
import JavaProject.src.gal.ast.Category;
import JavaProject.src.gal.ast.Condition;
import JavaProject.src.gal.ast.Direction;
import JavaProject.src.gal.ast.Expression;
import JavaProject.src.gal.ast.FunCall;
import JavaProject.src.gal.ast.IntDegree;
import JavaProject.src.gal.ast.IntPercent;
import JavaProject.src.gal.ast.IntValue;
import JavaProject.src.gal.ast.Key;
import JavaProject.src.gal.ast.Mode;
import JavaProject.src.gal.ast.State;
import JavaProject.src.gal.ast.Transition;
import JavaProject.src.gal.ast.UnaryOp;
import JavaProject.src.gal.ast.Underscore;
import JavaProject.src.gal.ast.Variable;
import JavaProject.src.gal.ast.iVisitor;

public class FSMbuilder implements iVisitor{

	private engine.gal.Automaton currentAutomaton;
	
	// REQUIRED BY INTERFACE IVisitor

	public Object visit(Category cat) {
		return cat;
	}

	public Object visit(Direction dir) {
		return dir;
	}

	public Object visit(Key key) {
		return key;
	}

	public Object visit(IntPercent per) {
		return per;
	}

	public Object visit(IntDegree deg) {
		return deg;
	}

	public Object visit(IntValue v) {
		return v;
	}

	public Object visit(Underscore u) {
		return u;
	}

	@Override
	public Object visit(Variable v) {
		return v;
	}

	// FUNCALL

	public void enter(FunCall funcall) {
	}

	public void visit(FunCall funcall) {
	}

	public void exit(FunCall funcall) {
	}

	public Object build(FunCall funcall, List<Object> params) {
		return funcall;
	}

	// BINOP

	public void enter(BinaryOp operator) {
	}

	public void visit(BinaryOp operator) {
	}

	public void exit(BinaryOp operator) {
	}

	public Object build(BinaryOp operator, Object left, Object right) {
		return operator;
	}

	// UNOP

	public void enter(UnaryOp unop) {
	}

	public void exit(UnaryOp unop) {
	}

	public Object build(UnaryOp operator, Object exp) {
		return operator;
	}

	// STATE

	public Object visit(State state) {
		return state;
	}

	// MODE

	public void enter(Mode mode) {
		//this.current_source_state = state_id(mode.state);
	}

	public void visit(Mode mode) {
	}

	public void exit(Mode mode) {
	}

	public Object build(Mode mode, Object source_state, Object behaviour) {
		return mode;
	}

	// CONDITION

	public void enter(Condition condition) {
	}

	public void exit(Condition condition) {
	}

	public Object build(Condition condition, Object exp) {
		return condition;
	}

	// ACTION

	public void enter(Actions action) {
	}

	public void visit(Actions action) {
	}

	public void exit(Actions action) {
	}

	public Object build(Actions action, String operator, List<Object> funcalls) {
		return action;
	}

	// TRANSITION

	public void enter(Transition transition) {
	}

	public void exit(Transition transition) {
	}

	public Object build(Transition transition, Object condition, Object action, Object target) {
		// Vos méthodes recevront ici les objets réels construits par les sous-nœuds !
	    Condition condObj = (Condition) condition; 
	    Actions  actObj = (Actions) action;
	    State targetState = (State) target;

	    // Instanciation de votre modèle d'exécution d'automate
	    Transition myTrans = new Transition(condObj, actObj, targetState);
	    
	    return myTrans; // On propage l'objet vers le haut (le Behaviour)
	}

	// BEHAVIOUR

	public Object visit(Behaviour behaviour, List<Object> transitions) {
		return behaviour;
	}

	// AUTOMATON

	public void enter(Automaton automaton) {
		//this.state_map.clear();
		//Dot.start_subgraph(this.ps, automaton.name);
		//automaton_node(automaton);
	}

	public void exit(Automaton automaton) {
	}

	public Object build(Automaton automaton, Object initial_state, List<Object> modes) {
        engine.gal.State initial = new engine.gal.State(initial_state.toString(),0);
        
        // Création de VOTRE instance d'Automaton avec son constructeur : Automaton(String name, State initial)
        this.currentAutomaton = new engine.gal.Automaton(automaton.name, initial);
        
        for(Mode m:(List<Mode>) (Object) modes) {
            for(Transition t:m.behaviour.transitions) {
            	HashMap var_map=new HashMap<String,Variable>();    //tableau des variables de la transition
            	engine.gal.State targ=new engine.gal.State(t.target.toString(),0); 
            	engine.gal.State source=new engine.gal.State(m.state.toString(),0);
            	////////////////////////////
            	// On crée une liste pour stocker toutes les actions de la transition
                List<engine.gal.GALAction> executionActions = new ArrayList<>();
                
                // t.action est un gal.ast.Actions, on boucle sur sa LinkedList<FunCall>
                if (t.action != null && t.action.actions != null) {
                    for (FunCall call : t.action.actions) {
                    	executionActions.add(transformfuncall(call));
                    }
                }
            	//engine.gal.GALAction act=new engine.gal.Move(t.action.);
                engine.gal.GALAction act=executionActions.get(0);
            	//engine.gal.GALCondition cond=new engine.gal.GALCondition(t.action);
                True tru=engine.gal.GALCondition.TRUE;
            	engine.gal.Transition tran=new engine.gal.Transition(source,(iGALCondition)tru,act,targ);
            	this.currentAutomaton.add(tran);
            }
        }
        // Les transitions ont été ajoutées au fur et à mesure lors du parcours des 'Mode' en Bottom-Up.
        // On retourne l'automate complet et prêt à l'emploi.
        return this.currentAutomaton;
        //return null;
	}
	
	//transform
	
	public engine.gal.GALAction transformfuncall(FunCall f){
		int paramSize = f.parameters.size();
        String actionName = f.name;
		switch(paramSize) {
		case 1:
			Object param = f.parameters.get(0);
			engine.gal.Direction d;
    		if(param instanceof IntValue) {
    			d=engine.gal.Direction.toDir(((IntValue)param).value);
    		}
    		else if(param instanceof Direction) {
    			d=engine.gal.Direction.canonical(((Direction)f.parameters.get(0)).toString());
    		}
    		else if(param instanceof Variable) {
    			d=engine.gal.Direction.canonical("E");	
    		}
    		else {
    			throw new IllegalArgumentException("Type de paramètre inconnue");
    		}
	    	switch(actionName) {
	        // Selon le nom de l'action (Move, Hit, Push...), on instancie le bon type
	    	case"Hit":
	    		return new engine.gal.Move(d);
	    	case"Push":
	    		return new engine.gal.Move(d);
	    	case"Move":
	    		return new engine.gal.Move(d);
	    	default:
	            // Par défaut ou pour les autres actions
	            //executionActions.add(new engine.gal.GALAction(call.name));
	        	return null;
	    	}
		case 2:
	    	switch(f.name) {
	        // Selon le nom de l'action (Move, Hit, Push...), on instancie le bon type
	    	case"Hit":
	    		engine.gal.Direction dt=engine.gal.Direction.toDir(((IntValue)f.parameters.getFirst()).value);
	    		double j=(double)((IntValue)(f.parameters.get(1))).value;
	    		return new engine.gal.Move(dt,j);
	    	case"Push":
	    		engine.gal.Direction d2=engine.gal.Direction.canonical(((Direction)(f.parameters.getFirst())).toString());
	    		double j3=(double)((IntValue)(f.parameters.get(1))).value;
	    		return new engine.gal.Move(d2,j3);
	    	case"Move":
	    		engine.gal.Direction d3=engine.gal.Direction.toDir(((IntValue)f.parameters.get(1)).value);
	    		double j2=(double)((IntValue)(f.parameters.get(2))).value;
	    		return new engine.gal.Move(d3,j2);
	    	default:
	            // Par défaut ou pour les autres actions
	            //executionActions.add(new engine.gal.GALAction(call.name));
	        	return null;
	    	}
		case 3:
	    	switch(f.name) {
	        // Selon le nom de l'action (Move, Hit, Push...), on instancie le bon type
	    	case"Hit":
	    		engine.gal.Direction dt=engine.gal.Direction.toDir(((IntValue)f.parameters.getFirst()).value);
	    		double j=(double)((IntValue)(f.parameters.get(1))).value;
	    		return new engine.gal.Move(dt,j);
	    	case"Push":
	    		engine.gal.Direction d2=engine.gal.Direction.canonical(((Direction)(f.parameters.getFirst())).toString());
	    		return new engine.gal.Move(d2,0);
	    	case"Move":
	    		engine.gal.Direction d3=engine.gal.Direction.toDir(((IntValue)f.parameters.get(1)).value);
	    		double j2=(double)((IntValue)(f.parameters.get(2))).value;
	    		return new engine.gal.Move(d3,j2);
	    	default:
	            // Par défaut ou pour les autres actions
	            //executionActions.add(new engine.gal.GALAction(call.name));
	        	return null;
	    	}
	    default:
	    	return null;
		}
	}

	
	public engine.gal.iGALCondition transformcond(Expression f){
		if(f instanceof BinaryOp bo) {
			String op=bo.operator;
			if(op.equals("&")) {
				engine.gal.Conjunction con=new engine.gal.Conjunction();
				con.add(transformcond(bo.right_operand));
				con.add(transformcond(bo.right_operand));
				return con;
			}
			if(op.equals("|")) {
				engine.gal.Disjunction dis=new engine.gal.Disjunction();
				dis.add(transformcond(bo.right_operand));
				dis.add(transformcond(bo.right_operand));
				return dis;
			}
		}
		
		if(f instanceof UnaryOp uo) {
			
		}
		else {
			
		}
		return null;
		
	}
	// AST

	public void enter(AST ast) {
	}

	public void exit(AST ast) {
	}

	public Object build(AST ast, List<Object> automata) {
		return automata;
	}
	
}
