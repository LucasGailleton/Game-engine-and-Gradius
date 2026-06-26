package engine.GAL;
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
import game.entities.Ship;

public class FSMbuilder implements iVisitor{

	private engine.GAL.Automaton currentAutomaton;
	
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
		//Dot.start_subgraph(th            	is.ps, automaton.name);
		//automaton_node(automaton);
	}

	public void exit(Automaton automaton) {
	}

	public Object build(Automaton automaton, Object initial_state, List<Object> modes) {
        engine.GAL.State initial = new engine.GAL.State(initial_state.toString(),0);
        
        // Création de VOTRE instance d'Automaton avec son constructeur : Automaton(String name, State initial)
        this.currentAutomaton = new engine.GAL.Automaton(automaton.name, initial);
        
        for(Mode m:(List<Mode>) (Object) modes) {
            for(Transition t:m.behaviour.transitions) {
            	engine.GAL.iGALCondition cond=transformcond(t.condition.expression);
            	engine.GAL.Variable var=cond.variable();
            	//HashMap<Variable,Object> var_map=new HashMap<Variable,Object>();    //tableau des variables de la transition
            	engine.GAL.State targ=new engine.GAL.State(t.target.toString(),0); 
            	engine.GAL.State source=new engine.GAL.State(m.state.toString(),0);
            	////////////////////////////
            	// On crée une liste pour stocker toutes les actions de la transition
                List<engine.GAL.GALAction> executionActions = new ArrayList<>();
                
                // t.action est un GAL.ast.Actions, on boucle sur sa LinkedList<FunCall>
                if (t.action != null && t.action.actions != null) {
                    for (FunCall call : t.action.actions) {
                    	executionActions.add(transformfuncall(call,var));
                    }
                }
            	//engine.GAL.GALAction act=new engine.GAL.Move(t.action.);
                engine.GAL.GALAction act=executionActions.get(0);
            	
                //True tru=engine.GAL.GALCondition.TRUE;
            	engine.GAL.Transition tran=new engine.GAL.Transition(source,cond,act,targ);
            	this.currentAutomaton.add(tran);
            }
        }
        // Les transitions ont été ajoutées au fur et à mesure lors du parcours des 'Mode' en Bottom-Up.
        // On retourne l'automate complet et prêt à l'emploi.
        return this.currentAutomaton;
        //return null;
	}
	
	//transform
