package org.example.restfullapi.javaFx;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.restfullapi.dto.AppointmentStatsDTO;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class AppointmentStatsChartApp extends Application {
    private static final String API_URL = "http://localhost:8080/v1/appointments/stats/new-appointments";

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Статистика новых записей на прием");

        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Дата");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Количество записей");

        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Новые записи на прием по датам");

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Записи");

        // Получаем данные с API
        List<AppointmentStatsDTO> stats = fetchStats();

        // Добавляем данные в серию
        for (AppointmentStatsDTO stat : stats) {
            series.getData().add(new XYChart.Data<>(stat.date.toString(), stat.countNewAppointments));
        }

        barChart.getData().add(series);

        VBox vbox = new VBox(barChart);
        Scene scene = new Scene(vbox, 800, 600);

        stage.setScene(scene);
        stage.show();
    }

    private List<AppointmentStatsDTO> fetchStats() throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(response.body(), new TypeReference<List<AppointmentStatsDTO>>() {});
    }

    public static void main(String[] args) {
        launch();
    }
}