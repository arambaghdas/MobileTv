package train.apitrainclient.networks.retrofit;

import android.content.Context;
import android.widget.Toast;

import com.android.billingclient.api.Purchase;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import train.apitrainclient.listeners.OnFitnessGoalCompletionListener;
import train.apitrainclient.listeners.OnFoodListCompletionListener;
import train.apitrainclient.listeners.OnForgotPasswordCompletionListener;
import train.apitrainclient.listeners.OnGetCircuitCompletionListener;
import train.apitrainclient.listeners.OnGetExerciseCompletionListener;
import train.apitrainclient.listeners.OnGetFeqsListCompletionListener;
import train.apitrainclient.listeners.OnGetGraphInfoCompletionListener;
import train.apitrainclient.listeners.OnGetProgressDataCompletionListener;
import train.apitrainclient.listeners.OnGetTrainingLogsCompletionListener;
import train.apitrainclient.listeners.OnGetTrainingPlanListCompletionListener;
import train.apitrainclient.listeners.OnGetTrainingPlanListener;
import train.apitrainclient.listeners.OnGetTrainingPlansCompletionListener;
import train.apitrainclient.listeners.OnGetWorkoutsCompletionListener;
import train.apitrainclient.listeners.OnLoginViaEmailCompletionListener;
import train.apitrainclient.listeners.OnPasswordUpdatedListener;
import train.apitrainclient.listeners.OnSignUpCompletionListener;
import train.apitrainclient.listeners.OnTokenUpdatedListener;
import train.apitrainclient.listeners.OnUpdateUserAccountCompletionListener;
import train.apitrainclient.listeners.OnUserPackageTypeListener;
import train.apitrainclient.listeners.OnUserPackageUpdateListener;

import com.pttrackershared.models.eventbus.Circuit;
import com.pttrackershared.models.eventbus.FequsModel;
import com.pttrackershared.models.eventbus.FitnessGoalModel;
import com.pttrackershared.models.eventbus.GraphInfo;
import com.pttrackershared.models.eventbus.GraphInfoItem;
import com.pttrackershared.models.eventbus.Meals;
import com.pttrackershared.models.eventbus.PackageTypeModel;
import com.pttrackershared.models.eventbus.ProgressModel;
import com.pttrackershared.models.eventbus.ProgressModelBody;
import com.pttrackershared.models.eventbus.TrainingLog;
import com.pttrackershared.models.eventbus.TrainingPlan;
import com.pttrackershared.models.eventbus.TrainingPlanJsonModel;
import com.pttrackershared.models.eventbus.TrainingPlanView;
import com.pttrackershared.models.eventbus.User;

import com.pttrackershared.models.eventbus.Exercise;
import com.pttrackershared.models.eventbus.Workout;
import com.pttrackershared.models.eventbus.WorkoutJsonModel;

import train.apitrainclient.utils.LoggerUtils;
import train.apitrainclient.utils.SharedPrefManager;
import train.apitrainclient.utils.TimeUtils;
import train.apitrainclient.utils.UserPrefManager;
import train.apitrainclient.views.activities.HomeActivity;

public class RestApiManager {



    public static void forgotPassword(Context context, String email, OnForgotPasswordCompletionListener onForgotPasswordCompletionListener){
        Call<Object> call = Api.getApiService(context).forgotPassword(email);
        call.enqueue(new ResponseHandler<Object>() {
            @Override
            public void onSuccess(Response<Object> response) {
                onForgotPasswordCompletionListener.onSuccess(response.message());
            }

            @Override
            public void onFailure(int errorCode, String errorMessage) {
                updateToken(context,errorMessage, new OnTokenUpdatedListener() {
                    @Override
                    public void OnTokenUpdated() {
                        Call<Object> call = Api.getApiService(context).forgotPassword(email);
                        call.enqueue(new ResponseHandler<Object>() {
                            @Override
                            public void onSuccess(Response<Object> response) {
                                onForgotPasswordCompletionListener.onSuccess(response.message());
                            }

                            @Override
                            public void onFailure(int errorCode, String errorMessage) {
                                if (onForgotPasswordCompletionListener != null) {
                                    onForgotPasswordCompletionListener.onFailure(errorCode, errorMessage);
                                } else {
                                    LoggerUtils.e("onFailure: " + errorMessage);
                                }
                            }
                        });
                    }

                    @Override
                    public void OnTokenFailed() {
                        HomeActivity.getInstance().logout();
                    }
                });
                if (onForgotPasswordCompletionListener != null) {
                    onForgotPasswordCompletionListener.onFailure(errorCode, errorMessage);
                } else {
                    LoggerUtils.e("onFailure: " + errorMessage);
                }
            }
        });
    }


