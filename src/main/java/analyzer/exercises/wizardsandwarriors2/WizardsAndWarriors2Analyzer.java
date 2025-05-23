package analyzer.exercises.wizardsandwarriors2;

import analyzer.Analyzer;
import analyzer.OutputCollector;
import analyzer.Solution;
import analyzer.comments.ExemplarSolution;
import analyzer.comments.PreferStringConcatenation;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.nodeTypes.NodeWithType;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.List;

/**
 * The {@link WizardsAndWarriors2Analyzer} is the analyzer implementation for the {@code wizards-and-warriors-2} practice exercise.
 *
 * @see <a href="https://github.com/exercism/java/tree/main/exercises/concept/wizards-and-warriors-2">The wizards-and-warriors exercise on the Java track</a>
 */
public class WizardsAndWarriors2Analyzer extends VoidVisitorAdapter<OutputCollector> implements Analyzer {

    private static final String EXERCISE_NAME = "Wizards and Warriors 2";
    private static final String GAME_MASTER = "GameMaster";
    private static final String DESCRIBE = "describe";
    private static final String FORMAT = "format";

    @Override
    public void analyze(Solution solution, OutputCollector output) {

        for (var compilationUnit : solution.getCompilationUnits()) {
            compilationUnit.getClassByName(GAME_MASTER).ifPresent(c -> c.accept(this, output));
        }

        if (output.getComments().isEmpty()) {
            output.addComment(new ExemplarSolution(EXERCISE_NAME));
            output.addTag("construct:method-overloading");
        }
    }

    @Override
    public void visit(MethodDeclaration node, OutputCollector output) {

        if(!node.getNameAsString().equals(DESCRIBE)) {
            return;
        }

        if(node.getParameters().size() > 1 && !useOverload(node)) {

            output.addComment(new UseMethodOverloading());

        }

        if(useFormat(node)) {

            output.addComment(new PreferStringConcatenation());

        }


        super.visit(node, output);
    }

    private static boolean useOverload(MethodDeclaration node) {

        int paramCount = node.getParameters().size();

        // finds all methods with the name "describe" inside the current method
        List<MethodCallExpr> describeCalls = node.findAll(MethodCallExpr.class).stream()
                .filter(m -> m.getNameAsString().equals(DESCRIBE))
                .toList();

        // if paramCount is 2, then is the case of describe(a,b) where I can call describe(a) and describe(b) and describe ("walking") or describe(a,b,"c").
        if (paramCount == 2) {
            return describeCalls.size() == 1 || describeCalls.size() == 3;
        }

        // if paramCount is 3, then is the case of describe(a,b,c) where I can call describe(a) and describe(b) and describe (c).
        if (paramCount == 3) {
            return describeCalls.size() == 3;
        }

        return false;
    }

    private static boolean useFormat(MethodDeclaration node) {
        return node.findAll(MethodCallExpr.class).stream()
                .anyMatch(m -> m.getNameAsString().contains(FORMAT));
    }

}
