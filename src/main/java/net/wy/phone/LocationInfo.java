package net.wy.phone;

/**
 * @author WY
 */
public class LocationInfo {

    /**
     * 号码
     */
    private String phoneNumber;
    /**
     * 省份
     */
    private String province;
    /**
     * 城市
     */
    private String city;
    /**
     * 运营商
     */
    private String operator;
    /**
     * 省份地域编码
     */
    private String provinceAdCode;
    /**
     * 省份中心坐标
     */
    private String provinceCenter;
    /**
     * 城市编码
     */
    private String cityCode;
    /**
     * 城市地域编码
     */
    private String cityAdCode;
    /**
     * 城市中心坐标
     */
    private String cityCenter;
    /**
     * 省份国际ISO编码
     */
    private String isoCode;

    @Override
    public String toString() {
        return "LocationInfo{" +
                "phoneNumber='" + phoneNumber + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", operator='" + operator + '\'' +
                ", provinceAdCode='" + provinceAdCode + '\'' +
                ", provinceCenter='" + provinceCenter + '\'' +
                ", cityCode='" + cityCode + '\'' +
                ", cityAdCode='" + cityAdCode + '\'' +
                ", cityCenter='" + cityCenter + '\'' +
                ", isoCode='" + isoCode + '\'' +
                '}';
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getProvinceAdCode() {
        return provinceAdCode;
    }

    public void setProvinceAdCode(String provinceAdCode) {
        this.provinceAdCode = provinceAdCode;
    }

    public String getProvinceCenter() {
        return provinceCenter;
    }

    public void setProvinceCenter(String provinceCenter) {
        this.provinceCenter = provinceCenter;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityAdCode() {
        return cityAdCode;
    }

    public void setCityAdCode(String cityAdCode) {
        this.cityAdCode = cityAdCode;
    }

    public String getCityCenter() {
        return cityCenter;
    }

    public void setCityCenter(String cityCenter) {
        this.cityCenter = cityCenter;
    }

    public String getIsoCode() {
        return isoCode;
    }

    public void setIsoCode(String isoCode) {
        this.isoCode = isoCode;
    }
}
