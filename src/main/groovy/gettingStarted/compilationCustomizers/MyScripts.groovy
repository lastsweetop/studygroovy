package gettingStarted.compilationCustomizers

import groovy.transform.CompileStatic
import groovy.transform.ConditionalInterrupt
import groovy.util.logging.Log
import org.codehaus.groovy.ast.builder.AstBuilder
import org.codehaus.groovy.ast.expr.AttributeExpression
import org.codehaus.groovy.ast.expr.ClosureExpression
import org.codehaus.groovy.ast.stmt.ExpressionStatement
import org.codehaus.groovy.control.CompilePhase
import org.codehaus.groovy.control.CompilerConfiguration
import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.control.customizers.ASTTransformationCustomizer
import org.codehaus.groovy.control.customizers.ImportCustomizer
import org.codehaus.groovy.control.customizers.SecureASTCustomizer
import org.codehaus.groovy.control.customizers.SourceAwareCustomizer
import org.codehaus.groovy.runtime.InvokerHelper
import org.codehaus.groovy.transform.ConditionalInterruptibleASTTransformation

import static org.codehaus.groovy.syntax.Types.*
import static org.codehaus.groovy.control.customizers.builder.CompilerCustomizationBuilder.withConfig

def config = new CompilerConfiguration()
//Import customizer
def icz = new ImportCustomizer()

icz.addImports('java.util.concurrent.atomic.AtomicInteger', 'java.util.concurrent.ConcurrentHashMap')
icz.addImport('CHM', 'java.util.concurrent.ConcurrentHashMap')
icz.addStaticImport('java.lang.Math', 'PI') // import static java.lang.Math.PI
icz.addStaticImport('pi', 'java.lang.Math', 'PI') // import static java.lang.Math.PI as pi
icz.addStarImports 'java.util.concurrent' // import java.util.concurrent.*
icz.addStaticStars 'java.lang.Math' // import static java.lang.Math.*

config.addCompilationCustomizers(icz)


//AST transformation customizer
def acz = new ASTTransformationCustomizer(Log,value:'LOGGER')
config.addCompilationCustomizers(acz)

def configuration = new CompilerConfiguration()
def nodelist = new AstBuilder().buildFromCode(CompilePhase.CONVERSION,{->false
})*.expression[0]

def customizer  = new ASTTransformationCustomizer(ConditionalInterrupt,value:  nodelist,thrown:SecurityException)
configuration.addCompilationCustomizers(customizer)
def shell = new GroovyShell(configuration)
shell.evaluate("""
// equivalent to adding 
//        @groovy.transform.ConditionalInterrupt(value={true}, thrown=SecurityException)
        class MyClass {
            void doIt() { }
        }
        new MyClass().doIt()
""")



//Secure AST customizer
def scz = new SecureASTCustomizer()
scz.with {
//    closuresAllowed = false // user will not be able to write closures
//    methodDefinitionAllowed = false // user will not be able to define methods
    importsWhitelist = [] // empty whitelist means imports are disallowed
    staticImportsWhitelist = [] // same for static imports
    staticStarImportsWhitelist = ['java.lang.Math'] // only java.lang.Math is allowed
    // the list of tokens the user can find
    // constants are defined in org.codehaus.groovy.syntax.Types
    tokensWhitelist = [
            EQUAL,
            PLUS,
            MINUS,
            MULTIPLY,
            DIVIDE,
            MOD,
            POWER,
            PLUS_PLUS,
            MINUS_MINUS,
            COMPARE_EQUAL,
            COMPARE_NOT_EQUAL,
            COMPARE_LESS_THAN,
            COMPARE_LESS_THAN_EQUAL,
            COMPARE_GREATER_THAN,
            COMPARE_GREATER_THAN_EQUAL,
    ].asImmutable()
    // limit the types of constants that a user can define to number types only
    constantTypesClassesWhiteList = [
            Integer,
            Float,
            Long,
            Double,
            BigDecimal,
            Integer.TYPE,
            Long.TYPE,
            Float.TYPE,
            Double.TYPE,
    ].asImmutable()
    // method calls are only allowed if the receiver is of one of those types
    // be careful, it's not a runtime type!
    receiversClassesWhiteList = [
            Math,
            Integer,
            Integer.TYPE,
            Float,
            Double,
            Long,
            BigDecimal,
            InvokerHelper,
    ].asImmutable()
}

config = new CompilerConfiguration()
config.addCompilationCustomizers(scz)
new GroovyShell(config).evaluate '''
    3.times {
        int a = 3*3
    }
'''

scz = new SecureASTCustomizer()
def checker = {!(it instanceof AttributeExpression)} as SecureASTCustomizer.ExpressionChecker
scz.addExpressionCheckers(checker)

config = new CompilerConfiguration()
config.addCompilationCustomizers(scz)

new GroovyShell(config).evaluate '''
    class A {
        int val
    }
    
    def a = new A(val: 123)
//    a.@val 
'''

// Source aware customizer
def importCZ = new ImportCustomizer()
importCZ.addImport('java.util.concurrent.atomic.AtomicInteger')
def sac = new SourceAwareCustomizer(importCZ)
// the customizer will only be applied to classes contained in a file name ending with 'Bean'
// the customizer will only be applied to classes contained in a file name ending with 'Bean'
sac.baseNameValidator = { baseName ->
    baseName.endsWith 'Bean'
}

// the customizer will only be applied to files which extension is '.spec'
sac.extensionValidator = { ext -> ext == 'spec' }

// source unit validation
// allow compilation only if the file contains at most 1 class
sac.sourceUnitValidator = { SourceUnit sourceUnit -> sourceUnit.AST.classes.size() == 1 }

// class validation
// the customizer will only be applied to classes ending with 'Bean'
sac.classValidator = { cn ->
    println cn.name
    cn.name.endsWith('Bean')
}

config=new CompilerConfiguration()
config.addCompilationCustomizers(sac)

new GroovyShell(config).evaluate '''
    class A{
        int v
    }
    println new A()
'''

//Customizer builder
config=new CompilerConfiguration()
withConfig(config){
    //Import customizer
    imports {
        normal 'groovy.transform.ToString'
        alias 'AI', 'java.util.concurrent.atomic.AtomicInteger' // an aliased import
        star 'java.util.concurrent' // star imports
        staticMember 'java.lang.Math', 'PI' // static import
        staticMember 'pi', 'java.lang.Math', 'PI' // aliased static import
    }
    // AST transformation customizer
    ast(Log, value: 'LOGGER')

    // Secure AST customizer
    secureAst {
        closuresAllowed = true
    }
    //Source aware customizer
    source(basenameValidator: { it in ['foo', 'bar'] }) {
        ast(CompileStatic)
    }
    inline(phase:'CONVERSION') { source, context, classNode ->
        println "visiting $classNode"
    }
}

new GroovyShell(config).evaluate '''
    @ToString
    class B{
        int v
        void show(){
            LOGGER.info 'show'
            println v
        }
    }
    def b = new B(v:123)
    b.show()
    println {
        b
    }
'''


