package scanner.prototype.service;

public enum ScanParseString {
    TOTAL("Passed checks"),
    CHECK("Check:"),
    PASSED("PASSED"),
    FAILED("FAILED"),
    FILE("File");

    private final String parseString;

    private ScanParseString(String parseString) { 
        this.parseString = parseString; 
    }

    public String getParseString() { 
        return parseString; 
    }
}
