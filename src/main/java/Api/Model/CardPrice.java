package Api.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CardPrice {

    @SerializedName("cardmarket_price")
    @Expose
    private String cardmarketPrice;
    @SerializedName("tcgplayer_price")
    @Expose
    private String tcgplayerPrice;
    @SerializedName("ebay_price")
    @Expose
    private String ebayPrice;
    @SerializedName("amazon_price")
    @Expose
    private String amazonPrice;
    @SerializedName("coolstuffinc_price")
    @Expose
    private String coolstuffincPrice;

    /**
     * No args constructor for use in serialization
     */
    public CardPrice() {
    }

    /**
     * @param ebayPrice
     * @param tcgplayerPrice
     * @param coolstuffincPrice
     * @param amazonPrice
     * @param cardmarketPrice
     */
    public CardPrice(String cardmarketPrice, String tcgplayerPrice, String ebayPrice, String amazonPrice, String coolstuffincPrice) {
        super();
        this.cardmarketPrice = cardmarketPrice;
        this.tcgplayerPrice = tcgplayerPrice;
        this.ebayPrice = ebayPrice;
        this.amazonPrice = amazonPrice;
        this.coolstuffincPrice = coolstuffincPrice;
    }

    public String getCardmarketPrice() {
        return cardmarketPrice;
    }

    public void setCardmarketPrice(String cardmarketPrice) {
        this.cardmarketPrice = cardmarketPrice;
    }

    public String getTcgplayerPrice() {
        return tcgplayerPrice;
    }

    public void setTcgplayerPrice(String tcgplayerPrice) {
        this.tcgplayerPrice = tcgplayerPrice;
    }

    public String getEbayPrice() {
        return ebayPrice;
    }

    public void setEbayPrice(String ebayPrice) {
        this.ebayPrice = ebayPrice;
    }

    public String getAmazonPrice() {
        return amazonPrice;
    }

    public void setAmazonPrice(String amazonPrice) {
        this.amazonPrice = amazonPrice;
    }

    public String getCoolstuffincPrice() {
        return coolstuffincPrice;
    }

    public void setCoolstuffincPrice(String coolstuffincPrice) {
        this.coolstuffincPrice = coolstuffincPrice;
    }

    @Override
    public String toString() {
        return "CardPrice{" +
                "cardmarketPrice='" + cardmarketPrice + '\'' +
                ", tcgplayerPrice='" + tcgplayerPrice + '\'' +
                ", ebayPrice='" + ebayPrice + '\'' +
                ", amazonPrice='" + amazonPrice + '\'' +
                ", coolstuffincPrice='" + coolstuffincPrice + '\'' +
                '}';
    }
}