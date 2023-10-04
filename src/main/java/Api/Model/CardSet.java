package Api.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CardSet {


    @SerializedName("set_name")
    @Expose
    private String setName;
    @SerializedName("set_code")
    @Expose
    private String setCode;
    @SerializedName("set_rarity")
    @Expose
    private String setRarity;
    @SerializedName("set_rarity_code")
    @Expose
    private String setRarityCode;
    @SerializedName("set_price")
    @Expose
    private String setPrice;

    /**
     * No args constructor for use in serialization
     */
    public CardSet() {
    }

    /**
     * @param setName
     * @param setCode
     * @param setRarity
     * @param setPrice
     * @param setRarityCode
     */
    public CardSet(String setName, String setCode, String setRarity, String setRarityCode, String setPrice) {
        super();
        this.setName = setName;
        this.setCode = setCode;
        this.setRarity = setRarity;
        this.setRarityCode = setRarityCode;
        this.setPrice = setPrice;
    }

    public String getSetName() {
        return setName;
    }

    public void setSetName(String setName) {
        this.setName = setName;
    }

    public String getSetCode() {
        return setCode;
    }

    public void setSetCode(String setCode) {
        this.setCode = setCode;
    }

    public String getSetRarity() {
        return setRarity;
    }

    public void setSetRarity(String setRarity) {
        this.setRarity = setRarity;
    }

    public String getSetRarityCode() {
        return setRarityCode;
    }

    public void setSetRarityCode(String setRarityCode) {
        this.setRarityCode = setRarityCode;
    }

    public String getSetPrice() {
        return setPrice;
    }

    public void setSetPrice(String setPrice) {
        this.setPrice = setPrice;
    }

    @Override
    public String toString() {
        return "CardSet{" +
                "setName='" + setName + '\'' +
                ", setCode='" + setCode + '\'' +
                ", setRarity='" + setRarity + '\'' +
                ", setRarityCode='" + setRarityCode + '\'' +
                ", setPrice='" + setPrice + '\'' +
                '}';
    }
}