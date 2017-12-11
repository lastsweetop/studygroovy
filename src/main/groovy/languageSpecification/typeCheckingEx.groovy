package languageSpecification

import groovy.transform.CompileStatic
import groovy.transform.TypeChecked
import groovy.xml.MarkupBuilder
import org.codehaus.groovy.ast.ClassNode

import static org.codehaus.groovy.ast.ClassNode.*
import org.codehaus.groovy.control.CompilerConfiguration
import org.codehaus.groovy.control.customizers.ASTTransformationCustomizer

def script="""
    robot.move 100
"""
def config = new CompilerConfiguration()
config.addCompilationCustomizers(
        new ASTTransformationCustomizer(TypeChecked,
                extensions:'myextension.groovy'
        )
)
def shell = new GroovyShell(config)
def robot = new Robot()
shell.setVariable('robot', robot)
shell.evaluate(script)

script="""
    move 50
"""
//def config = new CompilerConfiguration()
config.scriptBaseClass = 'groovy.util.DelegatingScript'
//config.addCompilationCustomizers(
//        new ASTTransformationCustomizer(
//                CompileStatic,
//                extensions:['myextension.groovy'])
//)
def shell1 = new GroovyShell(config)
def runner = shell1.parse(script)
runner.setDelegate(new Robot())
runner.run()

def writer = new StringWriter()
def builder = new MarkupBuilder(writer)

builder.html {
    head {

    }
    body {
        p 'hello,world!'
    }
}

println writer


