package scanner.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;


@AllArgsConstructor
@Getter
public enum UserState {
    ACTIVATE("Activate"),
    DEACTIVATE("Deactivate"),
    DELETED("Deleted");

    private final String state;

    public static UserState of(String state) {

        return Arrays.stream(UserState.values())
                .filter(r -> r.getState().equals(state))
                .findAny()
                .orElse(ACTIVATE);
    }
}