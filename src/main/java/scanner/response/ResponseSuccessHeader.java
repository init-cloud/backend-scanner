package scanner.response;


import lombok.Getter;

@Getter
public class ResponseSuccessHeader<T> extends ResponseHeader {
    private T data;

    public ResponseSuccessHeader(Integer code, String message, T data) {
        super(true, code, message);
        this.data = data;
    }
}
