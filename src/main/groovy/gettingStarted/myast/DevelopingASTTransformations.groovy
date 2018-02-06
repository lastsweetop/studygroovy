package gettingStarted.myast

import groovy.transform.CompileStatic
import org.codehaus.groovy.ast.ASTNode
import org.codehaus.groovy.ast.ClassCodeExpressionTransformer
import org.codehaus.groovy.ast.MethodNode
import org.codehaus.groovy.ast.expr.ArgumentListExpression
import org.codehaus.groovy.ast.expr.ConstantExpression
import org.codehaus.groovy.ast.expr.Expression
import org.codehaus.groovy.ast.expr.MethodCallExpression
import org.codehaus.groovy.ast.expr.VariableExpression
import org.codehaus.groovy.ast.stmt.BlockStatement
import org.codehaus.groovy.ast.stmt.ExpressionStatement
import org.codehaus.groovy.ast.stmt.Statement
import org.codehaus.groovy.control.CompilePhase
import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.transform.ASTTransformation
import org.codehaus.groovy.transform.GroovyASTTransformation
import org.codehaus.groovy.transform.GroovyASTTransformationClass

import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Target

@Retention(RetentionPolicy.SOURCE)
@Target([ElementType.METHOD])
@GroovyASTTransformationClass(["gettingStarted.myast.WithLoggingASTTransformation"])
public @interface WithLogging {

}

@CompileStatic
@GroovyASTTransformation(phase = CompilePhase.SEMANTIC_ANALYSIS)
class WithLoggingASTTransformation implements ASTTransformation {

    @Override
    void visit(ASTNode[] astNodes, SourceUnit sourceUnit) {
        MethodNode method = (MethodNode) astNodes[1]
        def startMessage = createPrintlnAst("Starting $method.name")
        def endMessage = createPrintlnAst("Ending $method.name")
        def existingStatements = ((BlockStatement) method.code).statements
        existingStatements.add(0, startMessage)
        existingStatements.add(endMessage)
    }

    private static Statement createPrintlnAst(String message) {
        new ExpressionStatement(new MethodCallExpression(
                new VariableExpression("this"),
                new ConstantExpression("println"),
                new ArgumentListExpression(new ConstantExpression(message))
        ))
    }
}

@CompileStatic
@GroovyASTTransformation(phase = CompilePhase.SEMANTIC_ANALYSIS)
class WithLoggingASTTransformation2 implements ASTTransformation {

    @Override
    void visit(ASTNode[] astNodes, SourceUnit sourceUnit) {
        sourceUnit.AST.classes.each {
            it.methods.findAll {
                it.name != 'main' && it.name != 'run'
            } each {
                def startMessage = createPrintlnAst "Starting $it.name"
                def endMessage = createPrintlnAst "Ending $it.name"
                def existingStatements = ((BlockStatement) it.code).statements
                existingStatements.add 0, startMessage
                existingStatements.add(existingStatements.size()-1,endMessage)
            }
        }


    }

    private static Statement createPrintlnAst(String message) {
        new ExpressionStatement(new MethodCallExpression(
                new VariableExpression("this"),
                new ConstantExpression("println"),
                new ArgumentListExpression(new ConstantExpression(message))
        ))
    }
}

@Retention(RetentionPolicy.SOURCE)
@Target([ElementType.METHOD])
@GroovyASTTransformationClass(["gettingStarted.myast.ShoutASTTransformation"])
public @interface Shout {

}

@CompileStatic
@GroovyASTTransformation(phase = CompilePhase.SEMANTIC_ANALYSIS)
class ShoutASTTransformation implements ASTTransformation {
    @Override
    void visit(ASTNode[] astNodes, SourceUnit sourceUnit) {
        new CCET().visitMethod((MethodNode)astNodes[1])
    }
}

class CCET extends ClassCodeExpressionTransformer {
    private boolean inArgList = false

    @Override
    protected SourceUnit getSourceUnit() {
        sourceUnit
    }

    @Override
    Expression transform(final Expression exp) {
        if (exp instanceof ArgumentListExpression) {
            inArgList = true
        } else if (inArgList &&
                exp instanceof ConstantExpression && exp.value instanceof String) {
            return new ConstantExpression(exp.value.toUpperCase())
        }
        def trn = super.transform(exp)
        inArgList = false
        trn
    }

}