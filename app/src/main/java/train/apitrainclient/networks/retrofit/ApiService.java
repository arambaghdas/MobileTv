package train.apitrainclient.networks.retrofit;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;

import com.pttracker.trainingaid.models.ClientPlanRange;
import com.pttrackershared.models.eventbus.Circuit;
import com.pttrackershared.models.eventbus.ClientExercise;
import com.pttrackershared.models.eventbus.Clients;
import com.pttrackershared.models.eventbus.FequsModel;
import com.pttrackershared.models.eventbus.FitnessGoalModel;
import com.pttrackershared.models.eventbus.GraphInfo;
import com.pttrackershared.models.eventbus.Meals;
import com.pttrackershared.models.eventbus.PackageTypeModel;
import com.pttrackershared.models.eventbus.ProgressModel;
import com.pttrackershared.models.eventbus.ProgressModelBody;
import com.pttrackershared.models.eventbus.TrainingPlan;
import com.pttrackershared.models.eventbus.TrainingPlanJsonModel;
import com.pttrackershared.models.eventbus.TrainingPlanView;
import com.pttrackershared.models.eventbus.User;

import com.pttrackershared.models.eventbus.Exercise;
import com.pttrackershared.models.eventbus.Workout;
import com.pttrackershared.models.eventbus.WorkoutJsonModel;


/**
 * @author Atif Ali
 * @since August 29, 2017 5:02 PM
 */

public interface ApiService {
    @POST("client/login")
    @FormUrlEncoded
    Call<User> login(@Field("email") String email
            , @Field("password") String password);

    @FormUrlEncoded
    @POST("client/current-userby-mobile")
    Call<User> updateToken(@Field("client_mobile") String mobile);

    @POST("client/mobile-login")
    @FormUrlEncoded
    Call<User> loginViaMobile(@Field("Telephone") String mobile
            , @Field("pass2") String password);

    @POST("client/user-package")
    @FormUrlEncoded
    Call<User> getUserPackage(@Field("email") String email
            , @Field("password") String password);


    @GET("clients-update")
    Call<GraphInfo> getGraphInfo(/*@Query("email") String email*/);

    @POST("clients-update/graph-data")
    @FormUrlEncoded
    Call<GraphInfo> getGraphInfoClient(@Field("client-id") int clientid);

    @POST("clients-update/create")
    @FormUrlEncoded
    Call<GraphInfo> updateWeight(@Field("weight") String weight
            , @Field("date") String date);

    //    @GET("clients-plan/batch")
    @POST("clients-plan/batch")
    @FormUrlEncoded
    Call<List<TrainingPlanJsonModel>> getTrainingPlans(@Field("client_id") int client_id,
                                                       @Field("trainer_id") int trainer_id);

    @GET("clients-plan/user-plan-batch")
    Call<List<TrainingPlanJsonModel>> getAllTrainingPlans();

    @GET("clients-plan/view")
    Call<TrainingPlan> getTrainingPlansView();

    @POST("clients-plan/client-plan")
    @FormUrlEncoded
    Call<TrainingPlanView> getTrainingPlansView(@Field("client_id") int client_id);

    @POST("clients-plan/list")
    @FormUrlEncoded
    Call<List<TrainingPlan>> getTrainingPlansList(@Field("lang_trainer_id") int lang_trainer_id,
                                                           @Field("trainer_id") int trainer_id,
                                                           @Field("client_id") int client_id);

    @POST("clients-plan/get-training-plan")
    @FormUrlEncoded
    Call<List<TrainingPlanJsonModel>> getTrainingPlanList_updateuser(
            @Field("fit_goal_id") int selectedFit_goal_id);


