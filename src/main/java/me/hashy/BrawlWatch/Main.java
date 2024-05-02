package me.hashy.BrawlWatch;

import brawljars.BrawlJars;
import io.github.cdimascio.dotenv.Dotenv;
import me.hashy.BrawlWatch.utils.Weight;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import supercell.api.wrapper.essentials.connector.Connector;
import supercell.api.wrapper.essentials.connector.StandardConnector;

public class Main {

    private static JDA jda; 
    private static Dotenv dotenv = Dotenv.load();
    private static Connector connector = new StandardConnector();
    private static BrawlJars brawlJars = new BrawlJars("https://bsproxy.royaleapi.dev/v1", dotenv.get("BRAWLSTARS_API"), connector);

    public static void main(String[] args) throws InterruptedException {
        jda = JDABuilder.createLight(dotenv.get("DISCORD_API")).build();
        jda.awaitReady();
        System.out.println("Imposta Sus\'s weight: " + Weight.getWeight("#YU22LP88L"));
        System.out.println("Hashy\'s weight: " + Weight.getWeight("#LCVUJYRV9"));

    }

    public static JDA getJDA() {
        return jda;
    }

    public static BrawlJars getBrawlJars() {
        return brawlJars;
    }

}
