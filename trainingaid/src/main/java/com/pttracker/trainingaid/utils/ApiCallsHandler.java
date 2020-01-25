package com.pttracker.trainingaid.utils;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;
import com.pttracker.trainingaid.activities.ResultActivity;
import com.pttrackershared.models.eventbus.TrainingLog;
import com.pttrackershared.models.eventbus.TrainingPlanJsonModel;
import com.pttrackershared.models.eventbus.User;
import com.pttrackershared.models.eventbus.WorkoutJsonModel;
import com.pttrackershared.models.eventbus.WorkoutJsonModelMapper;
import com.pttracker.trainingaid.networks.retrofit.Api;
import com.pttracker.trainingaid.networks.retrofit.ResponseHandler;
import com.pttrackershared.plugins.LoggerUtils;
import com.pttrackershared.utils.Constants;

import java.util.List;

import retrofit2.Response;

public class ApiCallsHandler {

    Context context;

    public ApiCallsHandler(Context context) {
        this.context = context;
    }


    public void updateTrainingLogWithExercise(final TrainingLog trainingLog, final ListenersHandler.OnUpdateTrainingLogCompletionListener onUpdateRoutineCompletionListener) {
        Gson gson = new Gson();
        final String json = gson.toJson(trainingLog);
        Log.d("checkNitin","Json::"+json);
        Api.getApiService(context).updateTrainingLogWithExercise(json)
                .enqueue(new ResponseHandler<WorkoutJsonModel>() {
                    @Override
                    public void onSuccess(Response<WorkoutJsonModel> response) {
                        if (onUpdateRoutineCompletionListener != null) {
                            TrainingLog trainingLog1 = new WorkoutJsonModelMapper().convertToTrainingLog(response.body());
                            onUpdateRoutineCompletionListener.onSuccess(trainingLog1);
                        } else {
                            LoggerUtils.d("onSuccess");
                        }
                    }

                    @Override
                    public void onFailure(int errorCode, String errorMessage) {
                        ApiCallsHandler.updateToken(context, errorMessage, new OnTokenUpdatedListener() {
                            @Override
                            public void OnTokenUpdated() {
                                Api.getApiService(context).updateTrainingLogWithExercise(json)
                                        .enqueue(new ResponseHandler<WorkoutJsonModel>() {
                                            @Override
                                            public void onSuccess(Response<WorkoutJsonModel> response) {
                                                if (onUpdateRoutineCompletionListener != null) {
                                                    TrainingLog trainingLog1 = new WorkoutJsonModelMapper().convertToTrainingLog(response.body());
                                                    onUpdateRoutineCompletionListener.onSuccess(trainingLog1);
                                                } else {
                                                    LoggerUtils.d("onSuccess");
                                                }
                                            }

                                            @Override
                                            public void onFailure(int errorCode, String errorMessage) {
                                                if (onUpdateRoutineCompletionListener != null) {
                                                    onUpdateRoutineCompletionListener.onFailure(errorCode, errorMessage);
                                                } else {
                                                    LoggerUtils.e("onFailure: " + errorMessage);
                                                }
                                            }
                                        });
                            }

                            @Override
                            public void OnTokenFailed() {
                                Intent intent = new Intent("isr.LAUNCH");
                                intent.putExtra("fromResult", "fromResult");
                                context.startActivity(intent);
                                if (ResultActivity.getInstance() != null)
                                    ResultActivity.finishResultActivity();
                            }
                        });
                        if (onUpdateRoutineCompletionListener != null) {
                            onUpdateRoutineCompletionListener.onFailure(errorCode, errorMessage);
                        } else {
                            LoggerUtils.e("onFailure: " + errorMessage);
                        }
                    }
                });
    }


    public static void getTrainingPlans(Context context,final ListenersHandler.OnGetTrainingPlansCompletionListener onGetTrainingPlansCompletionListener) {
        User user = SaveUserPreferences.getUser(context);
        Api.getApiService(context).getTrainingPlans(user.getUserId(),user.getTrainerId()).enqueue(new ResponseHandler<List<TrainingPlanJsonModel>>() {
            @Override
            public void onSuccess(Response<List<TrainingPlanJsonModel>> response) {
                if (onGetTrainingPlansCompletionListener != null) {
                    onGetTrainingPlansCompletionListener.onSuccess(response.body());
                } else {
                    LoggerUtils.d("onSuccess");
                }
            }

            @Override
            public void onFailure(int errorCode, String errorMessage) {
                if (onGetTrainingPlansCompletionListener != null) {
                    onGetTrainingPlansCompletionListener.onFailure(errorCode, errorMessage);
                } else {
                    LoggerUtils.e("onFailure: " + errorMessage);
                }
            }
        });
    }

    public void Workouts() {
        Api.getApiService(context).getWorkouts().enqueue(new ResponseHandler<List<WorkoutJsonModel>>() {
            @Override
            public void onSuccess(Response<List<WorkoutJsonModel>> response) {
                List<WorkoutJsonModel>list = response.body();
                int size = list.size();
            }

            @Override
            public void onFailure(int errorCode, String errorMessage) {
            }
        });
    }

    public static void updateToken(final Context context, String message, final OnTokenUpdatedListener onTokenUpdatedListener){
        User user = SaveUserPreferences.getUser(context);
        if (user == null){
            user = Constants.user;
        }
        if(message.contains("Unauthorized") || message.contains("unauthorized"))
            Api.getApiService(context).updateToken(user.getTelephone()).enqueue(new ResponseHandler<User>() {
                @Override
                public void onSuccess(Response<User> response) {
                    UserPrefManager userPrefManager = new UserPrefManager(context);
                    userPrefManager.saveAccessToken(response.body().getAccessToken());
                    onTokenUpdatedListener.OnTokenUpdated();
                }

                @Override
                public void onFailure(int errorCode, String errorMessage) {
                    onTokenUpdatedListener.OnTokenFailed();
                }
            });
    }

}
