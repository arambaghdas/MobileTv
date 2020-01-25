package com.pttrackershared.models.eventbus;

import com.google.gson.annotations.SerializedName;
import com.pttrackershared.utils.Constants;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;


/**
 * Green dao uses this class as a type of tuple in it.
 */

public class GraphInfo implements Serializable {

    @SerializedName("goal")
    private String weightGoal;
    @SerializedName("goal_bmi")
    private String bmiGoal;
    @SerializedName("days_training")
    private String daysTraining;
    @SerializedName("current_weight")
    private String currentWeight;
    @SerializedName("current_bmi")
    private String currentBmi;
    @SerializedName("current_routine")
    private CurrentTrainingPlan currentRoutine;
    @SerializedName("graph_data")
    private List<GraphInfoItem> graphData = null;

    private String trn_start_date;
    private Integer total_training_logs;


    public String getTrn_start_date() {
        return trn_start_date;
    }

    public void setTrn_start_date(String trn_start_date) {
        this.trn_start_date = trn_start_date;
    }

    public Integer getTotal_training_logs() {
        return total_training_logs;
    }

    public void setTotal_training_logs(Integer total_training_logs) {
        this.total_training_logs = total_training_logs;
    }

    public String getWeightGoal() {
        return weightGoal;
    }

    public void setWeightGoal(String weightGoal) {
        this.weightGoal = weightGoal;
    }

    public String getBmiGoal() {
        return bmiGoal;
    }

    public void setBmiGoal(String bmiGoal) {
        this.bmiGoal = bmiGoal;
    }

    public String getDaysTraining() {
        return daysTraining;
    }

    public void setDaysTraining(String daysTraining) {
        this.daysTraining = daysTraining;
    }

    public String getCurrentWeight() {
        return currentWeight;
    }

    public void setCurrentWeight(String currentWeight) {
        this.currentWeight = currentWeight;
    }

    public String getCurrentBmi() {
        return currentBmi;
    }

    public void setCurrentBmi(String currentBmi) {
        this.currentBmi = currentBmi;
    }

    public CurrentTrainingPlan getCurrentRoutine() {
        return currentRoutine;
    }

    public void setCurrentRoutine(CurrentTrainingPlan currentRoutine) {
        this.currentRoutine = currentRoutine;
    }

    public List<GraphInfoItem> getGraphData() {
        return graphData;
    }

    public void setGraphData(List<GraphInfoItem> graphData) {
        this.graphData = graphData;
    }


    public static GraphInfoItem convertToGraphInfoItem(GraphInfoItem graphInfoItemJsonModel) {
        GraphInfoItem graphInfoItem = new GraphInfoItem();
        if(graphInfoItemJsonModel.getWeight()!=null)
            graphInfoItem.setWeight(graphInfoItemJsonModel.getWeight());

        if(graphInfoItemJsonModel.getBmi()!= null)
            graphInfoItem.setBmi(graphInfoItemJsonModel.getBmi());

        if(graphInfoItemJsonModel.getDate() != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DATE_FORMAT);
            dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            graphInfoItem.setDate(graphInfoItemJsonModel.getDate());
        }

        graphInfoItem.setWorkoutname(graphInfoItemJsonModel.getWorkoutname());
        graphInfoItem.setTimeComplete(graphInfoItemJsonModel.getTimeComplete());
        return graphInfoItem;
    }

    public static GraphInfo convertToGraphInfo(GraphInfo graphInfoJsonModel){
        GraphInfo graphInfo = new GraphInfo();
        if(graphInfoJsonModel.getCurrentRoutine() != null) {
            graphInfo.setCurrentRoutine(graphInfoJsonModel.getCurrentRoutine());
        }
        if(graphInfoJsonModel.getWeightGoal() != null) {

            graphInfo.setWeightGoal(graphInfoJsonModel.getWeightGoal());
        }
        if(graphInfoJsonModel.getBmiGoal() != null) {

            graphInfo.setBmiGoal(graphInfoJsonModel.getBmiGoal());
        }
        if(graphInfoJsonModel.getDaysTraining() != null) {
            graphInfo.setDaysTraining(graphInfoJsonModel.getDaysTraining());
        }
        if(graphInfoJsonModel.getCurrentWeight() != null) {
            graphInfo.setCurrentWeight(graphInfoJsonModel.getCurrentWeight());
        }
        if(graphInfoJsonModel.getCurrentBmi() != null) {

            graphInfo.setCurrentBmi(graphInfoJsonModel.getCurrentBmi());
        }

        if(graphInfoJsonModel.getTrn_start_date()!=null)
        {
            graphInfo.setTrn_start_date(graphInfoJsonModel.getTrn_start_date());
        }
        if(graphInfoJsonModel.getTotal_training_logs()!=null){
            graphInfo.setTotal_training_logs(graphInfoJsonModel.getTotal_training_logs());
        }

        if(graphInfoJsonModel.getGraphData() != null) {

        }
        return graphInfo;
    }
}
