package com.pttrackershared.models.eventbus;


/**
 * Converts TrainingPlanJsonModel to TrainingPlan
 */
public class TrainingPlanJsonModelMapper {
    public TrainingPlan convertToTrainingPlan(TrainingPlanJsonModel trainingPlanJsonModel) {
        TrainingPlan trainingPlan = new TrainingPlan();
        trainingPlan.setTrainingPlanId(trainingPlanJsonModel.getPlanId());
        trainingPlan.setTrainerId(trainingPlanJsonModel.getTrainerId());
        trainingPlan.setClientId(trainingPlanJsonModel.getClientId());
        trainingPlan.setName(trainingPlanJsonModel.getPlanName());
        trainingPlan.setIsActive(trainingPlanJsonModel.getIsActive());
        trainingPlan.setStartDateString(trainingPlanJsonModel.getStartDate());
        trainingPlan.setFinishDateString(trainingPlanJsonModel.getFinishDate());
        trainingPlan.setWorkout_id1(trainingPlanJsonModel.getWorkoutId1());
        trainingPlan.setWorkout_id2(trainingPlanJsonModel.getWorkoutId2());
        trainingPlan.setWorkout_id3(trainingPlanJsonModel.getWorkoutId3());
        trainingPlan.setWorkout_id4(trainingPlanJsonModel.getWorkoutId4());
        trainingPlan.setWorkout_id5(trainingPlanJsonModel.getWorkoutId5());
        trainingPlan.setWorkout_id6(trainingPlanJsonModel.getWorkoutId6());
        trainingPlan.setWorkout_id7(trainingPlanJsonModel.getWorkoutId7());
        trainingPlan.setWeight(trainingPlanJsonModel.getWeight());
        return trainingPlan;
    }

}
