package Api.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Card {


    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("frameType")
    @Expose
    private String frameType;
    @SerializedName("desc")
    @Expose
    private String desc;
    @SerializedName("pend_desc")
    @Expose
    private String pendDesc;
    @SerializedName("monster_desc")
    @Expose
    private String monsterDesc;
    @SerializedName("atk")
    @Expose
    private Integer atk;
    @SerializedName("def")
    @Expose
    private Integer def;
    @SerializedName("level")
    @Expose
    private Integer level;
    @SerializedName("race")
    @Expose
    private String race;
    @SerializedName("attribute")
    @Expose
    private String attribute;
    @SerializedName("archetype")
    @Expose
    private String archetype;
    @SerializedName("linkval")
    @Expose
    private Integer linkval;
    @SerializedName("linkmarkers")
    @Expose
    private List<String> linkmarkers;
    @SerializedName("scale")
    @Expose
    private Integer scale;
    @SerializedName("card_sets")
    @Expose
    private List<CardSet> cardSets;
    @SerializedName("card_images")
    @Expose
    private List<CardImage> cardImages;
    @SerializedName("card_prices")
    @Expose
    private List<CardPrice> cardPrices;

    /**
     * No args constructor for use in serialization
     */
    public Card() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFrameType() {
        return frameType;
    }

    public void setFrameType(String frameType) {
        this.frameType = frameType;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPendDesc() {
        return pendDesc;
    }

    public void setPendDesc(String pendDesc) {
        this.pendDesc = pendDesc;
    }

    public String getMonsterDesc() {
        return monsterDesc;
    }

    public void setMonsterDesc(String monsterDesc) {
        this.monsterDesc = monsterDesc;
    }

    public Integer getAtk() {
        return atk;
    }

    public void setAtk(Integer atk) {
        this.atk = atk;
    }

    public Integer getDef() {
        return def;
    }

    public void setDef(Integer def) {
        this.def = def;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getArchetype() {
        return archetype;
    }

    public void setArchetype(String archetype) {
        this.archetype = archetype;
    }

    public Integer getLinkval() {
        return linkval;
    }

    public void setLinkval(Integer linkval) {
        this.linkval = linkval;
    }

    public List<String> getLinkmarkers() {
        return linkmarkers;
    }

    public void setLinkmarkers(List<String> linkmarkers) {
        this.linkmarkers = linkmarkers;
    }

    public Integer getScale() {
        return scale;
    }

    public void setScale(Integer scale) {
        this.scale = scale;
    }

    public List<CardSet> getCardSets() {
        return cardSets;
    }

    public void setCardSets(List<CardSet> cardSets) {
        this.cardSets = cardSets;
    }

    public List<CardImage> getCardImages() {
        return cardImages;
    }

    public void setCardImages(List<CardImage> cardImages) {
        this.cardImages = cardImages;
    }

    public List<CardPrice> getCardPrices() { return cardPrices; }

    public void setCardPrices(List<CardPrice> cardPrices) { this.cardPrices = cardPrices; }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", frameType='" + frameType + '\'' +
                ", desc='" + desc + '\'' +
                ", pendDesc='" + pendDesc + '\'' +
                ", monsterDesc='" + monsterDesc + '\'' +
                ", atk=" + atk +
                ", def=" + def +
                ", level=" + level +
                ", race='" + race + '\'' +
                ", attribute='" + attribute + '\'' +
                ", archetype='" + archetype + '\'' +
                ", linkval=" + linkval +
                ", linkmarkers=" + linkmarkers +
                ", scale=" + scale +
                ", cardSets=" + cardSets +
                ", cardImages=" + cardImages +
                ", cardPrices=" + cardPrices +
                '}';


    }
}