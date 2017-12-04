package languageSpecification

beforeVisitMethod { methodNode ->
    println "Entering ${methodNode.name}"
}

unresolvedVariable {
    var ->
    if ('robot'==var.name) {
        storeType(var, classNodeFor(Robot))
        handled = true
    }
}

methodNotFound { receiver, name, argList, argTypes, call ->
    if (isMethodCallExpression(call)
            && call.implicitThis
            && 'move'==name
            && argTypes.length==1
            && argTypes[0] == classNodeFor(int)
    ) {
//        println "method = $name"
//        handled = true
//        newMethod('move', classNodeFor(Robot))
        makeDynamic(call, classNodeFor(Robot))
    }
}
