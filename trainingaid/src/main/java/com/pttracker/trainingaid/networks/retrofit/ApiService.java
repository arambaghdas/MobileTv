package com.pttracker.trainingaid.networks.retrofit;



import com.pttracker.trainingaid.networks.CircuitJsonModel;
import com.pttrackershared.models.eventbus.ExerciseJsonModel;
import com.pttracker.trainingaid.networks.GraphInfoJsonModel;
import com.pttracker.trainingaid.networks.PackageTypeJsonModel;
import com.pttracker.trainingaid.networks.UserJsonModel;
import com.pttrackershared.models.eventbus.TrainingPlanJsonModel;
import com.pttrackershared.models.eventbus.TrainingPlanViewJsonModel;
import com.pttrackershared.models.eventbus.User;
import com.pttrackershared.models.eventbus.WorkoutJsonModel;
import com.pttracker.trainingaid.models.ClientExercise;
import com.pttracker.trainingaid.models.ClientPlanRange;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;


/**
 * @author Atif Ali
 * @since August 29, 2017 5:02 PM
 */

public interface ApiService
{
    @POST("client/login")
    @FormUrlEncoded
    Call<UserJsonModel> login(@Field("email") String email
            , @Field("password") String password);

    @GET("clients-update")
    Call<GraphInfoJsonModel> getGraphInfo(/*@Query("email") String email*/);

    @POST("clients-update/create")
    @FormUrlEncoded
    Call<GraphInfoJsonModel> updateWeight(@Field("weight") String weight
            , @Field("date") String date);

    @POST("clients-plan/batch")
    @FormUrlEncoded
    Call<List<TrainingPlanJsonModel>> getTrainingPlans(@Field("client_id") int client_id,
                                                       @Field("trainer_id") int trainer_id);

    @POST("clients-plan/client-plan")
    @FormUrlEncoded
    Call<TrainingPlanViewJsonModel> getTrainingPlansView(@Field("client_id") int client_id);

    @POST("clients-plan/list")
    @FormUrlEncoded
    Call<List<TrainingPlanJsonModel>> getTrainingPlansList(@Field("lang_trainer_id") int lang_trainer_id,
                                                           @Field("trainer_id") int trainer_id,
                                                           @Field("client_id") int client_id);

    @POST("clients-plan/get-training-plan")
    @FormUrlEncoded
    Call<List<TrainingPlanJsonModel>> getTrainingPlanList_updateuser(
            @Field("fit_goal_id") int selectedFit_goal_id);

    @GET("exercise")
    Call<List<ExerciseJsonModel>> getExercises();

    @GET("circuits?expand=exercises")
    Call<List<CircuitJsonModel>> getCircuits();

    @GET("workouts?expand=circuits")
    Call<List<WorkoutJsonModel>> getWorkouts();

    @POST("clients-workout/my-training-log")
    @FormUrlEncoded
    Call<List<WorkoutJsonModel>> getTrainingLogs(@Query("page") int page,
                                                 @Field("client_id") int client_id);

    @POST("clients-workout/create")
    @FormUrlEncoded
    Call<WorkoutJsonModel> updateTrainingLog(
//            @Query("id") int routineId
//            , @Query("plan_id") int planId,
            @Field("workout_id") int workout_id
            , @Field("client_id") int client_id
            , @Field("trainer_id") int trainer_id
            , @Field("workoutstatus") int workoutstatus
            , @Field("Date_completed") String date_completed
            , @Field("Time_completed") String time_completed
            , @Field("feedback") String feedback
            , @Field("Max_BPM") String max_bpm
            , @Field("Min_BPM") String min_bpm
            , @Field("Avg_BPM") String avg_bpm
            , @Field("calories") String calories);


    @POST("clients-workout/create-workout-excercise")
//calling by wear when workout complete and feedback save
    @FormUrlEncoded
    Call<WorkoutJsonModel> updateTrainingLogWithExercise(
            @Field("data") String trainingLog);

    @PUT("profile/update")
    @FormUrlEncoded
    Call<UserJsonModel> updateUser(@Query("id") int userId
            , @Field("firstname") String firstname
            , @Field("surname") String surname
            , @Field("clientemail") String clientemail
            , @Field("weightgoal") String weightgoal
            , @Field("ptemail") String ptemail
            , @Field("trainer_id") String trainer_id
            , @Field("goal_date") String goal_date
            , @Field("bmi") String bmi
            , @Field("telephone") String telephone
            , @Field("dob") String dob
            , @Field("height") String height
    );

