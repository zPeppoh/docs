package it.zpeppoh.docs.utils;

import org.bukkit.ChatColor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class utils {
    public static String cc(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static String formatDate(LocalDateTime ldt) {
        return ldt.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
}
