package discord.minigame.daily;

import lombok.Getter;

import java.io.File;

@Getter
public enum DailyQuestion {

    BOND("How long does a bond last?", null, "14 days"),
    VERAC("Which Barrows Brother hits through prayer?", null, "Verac", "Veracs", "Verac's"),
    ABYSS("Who do you speak to in order to access The Abyss?", null, "The mage of Zamorak"),
    BOWSTRING("Which skill allows you to make Bowstrings?", null, "Crafting"),
    MONKEY_MADNESS("What weapon can be wielded as a reward from monkey madness?", null, "Dragon scimitar"),
    LOBSTER_HEAL("How much does a Lobster maximum heal?", null, "14"),
    AHRIM("Which Barrows brother is known to use the Mage combat?", null, "Ahrim", "Ahrims"),
    BARROWS_BROTHERS("How many Barrows Brothers are there?", null, "6", "six"),
    TIRANWN("What is the province that contains all the elves, including Isafdar, Prifddinas, and Lletya?", null, "Tirannwn"),
    VANNAKA("Which slayer master requires a minimum of combat level 40 to acquire slayer tasks from?", null, "Vannaka"),
    TORSTOL("This herb is used in many high level Herblore potions including the super combat potion and anti-venom+. What is it?", null, "Torstol"),
    STAMINA_POTION("This is the secondary ingredient to create a stamina potion. What is it?", null, "Amylase crystal"),
    THE_WEDGE("He is the person to talk to in order to receive the rewards from the Kandarin Diary.", null, "The wedge"),
    ASHIHAMA("What is the boss called in the city 'Ashihama'", null, "THe nightmare", "The nightmare of Ashihama"),

    LUMBRIDGE_TASK_MASTER("What is the NPC in this picture called?", new File("./data/discord/assets/image/trivia/Hatius Cosaintus.png"), "Hatius Cosaintus"),
    TWO_PINTS("What is the NPC in this picture called?", new File("./data/discord/assets/image/trivia/Two-pints.png"), "Two-pints"),
    ZULRAH("What is the player fighting in this picture?", new File("./data/discord/assets/image/trivia/zulrah.png"), "Zulrah", "Snake monster"),
    GOD_WARS_DUNGEON("What place does this entrance lead to?", new File("./data/discord/assets/image/trivia/God Wars.png"), "God wars", "God wars dungeon"),
    VENENATIS("What is this boss called?", new File("./data/discord/assets/image/trivia/Venenatis.png"), "Venenatis"),

    ;

    private final String question;
    private final String[] answer;

    private final File picture;

    DailyQuestion(String question, File picture, String... answer) {
        this.question = question;
        this.picture = picture;
        this.answer = answer;
    }

    public boolean hasPicture() {
        return picture.exists();
    }

}