package scanner.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@AllArgsConstructor
@Builder
@Getter
public class ResponseHeader<T> {
    private final Boolean success;
    private final T data;

    private final Object error;
}
