package com.pttracker.trainingaid.networks;

import com.pttracker.trainingaid.models.GraphInfo;

/**
 * Converts GraphInfoJsonModel to GraphInfo
 */
public class GraphInfoJsonModelMapper {
    public GraphInfo convertToGraphInfo(GraphInfoJsonModel graphInfoJsonModel){
        GraphInfo graphInfo = new GraphInfo();
        if(graphInfoJsonModel.getCurrentRoutine() != null) {
            graphInfo.setCurrentTrainingPlan(graphInfoJsonModel.getCurrentRoutine().getWorkoutname());
        }
        if(graphInfoJsonModel.getWeightGoal() != null) {

            graphInfo.setWeightGoal(Float.parseFloat(graphInfoJsonModel.getWeightGoal()));
        }
        if(graphInfoJsonModel.getBmiGoal() != null) {

            graphInfo.setBmiGoal(Float.parseFloat(graphInfoJsonModel.getBmiGoal()));
        }
        if(graphInfoJsonModel.getDaysTraining() != null) {

            graphInfo.setDaysTrained(Integer.parseInt(graphInfoJsonModel.getDaysTraining()));
        }
        if(graphInfoJsonModel.getCurrentWeight() != null) {
            graphInfo.setCurrentWeight(Float.parseFloat(graphInfoJsonModel.getCurrentWeight()));
        }
        if(graphInfoJsonModel.getCurrentBmi() != null) {

            graphInfo.setCurrentBmi(Float.parseFloat(graphInfoJsonModel.getCurrentBmi()));
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
