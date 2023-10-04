package Api.Model;


import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;


/**
 *
 */
public enum CardParameter {
    NAME(OptionType.STRING,"Name or partial name of a card"),
    TYPE(OptionType.STRING, "Type of the card, you can enter multiple types separated with commas, e.g. (xyz monster,spell card)"),
    ATK(OptionType.STRING, "Attack of a monster, use <,>,<=,>= to indicate greater than or less than, respectively, e.g. >=3000"),
    DEF(OptionType.STRING, "Defence of a monster, use <,>,<=,>= to indicate greater than or less than, respectively, e.g. >=3000"),
    LEVEl(OptionType.STRING, "Level of a monster, use <,>,<=,>= to indicate greater than or less than, respectively, e.g. >=4"),
    RACE(OptionType.STRING, "Type of a monster. You can enter multiple types separated with commas, e.g. (Spellcaster,Warrior)"),
    ATTRIBUTE(OptionType.STRING, "Attribute of a monster. You can enter multiple attributes separated with commas, e.g. " +
            "Light,Dark"),
    LINK(OptionType.INTEGER, "Link value of a monster"),
    LINKMARKER(OptionType.STRING, "Link markers of a monster. You can enter multiple markers separated with commas, e.g. top-left,left"),
    SCALE(OptionType.STRING, "Scale rating of a monster."),
    CARDSET(OptionType.STRING, "Card set of a card, e.g. Wild Survivors OR Metal Raiders"),
    //BANLIST("Find cards that are on a ban list, e.g. TCG OR OCG OR Goat"), TODO: Decide whether or not to include an option to filter on ban list (Ygoprodeck API's way of handling the ban list is not pretty).
    ARCHETYPE(OptionType.STRING, "Name of the archetype of a card, e.g. Blue-Eyes OR Dark magician OR Prank-Kids");


    public final OptionType type;
    public final String description;

    CardParameter(OptionType type, String description) {
        this.type = type;
        this.description = description;
    }

    /**
     * Overrides default toString to return enums lowercase
     * @return Enum name but lowercase
     */
    public OptionData toOption() {
        return new OptionData(type, name().toLowerCase(), description);
    }


}