////////Action
	public engine.GAL.GALAction transformfuncall(FunCall f,engine.GAL.Variable var){
		int paramSize = f.parameters.size();
        String actionName = f.name;
		switch(paramSize) {
		case 0:
			switch(actionName) {
				case "Hit":
					return new engine.GAL.Shoot();
				default:
					return null;
			}
		case 1:
			Object param = f.parameters.get(0);
    		if(param instanceof IntDegree id) {
    	    	switch(actionName) {
    	        // Selon le nom de l'action (Move, Hit, Push...), on instancie le bon type
    	    	case"Hit":
    	    		return new engine.GAL.Shoot(id.value);
    	    	case"Move":
    	    		return new engine.GAL.Move(id.value);
    	    	case"Turn":
    	    		return new engine.GAL.Move(id.value);
    	    	default:
    	            // Par défaut ou pour les autres actions
    	            //executionActions.add(new engine.GAL.GALAction(call.name));
    	        	return null;
    	    	}
    		}
    		else if(param instanceof IntPercent id) {
    	    	switch(actionName) {
    	        // Selon le nom de l'action (Move, Hit, Push...), on instancie le bon type
    	    	case"Move":
    	    		return new engine.GAL.Move(id.value);
    	    	default:
    	            // Par défaut ou pour les autres actions
    	            //executionActions.add(new engine.GAL.GALAction(call.name));
    	        	return null;
    	    	}
    		}
    		else if(param instanceof Direction dir) {
    			engine.GAL.Direction d=engine.GAL.Direction.canonical(dir.toString());
    	    	switch(actionName) {
    	        // Selon le nom de l'action (Move, Hit, Push...), on instancie le bon type
    	    	case"Hit":
    	    		return new engine.GAL.Move(d);
    	    	case"Move":
    	    		return new engine.GAL.Move(d);
    	    	case"Turn":
    	    		return new engine.GAL.Move(d);
    			case "Egg":
    				return new engine.GAL.Egg();
    	    	default:
    	            // Par défaut ou pour les autres actions
    	            //executionActions.add(new engine.GAL.GALAction(call.name));
    	        	return null;
    	    	}
    		}
    		else if(param instanceof Variable v) {
    	    	switch(actionName) {
    	        // Selon le nom de l'action (Move, Hit, Push...), on instancie le bon type
    	    	case"Hit":
    	    		//return new engine.GAL.Move(var_map.get(v));
    	    		return new engine.GAL.Shoot(var);
    	    	case"Move":
    	    		//return new engine.GAL.Move(var_map.get(v));
    	    	default:
    	            // Par défaut ou pour les autres actions
    	            //executionActions.add(new engine.GAL.GALAction(call.name));
    	        	return null;
    	    	}
    		}
    		else if(param instanceof Category c) {
    			switch(c.terminal.content){
    			case "@":
	    				Ship s=Ship.ship();
	        	    	switch(actionName) {
	        	        // Selon le nom de l'action (Move, Hit, Push...), on instancie le bon type
	        	    	case"Hit":
	        	    		return new engine.GAL.Shoot(s);
	        	    	default:
	        	            // Par défaut ou pour les autres actions
	        	            //executionActions.add(new engine.GAL.GALAction(call.name));
	        	        	return null;
	        	    	}
    			case "#":
	        	    	switch(actionName) {
	        	        // Selon le nom de l'action (Move, Hit, Push...), on instancie le bon type
	        	    	case"Egg":
	        	    		return new engine.GAL.Egg(engine.GAL.Category.Boss);
	        	    	default:
	        	            // Par défaut ou pour les autres actions
	        	            //executionActions.add(new engine.GAL.GALAction(call.name));
	        	        	return null;
	        	    	}
    			case "M":
        	    	switch(actionName) {
        	        // Selon le nom de l'action (Move, Hit, Push...), on instancie le bon type
        	    	case"Egg":
        	    		return new engine.GAL.Egg(engine.GAL.Category.Missile);
        	    	default:
        	            // Par défaut ou pour les autres actions
        	            //executionActions.add(new engine.GAL.GALAction(call.name));
        	        	return null;
        	    	}
    			case "O":
        	    	switch(actionName) {
        	        // Selon le nom de l'action (Move, Hit, Push...), on instancie le bon type
        	    	case"Egg":
        	    		return new engine.GAL.Egg(engine.GAL.Category.Obstacle);
        	    	default:
        	            // Par défaut ou pour les autres actions
        	            //executionActions.add(new engine.GAL.GALAction(call.name));
        	        	return null;
        	    	}
    			case "A":
        	    	switch(actionName) {
        	        // Selon le nom de l'action (Move, Hit, Push...), on instancie le bon type
        	    	case"Egg":
        	    		return new engine.GAL.Egg(engine.GAL.Category.Adversary);
        	    	default:
        	            // Par défaut ou pour les autres actions
        	            //executionActions.add(new engine.GAL.GALAction(call.name));
        	        	return null;
        	    	}
	    			
	    			default:
	    				throw new IllegalArgumentException("Type de category inconnue");
    			}
    		}
    		else {
    			throw new IllegalArgumentException("Type de paramètre inconnue");
    		}
		case 2:
			Object param1 = f.parameters.get(0);
			Object param2 = f.parameters.get(1);
	    	switch(f.name) {
	        // Selon le nom de l'action (Move, Hit, Push...), on instancie le bon type
	    	case"Move":
	    		if(param1 instanceof Direction d && param2 instanceof Variable v) {
	    			
	    		}
	    		else if(param1 instanceof Direction d && param2 instanceof IntPercent p) {
	    			
	    		}
	    		else if(param1 instanceof Direction d && param2 instanceof IntValue val) {
	    			
	    		}
	    		else if(param1 instanceof IntDegree id && param2 instanceof IntPercent p) {
	    			
	    		}
	    		else if(param1 instanceof IntDegree id && param2 instanceof IntValue val) {
	    			
	    		}
	    		else if(param1 instanceof IntPercent p && param2 instanceof IntValue val) {
	    			
	    		}
	    		
	    	default:
	            // Par défaut ou pour les autres actions
	            //executionActions.add(new engine.GAL.GALAction(call.name));
	        	return null;
	    	}
		case 3:
			Object param1_in_3 = f.parameters.get(0);
			Object param2_in_3 = f.parameters.get(1);
			Object param3_in_3 = f.parameters.get(2);
	    	switch(f.name) {
	        // Selon le nom de l'action (Move, Hit, Push...), on instancie le bon type
	    	case"Move":
	    		if(param1_in_3 instanceof Direction d && param2_in_3 instanceof Variable v && param3_in_3 instanceof IntPercent p ) {
	    			
	    		}
	    		else if(param1_in_3 instanceof Direction d && param2_in_3 instanceof Variable v && param3_in_3 instanceof IntValue val ) {
	    			
	    		}
	    		else if(param1_in_3 instanceof Direction d && param2_in_3 instanceof IntPercent p && param3_in_3 instanceof IntValue val ) {
	    			
	    		}
	    		else if(param1_in_3 instanceof IntDegree id && param2_in_3 instanceof IntPercent p && param3_in_3 instanceof IntValue val ) {
	    			
	    		}
	    		engine.GAL.Direction d3=engine.GAL.Direction.toDir(((IntValue)f.parameters.get(1)).value);
	    		double j2=(double)((IntValue)(f.parameters.get(2))).value;
	    		return new engine.GAL.Move(d3,j2);
	    	default:
	            // Par défaut ou pour les autres actions
	            //executionActions.add(new engine.GAL.GALAction(call.name));
	        	return null;
	    	}
		case 4:
			Object param1_in_4 = f.parameters.get(0);
			Object param2_in_4 = f.parameters.get(1);
			Object param3_in_4 = f.parameters.get(2);
			Object param4_in_4 = f.parameters.get(3);
	    	switch(f.name) {
	        // Selon le nom de l'action (Move, Hit, Push...), on instancie le bon type
	    	case"Move":
	    		if(param1_in_4 instanceof Direction d && param2_in_4 instanceof Variable v && param3_in_4 instanceof IntPercent p && param4_in_4 instanceof IntValue val ) {
	    			
	    		}
	    		engine.GAL.Direction d3=engine.GAL.Direction.toDir(((IntValue)f.parameters.get(1)).value);
	    		double j2=(double)((IntValue)(f.parameters.get(2))).value;
	    		return new engine.GAL.Move(d3,j2);
	    	default:
	            // Par défaut ou pour les autres actions
	            //executionActions.add(new engine.GAL.GALAction(call.name));
	        	return null;
	    	}
	    default:
	    	return null;
		}
	}
