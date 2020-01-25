package com.pttrackershared.models.eventbus;

import android.content.SharedPreferences;
import android.os.Parcel;
import android.os.Parcelable;

import com.pttrackershared.utils.Constants;

public class Exercise implements Parcelable{
    private Long id;
    private long circuitId;
    private int exerciseId;
    private int circuitExerciseId;
    private String name;
    private int reps;
    private int weight;
    private int calories;
    private int duration;
    private int set;
    private int restTime;
    private int trainerId;
    private String category;
    private String youtubeLink;
    private String imageLink;

    private String Category_name;

    private String exercise_description;

    private String cancel;
    private String effort_zone;
    private String effort_zone_show;
    private String distance;

    private String imageName;

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getEffort_zone_show() {
        return effort_zone_show;
    }

    public void setEffort_zone_show(String effort_zone_show) {
        this.effort_zone_show = effort_zone_show;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getEffort_zone() {
        return effort_zone;
    }

    public void setEffort_zone(String effort_zone) {
        this.effort_zone = effort_zone;
    }

    public String getCancel() {
        return cancel;
    }

    public void setCancel(String cancel) {
        this.cancel = cancel;
    }




    public String getExercise_description() {
        return exercise_description;
    }

    public void setExercise_description(String exercise_description) {
        this.exercise_description = exercise_description;
    }

    public Exercise(Long id, long circuitId, int exerciseId, int circuitExerciseId,
                    String name, int reps, int weight, int calories, int duration, int set,
                    int restTime, int trainerId, String category, String youtubeLink,
                    String imageLink, String Category_name, String exercise_description,
                    String cancel, String effort_zone, String effort_zone_show,
                    String distance, String imageName) {
        this.id = id;
        this.circuitId = circuitId;
        this.exerciseId = exerciseId;
        this.circuitExerciseId = circuitExerciseId;
        this.name = name;
        this.reps = reps;
        this.weight = weight;
        this.calories = calories;
        this.duration = duration;
        this.set = set;
        this.restTime = restTime;
        this.trainerId = trainerId;
        this.category = category;
        this.youtubeLink = youtubeLink;
        this.imageLink = imageLink;
        this.Category_name = Category_name;
        this.exercise_description = exercise_description;
        this.cancel = cancel;
        this.effort_zone = effort_zone;
        this.effort_zone_show = effort_zone_show;
        this.distance = distance;
        this.imageName = imageName;
    }

    public Exercise() {
    }

    public String getCategory_name() {
        return Category_name;
    }

    public void setCategory_name(String category_name) {
        Category_name = category_name;
    }

    protected Exercise(Parcel in) {
        circuitId = in.readLong();
        exerciseId = in.readInt();
        circuitExerciseId = in.readInt();
        name = in.readString();
        reps = in.readInt();
        weight = in.readInt();
        calories = in.readInt();
        duration = in.readInt();
        set = in.readInt();
        restTime = in.readInt();
        trainerId = in.readInt();
        category = in.readString();
        youtubeLink = in.readString();
        imageLink = in.readString();
        Category_name = in.readString();
        cancel = in.readString();
        effort_zone = in.readString();
        distance = in.readString();
        imageName = in.readString();
    }

    public static final Creator<Exercise> CREATOR = new Creator<Exercise>() {
        @Override
        public Exercise createFromParcel(Parcel in) {
            return new Exercise(in);
        }

        @Override
        public Exercise[] newArray(int size) {
            return new Exercise[size];
        }
    };

    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public long getCircuitId() {
        return this.circuitId;
    }
    public void setCircuitId(long circuitId) {
        this.circuitId = circuitId;
    }
    public int getExerciseId() {
        return this.exerciseId;
    }
    public void setExerciseId(int exerciseId) {
        this.exerciseId = exerciseId;
    }
    public int getCircuitExerciseId() {
        return this.circuitExerciseId;
    }
    public void setCircuitExerciseId(int circuitExerciseId) {
        this.circuitExerciseId = circuitExerciseId;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getReps() {
        return this.reps;
    }
    public void setReps(int reps) {
        this.reps = reps;
    }
    public int getWeight() {
        return this.weight;
    }
    public void setWeight(int weight) {
        this.weight = weight;
    }
    public int getCalories() {
        return this.calories;
    }
    public void setCalories(int calories) {
        this.calories = calories;
    }
    public int getDuration() {
        return this.duration;
    }
    public void setDuration(int duration) {
        this.duration = duration;
    }
    public int getSet() {
        return this.set;
    }
    public void setSet(int set) {
        this.set = set;
    }
    public int getRestTime() {
        return this.restTime;
    }
    public void setRestTime(int restTime) {
        this.restTime = restTime;
    }
    public int getTrainerId() {
        return this.trainerId;
    }
    public void setTrainerId(int trainerId) {
        this.trainerId = trainerId;
    }
    public String getCategory() {
        return this.category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public String getYoutubeLink() {
        return this.youtubeLink;
    }
    public void setYoutubeLink(String youtubeLink) {
        this.youtubeLink = youtubeLink;
    }
    public String getImageLink() {
        return this.imageLink;
    }
    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(circuitId);
        dest.writeInt(exerciseId);
        dest.writeInt(circuitExerciseId);
        dest.writeString(name);
        dest.writeInt(reps);
        dest.writeInt(weight);
        dest.writeInt(calories);
        dest.writeInt(duration);
        dest.writeInt(set);
        dest.writeInt(restTime);
        dest.writeInt(trainerId);
        dest.writeString(category);
        dest.writeString(youtubeLink);
        dest.writeString(imageLink);
        dest.writeString(Category_name);
        dest.writeString(exercise_description);
        dest.writeString(cancel);
        dest.writeString(effort_zone);
        dest.writeString(distance);
        dest.writeString(imageName);
    }
}
