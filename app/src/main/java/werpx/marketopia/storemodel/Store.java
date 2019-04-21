package werpx.marketopia.storemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Store {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private Object email;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("website")
    @Expose
    private String website;
    @SerializedName("open_time")
    @Expose
    private String openTime;
    @SerializedName("close_time")
    @Expose
    private String closeTime;
    @SerializedName("open_delivery")
    @Expose
    private String openDelivery;
    @SerializedName("close_delivery")
    @Expose
    private String closeDelivery;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("description")
    @Expose
    private Object description;
    @SerializedName("image_id")
    @Expose
    private String imageId;
    @SerializedName("active")
    @Expose
    private Integer active;
    @SerializedName("del_charge")
    @Expose
    private String delCharge;
    @SerializedName("delivery_time")
    @Expose
    private Integer deliveryTime;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("latitude")
    @Expose
    private Double latitude;
    @SerializedName("longitude")
    @Expose
    private Double longitude;
    @SerializedName("placeId")
    @Expose
    private Object placeId;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("producers")
    @Expose
    private List<Object> producers = null;
    @SerializedName("image")
    @Expose
    private Image image;
    @SerializedName("distributors")
    @Expose
    private List<Object> distributors = null;
    @SerializedName("subdistributors")
    @Expose
    private List<Object> subdistributors = null;
    @SerializedName("retailers")
    @Expose
    private List<Object> retailers = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getEmail() {
        return email;
    }

    public void setEmail(Object email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
    }

    public String getOpenDelivery() {
        return openDelivery;
    }

    public void setOpenDelivery(String openDelivery) {
        this.openDelivery = openDelivery;
    }

    public String getCloseDelivery() {
        return closeDelivery;
    }

    public void setCloseDelivery(String closeDelivery) {
        this.closeDelivery = closeDelivery;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Object getDescription() {
        return description;
    }

    public void setDescription(Object description) {
        this.description = description;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDelCharge() {
        return delCharge;
    }

    public void setDelCharge(String delCharge) {
        this.delCharge = delCharge;
    }

    public Integer getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Integer deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Object getPlaceId() {
        return placeId;
    }

    public void setPlaceId(Object placeId) {
        this.placeId = placeId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<Object> getProducers() {
        return producers;
    }

    public void setProducers(List<Object> producers) {
        this.producers = producers;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public List<Object> getDistributors() {
        return distributors;
    }

    public void setDistributors(List<Object> distributors) {
        this.distributors = distributors;
    }

    public List<Object> getSubdistributors() {
        return subdistributors;
    }

    public void setSubdistributors(List<Object> subdistributors) {
        this.subdistributors = subdistributors;
    }

    public List<Object> getRetailers() {
        return retailers;
    }

    public void setRetailers(List<Object> retailers) {
        this.retailers = retailers;
    }

}