    public static void getFeqsList(Context context, int language, OnGetFeqsListCompletionListener onGetFeqsListCompletionListener){
        Api.getApiService(context).geFeqsList(language)
                .enqueue(new ResponseHandler<List<FequsModel>>() {
                    @Override
                    public void onSuccess(Response<List<FequsModel>> response) {
                        onGetFeqsListCompletionListener.onSuccess(response.body());
                    }

                    @Override
                    public void onFailure(int errorCode, String errorMessage) {
                        updateToken(context,errorMessage, new OnTokenUpdatedListener() {
                            @Override
                            public void OnTokenUpdated() {
                                Api.getApiService(context).geFeqsList(language)
                                        .enqueue(new ResponseHandler<List<FequsModel>>() {
                                            @Override
                                            public void onSuccess(Response<List<FequsModel>> response) {
                                                onGetFeqsListCompletionListener.onSuccess(response.body());
                                            }

                                            @Override
                                            public void onFailure(int errorCode, String errorMessage) {


                                                onGetFeqsListCompletionListener.onFailure(errorCode,errorMessage);
                                            }
                                        });
                            }

                            @Override
                            public void OnTokenFailed() {
                                HomeActivity.getInstance().logout();
                            }
                        });

                      onGetFeqsListCompletionListener.onFailure(errorCode,errorMessage);
                    }
                });
    }


    public static void updateUserWeight(Context context, String weight, String date, OnGetGraphInfoCompletionListener onGetGraphInfoCompletionListener){
        Api.getApiService(context).updateWeight(weight,date).enqueue(new ResponseHandler<GraphInfo>() {
            @Override
            public void onSuccess(Response<GraphInfo> response) {
                    onGetGraphInfoCompletionListener.onSuccess(null,null);
            }

            @Override
            public void onFailure(int errorCode, String errorMessage) {
                    updateToken(context,errorMessage, new OnTokenUpdatedListener() {
                        @Override
                        public void OnTokenUpdated() {
                            Api.getApiService(context).updateWeight(weight,date).enqueue(new ResponseHandler<GraphInfo>() {
                                @Override
                                public void onSuccess(Response<GraphInfo> response) {
                                    onGetGraphInfoCompletionListener.onSuccess(null,null);
                                }

                                @Override
                                public void onFailure(int errorCode, String errorMessage) {
                                    onGetGraphInfoCompletionListener.onFailure(errorCode, errorMessage);
                                }
                            });
                        }

                        @Override
                        public void OnTokenFailed() {
                            HomeActivity.getInstance().logout();
                        }
                    });
                    onGetGraphInfoCompletionListener.onFailure(errorCode, errorMessage);
            }
        });
    }

    public static void getTrainingLogs(Context context, int pageNum, OnGetTrainingLogsCompletionListener onGetTrainingLogsCompletionListener){
        final int lastPageCalled = SharedPrefManager.getTrainingPlanPage(context);
        final int pageCount = SharedPrefManager.getTrainingPlanPageCount(context);
        User user= SharedPrefManager.getUser(context);
        if (pageCount == 0 || lastPageCalled < pageCount){
            Api.getApiService(context).getTrainingLogs(pageNum,user.getUserId()).enqueue(new ResponseHandler<List<WorkoutJsonModel>>() {
                @Override
                public void onSuccess(Response<List<WorkoutJsonModel>> response) {
                    SharedPrefManager.setTrainingPlanPage(context, pageNum);
                    SharedPrefManager.setTrainingPlanPageCount(context, pageCount);
                    if (pageNum <= pageCount)
                        SharedPrefManager.addTrainingLogsToList(context, response.body());
                    List<TrainingLog> workoutList = TrainingLog.makeTrainingLogs(response.body());
                    onGetTrainingLogsCompletionListener.onSuccess(workoutList,
                            Integer.parseInt(response.headers().get("X-Pagination-Current-Page")),
                            Integer.parseInt(response.headers().get("X-Pagination-Page-Count")));
                }

                @Override
                public void onFailure(int errorCode, String errorMessage) {
                    updateToken(context,errorMessage, new OnTokenUpdatedListener() {
                        @Override
                        public void OnTokenUpdated() {
                            Api.getApiService(context).getTrainingLogs(pageNum,user.getUserId()).enqueue(new ResponseHandler<List<WorkoutJsonModel>>() {
                                @Override
                                public void onSuccess(Response<List<WorkoutJsonModel>> response) {
                                    SharedPrefManager.setTrainingPlanPage(context, pageNum);
                                    SharedPrefManager.setTrainingPlanPageCount(context, pageCount);
                                    if (pageNum <= pageCount)
                                        SharedPrefManager.addTrainingLogsToList(context, response.body());
                                    List<TrainingLog> workoutList = TrainingLog.makeTrainingLogs(response.body());
                                    onGetTrainingLogsCompletionListener.onSuccess(workoutList,
                                            Integer.parseInt(response.headers().get("X-Pagination-Current-Page")),
                                            Integer.parseInt(response.headers().get("X-Pagination-Page-Count")));
                                }

                                @Override
                                public void onFailure(int errorCode, String errorMessage) {
                                    if (onGetTrainingLogsCompletionListener != null) {
                                        onGetTrainingLogsCompletionListener.onFailure(errorCode, errorMessage);
                                    } else {
                                        LoggerUtils.e("onFailure: " + errorMessage);
                                    }
                                }
                            });
                        }

                        @Override
                        public void OnTokenFailed() {
                            HomeActivity.getInstance().logout();
                        }
                    });


                    if (onGetTrainingLogsCompletionListener != null) {
                        onGetTrainingLogsCompletionListener.onFailure(errorCode, errorMessage);
                    } else {
                        LoggerUtils.e("onFailure: " + errorMessage);
                    }
                }
            });
        }
    }

