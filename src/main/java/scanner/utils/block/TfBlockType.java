package scanner.utils.block;

public enum TfBlockType {
    data("data"),
    module("module"),
    output("output"),
    provider("provider"),
    resource("resource"),
    variable("variable");

    private final String type;

    private TfBlockType(String type) { 
        this.type = type; 
    }

    public String getType() { 
        return type; 
    }
}