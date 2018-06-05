package demo.beethoven;

import lombok.Data;
import lombok.Getter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static java.util.Optional.ofNullable;

@Data
public class BeethovenOperation {

    private String workflowName;
    private String instanceName;
    private Operation operation;
    private Set<ContextualInput> inputs = new HashSet<>();

    public void setOperation(String operation) {
        this.operation = Operation.findById(operation);
    }

    public String getOperation() {
        return ofNullable(operation)
                .map(op -> op.id)
                .orElseGet(null);
    }

    public enum Operation {
        SCHEDULE("schedule"),
        START("start"),
        STOP("stop"),
        CANCEL("cancel");

        @Getter
        private String id;

        Operation(String id) {
            this.id = id;
        }

        public static Operation findById(String id) {
            return Arrays.stream(values())
                    .filter(op -> op.id.equals(id)).findFirst()
                    .orElseGet(null);
        }

    }
}