    public static void getWorkouts(Context context, OnGetWorkoutsCompletionListener onGetWorkoutsCompletionListener){
        Api.getApiService(context).getWorkouts().enqueue(new ResponseHandler<List<WorkoutJsonModel>>() {
            @Override
            public void onSuccess(Response<List<WorkoutJsonModel>> response) {
                SharedPrefManager.setWorkoutsList(context, response.body());
                List<Workout> workoutList = Workout.makeWorkouts(response.body());
                onGetWorkoutsCompletionListener.onSuccess(workoutList);
            }

            @Override
            public void onFailure(int errorCode, String errorMessage) {
                updateToken(context,errorMessage, new OnTokenUpdatedListener() {
                    @Override
                    public void OnTokenUpdated() {
                        Api.getApiService(context).getWorkouts().enqueue(new ResponseHandler<List<WorkoutJsonModel>>() {
                            @Override
                            public void onSuccess(Response<List<WorkoutJsonModel>> response) {
                                SharedPrefManager.setWorkoutsList(context, response.body());
                                List<Workout> workoutList = Workout.makeWorkouts(response.body());
                                onGetWorkoutsCompletionListener.onSuccess(workoutList);
                            }

                            @Override
                            public void onFailure(int errorCode, String errorMessage) {
                                onGetWorkoutsCompletionListener.onFailure(errorCode, errorMessage);
                            }
                        });
                    }

                    @Override
                    public void OnTokenFailed() {
                        HomeActivity.getInstance().logout();
                    }
                });
                    onGetWorkoutsCompletionListener.onFailure(errorCode, errorMessage);
            }
        });
    }

    public static void getFoodList(Context context, OnFoodListCompletionListener onGetWorkoutsCompletionListener){
        User user = SharedPrefManager.getUser(context);
        Api.getApiService(context).getFoodList(user.getTrainerId(), user.getUserId())
                .enqueue(new ResponseHandler<List<Meals>>() {
                    @Override
                    public void onSuccess(Response<List<Meals>> response) {
                        onGetWorkoutsCompletionListener.onSuccess(response.body());
                    }

                    @Override
                    public void onFailure(int errorCode, String errorMessage) {
                        updateToken(context,errorMessage, new OnTokenUpdatedListener() {
                            @Override
                            public void OnTokenUpdated() {
                                Api.getApiService(context).getFoodList(user.getTrainerId(), user.getUserId())
                                        .enqueue(new ResponseHandler<List<Meals>>() {
                                            @Override
                                            public void onSuccess(Response<List<Meals>> response) {
                                                onGetWorkoutsCompletionListener.onSuccess(response.body());
                                            }

                                            @Override
                                            public void onFailure(int errorCode, String errorMessage) {

                                                onGetWorkoutsCompletionListener.onFailure(errorCode, errorMessage);
                                            }
                                        });
                            }

                            @Override
                            public void OnTokenFailed() {
                              HomeActivity.getInstance().logout();
                            }
                        });
                        onGetWorkoutsCompletionListener.onFailure(errorCode, errorMessage);
                    }
                });
    }

    public static void getExercises(Context context, OnGetExerciseCompletionListener onGetExerciseCompletionListener){
        Api.getApiService(context).getExercises().enqueue(new ResponseHandler<List<Exercise>>() {
            @Override
            public void onSuccess(Response<List<Exercise>> response) {
                SharedPrefManager.setExercisesList(context, response.body());
                List<Exercise> exerciseList = response.body();
               onGetExerciseCompletionListener.onSuccess(response.body());
            }

            @Override
            public void onFailure(int errorCode, String errorMessage) {
                updateToken(context,errorMessage, new OnTokenUpdatedListener() {
                    @Override
                    public void OnTokenUpdated() {
                        Api.getApiService(context).getExercises().enqueue(new ResponseHandler<List<Exercise>>() {
                            @Override
                            public void onSuccess(Response<List<Exercise>> response) {
                                SharedPrefManager.setExercisesList(context, response.body());
                                List<Exercise> exerciseList = response.body();
                                onGetExerciseCompletionListener.onSuccess(response.body());
                            }

                            @Override
                            public void onFailure(int errorCode, String errorMessage) {
                                onGetExerciseCompletionListener.onFailure(errorCode,errorMessage);
                            }
                        });
                    }

                    @Override
                    public void OnTokenFailed() {
                        HomeActivity.getInstance().logout();
                    }
                });
                onGetExerciseCompletionListener.onFailure(errorCode,errorMessage);
            }
        });
    }

