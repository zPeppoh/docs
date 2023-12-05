package it.zpeppoh.docs.commands;

import com.google.common.collect.Lists;
import it.zpeppoh.docs.Docs;
import it.zpeppoh.docs.enums.Enum;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import it.zpeppoh.docs.utils.Utils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static it.zpeppoh.docs.utils.Utils.cc;

public class DocCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player target;
        String veicolo;
        int punti;

        ItemStack libro = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta meta = (BookMeta)libro.getItemMeta();

        Player p = (Player) sender;

        if (!p.hasPermission(Docs.getInstance().getConfig().getString("permissions.emetti"))) {
            p.sendMessage(Utils.cc("&cNon hai il permesso!"));
            return true;
        }

        if (args.length < 3) {
            p.sendMessage(Utils.cc("&cUtilizzo Corretto: /doc <patente/libretto/assicurazione> <player> <nomeveicolo/puntipatente>"));
            return true;
        }

        target = Bukkit.getPlayer(args[1]);
        veicolo = args[2];
        punti = Integer.parseInt(args[2]);

        Enum.DocumentType documentType;
        try {
            documentType = Enum.DocumentType.valueOf(args[0].toUpperCase());
        } catch (IllegalArgumentException e) {
            p.sendMessage(Utils.cc("&cUtilizzo corretto: /doc <patente/libretto/assicurazione> <player> <nomeveicolo/puntipatente>"));
            return true;
        }

        switch (documentType) {
            case PATENTE:
                handlePatente(p, target, punti, meta, libro);
                break;
            case LIBRETTO:
                handleLibretto(p, target, veicolo, meta, libro);
                break;
            case ASSICURAZIONE:
                handleAssicurazione(p, target, veicolo, meta, libro);
                break;
        }
        return false;
    }

    private void handlePatente(Player p, Player target, int punti, BookMeta meta, ItemStack libro) {
        if (p.hasPermission(Docs.getInstance().getConfig().getString("permissions.patente"))) {
            if (target.isOnline()) {
                p.sendMessage(Utils.cc("&aHai rilasciato la patente a " + target.getName().toString() + " con successo."));
                target.sendMessage(Utils.cc("&aHai ricevuto la patente"));
                meta.setTitle(cc("&aPatente di &2" + target.getName()));
                meta.setAuthor(cc("&2Scuola Guida"));
                meta.setPages(cc("&8-------------------\n&2        Patente\n&8-------------------\n\n&2Rilasciata a: &a\n " + target.getName() + "\n\n &2Punti: &a\n " + punti + "\n\n&2Data: &a\n " + Utils.formatDate(LocalDateTime.now())));
                meta.addPages(Component.text("\n\nNon smarrire questo documento o potresti essere multato.").color(TextColor.color(255, 20, 0)));
                libro.setItemMeta(meta);
                p.getInventory().addItem(libro);
            } else {
                p.sendMessage(Utils.cc("&cIl giocatore non è online"));
            }
        } else {
            p.sendMessage(Utils.cc("&cNon hai il permesso!"));
        }
    }

    private void handleLibretto(Player p, Player target, String veicolo, BookMeta meta, ItemStack libro) {
        if (p.hasPermission(Docs.getInstance().getConfig().getString("permissions.libretto"))) {
            if (target.isOnline()) {
                p.sendMessage(Utils.cc("&aHai creato un libretto a " + target.getName().toString() + " con successo."));
                target.sendMessage(Utils.cc("&aHai ricevuto il libretto"));
                meta.setTitle(cc("&aLibretto di &2" + target.getName()));
                meta.setAuthor(cc("&2Concessionaria"));
                meta.setPages(cc("&8-------------------\n&2        Libretto\n&8-------------------\n\n&2Rilasciato a: &a\n " + target.getName() + "\n\n&2Veicolo: &a\n " + veicolo + "\n\n&2Data: &a\n " + Utils.formatDate(LocalDateTime.now())));
                meta.addPages(Component.text("\n\nNon smarrire questo libro o potresti essere multato.").color(TextColor.color(255, 20, 0)));
                libro.setItemMeta(meta);
                p.getInventory().addItem(libro);
            } else {
                p.sendMessage(Utils.cc("&cIl giocatore non è online"));
            }
        } else {
            p.sendMessage(Utils.cc("&cNon hai il permesso!"));
        }
    }

    private void handleAssicurazione(Player p, Player target, String veicolo, BookMeta meta, ItemStack libro) {
        if (p.hasPermission(Docs.getInstance().getConfig().getString("permissions.assicurazione"))) {
            if (target.isOnline()) {
                p.sendMessage(Utils.cc("&aHai creato un'assicurazione a " + target.getName().toString() + " con successo."));
                target.sendMessage(Utils.cc("&aHai ricevuto l'assicurazione"));
                meta.setTitle(cc("&aAssicurazione di &2" + target.getName()));
                meta.setAuthor(cc("&2Concessionaria"));
                meta.setPages(cc("&8-------------------\n&2        Assicurazione\n&8-------------------\n\n&2Rilasciata a: &a\n " + target.getName() + "\n\n&2Veicolo: &a\n " + veicolo + "\n\n&2Data: &a\n " + Utils.formatDate(LocalDateTime.now())));
                meta.addPages(Component.text("\n\nNon smarrire questo libro o potresti essere multato.").color(TextColor.color(255, 20, 0)));
                libro.setItemMeta(meta);
                p.getInventory().addItem(libro);
            } else {
                p.sendMessage(Utils.cc("&cIl giocatore non è online"));
            }
        } else {
            p.sendMessage(Utils.cc("&cNon hai il permesso!"));
        }
    }

    @Nullable
    public List<String> tabCompleter(@NotNull CommandSender sender, @NotNull List<String> args) {
        if (args.size() == 1)
            return Lists.newArrayList("patente", "libretto", "assicurazione");
        return Lists.newArrayList();
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            completions.add("patente");
            completions.add("libretto");
            completions.add("assicurazione");
        } else if (args.length == 3) {
            if (args[0].contains("patente")) {
                completions.add("30");
            } else {
                completions.add("NomeVeicolo");
            }
        } else {
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                completions.add(onlinePlayer.getName());
            }
        }
        return completions;
    }
}
