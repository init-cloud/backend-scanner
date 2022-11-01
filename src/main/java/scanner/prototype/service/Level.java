package scanner.prototype.service;

public enum Level {
    CRITICAL(5),
    HIGH(4),
    MEDIUM(3),
    LOW(2),
    UNKNOWN(1);

    private final int level;

    private Level(int level) { 
        this.level = level; 
    }

    public int getLevel() { 
        return level; 
    }
}