    public static void getCircuits(Context context, OnGetCircuitCompletionListener onGetCircuitCompletionListener){
        Api.getApiService(context).getCircuits().enqueue(new ResponseHandler<List<Circuit>>() {
            @Override
            public void onSuccess(Response<List<Circuit>> response) {
                SharedPrefManager.setCircuitsList(context,response.body());
                onGetCircuitCompletionListener.onSuccess(response.body());
            }

            @Override
            public void onFailure(int errorCode, String errorMessage) {
                updateToken(context,errorMessage, new OnTokenUpdatedListener() {
                    @Override
                    public void OnTokenUpdated() {
                        Api.getApiService(context).getCircuits().enqueue(new ResponseHandler<List<Circuit>>() {
                            @Override
                            public void onSuccess(Response<List<Circuit>> response) {
                                SharedPrefManager.setCircuitsList(context,response.body());
                                onGetCircuitCompletionListener.onSuccess(response.body());
                            }

                            @Override
                            public void onFailure(int errorCode, String errorMessage) {
                                onGetCircuitCompletionListener.onFailure(errorCode, errorMessage);
                            }
                        });
                    }

                    @Override
                    public void OnTokenFailed() {
                        HomeActivity.getInstance().logout();
                    }
                });
                onGetCircuitCompletionListener.onFailure(errorCode, errorMessage);
            }
        });
    }

    public static void updateProfileImage(Context context, User user, File file, OnUpdateUserAccountCompletionListener onUpdateUserAccountCompletionListener){
        RequestBody mFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("imageFile", file.getName(), mFile);
        Api.getApiService(context).uploadProfileImage(user.getUserId(),
    fileToUpload
        ).enqueue(new ResponseHandler<Object>() {
        @Override
        public void onSuccess(Response<Object> response) {
            onUpdateUserAccountCompletionListener.onSuccess(response.body().toString());
        }

        @Override
        public void onFailure(int errorCode, String errorMessage) {
            updateToken(context,errorMessage, new OnTokenUpdatedListener() {
                @Override
                public void OnTokenUpdated() {
                    Api.getApiService(context).uploadProfileImage(user.getUserId(),
                            fileToUpload
                    ).enqueue(new ResponseHandler<Object>() {
                        @Override
                        public void onSuccess(Response<Object> response) {
                            onUpdateUserAccountCompletionListener.onSuccess(response.body().toString());
                        }

                        @Override
                        public void onFailure(int errorCode, String errorMessage) {
                            onUpdateUserAccountCompletionListener.onFailure(errorCode, errorMessage);
                        }
                    });
                }

                @Override
                public void OnTokenFailed() {
                    HomeActivity.getInstance().logout();
                }
            });
            onUpdateUserAccountCompletionListener.onFailure(errorCode, errorMessage);
        }
    });
}

    public static void updatePackagePayment(Context context, Purchase purchase, double price, double payment_amount, OnUserPackageUpdateListener onUserPackageUpdateListener){
        User user = SharedPrefManager.getUser(context);
        int uid = user.getUserId();
        int order_count = 1;
        int shipping_price = 0;
        String bill_number = purchase.getPurchaseToken();
        int payment_type = 3;
        int status = 1;
        String date_payed = "" + purchase.getPurchaseTime();
        String date_created = "" + purchase.getPurchaseTime();
        String date_issued = "" + purchase.getPurchaseTime();
        String date_storno = "" + purchase.getPurchaseTime();
        Api.getApiService(context).updatePackagePayment(uid, price, order_count, shipping_price,
                bill_number, payment_type, payment_amount, status, date_payed, date_created, date_issued, date_storno, "", "").enqueue(new ResponseHandler<Object>() {
            @Override
            public void onSuccess(Response<Object> response) {
                onUserPackageUpdateListener.onSuccess(response.body());

            }

            @Override
            public void onFailure(int errorCode, String errorMessage) {
                updateToken(context,errorMessage, new OnTokenUpdatedListener() {
                    @Override
                    public void OnTokenUpdated() {
                        Api.getApiService(context).updatePackagePayment(uid, price, order_count, shipping_price,
                                bill_number, payment_type, payment_amount, status, date_payed, date_created, date_issued, date_storno, "", "").enqueue(new ResponseHandler<Object>() {
                            @Override
                            public void onSuccess(Response<Object> response) {
                                onUserPackageUpdateListener.onSuccess(response.body());

                            }

                            @Override
                            public void onFailure(int errorCode, String errorMessage) {
                                onUserPackageUpdateListener.onFailure(errorCode, errorMessage);
                            }
                        });
                    }

                    @Override
                    public void OnTokenFailed() {
                        HomeActivity.getInstance().logout();
                    }
                });
                onUserPackageUpdateListener.onFailure(errorCode, errorMessage);
            }
        });
    }

