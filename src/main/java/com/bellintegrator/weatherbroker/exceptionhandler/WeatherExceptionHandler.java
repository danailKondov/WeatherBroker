package com.bellintegrator.weatherbroker.exceptionhandler;

import com.bellintegrator.weatherbroker.exceptionhandler.exceptions.WeatherException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class WeatherExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(WeatherExceptionHandler.class);

    @ExceptionHandler({WeatherException.class})
    public ModelAndView handleCustomException(RuntimeException ex) {
        log.error(ex.getMessage(), ex.getCause());
        ModelAndView view = new ModelAndView();
        view.addObject("exception", ex);
        view.setViewName("index");
        return view;
    }

    @ExceptionHandler(Exception.class)
    public void handleAllOtherExceptions(Exception ex) {
        log.error(ex.getMessage(), ex.getCause());
    }
}
