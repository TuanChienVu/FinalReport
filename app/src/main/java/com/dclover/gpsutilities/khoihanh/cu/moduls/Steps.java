package com.dclover.gpsutilities.khoihanh.cu.moduls;

public class Steps{

    private String step_distance;
    private String step_duration;
    private String html_instructions;
    private String points;

    public Steps(){

    }

    public Steps(String step_distance, String step_duration, String html_instructions, String points){
        this.step_distance = step_distance;
        this.step_duration = step_duration;
        this.html_instructions = html_instructions;
        this.points = points;
    }

    public String getStep_distance() {
        return step_distance;
    }

    public void setStep_distance(String step_distance) {
        this.step_distance = step_distance;
    }

    public String getStep_duration() {
        return step_duration;
    }

    public void setStep_duration(String step_duration) {
        this.step_duration = step_duration;
    }

    public String getHtml_instructions() {
        return html_instructions;
    }

    public void setHtml_instructions(String html_instructions) {
        this.html_instructions = html_instructions;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }
}
