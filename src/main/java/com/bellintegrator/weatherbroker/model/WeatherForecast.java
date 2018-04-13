package com.bellintegrator.weatherbroker.model;

import com.bellintegrator.weatherbroker.views.forecast.Channel;
import com.bellintegrator.weatherbroker.views.forecast.Forecast;
import com.bellintegrator.weatherbroker.views.forecast.WeatherForecastView;

import javax.persistence.*;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Entity
@Table(name = "weather_forecast")
public class WeatherForecast implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Version
    private Integer version = 0;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_forecast")
    private Date date;

    private String city;

    @Column(name = "temp_type")
    private String tempType;

    @OneToMany(
            fetch = FetchType.EAGER,
            mappedBy = "forecast",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<ForecastForDay> forecasts = new HashSet<>();

    public WeatherForecast() {
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTempType() {
        return tempType;
    }

    public void setTempType(String tempType) {
        this.tempType = tempType;
    }

    public Set<ForecastForDay> getForecasts() {
        return forecasts;
    }

    public void setForecasts(Set<ForecastForDay> forecasts) {
        for (ForecastForDay forecast : forecasts) {
            forecast.setForecast(this);
        }
        this.forecasts = forecasts;
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WeatherForecast forecast = (WeatherForecast) o;
        return Objects.equals(date, forecast.date) &&
                Objects.equals(city, forecast.city) &&
                Objects.equals(tempType, forecast.tempType);
    }

    @Override
    public int hashCode() {

        return Objects.hash(date, city, tempType);
    }
}
