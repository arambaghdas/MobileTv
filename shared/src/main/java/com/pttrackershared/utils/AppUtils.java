package com.pttrackershared.utils;

import android.content.Context;

import com.pttrackershared.models.eventbus.Circuit;
import com.pttrackershared.models.eventbus.CircuitJsonModel;
import com.pttrackershared.models.eventbus.CircuitJsonModelMapper;
import com.pttrackershared.models.eventbus.Exercise;
import com.pttrackershared.models.eventbus.ExerciseJsonModel;
import com.pttrackershared.models.eventbus.ExerciseJsonModelMapper;
import com.pttrackershared.models.eventbus.TrainingPlan;
import com.pttrackershared.models.eventbus.TrainingPlanJsonModel;
import com.pttrackershared.models.eventbus.TrainingPlanJsonModelMapper;
import com.pttrackershared.models.eventbus.Workout;
import com.pttrackershared.models.eventbus.WorkoutJsonModel;
import com.pttrackershared.models.eventbus.WorkoutJsonModelMapper;

import java.util.ArrayList;
import java.util.List;

public class AppUtils {

    public static List<TrainingPlan> saveTrainingPlans(Context context, List<TrainingPlanJsonModel> trainingPlanJsonModelList) {

        if (trainingPlanJsonModelList == null) {
            return new ArrayList<>();
        }
        List<TrainingPlan> trainingPlanList = new ArrayList<>();
        List<Workout> workouts = new ArrayList<>();
        List<Circuit> circuits = new ArrayList<>();
        List<Exercise> exercises = new ArrayList<>();


        TrainingPlanJsonModel modelTrainingPlan = trainingPlanJsonModelList.get(0);
        TrainingPlan trainingPlan = new TrainingPlanJsonModelMapper().convertToTrainingPlan(modelTrainingPlan);
        trainingPlan.setIsSynced(true);
        trainingPlan.setWorkoutList(new ArrayList<Workout>());
        for (int i = 0; i < modelTrainingPlan.getClientWorkouts().size(); i++) {
            WorkoutJsonModel workoutJsonModel = modelTrainingPlan.getClientWorkouts().get(i);
            Workout workout = new WorkoutJsonModelMapper().convertToWorkout(workoutJsonModel);
            workout.setCircuitList(new ArrayList<Circuit>());
            for (int j = 0; j < workoutJsonModel.getCircuits().size(); j++) {
                CircuitJsonModel circuitJsonModel = workoutJsonModel.getCircuits().get(j);
                Circuit circuit = new CircuitJsonModelMapper().convertToCircuit(circuitJsonModel);
                circuit.setWorkoutId(workouts.size()+1);
                circuit.setExerciseList(new ArrayList<ExerciseJsonModel>());
                workout.getCircuitList().add(circuit);
                for (int k = 0; k < circuitJsonModel.getExercises().size(); k++) {
                    ExerciseJsonModel exerciseJsonModel = circuitJsonModel.getExercises().get(k);
                    Exercise exercise = new ExerciseJsonModelMapper().convertToExercise(exerciseJsonModel);
                    exercise.setCircuitId(circuits.size()+1);
                    circuit.getExerciseList().add(exerciseJsonModel);
                }
            }
            trainingPlan.getWorkoutList().add(workout);
        }

        trainingPlanList.add(trainingPlan);
//        for (int tr = 0; tr < trainingPlanJsonModelList.size(); tr++) {
//            TrainingPlanJsonModel modelTrainingPlan = trainingPlanJsonModelList.get(tr);
//
//            if (modelTrainingPlan.getIsActive() == 1) {
//
//                TrainingPlan trainingPlan = new TrainingPlanJsonModelMapper().convertToTrainingPlan(modelTrainingPlan);
//                trainingPlan.setIsSynced(true);
//
//                if (modelTrainingPlan.getClientWorkouts() != null) {
//                    for (int wo = 0; wo < modelTrainingPlan.getClientWorkouts().size(); wo++) {
//                        WorkoutJsonModel workoutJsonModel = modelTrainingPlan.getClientWorkouts().get(wo);
//                        Workout workout = new WorkoutJsonModelMapper().convertToWorkout(workoutJsonModel);
//                        workout.setTrainingPlanId(trainingPlanList.size()+1);
//                        if (workoutJsonModel.getCircuits() != null) {
//                            for (CircuitJsonModel circuitJsonModel : workoutJsonModel.getCircuits()) {
//                                Circuit circuit = new CircuitJsonModelMapper().convertToCircuit(circuitJsonModel);
//                                circuit.setWorkoutId(workouts.size()+1);
//                                if (circuitJsonModel.getExercises() != null) {
//                                    for (ExerciseJsonModel exerciseJsonModel : circuitJsonModel.getExercises()) {
//                                        Exercise exercise = new ExerciseJsonModelMapper().convertToExercise(exerciseJsonModel);
//                                        exercise.setCircuitId(circuits.size()+1);
//                                        exercises.add(exercise);
//                                    }
//                                }
//                                circuit.setExerciseList(exercises);
//                                circuits.add(circuit);
//                            }
//                        }
//                        workout.setCircuitList(circuits);
//                        workouts.add(workout);
//                    }
//                }
//                trainingPlan.setWorkoutList(workouts);
//                trainingPlanList.add(trainingPlan);
//            }
//
//            if (trainingPlanList.size() > 0) {
//                SharedPrefManager.setTrainingPlanList(context,trainingPlanList);
//            }
//
////            if (workouts.size() > 0) {
////               SharedPrefManager.setWorkoutsList(context,workouts);
////            }
////
////            if (circuits.size() > 0) {
////                SharedPrefManager.setCircuitsList(context,circuits);
////            }
////
////            if (exercises.size() > 0) {
////                SharedPrefManager.setExercisesList(context,exercises);
////            }
//
////            ExerciseImagesDownloadService.startDownloading(context);
//        }
        return trainingPlanList;
    }
}