    public static void getUserPackageType(Context context, int clientId, OnUserPackageTypeListener onUserPackageTypeListener){
        Api.getApiService(context).getUserPacakgeType(clientId).enqueue(new ResponseHandler<List<PackageTypeModel>>() {
            @Override
            public void onSuccess(Response<List<PackageTypeModel>> response) {
                onUserPackageTypeListener.onSuccess(response.body());
            }

            @Override
            public void onFailure(int errorCode, String errorMessage) {
                updateToken(context,errorMessage, new OnTokenUpdatedListener() {
                    @Override
                    public void OnTokenUpdated() {
                        Api.getApiService(context).getUserPacakgeType(clientId).enqueue(new ResponseHandler<List<PackageTypeModel>>() {
                            @Override
                            public void onSuccess(Response<List<PackageTypeModel>> response) {
                                onUserPackageTypeListener.onSuccess(response.body());
                            }

                            @Override
                            public void onFailure(int errorCode, String errorMessage) {
                                onUserPackageTypeListener.onFailure(errorCode, errorMessage);
                            }
                        });
                    }

                    @Override
                    public void OnTokenFailed() {
                        HomeActivity.getInstance().logout();
                    }
                });
                onUserPackageTypeListener.onFailure(errorCode, errorMessage);
            }
        });
    }

    public static void updatePackage(Context context,int clientId,int packid,int pid,OnUserPackageUpdateListener onUserPackageUpdateListener){
        Api.getApiService(context).updatePackage(clientId, packid, pid).enqueue(new ResponseHandler<Object>() {
            @Override
            public void onSuccess(Response<Object> response) {
                onUserPackageUpdateListener.onSuccess(response.body());
            }

            @Override
            public void onFailure(int errorCode, String errorMessage) {
                updateToken(context,errorMessage, new OnTokenUpdatedListener() {
                    @Override
                    public void OnTokenUpdated() {
                        Api.getApiService(context).updatePackage(clientId, packid, pid).enqueue(new ResponseHandler<Object>() {
                            @Override
                            public void onSuccess(Response<Object> response) {
                                onUserPackageUpdateListener.onSuccess(response.body());
                            }

                            @Override
                            public void onFailure(int errorCode, String errorMessage) {
                                onUserPackageUpdateListener.onFailure(errorCode, errorMessage);
                            }
                        });
                    }

                    @Override
                    public void OnTokenFailed() {
                        HomeActivity.getInstance().logout();
                    }
                });
                onUserPackageUpdateListener.onFailure(errorCode, errorMessage);
            }
        });
    }

    public static void loginViaEmail(Context context, String email, String password, OnLoginViaEmailCompletionListener onLoginViaEmailCompletionListener){
        Api.getApiService(context).login(email, password).enqueue(new ResponseHandler<User>() {
            @Override
            public void onSuccess(Response<User> response) {
                onLoginViaEmailCompletionListener.onSuccess(User.convertToUser(response.body()), response.body().getAccessToken());
            }

            @Override
            public void onFailure(int errorCode, String errorMessage) {
                onLoginViaEmailCompletionListener.onFailure(errorCode, errorMessage);
                if (errorMessage.contains("Unauthorized") || errorMessage.contains("unauthorized")){

                }
            }
        });
    }

    public static void getTrainingPlans(Context context,User user, OnGetTrainingPlansCompletionListener onGetTrainingPlansCompletionListener){
        Api.getApiService(context).getTrainingPlans(user.getUserId(),user.getTrainerId()).enqueue(new ResponseHandler<List<TrainingPlanJsonModel>>() {
            @Override
            public void onSuccess(Response<List<TrainingPlanJsonModel>> response) {
                onGetTrainingPlansCompletionListener.onSuccess(response.body());
            }

            @Override
            public void onFailure(int errorCode, String errorMessage) {
                updateToken(context,errorMessage, new OnTokenUpdatedListener() {
                    @Override
                    public void OnTokenUpdated() {
                        Api.getApiService(context).getTrainingPlans(user.getUserId(),user.getTrainerId()).enqueue(new ResponseHandler<List<TrainingPlanJsonModel>>() {
                            @Override
                            public void onSuccess(Response<List<TrainingPlanJsonModel>> response) {
                                onGetTrainingPlansCompletionListener.onSuccess(response.body());
                            }

                            @Override
                            public void onFailure(int errorCode, String errorMessage) {
                                onGetTrainingPlansCompletionListener.onFailure(errorCode, errorMessage);
                            }
                        });
                    }

                    @Override
                    public void OnTokenFailed() {
                        HomeActivity.getInstance().logout();
                    }
                });
                onGetTrainingPlansCompletionListener.onFailure(errorCode, errorMessage);
            }
        });
    }

