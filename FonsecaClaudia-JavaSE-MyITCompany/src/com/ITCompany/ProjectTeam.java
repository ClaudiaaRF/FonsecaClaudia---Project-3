package com.ITCompany;

import java.util.List;

public class ProjectTeam {
    private String name;
    private String category;
    private List<ActiveProgrammers> programmerList;

    public ProjectTeam(String name, String category, List<ActiveProgrammers> programmerList) {
        this.name = name;
        this.category = category;
        this.programmerList = programmerList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<ActiveProgrammers> getProgrammerList() {
        return programmerList;
    }

    public void setProgrammerList(List<ActiveProgrammers> programmerList) {
        this.programmerList = programmerList;
    }

    void addProgrammer(ActiveProgrammers programmer) {
        programmerList.add(programmer);
    }

    int size() {
        return programmerList.size();
    }

    int hasWorked() {
        int count = 0;
        for (Programmers programmer : programmerList) {
            if (programmer.getWorkedDays() != 0) {
                count++;
            }
        }
        return count;
    }

    int totalDaysWorked() {
        int days = 0;
        for (Programmers programmer : programmerList) {
            days += programmer.getWorkedDays();
        }
        return days;
    }

    int totalDaysToWork() {
        int days = 0;
        for (Programmers programmer : programmerList) {
            days += programmer.getDaysLeftOfWorking();
        }
        return days;
    }
}