//////////////////////////////////////////////////////////////////////////////////////
	////////Condition
	/// /////////////////////////////////////////////////////////////////////////////////
	public engine.GAL.iGALCondition transformcond(Expression f){
		if(f instanceof BinaryOp bo) {
			String op=bo.operator;
			if(op.equals("&")) {
				engine.GAL.Conjunction con=new engine.GAL.Conjunction();
				con.add(transformcond(bo.right_operand));
				con.add(transformcond(bo.right_operand));
				return con;
			}
			if(op.equals("|")) {
				engine.GAL.Disjunction dis=new engine.GAL.Disjunction();
				dis.add(transformcond(bo.right_operand));
				dis.add(transformcond(bo.right_operand));
				return dis;
			}
		}
		
		if(f instanceof UnaryOp uo) {
			
		}
		else {
			FunCall ff=(FunCall)f;
			int paramSize = ff.parameters.size();
	        String actionName = ff.name;
			switch(paramSize) {
			case 0:
				return engine.GAL.GALCondition.TRUE;
			case 1:
				Object param = ff.parameters.get(0);
	    		if(param instanceof Category c) {
	    			switch(c.terminal.content){
	    			case "@":
		    				Ship s=Ship.ship();
		        	    	switch(actionName) {
		        	        // Selon le nom de l'action (Move, Hit, Push...), on instancie le bon type
		        	    	case"Closest":
		        	    		return new engine.GAL.Closest(s,new engine.GAL.Variable());
		        	    	default:
		        	            // Par défaut ou pour les autres actions
		        	            //executionActions.add(new engine.GAL.GALAction(call.name));
		        	        	return null;
		        	    	}
	    			case "#":
		        	    	switch(actionName) {
		        	        // Selon le nom de l'action (Move, Hit, Push...), on instancie le bon type
		        	    	case"Closest":
		        	    		return new engine.GAL.Closest(engine.GAL.Category.Boss,new engine.GAL.Variable());
		        	    	default:
		        	            // Par défaut ou pour les autres actions
		        	            //executionActions.add(new engine.GAL.GALAction(call.name));
		        	        	return null;
		        	    	}
	    			case "M":
	        	    	switch(actionName) {
	        	        // Selon le nom de l'action (Move, Hit, Push...), on instancie le bon type
	        	    	case"Closest":
	        	    		return new engine.GAL.Closest(engine.GAL.Category.Missile,new engine.GAL.Variable());
	        	    	default:
	        	            // Par défaut ou pour les autres actions
	        	            //executionActions.add(new engine.GAL.GALAction(call.name));
	        	        	return null;
	        	    	}
	    			case "O":
	        	    	switch(actionName) {
	        	        // Selon le nom de l'action (Move, Hit, Push...), on instancie le bon type
	        	    	case"Closest":
	        	    		return new engine.GAL.Closest(engine.GAL.Category.Obstacle,new engine.GAL.Variable());
	        	    	default:
	        	            // Par défaut ou pour les autres actions
	        	            //executionActions.add(new engine.GAL.GALAction(call.name));
	        	        	return null;
	        	    	}
	    			case "A":
	        	    	switch(actionName) {
	        	        // Selon le nom de l'action (Move, Hit, Push...), on instancie le bon type
	        	    	case"Closest":
	        	    		return new engine.GAL.Closest(engine.GAL.Category.Adversary,new engine.GAL.Variable());
	        	    	default:
	        	            // Par défaut ou pour les autres actions
	        	            //executionActions.add(new engine.GAL.GALAction(call.name));
	        	        	return null;
	        	    	}
	    			}
	    		}
	    		else {
	    			throw new IllegalArgumentException("Type de paramètre inconnue");
	    		}
			case 2:
				Object param1 = ff.parameters.get(0);
				Object param2 = ff.parameters.get(1);
	    		if(param1 instanceof Direction d && param2 instanceof Category cat) {
	    	    	switch(actionName) {
	    	        // Selon le nom de l'action (Move, Hit, Push...), on instancie le bon type
	    	    	case"Closest":
	    	    		return new engine.GAL.Closest(engine.GAL.Direction.canonical(d.toString()), engine.GAL.Category.canonical(cat.toString()),new engine.GAL.Variable());
	    	    	case"Step":
	    	    		return new engine.GAL.AtStep(engine.GAL.Direction.canonical(d.toString()), engine.GAL.Category.canonical(cat.toString()),new engine.GAL.Variable());
	    	    	default:
	    	            // Par défaut ou pour les autres actions
	    	            //executionActions.add(new engine.GAL.GALAction(call.name));
	    	        	return null;
	    	    	}
	    		}
	    		else if(param1 instanceof Category cat && param2 instanceof IntValue iv) {
	    	    	switch(actionName) {
	    	        // Selon le nom de l'action (Move, Hit, Push...), on instancie le bon type
	    	    	case"Closest":
	    	    		return new engine.GAL.Closest(engine.GAL.Category.canonical(cat.toString()),iv.value,new engine.GAL.Variable());
	    	    	default:
	    	            // Par défaut ou pour les autres actions
	    	            //executionActions.add(new engine.GAL.GALAction(call.name));
	    	        	return null;
	    	    	}
	    		}
	    		else {
	    			throw new IllegalArgumentException("Type de paramètre inconnue");
	    		}
			case 3:
				Object param1_in_3 = ff.parameters.get(0);
				Object param2_in_3 = ff.parameters.get(1);
				Object param3_in_3 = ff.parameters.get(2);
	    		if(param1_in_3 instanceof Direction d && param2_in_3 instanceof IntValue iv && param3_in_3 instanceof Category cat) {
	    	    	switch(actionName) {
	    	        // Selon le nom de l'action (Move, Hit, Push...), on instancie le bon type
	    	    	case"Closest":
	    	    		return new engine.GAL.Closest(engine.GAL.Direction.canonical(d.toString()), engine.GAL.Category.canonical(cat.toString()),iv.value,new engine.GAL.Variable());
	    	    	case"Step":
	    	    		return new engine.GAL.AtStep(engine.GAL.Direction.canonical(d.toString()), engine.GAL.Category.canonical(cat.toString()),iv.value,new engine.GAL.Variable());
	    	    	default:
	    	            // Par défaut ou pour les autres actions
	    	            //executionActions.add(new engine.GAL.GALAction(call.name));
	    	        	return null;
	    	    	}
	    		}
	    		else {
	    			throw new IllegalArgumentException("Type de paramètre inconnue");
	    		}
		}
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