    public static void getAllTrainingPlans(Context context, OnGetTrainingPlansCompletionListener onGetTrainingPlansCompletionListener){
        Api.getApiService(context).getAllTrainingPlans().enqueue(new ResponseHandler<List<TrainingPlanJsonModel>>() {
            @Override
            public void onSuccess(Response<List<TrainingPlanJsonModel>> response) {
                onGetTrainingPlansCompletionListener.onSuccess(response.body());
            }

            @Override
            public void onFailure(int errorCode, String errorMessage) {
                updateToken(context,errorMessage, new OnTokenUpdatedListener() {
                    @Override
                    public void OnTokenUpdated() {
                        Api.getApiService(context).getAllTrainingPlans().enqueue(new ResponseHandler<List<TrainingPlanJsonModel>>() {
                            @Override
                            public void onSuccess(Response<List<TrainingPlanJsonModel>> response) {
                                onGetTrainingPlansCompletionListener.onSuccess(response.body());
                            }

                            @Override
                            public void onFailure(int errorCode, String errorMessage) {
                                onGetTrainingPlansCompletionListener.onFailure(errorCode, errorMessage);
                            }
                        });
                    }

                    @Override
                    public void OnTokenFailed() {
                        HomeActivity.getInstance().logout();
                    }
                });
                onGetTrainingPlansCompletionListener.onFailure(errorCode, errorMessage);
            }
        });
    }

    public static void getGraphInfo(Context context,OnGetGraphInfoCompletionListener onGetGraphInfoCompletionListener){
        Api.getApiService(context).getGraphInfo().enqueue(new ResponseHandler<GraphInfo>() {
            @Override
            public void onSuccess(Response<GraphInfo> response) {
                    GraphInfo graphInfoJsonModel = response.body();
                    GraphInfo graphInfo = GraphInfo.convertToGraphInfo(graphInfoJsonModel);
                    List<GraphInfoItem> graphInfoItemList = new ArrayList<>();

                    if(graphInfoJsonModel.getGraphData() != null) {
                        for (GraphInfoItem graphInfoItemJsonModel : graphInfoJsonModel.getGraphData()){
                            graphInfoItemList.add(GraphInfo.convertToGraphInfoItem(graphInfoItemJsonModel));
                        }
                    }
                    onGetGraphInfoCompletionListener.onSuccess(graphInfo, graphInfoItemList);

            }

            @Override
            public void onFailure(int errorCode, String errorMessage) {
                if (onGetGraphInfoCompletionListener != null) {
                    onGetGraphInfoCompletionListener.onFailure(errorCode, errorMessage);
                } else {
                    LoggerUtils.e("onFailure: " + errorMessage);
                }
            }
        });
    }

    public static void getFitnessGoal(Context context, OnFitnessGoalCompletionListener onFitnessGoalCompletionListener){
        Api.getApiService(context).getFitnessGoal().enqueue(new ResponseHandler<List<FitnessGoalModel>>() {
            @Override
            public void onSuccess(Response<List<FitnessGoalModel>> response) {
                onFitnessGoalCompletionListener.onSuccess(response.body());
            }

            @Override
            public void onFailure(int errorCode, String errorMessage) {
                updateToken(context,errorMessage, new OnTokenUpdatedListener() {
                    @Override
                    public void OnTokenUpdated() {
                        Api.getApiService(context).getFitnessGoal().enqueue(new ResponseHandler<List<FitnessGoalModel>>() {
                            @Override
                            public void onSuccess(Response<List<FitnessGoalModel>> response) {
                                onFitnessGoalCompletionListener.onSuccess(response.body());
                            }

                            @Override
                            public void onFailure(int errorCode, String errorMessage) {
                                onFitnessGoalCompletionListener.onFailure(errorCode, errorMessage);
                            }
                        });
                    }

                    @Override
                    public void OnTokenFailed() {
                        HomeActivity.getInstance().logout();
                    }
                });
                onFitnessGoalCompletionListener.onFailure(errorCode, errorMessage);
            }
        });
    }

    public static void getSingleTrainingPlan(Context context, OnGetTrainingPlanListener onGetTrainingPlanListener){
        User user = SharedPrefManager.getUser(context);

        Api.getApiService(context).getTrainingPlansView(user.getUserId()).enqueue(new ResponseHandler<TrainingPlanView>() {
            @Override
            public void onSuccess(Response<TrainingPlanView> response) {
                    onGetTrainingPlanListener.onSuccess(response.body());
            }

            @Override
            public void onFailure(int errorCode, String errorMessage) {
                updateToken(context,errorMessage, new OnTokenUpdatedListener() {
                    @Override
                    public void OnTokenUpdated() {
                        Api.getApiService(context).getTrainingPlansView(user.getUserId()).enqueue(new ResponseHandler<TrainingPlanView>() {
                            @Override
                            public void onSuccess(Response<TrainingPlanView> response) {
                                onGetTrainingPlanListener.onSuccess(response.body());
                            }

                            @Override
                            public void onFailure(int errorCode, String errorMessage) {
                                onGetTrainingPlanListener.onFailure(errorCode, errorMessage);

                            }
                        });
                    }

                    @Override
                    public void OnTokenFailed() {
                        HomeActivity.getInstance().logout();
                    }
                });
                    onGetTrainingPlanListener.onFailure(errorCode, errorMessage);

            }
        });
    }


