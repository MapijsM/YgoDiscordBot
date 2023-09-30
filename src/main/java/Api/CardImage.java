package Api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CardImage {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("image_url")
    @Expose
    private String imageUrl;
    @SerializedName("image_url_small")
    @Expose
    private String imageUrlSmall;
    @SerializedName("image_url_cropped")
    @Expose
    private String imageUrlCropped;

    /**
     * No args constructor for use in serialization
     */
    public CardImage() {
    }

    /**
     * @param imageUrl
     * @param imageUrlCropped
     * @param id
     * @param imageUrlSmall
     */
    public CardImage(Integer id, String imageUrl, String imageUrlSmall, String imageUrlCropped) {
        super();
        this.id = id;
        this.imageUrl = imageUrl;
        this.imageUrlSmall = imageUrlSmall;
        this.imageUrlCropped = imageUrlCropped;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrlSmall() {
        return imageUrlSmall;
    }

    public void setImageUrlSmall(String imageUrlSmall) {
        this.imageUrlSmall = imageUrlSmall;
    }

    public String getImageUrlCropped() {
        return imageUrlCropped;
    }

    public void setImageUrlCropped(String imageUrlCropped) {
        this.imageUrlCropped = imageUrlCropped;
    }

}