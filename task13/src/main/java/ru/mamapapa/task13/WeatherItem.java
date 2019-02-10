package ru.mamapapa.task13;

/**
 * Информация о погоде
 *
 * @author Popov Maxim <m_amapapa@mail.ru>
 */
public class WeatherItem {
    private String mainText;
    private String tempFirst;
    private String tempSecond;

    public String getMainText() {
        return mainText;
    }

    public void setMainText(String mainText) {
        this.mainText = mainText;
    }

    public String getTempFirst() {
        return tempFirst;
    }

    public void setTempFirst(String tempFirst) {
        this.tempFirst = tempFirst;
    }

    public String getTempSecond() {
        return tempSecond;
    }

    public void setTempSecond(String tempSecond) {
        this.tempSecond = tempSecond;
    }
}
