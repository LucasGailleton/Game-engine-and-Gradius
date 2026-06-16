package engine.gal;
import java.util.List;

import JavaProject.src.gal.ast.AST;
import JavaProject.src.gal.ast.Actions;
import JavaProject.src.gal.ast.Automaton;
import JavaProject.src.gal.ast.Behaviour;
import JavaProject.src.gal.ast.BinaryOp;
import JavaProject.src.gal.ast.Category;
import JavaProject.src.gal.ast.Condition;
import JavaProject.src.gal.ast.Direction;
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
		return cat.toString();
	}

	public Object visit(Direction dir) {
		return dir.toString();
	}

	public Object visit(Key key) {
		return key.toString();
	}

	public Object visit(IntPercent per) {
		return per.toString();
	}

	public Object visit(IntDegree deg) {
		return deg.toString();
	}

	public Object visit(IntValue v) {
		return v.toString();
	}

	public Object visit(Underscore u) {
		return u.toString();
	}

	@Override
	public Object visit(Variable v) {
		return v.toString();
	}

	// FUNCALL

	public void enter(FunCall funcall) {
	}

	public void visit(FunCall funcall) {
	}

	public void exit(FunCall funcall) {
	}

	public Object build(FunCall funcall, List<Object> params) {
		return null;
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
		return null;
	}

	// CONDITION

	public void enter(Condition condition) {
	}

	public void exit(Condition condition) {
	}

	public Object build(Condition condition, Object exp) {
		return null;
	}

	// ACTION

	public void enter(Actions action) {
	}

	public void visit(Actions action) {
	}

	public void exit(Actions action) {
	}

	public Object build(Actions action, String operator, List<Object> funcalls) {
		return null;
	}

	// TRANSITION

	public void enter(Transition transition) {
	}

	public void exit(Transition transition) {
	}

	public Object build(Transition transition, Object condition, Object action, Object target) {
		// Vos méthodes recevront ici les objets réels construits par les sous-nœuds !
	    Condition condObj = (Condition) condition; 
	    Actions actObj = (Actions) action;
	    State targetState = (State) target;

	    // Instanciation de votre modèle d'exécution d'automate
	    Transition myTrans = new Transition(condObj, actObj, targetState);
	    
	    return myTrans; // On propage l'objet vers le haut (le Behaviour)
	}

	// BEHAVIOUR

	public Object visit(Behaviour behaviour, List<Object> transitions) {
		return null;
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
		String initialStateName = (String) initial_state;
        engine.gal.State initial = new engine.gal.State(initialStateName,0);
        
        // Création de VOTRE instance d'Automaton avec son constructeur : Automaton(String name, State initial)
        this.currentAutomaton = new engine.gal.Automaton(automaton.name, initial);
        
        // Les transitions ont été ajoutées au fur et à mesure lors du parcours des 'Mode' en Bottom-Up.
        // On retourne l'automate complet et prêt à l'emploi.
        return this.currentAutomaton;
        //return null;
	}

	// AST

	public void enter(AST ast) {
	}

	public void exit(AST ast) {
	}

	public Object build(AST ast, List<Object> automata) {
		return null;
	}
	
}
