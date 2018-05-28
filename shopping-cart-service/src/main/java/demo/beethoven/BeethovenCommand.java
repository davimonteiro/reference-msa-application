package demo.beethoven;

import lombok.*;

import java.io.Serializable;

import static demo.beethoven.BeethovenCommand.CommandOperation.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BeethovenCommand implements Serializable {

    private CommandOperation operation;
    private String taskName;
    private String workflowName;
    private String input;

    public enum CommandOperation {
        START_TASK, START_WORKFLOW, STOP_WORKFLOW, CANCEL_WORKFLOW, FINISH_WORKFLOW;
    }


    public static BeethovenCommand startTask(@NonNull String taskName) {
        return BeethovenCommand.builder().taskName(taskName).operation(START_TASK).build();
    }

    public static BeethovenCommand startWorkflow(@NonNull String workflowName) {
        return BeethovenCommand.builder().workflowName(workflowName).operation(START_WORKFLOW).build();
    }

    public static BeethovenCommand stopWorkflow(@NonNull String workflowName) {
        return BeethovenCommand.builder().workflowName(workflowName).operation(STOP_WORKFLOW).build();
    }

    public static BeethovenCommand cancelWorkflow(@NonNull String workflowName) {
        return BeethovenCommand.builder().workflowName(workflowName).operation(CANCEL_WORKFLOW).build();
    }

    public static BeethovenCommand finishWorkflow(@NonNull String workflowName) {
        return BeethovenCommand.builder().workflowName(workflowName).operation(FINISH_WORKFLOW).build();
    }

}