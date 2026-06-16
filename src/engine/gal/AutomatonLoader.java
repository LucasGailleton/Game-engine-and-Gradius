package engine.gal;
import java.util.LinkedList;
import java.util.List;

import JavaProject.src.gal.ast.AST;
import JavaProject.src.gal.parser.Parser;

public class AutomatonLoader {
	int j;
	public AutomatonLoader(int i){
		j=i;
	}
	
	public List<Automaton> loadAutomata(String filename) {
		LinkedList<Automaton> fsm_list;
		try {
		AST ast = (AST) Parser.from_file(filename);
		// 2. Instanciation de notre tout nouveau Builder
        FSMbuilder builder = new FSMbuilder();
		// TODO à vous de constuire les FSM (automates exécutables) à partir
		//de l'AST
        List<Automaton> FSM_list = (List<Automaton>) ast.accept(builder);
		return FSM_list;
		} catch (Exception ex) {
		return null;
		}
	}
}
