package scanner.response;


import lombok.Builder;
import lombok.Getter;

@Getter
public class ResponseHeader<T> {
    private final Boolean success;
    private final T data;
    private final Object error;


    @Builder
    public ResponseHeader(Boolean success, T data, Object error) {
        this.success = success;
        this.data = data;
        this.error = error;
    }
}