    public static void userSignUp(Context context,String name, String surname, String email, String mobile, String password, String salt, int selectedMember,
                                  int package_item_clients, int users_groups_clients, int profile_users,
                                  int package_item_users, int package_clients, int package_users,
                                  final OnSignUpCompletionListener onSignUpCompletionListener){
        Api.getApiService(context).userSignup(name, surname, email, mobile, password, salt,
                selectedMember, TimeUtils.getCurrentDate(),
                package_item_clients, users_groups_clients, profile_users, package_item_users, package_clients, package_users).enqueue(new ResponseHandler<Object>() {
            @Override
            public void onSuccess(Response<Object> response) {
                onSignUpCompletionListener.onSuccess(response.body());
            }

            @Override
            public void onFailure(int errorCode, String errorMessage) {
                onSignUpCompletionListener.onFailure(errorCode, errorMessage);

            }
        });
    }

    public static void updateUserAccountLogin2(Context context,String telephoneNo,String email,final User user,int require_training_plan, final OnLoginViaEmailCompletionListener onUpdateUserAccountCompletionListener) {
        Api.getApiService(context).updateUserAccountLogin2(
                telephoneNo,
                email,
                user.getTelephone(),
                user.getDobString(),
                user.getGender(),
                user.getHeight(),
                user.getWeight(),
                user.getFitness_goal(),
                user.getGoalDateString(),
                require_training_plan
        ).enqueue(new ResponseHandler<User>() {
            @Override
            public void onSuccess(Response<User> response) {
                if (onUpdateUserAccountCompletionListener != null) {
                    onUpdateUserAccountCompletionListener.onSuccess(
                           User.convertToUser(response.body()),response.body().getAccessToken());
                } else {
                    LoggerUtils.d("onSuccess");
                }
            }

            @Override
            public void onFailure(int errorCode, String errorMessage) {
                if (onUpdateUserAccountCompletionListener != null) {
                    onUpdateUserAccountCompletionListener.onFailure(errorCode, errorMessage);
                } else {
                    LoggerUtils.e("onFailure: " + errorMessage);
                }
            }
        });
    }

    public static void getTrainingPlanList_updateuser(Context context,int selectedFit_goal_id, final OnGetTrainingPlanListCompletionListener onGetTrainingPlanListCompletionListener) {

        Api.getApiService(context).getTrainingPlanList_updateuser(selectedFit_goal_id).enqueue(new ResponseHandler<List<TrainingPlanJsonModel>>() {
            @Override
            public void onSuccess(Response<List<TrainingPlanJsonModel>> response) {
                if (onGetTrainingPlanListCompletionListener != null) {
                    onGetTrainingPlanListCompletionListener.onSuccess(response.body());
                } else {
                    LoggerUtils.d("onSuccess");
                }
            }

            @Override
            public void onFailure(int errorCode, String errorMessage) {
                updateToken(context,errorMessage, new OnTokenUpdatedListener() {
                    @Override
                    public void OnTokenUpdated() {
                        Api.getApiService(context).getTrainingPlanList_updateuser(selectedFit_goal_id).enqueue(new ResponseHandler<List<TrainingPlanJsonModel>>() {
                            @Override
                            public void onSuccess(Response<List<TrainingPlanJsonModel>> response) {
                                if (onGetTrainingPlanListCompletionListener != null) {
                                    onGetTrainingPlanListCompletionListener.onSuccess(response.body());
                                } else {
                                    LoggerUtils.d("onSuccess");
                                }
                            }

                            @Override
                            public void onFailure(int errorCode, String errorMessage) {
                                if (onGetTrainingPlanListCompletionListener != null) {
                                    onGetTrainingPlanListCompletionListener.onFailure(errorCode, errorMessage);
                                } else {
                                    LoggerUtils.e("onFailure: " + errorMessage);
                                }
                            }
                        });
                    }

                    @Override
                    public void OnTokenFailed() {
                        HomeActivity.getInstance().logout();
                    }
                });
                if (onGetTrainingPlanListCompletionListener != null) {
                    onGetTrainingPlanListCompletionListener.onFailure(errorCode, errorMessage);
                } else {
                    LoggerUtils.e("onFailure: " + errorMessage);
                }
            }
        });
    }

