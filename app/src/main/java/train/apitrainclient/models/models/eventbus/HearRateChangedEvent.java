package train.apitrainclient.models.models.eventbus;

/**
 * RoutineDataReceivedEvent is posted whenever wear receives routine data from mobile
 */

public class HearRateChangedEvent {
    public static String TYPE_HEART="heart";
    public static String TYPE_STEPS="steps";

    private int bpm;
    private int steps;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public int getBpm() {
        return bpm;
    }

    public void setBpm(int bpm) {
        this.bpm = bpm;
    }
}
