package com.pttracker.datamanagers;

import android.content.Context;

import com.google.gson.Gson;
import com.pttracker.models.eventbus.RoutineDataFetchingEvent;
import com.pttracker.models.eventbus.RoutineDataReceivedEvent;
import com.pttrackershared.datamanagers.greendao.GreenDaoDataManager;
import com.pttrackershared.models.ExerciseImages;
import com.pttrackershared.models.ExerciseImagesDao;
import com.pttrackershared.models.TrainingPlan;
import com.pttrackershared.models.TrainingPlanDao;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;


/**
 * ExerciseImagesDataManager manages all the functionality that could be performed on
 * Exercise Images it provides the wrapping over to the database through easy utilities exposed
 */


public class ExerciseImagesDataManager {
    private static ExerciseImagesDataManager sInstance;
    private Context mContext;

    private ExerciseImagesDataManager(Context context) {
        mContext = context;
    }

    public static ExerciseImagesDataManager getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new ExerciseImagesDataManager(context);
        }
        return sInstance;
    }

    public List<ExerciseImages> getStoredExerciseImages() {
        GreenDaoDataManager.getInstance(mContext).getDaoSession().clear();
        List<ExerciseImages> images = GreenDaoDataManager.getInstance(mContext).getDaoSession().getExerciseImagesDao().queryBuilder()
                .list();
        return images;
    }

    public ExerciseImages getExerciseImage(long exerciseId) {
        GreenDaoDataManager.getInstance(mContext).getDaoSession().clear();
        ExerciseImages image = GreenDaoDataManager.getInstance(mContext).getDaoSession().
                getExerciseImagesDao().queryBuilder().where(ExerciseImagesDao.Properties.ExerciseId.eq(exerciseId)).unique();
        return image;
    }

    public void setExerciseImage(ExerciseImages exerciseImage) {
        GreenDaoDataManager.getInstance(mContext).getDaoSession().clear();
        GreenDaoDataManager.getInstance(mContext).getDaoSession().getExerciseImagesDao().insert(exerciseImage);
    }

    public boolean hasImage(long exerciseId) {
        if (GreenDaoDataManager
                .getInstance(mContext)
                .getDaoSession()
                .getExerciseImagesDao()
                .queryBuilder().where(ExerciseImagesDao.Properties.ExerciseId.eq(exerciseId)).unique() != null)
            return true;
        return false;
    }

    public void saveImagesData(String jsonData) {
        Gson gson = new Gson();
        JSONArray imagesJsonArray = null;
        try {
            imagesJsonArray = new JSONArray(jsonData);

            List<ExerciseImages> exerciseImages = new ArrayList<>();
            for (int i = 0; i < imagesJsonArray.length(); i++) {
                ExerciseImages image = gson.fromJson(imagesJsonArray.getJSONObject(i).toString(), ExerciseImages.class);
                exerciseImages.add(image);
            }
            if (exerciseImages.size() > 0) {
                GreenDaoDataManager.getInstance(mContext).getDaoSession().getExerciseImagesDao().deleteAll();
                GreenDaoDataManager.getInstance(mContext).getDaoSession().getExerciseImagesDao().insertInTx(exerciseImages);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        } finally {

            EventBus.getDefault().post(new RoutineDataFetchingEvent(true));
            EventBus.getDefault().post(new RoutineDataReceivedEvent());
        }
    }
}
