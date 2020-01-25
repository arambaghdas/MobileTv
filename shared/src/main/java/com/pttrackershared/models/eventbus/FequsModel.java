package com.pttrackershared.models.eventbus;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Nitin Sharma on 2/3/2019.
 */

public class FequsModel implements Serializable {
    @SerializedName("topic_id")
    int topic_id;
    @SerializedName("trainer_id")
    int trainer_id;
    @SerializedName("topic")
    String topic;
    @SerializedName("language")
    int language;
    @SerializedName("date")
    String date;
    @SerializedName("question")
    String question;
    @SerializedName("answer")
    String answer;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getTopic_id() {
        return topic_id;
    }

    public void setTopic_id(int topic_id) {
        this.topic_id = topic_id;
    }

    public int getTrainer_id() {
        return trainer_id;
    }

    public void setTrainer_id(int trainer_id) {
        this.trainer_id = trainer_id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public int getLanguage() {
        return language;
    }

    public void setLanguage(int language) {
        this.language = language;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