    @POST("clients-plan/assign-plan")
    @FormUrlEncoded
    Call<Object> assignPlanToClient(@Field("trainer_id") int trainer_id,
                                    @Field("client_id") int client_id,
                                    @Field("users_plan_id") int users_plan_id,
                                    @Field("plan_name") String plan_name,
                                    @Field("selected_client_id") String selected_client_id,
                                    @Field("client_lenght") int client_s_length,
                                    @Field("client_workout_id1") int client_workout_id1,
                                    @Field("client_workout_id2") int client_workout_id2,
                                    @Field("client_workout_id3") int client_workout_id3,
                                    @Field("client_workout_id4") int client_workout_id4,
                                    @Field("client_workout_id5") int client_workout_id5,
                                    @Field("client_workout_id6") int client_workout_id6,
                                    @Field("client_workout_id7") int client_workout_id7,
                                    @Field("week") int week
    );


    @POST("live-workout/add-live-workout")
    @FormUrlEncoded
    Call<Object> assignWorkoutToClientLive(@Field("trainer_id") int trainer_id,
                                           @Field("workout_id") int workout_id,
                                           @Field("selected_client_id") String selected_client_id,
                                           @Field("client_lenght") int client_s_length

    );


    @GET("exercise")
    Call<List<Exercise>> getExercises();

    @POST("clients-update/excercise-graph-data")
    @FormUrlEncoded
    Call<List<ClientExercise>> getExercisesDataByID(@Field("client_id") int client_id,
                                                    @Field("exercise_id") int exercise_id);

    @GET("circuits?expand=exercises")
    Call<List<Circuit>> getCircuits();

    @GET("workouts?expand=circuits")
    Call<List<WorkoutJsonModel>> getWorkouts();

    @POST("workouts/live-workouts")
    @FormUrlEncoded
    Call<List<Workout>> getWorkoutLive(@Field("client_id") int client_id);


    //    @GET("clients-workout/my-training-log")
    @POST("clients-workout/my-training-log")
    @FormUrlEncoded
    Call<List<WorkoutJsonModel>> getTrainingLogs(@Query("page") int page,
                                                 @Field("client_id") int client_id);

