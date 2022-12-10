package scanner.prototype.utils.block;

public enum NCPBlockType {
    ncloud("ncloud"),
    ncloud_server("ncloud_server"),
    ncloud_subnet("ncloud_subnet"),
    ncloud_network_interface("ncloud_network_interface"),
    region("region"),
    server_name("server_name");

    private final String block;

    private NCPBlockType(String block) { 
        this.block = block; 
    }

    public String getBlock() { 
        return block; 
    }
}
