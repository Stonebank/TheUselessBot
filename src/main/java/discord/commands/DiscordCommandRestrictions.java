package discord.commands;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface DiscordCommandRestrictions {

    /* the String array that allows only specific unique ID(s) to use the command */
    String[] discord_id() default "-1";

    /* the boolean that disables the command if true */
    boolean command_disabled() default false;

}
