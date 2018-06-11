package demo.beethoven;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class WorkflowInstance {

    private String workflowName;
    private String workflowInstanceName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private boolean successful;

    public Duration elapsedTime() {
        Duration duration = Duration.between(startTime, endTime);
        return duration.abs();
    }
}
