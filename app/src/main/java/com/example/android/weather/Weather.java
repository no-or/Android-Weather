package com.example.android.weather;

public class Weather {
    public Temperature temperature = new Temperature();
    public CurrentCondition currentCondition = new CurrentCondition();
    public byte[] iconData;

    public  class Temperature {
        private float temp;

        public float getTemp() {
            return temp;
        }
        public void setTemp(float temp) {
            this.temp = temp;
        }
    }

    public  class CurrentCondition {
        private String icon;

        public String getIcon() {
            return icon;
        }
        public void setIcon(String icon) {
            this.icon = icon;
        }
    }
}