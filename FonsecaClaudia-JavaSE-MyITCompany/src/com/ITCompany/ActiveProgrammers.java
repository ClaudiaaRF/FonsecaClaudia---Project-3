package com.ITCompany;


import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;

public class ActiveProgrammers implements Programmers {
    private String firstName;
    private String lastName;
    private String technology;
    private String team;
    private String category;
    private LocalDate startDate;
    private LocalDate endDate;

    public ActiveProgrammers(String firstName, String lastName, String technology, String team, String category, LocalDate startDate, LocalDate endDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.technology = technology;
        this.team = team;
        this.category = category;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTechnology() {
        return technology;
    }

    public void setTechnology(String technology) {
        this.technology = technology;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }


    @Override
    public long getContractDuration() {
        return ChronoUnit.DAYS.between(startDate, endDate) + 1;
    }

    @Override
    public long getWorkedDays() {
        LocalDate today = ITCompany.updateDate;

        if (getStartDate().compareTo(today) > 0) {
            return 0;
        }

        if (getStartDate().compareTo(today.withDayOfMonth(1)) > 0) {
            if (getEndDate().compareTo(today) < 0) {
                return getContractDuration();
            }
            return ChronoUnit.DAYS.between(getStartDate(), today) + 1;
        }

        if (getEndDate().compareTo(today) < 0) {
            if (getStartDate().compareTo(today.withDayOfMonth(1)) > 0) {
                return getContractDuration();
            }
            if (getEndDate().compareTo(today.withDayOfMonth(1)) < 0) {
                return 0;
            }
            return ChronoUnit.DAYS.between(today.withDayOfMonth(1), getEndDate()) + 1;
        }

        return ChronoUnit.DAYS.between(today.withDayOfMonth(1), today) + 1;
    }

    @Override
    public long getSalary() {
        return (getCategory().equals("fullPay") ? getWorkedDays() * 40 : getWorkedDays() * 20);
    }

    @Override
    public int getDaysLeftOfWorking() {
        LocalDate today = ITCompany.updateDate;

        int currentDate = today.getDayOfMonth();
        int lastDayOfMonth = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH);
        int firstDayContract = getStartDate().getDayOfMonth();
        int lastDayContract = getEndDate().getDayOfMonth();

        if (getEndDate().compareTo(today) < 0) {
            return 0;
        }

        if (getStartDate().compareTo(today) > 0 && getStartDate().getMonth().compareTo(
                today.getMonth()) == 0) {
            if (getEndDate().getDayOfMonth() < lastDayOfMonth && getEndDate().getYear() - today.getYear() == 0 && getEndDate().getMonth().compareTo(
                    today.getMonth()) == 0) {
                return lastDayContract - firstDayContract + 1;
            }
            return lastDayOfMonth - firstDayContract + 1;
        }

        if (getEndDate().compareTo(today.withDayOfMonth(lastDayOfMonth)) < 0) {
            return lastDayContract - currentDate;
        }

        return lastDayOfMonth - currentDate;
    }

    @Override
    public String toString(){
        return String.format("%s, %s, in charge of %s from %s to %s (duration of %s days), has worked %s days this month (total salary %s €)",
                getFirstName(),
                getLastName(),
                getTechnology(),
                getStartDate(),
                getEndDate(),
                getContractDuration(),
                getWorkedDays(),
                getSalary()
        );
    }
}
