package com.example.thankage.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class quiz {

    @Expose
    @SerializedName("1problem") private String problem_1;
    @Expose
    @SerializedName("1choice1") private String choice1_1;
    @Expose
    @SerializedName("1choice2") private String choice2_1;
    @Expose
    @SerializedName("1choice3") private String choice3_1;
    @Expose
    @SerializedName("1choice4") private String choice4_1;
    @Expose
    @SerializedName("1answer") private String answer_1;

    @Expose
    @SerializedName("2problem") private String problem_2;
    @Expose
    @SerializedName("2choice1") private String choice1_2;
    @Expose
    @SerializedName("2choice2") private String choice2_2;
    @Expose
    @SerializedName("2choice3") private String choice3_2;
    @Expose
    @SerializedName("2choice4") private String choice4_2;
    @Expose
    @SerializedName("2answer") private String answer_2;

    @Expose
    @SerializedName("3problem") private String problem_3;
    @Expose
    @SerializedName("3choice1") private String choice1_3;
    @Expose
    @SerializedName("3choice2") private String choice2_3;
    @Expose
    @SerializedName("3choice3") private String choice3_3;
    @Expose
    @SerializedName("3choice4") private String choice4_3;
    @Expose
    @SerializedName("3answer") private String answer_3;


    public String getProblem_1() {
        return problem_1;
    }

    public String getChoice1_1() {
        return choice1_1;
    }

    public String getChoice2_1() {
        return choice2_1;
    }

    public String getChoice3_1() {
        return choice3_1;
    }

    public String getChoice4_1() {
        return choice4_1;
    }

    public String getAnswer_1() {
        return answer_1;
    }

    public String getProblem_2() {
        return problem_2;
    }

    public String getChoice1_2() {
        return choice1_2;
    }

    public String getChoice2_2() {
        return choice2_2;
    }

    public String getChoice3_2() {
        return choice3_2;
    }

    public String getChoice4_2() {
        return choice4_2;
    }

    public String getAnswer_2() {
        return answer_2;
    }

    public String getProblem_3() {
        return problem_3;
    }

    public String getChoice1_3() {
        return choice1_3;
    }

    public String getChoice2_3() {
        return choice2_3;
    }

    public String getChoice3_3() {
        return choice3_3;
    }

    public String getChoice4_3() {
        return choice4_3;
    }

    public String getAnswer_3() {
        return answer_3;
    }
}
