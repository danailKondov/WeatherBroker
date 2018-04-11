package com.bellintegrator.weatherbroker.model;

import com.bellintegrator.weatherbroker.views.actual.WeatherActualView;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Entity
@Table(name = "weather_condition")
public class WeatherCondition {

    @Id
    @GeneratedValue
    private Long id;

    @Version
    private Integer version = 0;

    private String city;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_condition")
    private Date date;

    private Integer temp;

    @Column(name = "temp_type")
    private String tempType;

    private String description;

    public WeatherCondition() {
    }

    public WeatherCondition(WeatherActualView view, String cityName, String tempType) {
        this.tempType = tempType;
        city = cityName;
        description = view.getQuery().getResults().getChannel().getItem().getCondition().getText();
        temp = view.getQuery().getResults().getChannel().getItem().getCondition().getTemp();
        SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm a z", Locale.US);
        String dateToParse = view.getQuery().getResults().getChannel().getItem().getCondition().getDate();
        try {
            date = format.parse(dateToParse);
        } catch (ParseException e) {
            // TODO: логируем и выбрасываем свое исключение?
        }
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getTemp() {
        return temp;
    }

    public void setTemp(Integer temp) {
        this.temp = temp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public String getTempType() {
        return tempType;
    }

    public void setTempType(String tempType) {
        this.tempType = tempType;
    }
}
