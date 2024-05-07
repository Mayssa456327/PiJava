package com.example.pidevjava.controllers;

import com.example.pidevjava.models.Evenement;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

public class CalendarViewController {

    @FXML
    private GridPane calendarGrid;

    @FXML
    private Label monthLabel;
    @FXML
    private Button previousMonthButton;

    @FXML
    private Button nextMonthButton;
    private List<Evenement> events;
    private YearMonth currentYearMonth;

    public void initialize() {
        currentYearMonth = YearMonth.now();
        fillCalendar(currentYearMonth);

        previousMonthButton.setOnAction(e -> changeMonth(-1));
        nextMonthButton.setOnAction(e -> changeMonth(1));
    }

    private void changeMonth(int delta) {
        currentYearMonth = currentYearMonth.plusMonths(delta);
        fillCalendar(currentYearMonth);
        placeEventsOnCalendar();
    }

    public void setEvents(List<Evenement> events) {
        this.events = events;
        placeEventsOnCalendar();
    }

    private void fillCalendar(YearMonth month) {
        calendarGrid.getChildren().clear(); // Clear previous calendar entries
        LocalDate firstOfMonth = month.atDay(1);
        LocalDate firstOfGrid = firstOfMonth.minusDays(firstOfMonth.getDayOfWeek().getValue() - 1);
        int dayCount = 0;

        for (int row = 1; row <= 6; row++) { // 6 weeks covering any month
            for (int col = 0; col < 7; col++) { // 7 days a week
                LocalDate currentDate = firstOfGrid.plusDays(dayCount);
                VBox dayCell = new VBox();
                dayCell.setMinWidth(100); // Set minimum width for the day cell
                dayCell.setMinHeight(80); // Set minimum height for the day cell
                dayCell.setBorder(new Border(new BorderStroke(Color.LIGHTGRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

                Label dayLabel = new Label(String.valueOf(currentDate.getDayOfMonth()));
                dayLabel.setFont(new Font("Arial", 16));
                dayLabel.setPadding(new Insets(5));

                if (currentDate.getMonth().equals(month.getMonth())) {
                    dayCell.getChildren().add(dayLabel);
                    calendarGrid.add(dayCell, col, row);
                }
                dayCount++;
            }
        }

        monthLabel.setText(month.getMonth().toString() + " " + month.getYear());
    }

    private void placeEventsOnCalendar() {
        if (events == null || calendarGrid.getChildren().isEmpty()) return;

        System.out.println("Placing events on calendar. Total events: " + events.size());

        for (Evenement event : events) {
            LocalDate eventStartDate = event.getDate_debut().toLocalDate();
            LocalDate eventEndDate = event.getDate_fin().toLocalDate();

            System.out.println("Event: " + event.getNom_evenement() + " Start: " + eventStartDate + " End: " + eventEndDate);

            for (Node node : calendarGrid.getChildren()) {
                if (GridPane.getRowIndex(node) != null && GridPane.getColumnIndex(node) != null) {
                    VBox dayCell = (VBox) node;
                    Label dayLabel = (Label) dayCell.getChildren().get(0);

                    if (dayLabel != null && !dayLabel.getText().isEmpty()) {
                        int dayNumber = Integer.parseInt(dayLabel.getText());
                        LocalDate cellDate = currentYearMonth.atDay(dayNumber);

                        if ((cellDate.isEqual(eventStartDate) || cellDate.isAfter(eventStartDate)) &&
                                (cellDate.isEqual(eventEndDate) || cellDate.isBefore(eventEndDate))) {
                            Label eventNameLabel = new Label(event.getNom_evenement());
                            eventNameLabel.setFont(new Font("Arial", 10));
                            eventNameLabel.setTextFill(Color.WHITE);
                            eventNameLabel.setStyle("-fx-background-color: " + getColorForEvent(event) + "; -fx-padding: 3;");
                            eventNameLabel.setMaxWidth(Double.MAX_VALUE);
                            VBox.setVgrow(eventNameLabel, Priority.ALWAYS);
                            dayCell.getChildren().add(eventNameLabel);
                        }
                    }
                }
            }
        }
    }



    private String getColorForEvent(Evenement event) {
        // This function returns a color based on the event type or other logic
        // For simplicity, we'll return a random color
        return "blue"; // Return the color for the event label background
    }
}
