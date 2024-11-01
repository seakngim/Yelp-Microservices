package kh.edu.cstad.business.domain;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.List;

public class Business {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(unique = true, nullable = false)
    private String alias;

    @Column(unique = true, nullable = false)
    private String name;

    private String image_url;
    private  Boolean is_closed;
    private String url;
    private Integer review_count;

    private List<Category> categories;
    private String rating;

    public static class Coordinates {
        @Column (unique = true, nullable = false)
        private String latitude;
        @Column (unique = true, nullable = false)
        private String longitude;
    }

    private List<Transaction> transactions; // supported values are pickup, delivery and restaurant_reservation.

    private String price;

    public static class Location{
        private String adress1;
        private String address2;
        private String getAdress3;
        private String city;
        private String zip_code;
        private String country;
        private String state;

        @Column (unique = true, nullable = false)
        private List<County> display_address;

        private String cross_streets;
    }


    @Column (unique = true, nullable = false)
    private String phone;

    @Column (unique = true, nullable = false)
    private String display_phone;

    private String distance;

    @Data
    public static class Attributes {
        private boolean likedByVegetarians;
        private boolean likedByVegans;
        private boolean hotAndNew;
    }


    @Column (unique = true, nullable = false)
    private Boolean is_claimed;

    private String date_opened;
    private String date_closed;
    private List<String> photo;

    @Data
    public static class SpecialHours {
        @Column (unique = true, nullable = false)
        private String date;
        private String start;
        private String end;
        private Boolean isOvernight;
        private Boolean isClosed;
    }

    @Data
    public static class Messaging {

        @Column (unique = true, nullable = false)
        private String url;

        @Column (unique = true, nullable = false)
        private String phone;

        @Column (unique = true, nullable = false)
        private String display_phone;
        private String useCaseText;
        private String responseRate;
        private Integer responseTime;
        private Boolean isEnabled;
    }

    @Data
    public static class PhotoDetails {
        private String photoId;
        private String url;
        private String caption;
        private Integer width;
        private Integer height;
        private Boolean isUserSubmitted;
        private String userId;
        private String label;
    }

    private String yelp_menu_url;
    private String cbsa;
    @Data
    public static class PopularityScore {
        private String primaryCategory;
        private String score;
    }

    @Data
    public static class RAPC {
        private boolean isEnabled;
        private boolean isEligible;
    }
    @Data
    public static class Hours {
        @Column (unique = true, nullable = false)
        private String hourType;
        @Column (unique = true, nullable = false)
        private List<OpeningHour> open;

        @Column (unique = true, nullable = false)
        private Boolean is_open_now;
    }
}
