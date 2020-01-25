package train.apitrainclient.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

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
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import train.apitrainclient.R;
import com.pttrackershared.models.eventbus.User;

/**
 * Defines the utility methods that could be used at multiple places
 */

public class AppUtils {
    public static boolean networkConnection = true;
    /**
     * Converts kilograms to pounds
     */
    public static double convertKgToPound(double kilograms) {

        return (double) Math.round((kilograms * 2.20462262) * 100.0) / 100.0;
    }

    public static double convertPoundToKg(double pounds) {

        return (double) Math.round((pounds / 2.20462262) * 100.0) / 100.0;
    }

    public static int convertKgToPoundQ(double kilograms) {

        return (int) ((double) Math.round((kilograms * 2.20462262) * 100.0) / 100.0);
    }

    public static int convertPoundToKgQ(double pounds) {

        return (int) ((double) Math.round((pounds / 2.20462262) * 100.0) / 100.0);
    }

    public static int convertFeetInchesToCm(double foot,double inches) {
        double cm = (foot * 12) + inches;
        cm = cm * 2.54;
        int test = (int) cm;

        return test;
    }

    public static String convertCmToFootInch(double cm) {
        double foots = cm * 0.3937008;
        foots = foots / 12;

        String splitTo2 = String.valueOf(foots);
        String splitToF[] = splitTo2.split("\\.");

        int foot = Integer.parseInt(splitToF[0]);
        int inches = Integer.parseInt(splitToF[1].substring(0,2));

        inches = inches * 12;
        String in = String.valueOf(inches);
        in = in.substring(0,1);

        String footAndInches = foot + "/" + in + "";

        return footAndInches;
    }

    /**
     * Checks whether two calendar dates
     *
     * @param calendar1
     * @param calendar2 are same day dates. It is not mandatory to have same hours, minutes, seconds and milliseconds
     * @return
     */
    public static boolean AreSameDay(Calendar calendar1, Calendar calendar2) {
        return (calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR)
                && calendar1.get(Calendar.DAY_OF_YEAR) == calendar2.get(Calendar.DAY_OF_YEAR));
    }

    /**
     * Checks whether two date objects
     *
     * @param date1
     * @param date2 are same day dates. It is not mandatory to have same hours, minutes, seconds and milliseconds
     * @return
     */
    public static boolean AreSameDay(Date date1, Date date2) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date1);

        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date2);
        return (calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR)
                && calendar1.get(Calendar.DAY_OF_YEAR) == calendar2.get(Calendar.DAY_OF_YEAR));
    }

    public static byte[] getPictureByteOfArray(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, byteArrayOutputStream);
        return reduceImage(byteArrayOutputStream.toByteArray());
    }

    public static byte[] reduceImage(byte[] data) {
        // Decode image size
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        o.inPurgeable = true;
        o.inInputShareable = true;
        BitmapFactory.decodeByteArray(data, 0, data.length, o);

        // The new size we want to scale to
        final int REQUIRED_H = 70;
        final int REQUIRED_W = 150;

        // Find the correct scale value. It should be the power of 2.
        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;
        while (true) {
            if (width_tmp / 2 < REQUIRED_W || height_tmp / 2 < REQUIRED_H) {
                break;
            }
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }

        // Decode with inSampleSize
        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inPurgeable = true;
        o2.inInputShareable = true;
        o2.inSampleSize = scale;
        Bitmap bitmapScaled = null;
        bitmapScaled = BitmapFactory.decodeByteArray(data, 0, data.length, o2);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmapScaled.compress(Bitmap.CompressFormat.PNG, 0, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
    public static final String sha512(final String s) {
        try {
            int pass= Objects.hash(s);
            MessageDigest digest = MessageDigest
                    .getInstance("sha512");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void setImageExercise(final Context context, final ImageView ivExerciseImage, final Exercise exercise){
      if (!exercise.getImageLink().equalsIgnoreCase("")) {
            String imageName = exercise.getImageName();
            User user = SharedPrefManager.getUser(context);
            if (user.getGender().equalsIgnoreCase("2")) {
                Picasso.with(context).load(Constants.IMAGE_URL_FEMALE +
                        imageName).
                        error(R.drawable.error_image).into(ivExerciseImage);
            } else {
                Picasso.with(context).load(Constants.IMAGE_URL_MALE +
                        imageName).
                        error(R.drawable.error_image).into(ivExerciseImage);
            }
        } else {
            ivExerciseImage.setImageResource(R.drawable.error_image);
        }
    }

    public static final String sha_512(final String password) {
        try {
//            String salt=get
//            int pass= Objects.hash( Objects.hash(password+salt));

            final MessageDigest sha512 = MessageDigest.getInstance("SHA-512");
            sha512.update(password.getBytes());
            byte data[]= sha512.digest();

            StringBuffer hexData = new StringBuffer();
            for (int byteIndex = 0; byteIndex < data.length; byteIndex++)
                hexData.append(Integer.toString((data[byteIndex] & 0xff) + 0x100, 16).substring(1));

            System.out.println(hexData.toString());
            return hexData.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static Date convertStringtoDate(String stringDate){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(stringDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date; // Sat Jan 02 00:00:00 GMT 2010
    }

//    public static void checkForGenderView(Context context, View view){
//        User user = SharedPrefManager.getUser(context);
//        if (user.getGender().equals("1")){
//            view.setBackgroundColor(context.getResources().getColor(R.color.dark_blue));
//        }else {
//            view.setBackgroundColor(context.getResources().getColor(R.color.purlpe));
//        }
//    }

    public static String getDisplaySize(Activity activity) {
        double x = 0, y = 0;
        int mWidthPixels, mHeightPixels;
        try {
            WindowManager windowManager = activity.getWindowManager();
            Display display = windowManager.getDefaultDisplay();
            DisplayMetrics displayMetrics = new DisplayMetrics();
            display.getMetrics(displayMetrics);
            Point realSize = new Point();
            Display.class.getMethod("getRealSize", Point.class).invoke(display, realSize);
            mWidthPixels = realSize.x;
            mHeightPixels = realSize.y;
            DisplayMetrics dm = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
            x = Math.pow(mWidthPixels / dm.xdpi, 2);
            y = Math.pow(mHeightPixels / dm.ydpi, 2);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return String.format(Locale.US, "%.2f", Math.sqrt(x + y));
    }

    public static Boolean bigDevice(Activity activity){
        float size = Float.parseFloat(getDisplaySize(activity));
        if (size > 5.50){
            return true;
        }else {
            return false;
        }
    }

    public static List<TrainingPlan> saveTrainingPlans(Context context, List<TrainingPlanJsonModel> trainingPlanJsonModelList) {

        if (trainingPlanJsonModelList == null || trainingPlanJsonModelList.size() == 0) {
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
