package com.energia.gestao.service;

import com.energia.gestao.model.WeatherData; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ExternalApiService {

    private static final Logger logger = LoggerFactory.getLogger(ExternalApiService.class);

    private final RestTemplate restTemplate;
    private final String OPENWEATHER_API_KEY = "e7124e338e52fb2bb320e1213af791ee"; // Minha Key
    private final String OPENWEATHER_API_BASE_URL = "https://api.openweathermap.org/data/2.5/weather";

    @Autowired
    public ExternalApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Consome a API do OpenWeatherMap para obter dados climáticos de uma cidade.
     * @param city O nome da cidade (ex: "Sao Paulo", "London").
     * @return Objeto WeatherData com as informações climáticas, ou null em caso de erro/cidade não encontrada.
     */
    public WeatherData getWeatherForCity(String city) {
        if (OPENWEATHER_API_KEY.equals("e7124e338e52fb2bb320e1213af791ee")) {
            logger.error("API Key da OpenWeatherMap não configurada. Por favor, obtenha e insira sua chave real.");
            return null;
        }
        String url = String.format("%s?q=%s&appid=%s&units=metric",
                                   OPENWEATHER_API_BASE_URL, city, OPENWEATHER_API_KEY);

        logger.info("Tentando consumir API de clima em: {}", url);

        try {
            WeatherData weatherData = restTemplate.getForObject(url, WeatherData.class);

            if (weatherData != null) {
                logger.info("Dados climáticos para {} recebidos: Temperatura {}°C, Humidade {}%",
                            weatherData.getName(), weatherData.getMain().getTemp(), weatherData.getMain().getHumidity());
            } else {
                logger.warn("API de clima retornou nulo para a cidade: {}", city);
            }
            return weatherData;
        } catch (Exception e) {
            logger.error("Erro ao consumir a API de clima para {}: {}", city, e.getMessage());
            return null;
        }
    }
}