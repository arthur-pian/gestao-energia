package com.energia.gestao.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WeatherData {
    private Coord coord;
    private Weather[] weather;
    private Main main;
    private Wind wind;
    private String name; // Nome da cidade
    private long dt; // Timestamp de dados

    // Construtor padrão
    public WeatherData() {}

    // Getters e Setters
    public Coord getCoord() { return coord; }
    public void setCoord(Coord coord) { this.coord = coord; }

    public Weather[] getWeather() { return weather; }
    public void setWeather(Weather[] weather) { this.weather = weather; }

    public Main getMain() { return main; }
    public void setMain(Main main) { this.main = main; }

    public Wind getWind() { return wind; }
    public void setWind(Wind wind) { this.wind = wind; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public long getDt() { return dt; }
    public void setDt(long dt) { this.dt = dt; }

    // --- Classes Aninhadas para Estrutura JSON ---

    public static class Coord {
        private double lon;
        private double lat;

        public double getLon() { return lon; }
        public void setLon(double lon) { this.lon = lon; }
        public double getLat() { return lat; }
        public void setLat(double lat) { this.lat = lat; }
    }

    public static class Weather {
        private int id;
        private String main;
        private String description;
        private String icon;

        public int getId() { return id; }
        public void setId(int id) { this.id = id; }
        public String getMain() { return main; }
        public void setMain(String main) { this.main = main; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        public String getIcon() { return icon; }
        public void setIcon(String icon) { this.icon = icon; }
    }

    public static class Main {
        private double temp;
        @JsonProperty("feels_like")
        private double feelsLike;
        @JsonProperty("temp_min")
        private double tempMin;
        @JsonProperty("temp_max")
        private double tempMax;
        private int pressure;
        private int humidity;

        public double getTemp() { return temp; }
        public void setTemp(double temp) { this.temp = temp; }
        public double getFeelsLike() { return feelsLike; }
        public void setFeelsLike(double feelsLike) { this.feelsLike = feelsLike; }
        public double getTempMin() { return tempMin; }
        public void setTempMin(double tempMin) { this.tempMin = tempMin; }
        public double getTempMax() { return tempMax; }
        public void setTempMax(double tempMax) { this.tempMax = tempMax; }
        public int getPressure() { return pressure; }
        public void setPressure(int pressure) { this.pressure = pressure; }
        public int getHumidity() { return humidity; }
        public void setHumidity(int humidity) { this.humidity = humidity; }
    }

    public static class Wind {
        private double speed;
        private int deg;

        public double getSpeed() { return speed; }
        public void setSpeed(double speed) { this.speed = speed; }
        public int getDeg() { return deg; }
        public void setDeg(int deg) { this.deg = deg; }
    }
}