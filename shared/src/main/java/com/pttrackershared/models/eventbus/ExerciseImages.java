package com.pttrackershared.models.eventbus;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;


public class ExerciseImages implements Parcelable {

    private long exerciseId;
    private String imageUrl;
    private String exerciseName;
    private byte[] imageBytes;

    protected ExerciseImages(Parcel in) {
        exerciseId = in.readLong();
        imageUrl = in.readString();
        exerciseName = in.readString();
        imageBytes = in.createByteArray();
    }

    public ExerciseImages(long exerciseId, String imageUrl, String exerciseName,
            byte[] imageBytes) {
        this.exerciseId = exerciseId;
        this.imageUrl = imageUrl;
        this.exerciseName = exerciseName;
        this.imageBytes = imageBytes;
    }

    public ExerciseImages() {
    }

    public static final Creator<ExerciseImages> CREATOR = new Creator<ExerciseImages>() {
        @Override
        public ExerciseImages createFromParcel(Parcel in) {
            return new ExerciseImages(in);
        }

        @Override
        public ExerciseImages[] newArray(int size) {
            return new ExerciseImages[size];
        }
    };

    public long getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(long exerciseId) {
        this.exerciseId = exerciseId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public byte[] getImageBytes() {
        return imageBytes;
    }

    public void setImageBytes(byte[] imageBytes) {
        this.imageBytes = imageBytes;
    }

    public Bitmap getImageBitmap() {
        Bitmap imageBitmap;
        if (imageBytes != null && imageBytes.length > 0) {
            imageBitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            return imageBitmap;
        }
        return null;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(exerciseId);
        dest.writeString(imageUrl);
        dest.writeByteArray(imageBytes);
        dest.writeString(exerciseName);
    }
}
