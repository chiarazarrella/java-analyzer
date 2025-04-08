package analyzer.exercises.timfrommarketing;

import analyzer.Analyzer;
import analyzer.OutputCollector;
import analyzer.Solution;
import analyzer.comments.ExemplarSolution;
import analyzer.comments.PreferStringConcatenation;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

/**
 * author: chiarazarrella
 */
public class TimFromMarketingAnalyzer extends VoidVisitorAdapter<OutputCollector> implements Analyzer {

    private static final String EXERCISE_NAME = "Tim from Marketing";
    private static final String FORMAT = "format";

    @Override
    public void analyze(Solution solution, OutputCollector output) {

        for (var compilationUnit : solution.getCompilationUnits()) {
            compilationUnit.accept(this, output);
        }

        if (output.getComments().isEmpty()) {
            output.addComment(new ExemplarSolution(EXERCISE_NAME));

        }
    }

    @Override
    public void visit(MethodDeclaration node, OutputCollector output) {

        if(useOptionals()){
            output.addComment(new UseNullLiteral());
        }

        if(useFormat(node)) {
            output.addComment(new PreferStringConcatenation());
        }


        super.visit(node, output);
    }

    private static boolean useFormat(MethodDeclaration node) {
        return node.findAll(MethodCallExpr.class).stream()
                .anyMatch(m -> m.getNameAsString().contains(FORMAT));
    }

    private static boolean useOptionals(){
        return false;
    }
}