    @Multipart
    @POST("profile/image-upload")
    Call<Object> uploadProfileImage(@Query("id") int userId, @Part MultipartBody.Part file);

    @POST("client/mobile-login")
    @FormUrlEncoded
    Call<UserJsonModel> loginViaMobile(@Field("Telephone") String mobile
            , @Field("pass2") String password);

    @POST("client/user-package")
    @FormUrlEncoded
    Call<UserJsonModel> getUserPackage(@Field("email") String email
            , @Field("password") String password);

    @PUT("client/reset-password")
    @FormUrlEncoded
    Call<Object> updatePassword(@Field("id") int userId
            , @Field("currentPassword") String currentPassword
            , @Field("newPassword") String newPassword
    );

    @PUT("client/forgot-password")
    @FormUrlEncoded
    Call<Object> forgotPassword(@Field("email") String email
    );

    @PUT("client/update-by-telephone")
    @FormUrlEncoded
    Call<UserJsonModel> updateUserAccountLogin2(
            @Field("Telephone") String Telephone,
            @Field("email") String email,
            @Field("updated_telephone") String Updated_Telephone
            , @Field("DoB") String DoB
            , @Field("gender") String gender
            , @Field("height") String height
            , @Field("weight") String weight
            , @Field("fitness_goal") int fitness_goal
            , @Field("goal_date") String goal_date
            , @Field("require_training_plan") int require_training_plan
    );

    @PUT("client/signup")
    @FormUrlEncoded
    Call<Object> userSignup(@Field("firstname") String firstname,
                            @Field("surname") String surname
            , @Field("email") String email
            , @Field("telephone") String mobile
            , @Field("password") String password
            , @Field("salt") String salt
            , @Field("user_group") int user_group
            , @Field("trn_date") String trn_date
            , @Field("package_item_clients") int package_item_clients
            , @Field("users_groups_clients") int users_groups_clients
            , @Field("profile_users") int profile_users
            , @Field("package_item_users") int package_item_users
            , @Field("package_clients") int package_clients
            , @Field("package_users") int package_users
    );

    @POST("client/package-items")
    @FormUrlEncoded
    Call<List<PackageTypeJsonModel>> getUserPacakgeType(@Field("client_id") int clientId

    );

    @POST("clients-plan/plan-details")
    @FormUrlEncoded
    Call<ClientPlanRange> getClientTrainingRange(@Field("client_id") int clientId,//here client_id contain a trainerid
                                                 @Field("user_group") int user_group,
                                                 @Field("package_item") int package_item

    );

    @POST("client/update-package-item")
    @FormUrlEncoded
    Call<Object> updatePackage(@Field("client_id") int clientId,
                               @Field("id") int id,
                               @Field("pid") int pid
    );

    @POST("client/update-order")
    @FormUrlEncoded
    Call<Object> updatePackagePayment(
            @Field("uid") int uid,
            @Field("suma") double suma,
            @Field("order_count") int order_count,
            @Field("shipping_price") int shipping_price,
            @Field("bill_number") String bill_number,
            @Field("payment_type") int payment_type,
            @Field("payment_amount") double payment_amount,
            @Field("status") int status,
            @Field("date_payed") String date_payed,
            @Field("date_created") String date_created,
            @Field("date_issued") String date_issued,
            @Field("date_storno") String date_storno,
            @Field("checksum") String checksum,
            @Field("storno_reason_desc") String storno_reason_desc

    );

    @POST("clients-update/excercise-graph-data")
    @FormUrlEncoded
    Call<List<ClientExercise>> getExercisesDataByID(@Field("client_id") int client_id,
                                                    @Field("exercise_id") int exercise_id);

    @POST("clients-update/graph-data")
    @FormUrlEncoded
    Call<GraphInfoJsonModel> getGraphInfoClient(@Field("client-id") int clientid);

    @POST("workouts/live-workouts")
    @FormUrlEncoded
    Call<List<WorkoutJsonModel>> getWorkoutLive(@Field("client_id") int client_id);


    @POST("client/update-language")
    @FormUrlEncoded
    Call<Object> changeLanguage(@Field("client_id") int clientId,
                                @Field("lang") int language
    );

    @FormUrlEncoded
    @POST("client/current-userby-mobile")
    Call<User> updateToken(@Field("client_mobile") String mobile);

}
