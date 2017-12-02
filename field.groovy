package fileds

class Data {
    private int id
    protected String description
    public static final boolean DEBUG = false
}

class Data2 {
    private String id = IDGenerator.next()
    // ...
}

class BadPractice {
    private mapping
}
class GoodPractice {
    private Map<String,String> mapping
}
