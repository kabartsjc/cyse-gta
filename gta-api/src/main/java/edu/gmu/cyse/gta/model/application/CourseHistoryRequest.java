package edu.gmu.cyse.gta.model.application;

public class CourseHistoryRequest {
    private String year;
    private String semester;
    private String cyseId;

    // Getters and setters
    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getCyseId() {
        return cyseId;
    }

    public void setCyseId(String cyseId) {
        this.cyseId = cyseId;
    }

    @Override
    public String toString() {
        return "CourseHistoryRequest{" +
                "year='" + year + '\'' +
                ", semester='" + semester + '\'' +
                ", cyseId='" + cyseId + '\'' +
                '}';
    }
}
