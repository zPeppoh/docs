package it.zpeppoh.docs.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import it.zpeppoh.docs.utils.utils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDateTime;
import java.util.List;

import static it.zpeppoh.docs.utils.utils.cc;

public class docCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player target;
        String veicolo;
        int punti;

        ItemStack libro = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta meta = (BookMeta)libro.getItemMeta();

        Player p = (Player) sender;

        if (!p.hasPermission("docs.emetti")) {
            p.sendMessage(utils.cc("&cNon hai il permesso!"));
            return true;
        }

        if (args.length < 3) {
            p.sendMessage(utils.cc("&cUtilizzo Corretto: /doc <patente/libretto/assicurazione> <player> <nomeveicolo/puntipatente>"));
            return true;
        }

        target = Bukkit.getPlayer(args[1]);
        veicolo = args[2];
        punti = Integer.parseInt(args[2]);

        switch (args[0].toLowerCase()) {
            case "patente":
                if (p.hasPermission("doc.emetti.patente")) {
                    if (target.isOnline()) {
                        p.sendMessage(utils.cc("&aHai rilasciato la patente a " + target.getName().toString() + " con successo."));
                        target.sendMessage(utils.cc("&aHai ricevuto la patente"));
                        meta.setTitle(cc("&aPatente di &2" + target.getName()));
                        meta.setAuthor(cc("&2Scuola Guida"));
                        meta.setPages(cc("&8-------------------\n&2        Patente\n&8-------------------\n\n&2Rilasciata a: &a\n " + target.getName() + "\n\n &2Punti: &a\n " + punti + "\n\n&2Data: &a\n " + utils.formatDate(LocalDateTime.now())));
                        meta.addPages(Component.text("\n\nNon smarrire questo documento o potresti essere multato.").color(TextColor.color(255, 20, 0)));
                        libro.setItemMeta(meta);
                        p.getInventory().addItem(libro);
                    } else {
                        p.sendMessage(utils.cc("&cIl giocatore non è online"));
                    }
                } else {
                    p.sendMessage(utils.cc("&cNon hai il permesso!"));
                } break;
            case "libretto":
                if (p.hasPermission("doc.emetti.libretto")) {
                    if (target.isOnline()) {
                        p.sendMessage(utils.cc("&aHai creato un libretto a " + target.getName().toString() + " con successo."));
                        target.sendMessage(utils.cc("&aHai ricevuto il libretto"));
                        meta.setTitle(cc("&aLibretto di &2" + target.getName()));
                        meta.setAuthor(cc("&2Concessionaria"));
                        meta.setPages(cc("&8-------------------\n&2        Libretto\n&8-------------------\n\n&2Rilasciato a: &a\n " + target.getName() + "\n\n&2Veicolo: &a\n " + veicolo + "\n\n&2Data: &a\n " + utils.formatDate(LocalDateTime.now())));
                        meta.addPages(Component.text("\n\nNon smarrire questo libro o potresti essere multato.").color(TextColor.color(255, 20, 0)));
                        libro.setItemMeta(meta);
                        p.getInventory().addItem(libro);
                    } else {
                        p.sendMessage(utils.cc("&cIl giocatore non è online"));
                    }
                } else {
                    p.sendMessage(utils.cc("&cNon hai il permesso!"));
                } break;
            case "assicurazione":
                if (p.hasPermission("doc.emetti.assicurazione")) {
                    if (target.isOnline()) {
                        p.sendMessage(utils.cc("&aHai creato un'assicurazione a " + target.getName().toString() + " con successo."));
                        target.sendMessage(utils.cc("&aHai ricevuto l'assicurazione"));
                        meta.setTitle(cc("&aAssicurazione di &2" + target.getName()));
                        meta.setAuthor(cc("&2Concessionaria"));
                        meta.setPages(cc("&8-------------------\n&2        Assicurazione\n&8-------------------\n\n&2Rilasciata a: &a\n " + target.getName() + "\n\n&2Veicolo: &a\n " + veicolo + "\n\n&2Data: &a\n " + utils.formatDate(LocalDateTime.now())));
                        meta.addPages(Component.text("\n\nNon smarrire questo libro o potresti essere multato.").color(TextColor.color(255, 20, 0)));
                        libro.setItemMeta(meta);
                        p.getInventory().addItem(libro);
                    } else {
                        p.sendMessage(utils.cc("&cIl giocatore non è online"));
                    }
                } else {
                    p.sendMessage(utils.cc("&cNon hai il permesso!"));
                } break;
            default:
                p.sendMessage(utils.cc("&cUtilizzo Corretto: /doc <patente/libretto/assicurazione> <player> <nomeveicolo/puntipatente>"));
                return true;
        }
        return false;
    }

    @Nullable
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        return null;
    }
}