    public static void setTrainingPlan(Context context, User user, TrainingPlanJsonModel planName, OnGetTrainingPlansCompletionListener onGetTrainingPlansCompletionListener){

        int height = 100;
        if (user.getHeight() != null) height = Integer.parseInt(user.getHeight());
        List<TrainingPlanJsonModel>list = new ArrayList<>();

        int finalHeight = height;
        Api.getApiService(context).assignPlanToClient(user.getTrainerId(),user.getUserId(),planName.getUsers_plan_id(),planName.getPlanName(), String.valueOf(user.getUserId()),height,
                planName.getWorkoutId1(),planName.getWorkoutId2(),planName.getWorkoutId3(),planName.getWorkoutId4(),planName.getWorkoutId5(),planName.getWorkoutId6(),
                planName.getWorkoutId7(),planName.getWeek()).enqueue(new ResponseHandler<Object>() {
            @Override
            public void onSuccess(Response<Object> response) {
                onGetTrainingPlansCompletionListener.onSuccess(list);
            }

            @Override
            public void onFailure(int errorCode, String errorMessage) {
                updateToken(context,errorMessage, new OnTokenUpdatedListener() {
                    @Override
                    public void OnTokenUpdated() {
                        Api.getApiService(context).assignPlanToClient(user.getTrainerId(),user.getUserId(),planName.getUsers_plan_id(),planName.getPlanName(), String.valueOf(user.getUserId()), finalHeight,
                                planName.getWorkoutId1(),planName.getWorkoutId2(),planName.getWorkoutId3(),planName.getWorkoutId4(),planName.getWorkoutId5(),planName.getWorkoutId6(),
                                planName.getWorkoutId7(),planName.getWeek()).enqueue(new ResponseHandler<Object>() {
                            @Override
                            public void onSuccess(Response<Object> response) {
                                onGetTrainingPlansCompletionListener.onSuccess(list);
                            }

                            @Override
                            public void onFailure(int errorCode, String errorMessage) {
                            }
                        });
                    }

                    @Override
                    public void OnTokenFailed() {
                            HomeActivity.getInstance().logout();
                    }
                });
            }
        });
    }

    public static void updatePassword(Context context,User user, String currentPassword, String newPassword,
                               final OnPasswordUpdatedListener onUpdateUserPasswordCompletionListener) {
        Call<Object> call = Api.getApiService(context).updatePassword(user.getUserId(), currentPassword, newPassword);
        call.enqueue(new ResponseHandler<Object>() {
            @Override
            public void onSuccess(Response<Object> response) {
                if (onUpdateUserPasswordCompletionListener != null) {
                    onUpdateUserPasswordCompletionListener.onSuccess(response.body());
                } else {
                    LoggerUtils.d("onSuccess");
                }
            }

            @Override
            public void onFailure(int errorCode, String errorMessage) {
                updateToken(context, errorMessage, new OnTokenUpdatedListener() {
                    @Override
                    public void OnTokenUpdated() {
                        Call<Object> call = Api.getApiService(context).updatePassword(user.getUserId(), currentPassword, newPassword);
                        call.enqueue(new ResponseHandler<Object>() {
                            @Override
                            public void onSuccess(Response<Object> response) {
                                if (onUpdateUserPasswordCompletionListener != null) {
                                    onUpdateUserPasswordCompletionListener.onSuccess(response.body());
                                } else {
                                    LoggerUtils.d("onSuccess");
                                }
                            }

                            @Override
                            public void onFailure(int errorCode, String errorMessage) {
                                if (onUpdateUserPasswordCompletionListener != null) {
                                    onUpdateUserPasswordCompletionListener.onFailure(errorCode, errorMessage);
                                } else {
                                    LoggerUtils.e("onFailure: " + errorMessage);
                                }
                            }
                        });
                    }

                    @Override
                    public void OnTokenFailed() {
                        HomeActivity.getInstance().logout();
                    }
                });
                if (onUpdateUserPasswordCompletionListener != null) {
                    onUpdateUserPasswordCompletionListener.onFailure(errorCode, errorMessage);
                } else {
                    LoggerUtils.e("onFailure: " + errorMessage);
                }
            }
        });
    }

    public static void getProgressInfoData(Context context, OnGetProgressDataCompletionListener onGetProgressDataCompletionListener){
        User user = SharedPrefManager.getUser(context);
        Calendar calendar = Calendar.getInstance();
        Api.getApiService(context).getProgressInfo(user.getUserId(),calendar.get(Calendar.WEEK_OF_YEAR))
                .enqueue(new ResponseHandler<ProgressModel>() {
                    @Override
                    public void onSuccess(Response<ProgressModel> response) {
                        onGetProgressDataCompletionListener.onSuccess(response.body());
                    }

                    @Override
                    public void onFailure(int errorCode, String errorMessage) {
                        updateToken(context,errorMessage, new OnTokenUpdatedListener() {
                            @Override
                            public void OnTokenUpdated() {
                                Api.getApiService(context).getProgressInfo(1058,52)
                                        .enqueue(new ResponseHandler<ProgressModel>() {
                                            @Override
                                            public void onSuccess(Response<ProgressModel> response) {
                                                onGetProgressDataCompletionListener.onSuccess(response.body());
                                            }

                                            @Override
                                            public void onFailure(int errorCode, String errorMessage) {


                                                onGetProgressDataCompletionListener.onFailure(errorCode,errorMessage);
                                            }
                                        });
                            }

                            @Override
                            public void OnTokenFailed() {
                                HomeActivity.getInstance().logout();
                            }
                        });

                        onGetProgressDataCompletionListener.onFailure(errorCode,errorMessage);
                    }
                });
    }

    public static void updateToken(Context context,String message, OnTokenUpdatedListener onTokenUpdatedListener){
        String messages = message;
        if(message.contains("Unauthorized") || message.contains("unauthorized"))
        Api.getApiService(context).updateToken(SharedPrefManager.getUser(context).getTelephone()).enqueue(new ResponseHandler<User>() {
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
