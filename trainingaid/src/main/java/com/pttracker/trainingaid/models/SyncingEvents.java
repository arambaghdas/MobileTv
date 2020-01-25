package com.pttracker.trainingaid.models;

/**
 * Model class for the Events that occur while syncing from mobile to server
 */

public class SyncingEvents {
    public static class SyncingPlan{}//event trigger to initiation of syncing of Plan
    public static class SyncingImages{}//event trigger to initiation of syncing of Images
    public static class WearNotConnected{}//event trigger to initiation of syncing not possible
    public static class GenerateReport{//event trigger to initiation of syncing Completed

//        public int syncedWorkouts;
//        public int syncedCircuits;
//        public int syncedExercises;
//        public int syncedImages;
//
//        public GenerateReport(int syncedWorkouts, int syncedCircuits, int syncedExercises, int syncedImages) {
//            this.syncedWorkouts = syncedWorkouts;
//            this.syncedCircuits = syncedCircuits;
//            this.syncedExercises = syncedExercises;
//            this.syncedImages = syncedImages;
//        }
    }

    public static class UpdatePlanProgress{
        public int progress;

        public UpdatePlanProgress(int progress) {
            this.progress = progress;
        }
    }
    public static class UpdateImagesProgress{
        public int progress;

        public UpdateImagesProgress(int progress) {
            this.progress = progress;
        }
    }
}
