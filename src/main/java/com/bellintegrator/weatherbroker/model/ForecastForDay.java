package com.bellintegrator.weatherbroker.model;

import com.bellintegrator.weatherbroker.views.forecast.Forecast;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Entity
@Table(name = "forecast_for_day")
public class ForecastForDay {

    @Id
    @GeneratedValue
    private Long id;

    @Version
    private Integer version = 0;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_for_forecast")
    private Date date;

    @Column(name = "day_of_week")
    private String dayOfWeek;

    @Column(name = "high_temp")
    private Integer highTemp;

    @Column(name = "low_temp")
    private Integer lowTemp;

    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "forecast_id")
    private WeatherForecast forecast;

    public ForecastForDay() {
    }

    public ForecastForDay(Forecast forecast) {
        dayOfWeek = forecast.getDay();
        highTemp = Integer.valueOf(forecast.getHigh());
        lowTemp = Integer.valueOf(forecast.getLow());
        description = forecast.getText();
        SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy", Locale.US);
        try {
            date = format.parse(forecast.getDate());
        } catch (ParseException e) {
            // TODO: custom exception
        }
    }

    public Long getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public Integer getHighTemp() {
        return highTemp;
    }

    public void setHighTemp(Integer highTemp) {
        this.highTemp = highTemp;
    }

    public Integer getLowTemp() {
        return lowTemp;
    }

    public void setLowTemp(Integer lowTemp) {
        this.lowTemp = lowTemp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public WeatherForecast getForecast() {
        return forecast;
    }

    public void setForecast(WeatherForecast forecast) {
        this.forecast = forecast;
    }
}
