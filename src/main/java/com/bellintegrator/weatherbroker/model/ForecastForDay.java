package com.bellintegrator.weatherbroker.model;

import com.bellintegrator.weatherbroker.exceptionhandler.exceptions.WeatherException;
import com.bellintegrator.weatherbroker.views.forecast.Forecast;

import javax.persistence.*;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

@Entity
@Table(name = "forecast_for_day")
public class ForecastForDay implements Serializable{

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
            throw new WeatherException("Can't parse the date of the weather forecast", e);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ForecastForDay forecast = (ForecastForDay) o;
        return Objects.equals(date, forecast.date) &&
                Objects.equals(dayOfWeek, forecast.dayOfWeek) &&
                Objects.equals(highTemp, forecast.highTemp) &&
                Objects.equals(lowTemp, forecast.lowTemp) &&
                Objects.equals(description, forecast.description);
    }

    @Override
    public int hashCode() {

        return Objects.hash(date, dayOfWeek, highTemp, lowTemp, description);
    }
}
