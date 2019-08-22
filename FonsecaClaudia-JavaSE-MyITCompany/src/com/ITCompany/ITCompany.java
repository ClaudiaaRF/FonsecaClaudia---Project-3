package com.ITCompany;

import java.io.*;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

public class ITCompany {


    public static LocalDate updateDate;

    public static void main(String[] args) {
        List<ProjectTeam> teams = importFrom("ITCompanyReport.txt");

        /*
         * If file "ITCompanyData.txt" doesn't exist,
         * create default programmers and save a new file
         * with correct formatting
         */
        if (updateDate == null || teams.size() == 0) {
            updateDate = LocalDate.now();
            teams = createDefault();
        }

        printReport(teams);
        updateDate = updateDate.plus(1, DAYS);

        System.out.printf("%n Saving... %n");
        exportTo(teams, "ITCompanyReport.txt");

    }

    private static List<ProjectTeam> createDefault() {
        List<ProjectTeam> teams = new LinkedList<>();

        String[] defaultProgrammers = {
                "David|Dias|Java|team1|fullPay|2015-04-03|2021-01-01",
                "Joao|Sousa|PHP|team2|halfPay|2019-08-01|2021-02-10",
                "Ana|Diniz|Cobol|team3|fullPay|2020-08-01|2021-10-10",
                "Rita|Santos|Cobol|team1|halfPay|2018-04-21|2020-01-28",
                "Miguel|Castanho|Java|team2|fullPay|2019-02-04|2019-08-31",
                "Nelson|Silva|React|team3|halfPay|2018-11-04|2019-11-30"
        };

        for (String line : defaultProgrammers) {
            loadProgrammers(teams, line);
        }
        return teams;
    }

    private static void loadProgrammers(List<ProjectTeam> teams, String line) {
        String[] args = line.split("\\|");
        ActiveProgrammers currentProgrammer =
                new ActiveProgrammers(args[0], args[1], args[2], args[3], args[4],
                        LocalDate.parse(args[5]), LocalDate.parse(args[6]));
        if (!hasTeam(teams, args[3])) {
            // If team doesn't exist
            ProjectTeam projectTeam = new ProjectTeam(args[3], args[4], new LinkedList<>());
            projectTeam.addProgrammer(currentProgrammer);
            teams.add(projectTeam);
        } else {
            // Find team in teams to add the currentProgrammer
            teams.forEach(projectTeam -> {
                if (projectTeam.getName().equals(args[3])) {
                    projectTeam.addProgrammer(currentProgrammer);
                }
            });
        }
    }

    private static boolean hasTeam(List<ProjectTeam> teams, String name) {
        for (ProjectTeam team : teams) {
            if (team.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    private static void printReport(List<ProjectTeam> teams) {

        int numOfProgrammers = teams.stream().mapToInt(ProjectTeam::size).sum();
        int programmersThatWorked = teams.stream().mapToInt(ProjectTeam::hasWorked).sum();
        int totalDaysWorked = teams.stream().mapToInt(ProjectTeam::totalDaysWorked).sum();
        int totalDaysToWork = teams.stream().mapToInt(ProjectTeam::totalDaysToWork).sum();

        System.out.printf("IT COMPANY  - Report (%s) %n%n"
                        + "IT Company is actually composed of %d project teams, and %d programmers. %n"
                        + "This month, %d programmers have worked %d days, and there are %d days left to work. %n%n"
                        + "Project teams details: %n",
                updateDate,
                teams.size(),
                numOfProgrammers,
                programmersThatWorked,
                totalDaysWorked,
                totalDaysToWork
        );

        teams.forEach(projectTeam -> {
            System.out.printf("%n%nProject team: %s%n%n", projectTeam.getName());
            projectTeam.getProgrammerList().forEach(System.out::println);
        });
    }

    private static List<ProjectTeam> importFrom(String fileName) {
        List<ProjectTeam> teams = new LinkedList<>();

        try (
                FileInputStream fis = new FileInputStream(fileName);
                BufferedReader reader = new BufferedReader(new InputStreamReader(fis))
        ) {
            String line;
            updateDate = LocalDate.parse(reader.readLine());
            while ((line = reader.readLine()) != null) {
                loadProgrammers(teams, line);
            }
        } catch (IOException ioe) {
            System.out.printf("Problems loading the file %s :: Creating a new report from default ITCompany %n%n",
                    fileName);
        }
        return teams;
    }

    private static void exportTo(List<ProjectTeam> teams, String fileName) {
        try (
                FileOutputStream fos = new FileOutputStream(fileName);
                PrintWriter writer = new PrintWriter(fos)
        ) {
            writer.printf("%s%n", updateDate);
            teams.forEach(projectTeam -> {
                projectTeam.getProgrammerList().forEach(programmer -> {
                    writer.printf("%s|%s|%s|%s|%s|%s|%s%n",
                            programmer.getFirstName(),
                            programmer.getLastName(),
                            programmer.getTechnology(),
                            programmer.getTeam(),
                            programmer.getCategory(),
                            programmer.getStartDate(),
                            programmer.getEndDate());
                });
            });
        } catch (IOException ioe) {
            System.out.printf("Problem saving %s %n", fileName);
            ioe.printStackTrace();
        }
    }


}