    @POST("clients-workout/create")
    @FormUrlEncoded
    Call<Workout> updateTrainingLog(
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
    Call<Workout> updateTrainingLogWithExercise(
            @Field("data") String trainingLog);

    @PUT("profile/update")
    @FormUrlEncoded
    Call<User> updateUser(@Query("id") int userId
            , @Field("firstname") String firstname
            , @Field("surname") String surname
            , @Field("clientemail") String clientemail
            , @Field("weightgoal") String weightgoal
            , @Field("ptemail") String ptemail
            , @Field("trainer_id") String trainer_id
            , @Field("goal_date") String goal_date
            , @Field("bmi") String bmi
            , @Field("Telephone") String telephone
            , @Field("DoB") String dob
            , @Field("height") String height
    );


    @PUT("client/update-by-telephone")
    @FormUrlEncoded
    Call<User> updateUserAccountLogin2(
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

    @Multipart
    @POST("profile/image-upload")
    Call<Object> uploadProfileImage(@Query("id") int userId, @Part MultipartBody.Part file);


    @POST("client/create")
    @FormUrlEncoded
    Call<Object> createClient(@Field("id") int userId
            , @Field("firstname") String firstname
            , @Field("surname") String surname
            , @Field("DoB") String dob
            , @Field("height") String height
            , @Field("weight") String weight
            , @Field("clientemail") String clientemail
            , @Field("password") String password
            , @Field("Telephone") String telephone
            , @Field("fitness_goal") int fitnessgoal
            , @Field("weightgoal") String weightgoal
            , @Field("goal_date") String targetDate
            , @Field("trn_date") String trn_date
            , @Field("trainer_id") int trainer_id
            , @Field("profile") String profile
            , @Field("BMI") String BMI
            , @Field("image") String image
            , @Field("access_token") String access_token
            , @Field("package") int _package
            , @Field("package_item") int package_item
            , @Field("intro") int intro
    );

    @POST("client/edit-client")
    @FormUrlEncoded
    Call<Object> editClient(@Field("client-id") int clientId
            , @Field("id") int userId
            , @Field("firstname") String firstname
            , @Field("surname") String surname
            , @Field("DoB") String dob
            , @Field("height") String height
            , @Field("weight") String weight
            , @Field("clientemail") String clientemail
            , @Field("password") String password
            , @Field("Telephone") String telephone
            , @Field("fitness_goal") String fitnessgoal
            , @Field("weightgoal") String weightgoal
            , @Field("goal_date") String targetDate
            , @Field("trn_date") String trn_date
            , @Field("trainer_id") int trainer_id
            , @Field("profile") String profile
            , @Field("BMI") String BMI
            , @Field("image") String image
            , @Field("access_token") String access_token
    );

    @POST("client/save-tape-measurement")
    @FormUrlEncoded
    Call<Object> tapsBodyMeasurment(
            @Field("trainer_id") int trainer_id
            , @Field("client_id") int client_id
            , @Field("'date'") String date
            , @Field("neck") int neck
            , @Field("chest") int chest
            , @Field("upper_arms") int upper_arms
            , @Field("fore_arms") int fore_arms
            , @Field("thighs") int thighs
            , @Field("calves") int calves
            , @Field("waist") int waist
            , @Field("body_fat") int body_fat
            , @Field("lean_body") int lean_body
    );

    @POST("client/save-caliper-measurement")
    @FormUrlEncoded
    Call<Object> caliperBodyMeasurment(
            @Field("trainer_id") int trainer_id
            , @Field("client_id") int client_id
            , @Field("chest_skin_fold") int chest_skin_fold
            , @Field("subscapular_skin_fold") int subscapular_skin_fold
            , @Field("midaxilary_skin_fold") int midaxilary_skin_fold
            , @Field("tricep_skin_fold") int tricep_skin_fold
            , @Field("belly_skin_fold") int belly_skin_fold
            , @Field("hip_skin_fold") int hip_skin_fold
            , @Field("thigh_skin_fold") int thigh_skin_fold
            , @Field("body_fat") int body_fat
            , @Field("lean_body") int lean_body

    );


    @POST("client/list")
    @FormUrlEncoded
    Call<List<Clients>> getClients(@Field("trainer_id") int trainer_id,
                                   @Field("client-id") int clientId
    );

    @POST("client/live-workout-list")
    @FormUrlEncoded
    Call<List<Clients>> getLiveworkoutClientList(@Field("client-id") int clientId

    );

    @POST("client/food-plan")
    @FormUrlEncoded
    Call<List<Meals>> getFoodList(@Field("trainer_id") int trainer_id,
                                  @Field("client_id") int clientId
    );

    @GET("client/fitness-goal")
    Call<List<FitnessGoalModel>> getFitnessGoal();

    @POST("client/delete-client")
    @FormUrlEncoded
    Call<Object> deleteClient(@Field("client-id") int clientId

    );

    @POST("client/package-items")
    @FormUrlEncoded
    Call<List<PackageTypeModel>> getUserPacakgeType(@Field("client_id") int clientId

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

    @POST("client/update-language")
    @FormUrlEncoded
    Call<Object> changeLanguage(@Field("client_id") int clientId,
                                @Field("lang") int language
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

    @POST("client/assign-food-plan")
    @FormUrlEncoded
    Call<Object> assignFoodToClient(@Field("trainer_id") int trainer_id,
                                    @Field("client_id") int client_id,
                                    @Field("food_plan_id") int users_plan_id,
                                    @Field("selected_client_id") String selected_client_id,
                                    @Field("client_length") int client_s_length
    );

    @POST("client/faqs")
    @FormUrlEncoded
    Call<List<FequsModel>> geFeqsList(@Field("lang") int language);

    @POST("client/filtered-data")
    @FormUrlEncoded
    Call<ProgressModel> getProgressInfo(@Field("client_id")int client_id,@Field("calendar_week") int calendar_week);
}
